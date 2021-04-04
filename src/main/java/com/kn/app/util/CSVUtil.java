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
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author oghomwen.aigbedion
 */
@Component
public class CSVUtil {

    @Autowired
    ContactRepo contactRepo;

//    public static String TYPE = "text/csv";
//    static String[] HEADERS = {"name", "url"};
//
//    public static boolean hasCSVFormat(MultipartFile file) {
//        return TYPE.equals(file.getContentType());
//    }
    public void csvToContacts(InputStream is) {//List<Contact> csvToContacts(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                CSVParser csvParser = new CSVParser(fileReader,
                        CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

//            List<Contact> contacts = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                Contact contact = new Contact(
                        csvRecord.get("name"),
                        csvRecord.get("url")
                );

                contactRepo.save(contact);

//                contacts.add(contact);
            }

//            return contacts;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

}
