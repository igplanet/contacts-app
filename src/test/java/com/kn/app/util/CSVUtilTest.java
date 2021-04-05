/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kn.app.util;

import com.kn.app.ContactsApplication;
import com.kn.app.repo.ContactRepo;
import java.io.IOException;
import java.io.InputStream;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
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
public class CSVUtilTest {

    @Autowired
    CSVUtil csvUtil;
    @Autowired
    ContactRepo contactRepo;

    @Test
    public void afterInsertingCSVFileAsContactsOnAppStartUp_ContactTableIsNotEmpty() throws IOException {
        assertFalse(contactRepo.findAll().isEmpty());
    }

    @Test
    public void willlThrowException_WhenFailToParseCSV() throws IOException {
        Resource resource = new ClassPathResource("people_WrongRowHeader.csv");
        InputStream inputStream = resource.getInputStream();
        Exception exception = Assertions.assertThrows(RuntimeException.class, () -> {
            csvUtil.csvToContacts(inputStream);
        });

        String expectedMessage = "fail to parse CSV file:";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}
