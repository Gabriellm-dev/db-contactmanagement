package com.dbcontact.demo.database;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dbcontact.demo.entity.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long> {

    List<Contact> findByNameContaining(String name);

    List<Contact> findByLastNameContaining(String lastName);

    List<Contact> findByNameContainingAndLastNameContaining(String name, String lastName);

    List<Contact> findByBusinessContaining(String business);

    List<Contact> findByNicknameContaining(String nickname);
}
