/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kn.app.service;

import com.kn.app.ContactsApplication;
import com.kn.app.entity.Contact;
import com.kn.app.model.ContactsResponse;
import com.kn.app.repo.ContactRepo;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author oghomwen.aigbedion
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = ContactsApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class ContactServiceTest {

    @Autowired
    private ContactService contactService;
    @Autowired
    private ContactRepo contactRepo;

    @Test
    public void whenPageAndSizeNull_GetContactsReturnsFiveElements() {
        Optional<Integer> page = Optional.ofNullable(null);
        Optional<Integer> size = Optional.ofNullable(null);
        ContactsResponse contactsResponse = contactService.getContacts(page, size);

        assertEquals(contactsResponse.getContactPage().getNumberOfElements(), 5);
        assertFalse(contactsResponse.getPageNumbers().isEmpty());
    }

    @Test
    public void afterContactIsSaved_SearchBySavedContactNameReturnsResults() {
        String name = "Aigbedion Oghomwen";

        Contact contact = new Contact();
        contact.setName(name);
        contactRepo.save(contact);

        Optional<Integer> page = Optional.ofNullable(null);
        Optional<Integer> size = Optional.ofNullable(null);

        ContactsResponse contactsResponse = contactService.searchByName(page, size, name);

        assertTrue(contactsResponse.getContactPage().getTotalElements() > 0);
        assertFalse(contactsResponse.getPageNumbers().isEmpty());
    }

}
