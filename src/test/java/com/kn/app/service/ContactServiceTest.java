/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kn.app.service;

import com.kn.app.entity.Contact;
import com.kn.app.model.ContactsResponse;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

/**
 *
 * @author oghomwen.aigbedion
 */
@SpringBootTest
public class ContactServiceTest {

    @Autowired
    ContactService underTest;

    @Test
    public void itShouldReturnContacts() {
        //given
        Optional<Integer> page = Optional.empty();
        Optional<Integer> size = Optional.empty();
        Optional<String> name = Optional.empty();

        //when
        ContactsResponse contactsResponse = underTest.getContacts(page, size, name);
        Page<Contact> contacts = contactsResponse.getContactPage();
        List<Integer> pageNumbers = contactsResponse.getPageNumbers();

        //then
        Assertions.assertTrue(contacts.hasContent());
        Assertions.assertTrue(pageNumbers.size() > 0);
    }

}
