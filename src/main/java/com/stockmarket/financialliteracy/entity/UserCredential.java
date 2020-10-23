package com.stockmarket.financialliteracy.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stockmarket.financialliteracy.security.AuthProvider;
import com.stockmarket.financialliteracy.security.Role;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@SequenceGenerator(name = "seq", sequenceName = "user_credential_id_seq", allocationSize = 1)
@Entity
@Table(name = "user_credential", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
public class UserCredential {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Email
    @Column(nullable = false)
    private String email;

    @Column(name = "image_url")
    private String imageUrl;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;
}