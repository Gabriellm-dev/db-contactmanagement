package com.dbcontact.demo.database;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dbcontact.demo.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}