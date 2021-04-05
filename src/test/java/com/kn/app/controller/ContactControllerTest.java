/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kn.app.controller;

import com.kn.app.ContactsApplication;
import com.kn.app.entity.Contact;
import com.kn.app.repo.ContactRepo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
public class ContactControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    ContactRepo contactRepo;

    @Test
    public void whenGetContacts_ThenStatus200() throws Exception {

        mvc.perform(get("/contacts").contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk());
    }

    @Test
    public void givenContact_WhenSearchContactsByName_ThenStatus200() throws Exception {
        String name = "Aigbedion Oghomwen";

        Contact contact = new Contact();
        contact.setName(name);
        contactRepo.save(contact);

        mvc.perform(get("/search?name=" + name).contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk());
    }
}
