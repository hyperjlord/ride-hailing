package com.soa.emergencyservice.service;

import com.soa.emergencyservice.dao.SecurityDao;
import com.soa.emergencyservice.entity.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactService implements SecurityDao {

    @Autowired
    private SecurityDao securityDao;

    @Override
    public List<Contact> findAll() {
        return securityDao.findAll();
    }

    @Override
    public List<Contact> findAll(Sort sort) {
        return securityDao.findAll();
    }

    @Override
    public Page<Contact> findAll(Pageable pageable) {
        return null;
    }


    @Override
    public List<Contact> findAllById(Iterable<Integer> iterable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public void delete(Contact contact) {

    }

    @Override
    public void deleteAll(Iterable<? extends Contact> iterable) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends Contact> S save(S s) {
        return null;
    }

    @Override
    public <S extends Contact> List<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<Contact> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Contact> S saveAndFlush(S s) {
        return null;
    }

    @Override
    public void deleteInBatch(Iterable<Contact> iterable) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Contact getOne(Integer integer) {
        return null;
    }

    @Override
    public <S extends Contact> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Contact> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Contact> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Contact> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Contact> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Contact> boolean exists(Example<S> example) {
        return false;
    }
}
