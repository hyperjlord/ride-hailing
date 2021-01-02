package com.soa.emergencyservice.dao;

import com.soa.emergencyservice.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SecurityDao extends JpaRepository<Contact, Integer> {
    List<Contact> findAll();
}
