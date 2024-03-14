package com.example.Bootcamp_2024_OnClass.adapters.driving.http.controller;


import com.example.Bootcamp_2024_OnClass.adapters.driving.http.dto.request.AddTechnologyRequest;
import com.example.Bootcamp_2024_OnClass.adapters.driving.http.mapper.ITechnologyRequestMapper;
import com.example.Bootcamp_2024_OnClass.adapters.driving.http.mapper.ITechnologyResponseMapper;
import com.example.Bootcamp_2024_OnClass.domain.api.ITechnologyServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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

}
