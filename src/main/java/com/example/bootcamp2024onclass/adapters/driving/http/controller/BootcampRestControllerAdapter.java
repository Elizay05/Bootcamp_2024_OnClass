package com.example.bootcamp2024onclass.adapters.driving.http.controller;

import com.example.bootcamp2024onclass.adapters.driving.http.dto.reponse.BootcampResponse;
import com.example.bootcamp2024onclass.adapters.driving.http.dto.request.AddBootcampRequest;
import com.example.bootcamp2024onclass.adapters.driving.http.mapper.IBootcampRequestMapper;
import com.example.bootcamp2024onclass.adapters.driving.http.mapper.IBootcampResponseMapper;
import com.example.bootcamp2024onclass.domain.api.IBootcampServicePort;
import com.example.bootcamp2024onclass.domain.model.Bootcamp;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/bootcamp")
@RequiredArgsConstructor
public class BootcampRestControllerAdapter {

    private final IBootcampServicePort bootcampServicePort;
    private final IBootcampRequestMapper bootcampRequestMapper;
    private final IBootcampResponseMapper bootcampResponseMapper;

    @PostMapping("/")
    public ResponseEntity<BootcampResponse> addBootcamp(@Valid @RequestBody AddBootcampRequest request) {
        Bootcamp bootcamp = bootcampRequestMapper.addRequestToBootcamp(request);
        bootcamp = bootcampServicePort.saveBootcamp(bootcamp);
        BootcampResponse response = bootcampResponseMapper.toBootcampResponse(bootcamp);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/")
    public ResponseEntity<List<BootcampResponse>> getAllBootcamps(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false, defaultValue = "true") boolean isOrderByName,
            @RequestParam(required = false, defaultValue = "true") boolean isAscending){
        return ResponseEntity.ok(bootcampResponseMapper.toBootcampResponseList(
                bootcampServicePort.getAllBootcamps(page, size, isOrderByName, isAscending)
        ));
    }
}
