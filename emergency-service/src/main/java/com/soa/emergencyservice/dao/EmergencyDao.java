package com.soa.emergencyservice.dao;

import com.soa.emergencyservice.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmergencyDao extends JpaRepository<Contact, Integer> {
}
