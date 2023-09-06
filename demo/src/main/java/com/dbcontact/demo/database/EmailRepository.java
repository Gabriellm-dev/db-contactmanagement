package com.dbcontact.demo.database;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dbcontact.demo.entity.Email;

public interface EmailRepository extends JpaRepository<Email, Long> {
}
