package com.codewithfibbee.ajowebapp.repository;

import com.codewithfibbee.ajowebapp.configs.Roles;
import com.codewithfibbee.ajowebapp.model.Role;
import com.codewithfibbee.ajowebapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {
    Optional<Role> findByName(Roles sole);
}
