/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kn.app.util;

import com.kn.app.repo.ContactRepo;
import java.io.IOException;
import java.io.InputStream;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 *
 * @author oghomwen.aigbedion
 */
@SpringBootTest
public class CSVUtilTest {

    @Autowired
    private CSVUtil underTest;
    @Autowired
    private ContactRepo contactRepo;

    @Test
    public void itShouldInsertContactsIntoDB_AtInitialization() throws IOException {
        assertFalse(contactRepo.findAll().isEmpty());
    }

    @Test
    public void itShouldThrowException_WhenCSVFormatIsIncorrect() throws IOException {

        //given
        Resource resource = new ClassPathResource("people_WrongRowHeader.csv");
        InputStream inputStream = resource.getInputStream();
        String expectedMessage = "failed to parse CSV file:";
        Exception exception = new Exception();

        //when
        try {
            underTest.csvToContacts(inputStream);
        } catch (RuntimeException rte) {
            exception = rte;
        }

        //then
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

}
