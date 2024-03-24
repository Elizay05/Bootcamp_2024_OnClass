package com.example.bootcamp2024onclass.adapters.driving.http.controller;

import com.example.bootcamp2024onclass.adapters.driving.http.dto.reponse.CapacityResponse;
import com.example.bootcamp2024onclass.adapters.driving.http.dto.request.AddCapacityRequest;
import com.example.bootcamp2024onclass.adapters.driving.http.mapper.ICapacityRequestMapper;
import com.example.bootcamp2024onclass.adapters.driving.http.mapper.ICapacityResponseMapper;
import com.example.bootcamp2024onclass.domain.api.ICapacityServicePort;
import com.example.bootcamp2024onclass.domain.model.Capacity;
import com.example.bootcamp2024onclass.domain.model.Technology;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/capacity")
@RequiredArgsConstructor
public class CapacityRestControllerAdapter {

    private final ICapacityServicePort capacityServicePort;
    private final ICapacityRequestMapper capacityRequestMapper;
    private final ICapacityResponseMapper capacityResponseMapper;

    @PostMapping("/")
    public ResponseEntity<CapacityResponse> addCapacity(@RequestBody AddCapacityRequest request) {
        Capacity capacity = capacityRequestMapper.addRequestToCapacity(request);
        if (capacity.getTechnologies().size() < 3) {
            return ResponseEntity.badRequest().build();
        }
        Set<Technology> uniqueTechnologies = new HashSet<>(capacity.getTechnologies());
        if (uniqueTechnologies.size() != capacity.getTechnologies().size()) {
            return ResponseEntity.badRequest().build();
        }
        if (capacity.getTechnologies().size() > 20) {
            return ResponseEntity.badRequest().build();
        }
        capacity = capacityServicePort.saveCapacity(capacity);
        CapacityResponse response = capacityResponseMapper.toCapacityResponse(capacity);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
