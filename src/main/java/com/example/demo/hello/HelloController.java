package com.example.demo.hello;

import com.example.demo.common.ListResponse;
import com.example.demo.hello.service.HelloDto;
import com.example.demo.hello.service.HelloService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
public class HelloController {

    private final HelloService helloService;

    @GetMapping("/hello")
    public ListResponse<List<HelloDto.List>> getAll() {
        Long count = helloService.count();
        List<HelloDto.List> list = helloService.getAll().stream()
                .map(HelloDto.List::of)
                .collect(Collectors.toList());
        return new ListResponse<>(count, list);
    }

    @GetMapping("/hello/{id}")
    public HelloDto.Detail getById(@PathVariable Long id) {
        return HelloDto.Detail.of(helloService.getById(id));
    }

    @PostMapping("/hello")
    public ResponseEntity<Long> create(@RequestBody HelloDto.Create dto) throws URISyntaxException {
        Long id = helloService.create(dto);
        return ResponseEntity.created(new URI("/hello/" + id)).build();
    }
}
