package com.codewithfibbee.ajowebapp.service;

import com.codewithfibbee.ajowebapp.model.Role;
import com.codewithfibbee.ajowebapp.model.User;
import com.codewithfibbee.ajowebapp.payloads.requests.LoginRequest;
import com.codewithfibbee.ajowebapp.payloads.requests.SignUpRequest;
import com.codewithfibbee.ajowebapp.payloads.responses.LoginResponse;
import com.codewithfibbee.ajowebapp.payloads.responses.SignUpResponse;

import java.util.List;

public interface UserService {
    SignUpResponse createAdmin(SignUpRequest user) throws Exception;

    SignUpResponse createMember(SignUpRequest user);

}
