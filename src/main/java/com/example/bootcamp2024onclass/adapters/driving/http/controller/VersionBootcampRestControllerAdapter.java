package com.example.bootcamp2024onclass.adapters.driving.http.controller;

import com.example.bootcamp2024onclass.adapters.driving.http.dto.reponse.BootcampResponse;
import com.example.bootcamp2024onclass.adapters.driving.http.dto.reponse.VersionBootcampResponse;
import com.example.bootcamp2024onclass.adapters.driving.http.dto.request.AddVersionBootcampRequest;
import com.example.bootcamp2024onclass.adapters.driving.http.mapper.IVersionBootcampRequestMapper;
import com.example.bootcamp2024onclass.adapters.driving.http.mapper.IVersionBootcampResponseMapper;
import com.example.bootcamp2024onclass.domain.api.IVersionBootcampServicePort;
import com.example.bootcamp2024onclass.domain.model.VersionBootcamp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/versionBootcamp")
@RequiredArgsConstructor
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
            @ApiResponse(responseCode = "404", description = "Bootcamp not found", content = @Content)
    })
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
                            schema = @Schema(implementation = VersionBootcampResponse.class))  })})
    @GetMapping("/")
    public ResponseEntity<List<VersionBootcampResponse>> getAllVersionBootcamps(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String isOrderBy,
            @RequestParam(required = false, defaultValue = "true") boolean isAscending,
            @RequestParam(required = false) String bootcampName)
    {
        return ResponseEntity.ok(versionBootcampResponseMapper.toVersionBootcampResponseList(
                versionBootcampServicePort.getAllVersionBootcamps(page, size, isOrderBy, isAscending, bootcampName)
        ));
    }

}
