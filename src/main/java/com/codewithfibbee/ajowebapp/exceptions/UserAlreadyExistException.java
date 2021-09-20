package com.codewithfibbee.ajowebapp.exceptions;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistException extends RuntimeException{
    public UserAlreadyExistException(String message, HttpStatus userAlreadyExists){
        super(message);
    }

    public UserAlreadyExistException(String message){
        super(message);
    }
}
