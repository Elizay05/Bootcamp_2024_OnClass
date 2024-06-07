package com.example.bootcamp2024onclass.adapters.driving.http.controller;

import com.example.bootcamp2024onclass.adapters.driving.http.dto.reponse.CapacityResponse;
import com.example.bootcamp2024onclass.adapters.driving.http.dto.request.AddCapacityRequest;
import com.example.bootcamp2024onclass.adapters.driving.http.mapper.ICapacityRequestMapper;
import com.example.bootcamp2024onclass.adapters.driving.http.mapper.ICapacityResponseMapper;
import com.example.bootcamp2024onclass.domain.api.ICapacityServicePort;
import com.example.bootcamp2024onclass.domain.model.Capacity;
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
import java.util.List;

@RestController
@RequestMapping("/capacity")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class CapacityRestControllerAdapter {

    private final ICapacityServicePort capacityServicePort;
    private final ICapacityRequestMapper capacityRequestMapper;
    private final ICapacityResponseMapper capacityResponseMapper;

    @Operation(summary = "Create a new Capacity")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Correct create a new Capacity",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CapacityResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Capacitie already exists or size of technologies are invalid or technoloies are repeated or fields are invalid", content = @Content),
            @ApiResponse(responseCode = "404", description = "Technology not found", content = @Content),
            @ApiResponse(responseCode = "401", ref = "#/components/responses/UnauthorizedError")
    })
    @PreAuthorize("hasAuthority('ADMINISTRATOR') or hasAuthority('TUTOR')")
    @PostMapping("/")
    public ResponseEntity<CapacityResponse> addCapacity(@Valid @RequestBody AddCapacityRequest request) {
        Capacity capacity = capacityRequestMapper.addRequestToCapacity(request);
        capacity = capacityServicePort.saveCapacity(capacity);
        CapacityResponse response = capacityResponseMapper.toCapacityResponse(capacity);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get Capacities with pagination and order by name or order by size of technologies")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Correct get Capacities",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CapacityResponse.class))  }),
            @ApiResponse(responseCode = "401", ref = "#/components/responses/UnauthorizedError")
    })
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @GetMapping("/")
    public ResponseEntity<CustomPage<CapacityResponse>> getAllCapacities(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "true") boolean isOrderByName,
            @RequestParam(required = false, defaultValue = "true") boolean isAscending) {
        PaginationCriteria criteria = new PaginationCriteria(page, size, isAscending ? SortDirection.ASC : SortDirection.DESC, isOrderByName ? "name" : "id");
        CustomPage<Capacity> capacities = capacityServicePort.getAllCapacities(criteria);
        CustomPage<CapacityResponse> responses = new CustomPage<>(
                capacities.getContent().stream().map(capacityResponseMapper::toCapacityResponse).toList(),
                capacities.getPageNumber(), capacities.getPageSize(), capacities.getTotalElements(), capacities.getTotalPages()
        );
        return ResponseEntity.ok(responses);
    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @GetMapping("/total_body")
    public ResponseEntity<List<CapacityResponse>> getTotalBodyCapacities(){
        return ResponseEntity.ok(capacityResponseMapper.toCapacityResponseList(capacityServicePort.getTotalBodyCapacities()));
    }
}
