package com.example.cs204database;

import com.example.cs204database.entity.Users;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<Users, Integer> {
    Users findByUsername(String username);
}
