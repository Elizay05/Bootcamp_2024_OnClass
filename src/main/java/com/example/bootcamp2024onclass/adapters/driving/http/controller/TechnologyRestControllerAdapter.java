package com.example.bootcamp2024onclass.adapters.driving.http.controller;


import com.example.bootcamp2024onclass.adapters.driving.http.dto.request.AddTechnologyRequest;
import com.example.bootcamp2024onclass.adapters.driving.http.mapper.ITechnologyRequestMapper;
import com.example.bootcamp2024onclass.adapters.driving.http.mapper.ITechnologyResponseMapper;
import com.example.bootcamp2024onclass.domain.api.ITechnologyServicePort;
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

    /*Cambiar el Void(Regresar el ID, LA ESTRUCTURA DE LA CLASE TECHNOLOGY)*/
    @PostMapping("/")
    public ResponseEntity<Void> addTechnology(@Valid @RequestBody AddTechnologyRequest request){
        technologyServicePort.saveTechnology(technologyRequestMapper.addRequestToTechnology(request));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
