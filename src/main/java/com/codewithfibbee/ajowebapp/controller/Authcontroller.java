package com.codewithfibbee.ajowebapp.controller;

import com.codewithfibbee.ajowebapp.payloads.requests.LoginRequest;
import com.codewithfibbee.ajowebapp.payloads.requests.SignUpRequest;
import com.codewithfibbee.ajowebapp.payloads.responses.ApiResponse;
import com.codewithfibbee.ajowebapp.payloads.responses.AuthResponse;
import com.codewithfibbee.ajowebapp.security.TokenProvider;
import com.codewithfibbee.ajowebapp.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class Authcontroller {
    private UserService userService;

    @PostMapping("/admin/register")
    public ResponseEntity<?> registerAdmin(@RequestBody SignUpRequest signUpRequest) throws Exception {
        var result = userService.createAdmin(signUpRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/user/me")
                .buildAndExpand(result.getId()).toUri();
        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully@"));
    }

    @PostMapping("/member/register")
    public ResponseEntity<?> registerMember(@RequestBody SignUpRequest signUpRequest){
        var result = userService.createMember(signUpRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/user/me")
                .buildAndExpand(result.getId()).toUri();
        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully@"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(userService.authenticateUser(loginRequest));
    }


}
