package com.example.demo.hello;

import com.example.demo.JpaTestSupport;
import com.example.demo.domain.Hello;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

public class HelloRepositoryTest extends JpaTestSupport {

    @Autowired HelloRepository helloRepository;

    @Test
    public void getAllTest() {
        Hello h1 = new Hello("Hello1");
        Hello h2 = new Hello("Hello2");
        Hello h3 = new Hello("Hello3");
        helloRepository.saveAll(Arrays.asList(h1, h2, h3));

        List<Hello> all = helloRepository.getAll();
        System.out.println("all = " + all);
    }
}
