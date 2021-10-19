package com.codewithfibbee.ajowebapp.model;

import com.codewithfibbee.ajowebapp.enums.AuthenticationProvider;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
public class User implements Serializable {
    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Email
    private String email;

    private String imageUrl;

    @Column(nullable = false)
    private Boolean emailVerified = false;

    private String providerId;

    @JsonIgnore
    private String password;

    @Column(nullable = false)
    private String encryptedPassword;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthenticationProvider provider;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    private String phoneNumber;

    private Date dateCreated;
    private Boolean isDeactivated;
    private Boolean isDeleted;
    private String deactivationDate;

    @OneToMany(mappedBy = "user", cascade = ALL)
    private List<Contribution> contributions;

    @OneToMany(mappedBy = "user", cascade = ALL)
    private List<Request> requests;
}
