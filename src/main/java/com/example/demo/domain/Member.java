package com.example.demo.domain;

import com.example.demo.config.jpa.Auditable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor
public class Member extends Auditable implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "member")
    private Set<MemberAuthority> memberAuthoritySet = new HashSet<>();

    private boolean isAccountNonExpired = true;
    private boolean isAccountNonLocked = true;
    private boolean isCredentialsNonExpired = true;
    private boolean isEnabled = true;

    @Override
    @Transactional
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return memberAuthoritySet.stream()
                .map(MemberAuthority::getAuthority)
                .collect(Collectors.toList());
    }

    @Builder
    public Member(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void assign(Authority authority) {
        if (memberAuthoritySet.stream()
                .noneMatch(memberAuthority ->
                        memberAuthority.getAuthority().getAuthority().equals(
                                authority.getAuthority()))) {
            memberAuthoritySet.add(new MemberAuthority(this, authority));
        }
    }
}
