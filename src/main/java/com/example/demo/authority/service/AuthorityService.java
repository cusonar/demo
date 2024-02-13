package com.example.demo.authority.service;

import com.example.demo.authority.AuthorityRepository;
import com.example.demo.domain.Authority;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorityService {

    private final AuthorityRepository authorityRepository;

    public List<Authority> getAll() {
        return authorityRepository.findAll();
    }

    public Optional<Authority> get(String authority) {
        return authorityRepository.findByAuthority(authority);
    }

    @Transactional
    public void create(Authority authority) {
        authorityRepository.save(authority);
    }
}
