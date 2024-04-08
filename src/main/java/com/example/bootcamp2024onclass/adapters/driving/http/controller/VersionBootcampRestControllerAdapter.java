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

    @PostMapping("/")
    public ResponseEntity<VersionBootcampResponse> addVersionBootcamp(@Valid @RequestBody AddVersionBootcampRequest request) {
        VersionBootcamp versionBootcamp = versionBootcampRequestMapper.addRequestToVersionBootcamp(request);
        versionBootcamp = versionBootcampServicePort.saveVersionBootcamp(versionBootcamp);
        VersionBootcampResponse response = versionBootcampResponseMapper.toVersionBootcampResponse(versionBootcamp);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
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
