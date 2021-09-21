package com.codewithfibbee.ajowebapp.payloads.responses;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class LoginResponse {
    private final String token;
    private final List<String> roles;
    private String type = "Bearer";
}
