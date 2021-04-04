/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kn.app.repo;

import com.kn.app.entity.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author oghomwen.aigbedion
 */
public interface ContactRepo extends JpaRepository<Contact, Integer> {//CrudRepository<Contact, Integer>{

    Page<Contact> findByNameContaining(String infix, Pageable pageable);
}
