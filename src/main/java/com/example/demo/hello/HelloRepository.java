package com.example.demo.hello;

import com.example.demo.domain.Hello;
import com.example.demo.domain.QHello;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public interface HelloRepository extends JpaRepository<Hello, Long>, CustomHelloRepository {
}

interface CustomHelloRepository {
    List<Hello> getAll();
}

class CustomHelloRepositoryImpl extends QuerydslRepositorySupport implements  CustomHelloRepository {

    public CustomHelloRepositoryImpl() {
        super(QHello.class);
    }

    @Override
    public List<Hello> getAll() {
        QHello hello = QHello.hello;
        return from(hello).fetch();
    }
}