package com.codewithfibbee.ajowebapp.service.serviceImpl;

import com.codewithfibbee.ajowebapp.enums.Roles;
import com.codewithfibbee.ajowebapp.enums.AuthenticationProvider;
import com.codewithfibbee.ajowebapp.exceptions.ResourceNotFoundException;
import com.codewithfibbee.ajowebapp.exceptions.UserAlreadyExistException;
import com.codewithfibbee.ajowebapp.model.Role;
import com.codewithfibbee.ajowebapp.model.User;
import com.codewithfibbee.ajowebapp.payloads.requests.LoginRequest;
import com.codewithfibbee.ajowebapp.payloads.requests.SignUpRequest;
import com.codewithfibbee.ajowebapp.payloads.responses.AuthResponse;
import com.codewithfibbee.ajowebapp.repository.RoleRepo;
import com.codewithfibbee.ajowebapp.repository.UserRepo;
import com.codewithfibbee.ajowebapp.security.TokenProvider;
import com.codewithfibbee.ajowebapp.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static com.codewithfibbee.ajowebapp.enums.Roles.SUB_ADMIN;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    private UserRepo userRepo;
    private RoleRepo roleRepo;
    private BCryptPasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private TokenProvider tokenProvider;


    @Override
    public User createAdmin(SignUpRequest userDetails) {
        Set<Role> roles = new HashSet<>();
        var role = roleRepo.findByName(SUB_ADMIN)
                .orElseThrow(() -> new ResourceNotFoundException("No role found"));
        roles.add(role);
        return createUser(userDetails, roles);
    }


    @Override
    public User createMember(SignUpRequest userDetails) {
        Set<Role> roles = new HashSet<>();
        var role = roleRepo.findByName(Roles.MEMBER)
                .orElseThrow(() -> new ResourceNotFoundException("No role found"));
        roles.add(role);
        return createUser(userDetails, roles);
    }

    @Override
    public User getUserById(Long id) {
        return userRepo.findById(id).orElseThrow(()->
                new ResourceNotFoundException("User", "id", id)
                );
    }

    @Override
    public Object authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.createToken(authentication);
        System.out.println(token);
        return new AuthResponse(token);
    }


    private User createUser(SignUpRequest userDetails, Set<Role> roles){
        var userExists = userRepo.findByEmail(userDetails.getEmail());
        if (userExists.isEmpty()) {
            var mapper = new ModelMapper();
            User user = mapper.map(userDetails, User.class);
            user.setName(userDetails.getName());
            user.setEmail(userDetails.getEmail());
            user.setProvider(AuthenticationProvider.LOCAL);
            user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
            user.setRoles(roles);
            user.setDateCreated(new Date());
            user.setIsDeactivated(false);
            user.setIsDeleted(false);
            userRepo.save(user);
            return mapper.map(user, User.class);
        } else throw new UserAlreadyExistException("User already Exist");
    }
}
