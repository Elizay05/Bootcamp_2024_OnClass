package com.example.bootcamp2024onclass.adapters.driving.http.controller;

import com.example.bootcamp2024onclass.adapters.driving.http.dto.reponse.CapacityResponse;
import com.example.bootcamp2024onclass.adapters.driving.http.dto.request.AddCapacityRequest;
import com.example.bootcamp2024onclass.adapters.driving.http.mapper.ICapacityRequestMapper;
import com.example.bootcamp2024onclass.adapters.driving.http.mapper.ICapacityResponseMapper;
import com.example.bootcamp2024onclass.domain.api.ICapacityServicePort;
import com.example.bootcamp2024onclass.domain.model.Capacity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/capacity")
@RequiredArgsConstructor
public class CapacityRestControllerAdapter {

    private final ICapacityServicePort capacityServicePort;
    private final ICapacityRequestMapper capacityRequestMapper;
    private final ICapacityResponseMapper capacityResponseMapper;

    @PostMapping("/")
    public ResponseEntity<CapacityResponse> addCapacity(@Valid @RequestBody AddCapacityRequest request) {
        Capacity capacity = capacityRequestMapper.addRequestToCapacity(request);
        capacity = capacityServicePort.saveCapacity(capacity);
        CapacityResponse response = capacityResponseMapper.toCapacityResponse(capacity);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/")
    public ResponseEntity<List<CapacityResponse>> getAllCapacities(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false, defaultValue = "true") boolean isOrderByName,
            @RequestParam(required = false, defaultValue = "true") boolean isAscending){
        return ResponseEntity.ok(capacityResponseMapper.toCapacityResponseList(
                capacityServicePort.getAllCapacities(page, size, isOrderByName, isAscending)
        ));
    }
}
