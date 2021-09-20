package com.codewithfibbee.ajowebapp.service.serviceImpl;

import com.codewithfibbee.ajowebapp.configs.Roles;
import com.codewithfibbee.ajowebapp.exceptions.ResourceNotFoundException;
import com.codewithfibbee.ajowebapp.exceptions.UserAlreadyExistException;
import com.codewithfibbee.ajowebapp.model.Role;
import com.codewithfibbee.ajowebapp.model.User;
import com.codewithfibbee.ajowebapp.payloads.requests.SignUpRequest;
import com.codewithfibbee.ajowebapp.payloads.responses.SignUpResponse;
import com.codewithfibbee.ajowebapp.repository.RoleRepo;
import com.codewithfibbee.ajowebapp.repository.UserRepo;
import com.codewithfibbee.ajowebapp.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static com.codewithfibbee.ajowebapp.configs.Roles.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not Found"));
        Collection<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name())).toList();
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getEncryptedPassword(),
                authorities);
    }

    @Override
    public SignUpResponse createAdmin(SignUpRequest userDetails) {
        Set<Role> roles = new HashSet<>();
        var role = roleRepo.findByName(SUB_ADMIN)
                .orElseThrow(() -> new ResourceNotFoundException("No role found"));
        roles.add(role);
        return createUser(userDetails, roles);
    }


    @Override
    public SignUpResponse createMember(SignUpRequest userDetails) {
        Set<Role> roles = new HashSet<>();
        var role = roleRepo.findByName(Roles.MEMBER)
                .orElseThrow(() -> new ResourceNotFoundException("No role found"));
        roles.add(role);
        return createUser(userDetails, roles);
    }


    private SignUpResponse createUser(SignUpRequest userDetails, Set<Role> roles){
        var userExists = userRepo.findByEmail(userDetails.getEmail());
        if (userExists.isEmpty()) {
            var mapper = new ModelMapper();
            User user = mapper.map(userDetails, User.class);
            user.setFirstName(userDetails.getFirstName());
            user.setLastName(userDetails.getLastName());
            user.setEmail(userDetails.getEmail());
            user.setEncryptedPassword(passwordEncoder.encode(userDetails.getPassword()));
            user.setRoles(roles);
            user.setDateCreated(new Date());
            user.setIsDeactivated(false);
            user.setIsDeleted(false);
            userRepo.save(user);
            return mapper.map(user, SignUpResponse.class);
        } else throw new UserAlreadyExistException("User already Exist");
    }
}
