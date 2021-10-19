package com.codewithfibbee.ajowebapp.service.serviceImpl;

import com.codewithfibbee.ajowebapp.configs.Roles;
import com.codewithfibbee.ajowebapp.enums.AuthenticationProvider;
import com.codewithfibbee.ajowebapp.exceptions.ResourceNotFoundException;
import com.codewithfibbee.ajowebapp.exceptions.UserAlreadyExistException;
import com.codewithfibbee.ajowebapp.model.Role;
import com.codewithfibbee.ajowebapp.model.User;
import com.codewithfibbee.ajowebapp.payloads.requests.SignUpRequest;
import com.codewithfibbee.ajowebapp.repository.RoleRepo;
import com.codewithfibbee.ajowebapp.repository.UserRepo;
import com.codewithfibbee.ajowebapp.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static com.codewithfibbee.ajowebapp.configs.Roles.SUB_ADMIN;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final BCryptPasswordEncoder passwordEncoder;


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


    private User createUser(SignUpRequest userDetails, Set<Role> roles){
        var userExists = userRepo.findByEmail(userDetails.getEmail());
        if (userExists.isEmpty()) {
            var mapper = new ModelMapper();
            User user = mapper.map(userDetails, User.class);
            user.setName(userDetails.getName());
            user.setEmail(userDetails.getEmail());
            user.setProvider(AuthenticationProvider.LOCAL);
            user.setEncryptedPassword(passwordEncoder.encode(userDetails.getPassword()));
            user.setRoles(roles);
            user.setDateCreated(new Date());
            user.setIsDeactivated(false);
            user.setIsDeleted(false);
            userRepo.save(user);
            return mapper.map(user, User.class);
        } else throw new UserAlreadyExistException("User already Exist");
    }
}
