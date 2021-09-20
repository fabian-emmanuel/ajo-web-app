package com.codewithfibbee.ajowebapp.payloads.responses;

import lombok.Data;

@Data
public class SignUpResponse {
    private String firstName;
    private String lastName;
    private String email;
}
