package com.codewithfibbee.ajowebapp.controller;

import com.codewithfibbee.ajowebapp.model.User;
import com.codewithfibbee.ajowebapp.security.CurrentUser;
import com.codewithfibbee.ajowebapp.security.UserPrincipal;
import com.codewithfibbee.ajowebapp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('MEMBER')")
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal){
        return userService.getUserById(userPrincipal.getId());
    }


}
