package com.example.blank.controller;

import com.example.blank.service.IntrospectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class IntrospectController {
    private IntrospectService introspectService;
    @PostMapping("/introspectToken")
    public ResponseEntity<String> intospectToken(@RequestPart String token){
        String tokenOut=introspectService.introspectService(token);
        return new ResponseEntity<>(tokenOut, HttpStatus.OK);
    }

}
