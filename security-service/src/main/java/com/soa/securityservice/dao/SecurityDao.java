package com.soa.securityservice.dao;

import com.soa.securityservice.pojo.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SecurityDao extends JpaRepository<Contact, Integer> {
    List<Contact> findAll();
}
