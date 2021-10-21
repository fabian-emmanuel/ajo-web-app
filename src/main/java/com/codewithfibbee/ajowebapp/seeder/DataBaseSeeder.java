package com.codewithfibbee.ajowebapp.seeder;

import com.codewithfibbee.ajowebapp.enums.AuthenticationProvider;
import com.codewithfibbee.ajowebapp.exceptions.ResourceNotFoundException;
import com.codewithfibbee.ajowebapp.model.Role;
import com.codewithfibbee.ajowebapp.model.User;
import com.codewithfibbee.ajowebapp.repository.RoleRepo;
import com.codewithfibbee.ajowebapp.repository.UserRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.codewithfibbee.ajowebapp.enums.Roles.*;

@Component
@Slf4j
@AllArgsConstructor
public class DataBaseSeeder {
    private RoleRepo roleRepo;
    private UserRepo userRepo;

    @EventListener
    public void seed(ContextRefreshedEvent event){
        seedRolesTable();
        seedUsersTable();
    }

    private void seedRolesTable(){
        List<Role> roles = roleRepo.findAll();
        if(roles.isEmpty()){
            var role1 = new Role();
            var role2 = new Role();
            var role3 = new Role();
            role1.setName(SUPER_ADMIN);
            role2.setName(SUB_ADMIN);
            role3.setName(MEMBER);
            roleRepo.save(role1);
            roleRepo.save(role2);
            roleRepo.save(role3);
        }
    }

    private void seedUsersTable() {
        List<User> users = userRepo.findAll();
        Set<Role> roles = new HashSet<>();
        var role = roleRepo.findByName(SUPER_ADMIN)
                .orElseThrow(()-> new ResourceNotFoundException("Role not found"));
        roles.add(role);
        if(users.isEmpty()){
            User user = new User();
            user.setName("Admin Admin");
            user.setEmail("admin@admin.com");
            user.setEmailVerified(true);
            user.setProvider(AuthenticationProvider.LOCAL);
            user.setPassword(new BCryptPasswordEncoder().encode("admin2020"));
            user.setDateCreated(new Date());
            user.setIsDeactivated(false);
            user.setIsDeleted(false);
            user.setRoles(roles);
            userRepo.save(user);
            log.info("user with the role of {} has been seeded", user.getRoles());
        } else log.trace("User seeding not necessary!");
    }
}
