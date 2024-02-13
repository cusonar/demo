package com.example.demo.authority;

import com.example.demo.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    Optional<Authority> findByAuthority(String authority);
}