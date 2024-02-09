package com.example.demo.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
public class Member implements UserDetails {

    @Id
    private String username;

    private String password;


    private Set<? extends GrantedAuthority> authorities;

    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;

}
