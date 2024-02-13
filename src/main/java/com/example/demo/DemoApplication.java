package com.example.demo;

import com.example.demo.authority.service.AuthorityService;
import com.example.demo.domain.Authority;
import com.example.demo.hello.service.HelloDto;
import com.example.demo.hello.service.HelloService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class DemoApplication implements CommandLineRunner {

	private final HelloService helloService;
	private final AuthorityService authorityService;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) {
		helloService.create(new HelloDto.Create("A"));
		helloService.create(new HelloDto.Create("B"));
		helloService.create(new HelloDto.Create("C"));

		List<Authority> authorityList = authorityService.getAll();
		if (authorityList.size() == 0) {
			authorityService.create(new Authority("ROLE_USER"));
			authorityService.create(new Authority("ROLE_ADMIN"));
		}
	}
}
