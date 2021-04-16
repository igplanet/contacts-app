/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kn.app.util;

import com.kn.app.entity.Contact;
import com.kn.app.repo.ContactRepo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

/**
 *
 * @author oghomwen.aigbedion
 */
@Component
public class CSVUtil {

    @Autowired
    private ContactRepo contactRepo;

    private static final Logger LOG = Logger.getLogger(CSVUtil.class.getName());

    //Initial list should be one-time populated with people.csv
    @PostConstruct
    public void init() {
        try {
            Resource resource = new ClassPathResource("people.csv");
            InputStream inputStream = resource.getInputStream();
            csvToContacts(inputStream);
        } catch (IOException | RuntimeException e) {
            LOG.log(Level.SEVERE, e.getMessage());
        }
    }

    public void csvToContacts(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                CSVParser csvParser = new CSVParser(fileReader,
                        CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            Set<Contact> contacts = new HashSet<>();

            for (CSVRecord csvRecord : csvRecords) {
                Contact contact = new Contact(
                        csvRecord.get("name"),
                        csvRecord.get("url")
                );
                contacts.add(contact);
            }

            contactRepo.saveAll(contacts);
        } catch (Exception e) {
            throw new RuntimeException("failed to parse CSV file: " + e.getMessage());
        }
    }

}
