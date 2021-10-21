package com.codewithfibbee.ajowebapp.service;

import com.codewithfibbee.ajowebapp.exceptions.UserAlreadyExistException;
import com.codewithfibbee.ajowebapp.model.User;
import com.codewithfibbee.ajowebapp.payloads.requests.LoginRequest;
import com.codewithfibbee.ajowebapp.payloads.requests.SignUpRequest;

public interface UserService {
    User createAdmin(SignUpRequest user) throws UserAlreadyExistException;

    User createMember(SignUpRequest user);

    User getUserById(Long id);

    Object authenticateUser(LoginRequest loginRequest);
}
