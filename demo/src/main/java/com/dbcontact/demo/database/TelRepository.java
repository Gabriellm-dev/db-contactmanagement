package com.dbcontact.demo.database;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dbcontact.demo.entity.Tel;

public interface TelRepository extends JpaRepository<Tel, Long> {
}