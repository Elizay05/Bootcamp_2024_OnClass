package com.example.bootcamp2024onclass.adapters.driving.http.controller;


import com.example.bootcamp2024onclass.adapters.driving.http.dto.reponse.CapacityResponse;
import com.example.bootcamp2024onclass.adapters.driving.http.dto.reponse.TechnologyResponse;
import com.example.bootcamp2024onclass.adapters.driving.http.dto.request.AddTechnologyRequest;
import com.example.bootcamp2024onclass.adapters.driving.http.mapper.ITechnologyRequestMapper;
import com.example.bootcamp2024onclass.adapters.driving.http.mapper.ITechnologyResponseMapper;
import com.example.bootcamp2024onclass.domain.api.ITechnologyServicePort;
import com.example.bootcamp2024onclass.domain.model.CustomPage;
import com.example.bootcamp2024onclass.domain.model.PaginationCriteria;
import com.example.bootcamp2024onclass.domain.model.Technology;
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
import java.util.List;

@RestController
@RequestMapping("/technology")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class TechnologyRestControllerAdapter {
    private final ITechnologyServicePort technologyServicePort;
    private final ITechnologyRequestMapper technologyRequestMapper;
    private final ITechnologyResponseMapper technologyResponseMapper;


    @Operation(summary = "Create a protected resource")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Correct create a new Technology",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(   implementation = TechnologyResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Technology already exists or fields are invalid", content = @Content),
            @ApiResponse(responseCode = "401", ref = "#/components/responses/UnauthorizedError")
    })
    @PreAuthorize("hasAuthority('ADMINISTRATOR') or hasAuthority('TUTOR')")
    @PostMapping("/")
    public ResponseEntity<TechnologyResponse> addTechnology(@Valid @RequestBody AddTechnologyRequest request){
        Technology technology = technologyRequestMapper.addRequestToTechnology(request);
        technology = technologyServicePort.saveTechnology(technology);
        TechnologyResponse response = technologyResponseMapper.toTechnologyResponse(technology);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get Technologies with pagination and order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Correct get Technologies",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TechnologyResponse.class))  }),
            @ApiResponse(responseCode = "401", ref = "#/components/responses/UnauthorizedError")
    })
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @GetMapping("/")
    public ResponseEntity<CustomPage<TechnologyResponse>> getAllTechnologies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "true") boolean isAscending) {
        PaginationCriteria criteria = new PaginationCriteria(page, size, isAscending ? SortDirection.ASC : SortDirection.DESC, "name");
        CustomPage<Technology> technologies = technologyServicePort.getAllTechnologies(criteria);
        CustomPage<TechnologyResponse> responses = new CustomPage<>(
                technologies.getContent().stream().map(technologyResponseMapper::toTechnologyResponse).toList(),
                technologies.getPageNumber(), technologies.getPageSize(), technologies.getTotalElements(), technologies.getTotalPages()
        );
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Get Total Technologies")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Correct get Capacities",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CapacityResponse.class))  }),
            @ApiResponse(responseCode = "401", ref = "#/components/responses/UnauthorizedError")
    })
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @GetMapping("/total_body")
    public ResponseEntity<List<TechnologyResponse>> getTotalBodyTechnologies(){
        return ResponseEntity.ok(technologyResponseMapper.toTechnologyResponseList(technologyServicePort.getTotalBodyTechnologies()));
    }
}
