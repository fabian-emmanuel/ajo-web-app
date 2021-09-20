package com.codewithfibbee.ajowebapp.controller;

import com.codewithfibbee.ajowebapp.payloads.requests.LoginRequest;
import com.codewithfibbee.ajowebapp.payloads.requests.SignUpRequest;
import com.codewithfibbee.ajowebapp.payloads.responses.LoginResponse;
import com.codewithfibbee.ajowebapp.payloads.responses.SignUpResponse;
import com.codewithfibbee.ajowebapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class Authcontroller {
    private final UserService userService;

    @PostMapping("/admin/register")
    public ResponseEntity<SignUpResponse> registerAdmin(@RequestBody SignUpRequest signUpRequest) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createAdmin(signUpRequest));
    }

    @PostMapping("/member/register")
    public ResponseEntity<SignUpResponse> registerMember(@RequestBody SignUpRequest signUpRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createMember(signUpRequest));
    }

}
