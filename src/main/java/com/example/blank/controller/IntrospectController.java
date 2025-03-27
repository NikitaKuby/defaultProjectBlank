package com.example.blank.controller;

import com.example.blank.model.TokenResponse;
import com.example.blank.service.IntrospectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class IntrospectController {
    private final IntrospectService introspectService;

    public IntrospectController(IntrospectService introspectService) {
        this.introspectService = introspectService;
    }

    @GetMapping("/introspectToken/{token}")
    public ResponseEntity<TokenResponse> intospectToken(@PathVariable String token){
       // return new ResponseEntity<>(introspectService.introspectTokenViaBasicAuth(token), HttpStatus.OK);
        TokenResponse tokenResponse = introspectService.introspectTokenViaJWTAuth(token);
        introspectService.validateIntro(tokenResponse);
        return new ResponseEntity<>(tokenResponse, HttpStatus.OK);

    }


}
