package com.example.bootcamp2024onclass.adapters.driving.http.controller;

import com.example.bootcamp2024onclass.adapters.driving.http.dto.reponse.BootcampResponse;
import com.example.bootcamp2024onclass.adapters.driving.http.dto.reponse.VersionBootcampResponse;
import com.example.bootcamp2024onclass.adapters.driving.http.dto.request.AddVersionBootcampRequest;
import com.example.bootcamp2024onclass.adapters.driving.http.mapper.IVersionBootcampRequestMapper;
import com.example.bootcamp2024onclass.adapters.driving.http.mapper.IVersionBootcampResponseMapper;
import com.example.bootcamp2024onclass.domain.api.IVersionBootcampServicePort;
import com.example.bootcamp2024onclass.domain.model.CustomPage;
import com.example.bootcamp2024onclass.domain.model.PaginationCriteria;
import com.example.bootcamp2024onclass.domain.model.VersionBootcamp;
import com.example.bootcamp2024onclass.domain.util.SortDirection;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/versionBootcamp")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class VersionBootcampRestControllerAdapter {
    private final IVersionBootcampServicePort versionBootcampServicePort;
    private final IVersionBootcampRequestMapper versionBootcampRequestMapper;
    private final IVersionBootcampResponseMapper versionBootcampResponseMapper;

    @Operation(summary = "Create a new Version Bootcamp")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Correct create a new Version Bootcamp",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BootcampResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Version Date Bootcamp is already in use or the start date is before the current date or the start date is after the end date or fields are invalid", content = @Content),
            @ApiResponse(responseCode = "404", description = "Bootcamp not found", content = @Content),
            @ApiResponse(responseCode = "401", ref = "#/components/responses/UnauthorizedError")
    })
    @PreAuthorize("hasAuthority('ADMINISTRATOR') or hasAuthority('TUTOR')")
    @PostMapping("/")
    public ResponseEntity<VersionBootcampResponse> addVersionBootcamp(@Valid @RequestBody AddVersionBootcampRequest request) {
        VersionBootcamp versionBootcamp = versionBootcampRequestMapper.addRequestToVersionBootcamp(request);
        versionBootcamp = versionBootcampServicePort.saveVersionBootcamp(versionBootcamp);
        VersionBootcampResponse response = versionBootcampResponseMapper.toVersionBootcampResponse(versionBootcamp);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get Version Bootcamps with pagination, order by name, maximumQuota, startDate or found by bootcamp name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Correct get Version Bootcamps",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = VersionBootcampResponse.class))  }),
            @ApiResponse(responseCode = "401", ref = "#/components/responses/UnauthorizedError")
    })
    @PreAuthorize("hasAuthority('ADMINISTRATOR') or hasAuthority('TUTOR')")
    @GetMapping("/")
    public ResponseEntity<CustomPage<VersionBootcampResponse>> getAllVersionBootcamps(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false, defaultValue = "true") boolean isOrderByStartDate,
            @RequestParam(required = false, defaultValue = "true") boolean isAscending,
            @RequestParam(required = false) String bootcampName) {

        PaginationCriteria criteria = new PaginationCriteria(page, size, isAscending ? SortDirection.ASC : SortDirection.DESC, isOrderByStartDate ? "startDate" : "maximumQuota");
        CustomPage<VersionBootcamp> versionBootcamps = versionBootcampServicePort.getAllVersionBootcamps(criteria, bootcampName);
        CustomPage<VersionBootcampResponse> responses = new CustomPage<>(
                versionBootcamps.getContent().stream().map(versionBootcampResponseMapper::toVersionBootcampResponse).toList(),
                versionBootcamps.getPageNumber(), versionBootcamps.getPageSize(), versionBootcamps.getTotalElements(), versionBootcamps.getTotalPages()
        );
        return ResponseEntity.ok(responses);
    }
}
