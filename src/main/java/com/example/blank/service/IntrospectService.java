package com.example.blank.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IntrospectService {

    public String introspectService(String token){
        return token;
    }
}
