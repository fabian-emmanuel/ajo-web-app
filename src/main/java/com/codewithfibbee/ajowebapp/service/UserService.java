package com.codewithfibbee.ajowebapp.service;

import com.codewithfibbee.ajowebapp.exceptions.UserAlreadyExistException;
import com.codewithfibbee.ajowebapp.payloads.requests.SignUpRequest;
import com.codewithfibbee.ajowebapp.payloads.responses.SignUpResponse;

public interface UserService {
    SignUpResponse createAdmin(SignUpRequest user) throws UserAlreadyExistException;

    SignUpResponse createMember(SignUpRequest user);

}
