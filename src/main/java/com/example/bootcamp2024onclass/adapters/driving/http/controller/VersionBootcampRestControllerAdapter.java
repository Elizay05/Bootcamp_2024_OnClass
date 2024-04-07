package com.example.bootcamp2024onclass.adapters.driving.http.controller;

import com.example.bootcamp2024onclass.adapters.driving.http.dto.reponse.VersionBootcampResponse;
import com.example.bootcamp2024onclass.adapters.driving.http.dto.request.AddVersionBootcampRequest;
import com.example.bootcamp2024onclass.adapters.driving.http.mapper.IVersionBootcampRequestMapper;
import com.example.bootcamp2024onclass.adapters.driving.http.mapper.IVersionBootcampResponseMapper;
import com.example.bootcamp2024onclass.domain.api.IVersionBootcampServicePort;
import com.example.bootcamp2024onclass.domain.model.VersionBootcamp;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/versionBootcamp")
@RequiredArgsConstructor
public class VersionBootcampRestControllerAdapter {
    private final IVersionBootcampServicePort versionBootcampServicePort;
    private final IVersionBootcampRequestMapper versionBootcampRequestMapper;
    private final IVersionBootcampResponseMapper versionBootcampResponseMapper;

    @PostMapping("/")
    public ResponseEntity<VersionBootcampResponse> addVersionBootcamp(@Valid @RequestBody AddVersionBootcampRequest request) {
        VersionBootcamp versionBootcamp = versionBootcampRequestMapper.addRequestToVersionBootcamp(request);
        versionBootcamp = versionBootcampServicePort.saveVersionBootcamp(versionBootcamp);
        VersionBootcampResponse response = versionBootcampResponseMapper.toVersionBootcampResponse(versionBootcamp);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
