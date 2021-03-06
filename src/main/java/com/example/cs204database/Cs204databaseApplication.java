package com.example.cs204database;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class Cs204databaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(Cs204databaseApplication.class, args);
    }

    @GetMapping("/")
    public String index(){
        return "<a href='/users/all'>All users</a>";
    }
}
