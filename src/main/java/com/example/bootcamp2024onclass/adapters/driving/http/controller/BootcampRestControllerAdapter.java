package com.example.bootcamp2024onclass.adapters.driving.http.controller;

import com.example.bootcamp2024onclass.adapters.driving.http.dto.reponse.BootcampResponse;
import com.example.bootcamp2024onclass.adapters.driving.http.dto.request.AddBootcampRequest;
import com.example.bootcamp2024onclass.adapters.driving.http.mapper.IBootcampRequestMapper;
import com.example.bootcamp2024onclass.adapters.driving.http.mapper.IBootcampResponseMapper;
import com.example.bootcamp2024onclass.domain.api.IBootcampServicePort;
import com.example.bootcamp2024onclass.domain.model.Bootcamp;
import com.example.bootcamp2024onclass.domain.model.CustomPage;
import com.example.bootcamp2024onclass.domain.model.PaginationCriteria;
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
@RequestMapping("/bootcamp")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class BootcampRestControllerAdapter {

    private final IBootcampServicePort bootcampServicePort;
    private final IBootcampRequestMapper bootcampRequestMapper;
    private final IBootcampResponseMapper bootcampResponseMapper;

    @Operation(summary = "Create a new Bootcamp")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Correct create a new Bootcamp",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BootcampResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bootcamp already exists or size of capacities are invalid or capacities are repeated or fields are invalid", content = @Content),
            @ApiResponse(responseCode = "404", description = "Capacity not found", content = @Content),
            @ApiResponse(responseCode = "401", ref = "#/components/responses/UnauthorizedError")
    })
    @PreAuthorize("hasAuthority('ADMINISTRATOR') or hasAuthority('TUTOR')")
    @PostMapping("/")
    public ResponseEntity<BootcampResponse> addBootcamp(@Valid @RequestBody AddBootcampRequest request) {
        Bootcamp bootcamp = bootcampRequestMapper.addRequestToBootcamp(request);
        bootcamp = bootcampServicePort.saveBootcamp(bootcamp);
        BootcampResponse response = bootcampResponseMapper.toBootcampResponse(bootcamp);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get Bootcamps with pagination and order by name or order by size of capacities")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Correct get Bootcamps",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BootcampResponse.class))  }),
            @ApiResponse(responseCode = "401", ref = "#/components/responses/UnauthorizedError")
    })
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @GetMapping("/")
    public ResponseEntity<CustomPage<BootcampResponse>> getAllBootcamps(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false, defaultValue = "true") boolean isOrderByName,
            @RequestParam(required = false, defaultValue = "true") boolean isAscending){
        PaginationCriteria criteria = new PaginationCriteria(page, size, isAscending ? SortDirection.ASC : SortDirection.DESC, isOrderByName ? "name" : "id");
        CustomPage<Bootcamp> bootcamps = bootcampServicePort.getAllBootcamps(criteria);
        CustomPage<BootcampResponse> responses = new CustomPage<>(
                bootcamps.getContent().stream().map(bootcampResponseMapper::toBootcampResponse).toList(),
                bootcamps.getPageNumber(), bootcamps.getPageSize(), bootcamps.getTotalElements(), bootcamps.getTotalPages()
        );
        return ResponseEntity.ok(responses);
    }
}
