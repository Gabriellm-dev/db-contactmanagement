package com.dbcontact.demo.database;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dbcontact.demo.entity.Social;

public interface SocialRepository extends JpaRepository<Social, Long> {
}
