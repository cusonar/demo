package com.example.demo.hello.service;

import com.example.demo.domain.Hello;
import com.example.demo.hello.HelloRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class HelloService {

    private final HelloRepository helloRepository;

    public Long count() {
        return helloRepository.count();
    }

    public List<Hello> getAll() {
        return helloRepository.getAll();
    }

    public Hello getById(Long id) {
        return helloRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("getById: " + id));
    }

    @Transactional
    public Long create(HelloDto.Create dto) {
        log.info("HelloService.create : {}", dto);
        Hello newHello = dto.toEntity();
        Hello saved = helloRepository.save(newHello);
        return saved.getId();
    }

    @Transactional
    public HelloDto.Detail update(Long id, HelloDto.Update dto) {
        Hello hello = helloRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("update: " + id));
        hello.update(dto);
        return HelloDto.Detail.of(hello);
    }
}
