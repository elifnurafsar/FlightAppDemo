package com.example.FlightAppDemo.Entities.User;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users", schema = "public")
@Data
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @Column(name = "id", unique = true, columnDefinition = "uuid")
    private UUID ID;

    @NotBlank(message = "E-mail cannot be blank")
    @NotEmpty(message = "E-mail cannot be empty")
    @NotNull
    @Column(name = "username", columnDefinition = "text", nullable = false)
    private String username;

    @NotBlank(message = "Password cannot be blank")
    @NotEmpty(message = "Password cannot be empty")
    @NotNull
    @Column( name = "password", columnDefinition = "text", nullable = false)
    private String password;

    @Column(name = "enabled", columnDefinition = "boolean")
    private boolean enabled = true;

    @Enumerated(EnumType.STRING)
    @Column(name = "auth", columnDefinition = "text")
    private Authorities auth;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(auth.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
