package com.codewithfibbee.ajowebapp.payloads.responses;

import com.codewithfibbee.ajowebapp.model.Role;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@RequiredArgsConstructor
public class LoginResponse {
    private final String token;
    private final List<String> roles;
    private String type = "Bearer";
}
