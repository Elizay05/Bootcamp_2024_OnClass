package com.example.bootcamp2024onclass.adapters.driving.http.controller;


import com.example.bootcamp2024onclass.adapters.driving.http.dto.reponse.TechnologyResponse;
import com.example.bootcamp2024onclass.adapters.driving.http.dto.request.AddTechnologyRequest;
import com.example.bootcamp2024onclass.adapters.driving.http.mapper.ITechnologyRequestMapper;
import com.example.bootcamp2024onclass.adapters.driving.http.mapper.ITechnologyResponseMapper;
import com.example.bootcamp2024onclass.domain.api.ITechnologyServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/technology")
@RequiredArgsConstructor
public class TechnologyRestControllerAdapter {
    private final ITechnologyServicePort technologyServicePort;
    private final ITechnologyRequestMapper technologyRequestMapper;
    private final ITechnologyResponseMapper technologyResponseMapper;

    @PostMapping("/")
    public ResponseEntity<Void> addTechnology(@Valid @RequestBody AddTechnologyRequest request){
        technologyServicePort.saveTechnology(technologyRequestMapper.addRequestToTechnology(request));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/")
    public ResponseEntity<List<TechnologyResponse>> getAllTechnologies(@RequestParam Integer page, @RequestParam Integer size, @RequestParam Boolean isAscending){
        return ResponseEntity.ok(technologyResponseMapper.toTechnologyResponseList(technologyServicePort.getAllTechnologies(page, size, isAscending)));
    }

}
