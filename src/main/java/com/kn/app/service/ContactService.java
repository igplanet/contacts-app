/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kn.app.service;

import com.kn.app.entity.ApplicationInfo;
import com.kn.app.entity.Contact;
import com.kn.app.model.ContactsResponse;
import com.kn.app.repo.ApplicationInfoRepo;
import com.kn.app.repo.ContactRepo;
import com.kn.app.util.CSVUtil;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.annotation.PostConstruct;
import static org.hibernate.tool.schema.SchemaToolingLogging.LOGGER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 *
 * @author oghomwen.aigbedion
 */
@Service
public class ContactService {

    @Autowired
    private ContactRepo contactRepo;
    @Autowired
    private CSVUtil csvUtil;
    @Autowired
    private ApplicationInfoRepo applicationInfoRepo;

    //Initial list should be one-time populated with people.csv
    @PostConstruct
    public void init() {
        try {
            ApplicationInfo applicationInfo = new ApplicationInfo();;

            Optional<ApplicationInfo> appInfo = applicationInfoRepo.findByInfoName("isDBPopulatedWithPeopleCSV");
            boolean isAppInfoPresent = appInfo.isPresent();

            boolean isDBPopulatedWithPeopleCSV = false;

            if (isAppInfoPresent) {
                applicationInfo = appInfo.get();
                isDBPopulatedWithPeopleCSV = applicationInfo.getInfoValue().equalsIgnoreCase("TRUE");
            }

            if (!isDBPopulatedWithPeopleCSV) {
                Resource resource = new ClassPathResource("people.csv");
                InputStream inputStream = resource.getInputStream();
                csvUtil.csvToContacts(inputStream);

                applicationInfo.setInfoValue("TRUE");
                applicationInfo.setInfoName("isDBPopulatedWithPeopleCSV");
                applicationInfoRepo.save(applicationInfo);
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public ContactsResponse getContacts(Optional<Integer> page, Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        Sort sortOrder = Sort.by(Sort.Direction.ASC, "name");
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize, sortOrder);

        Page<Contact> contactsPage
                = contactRepo.findAll(pageable);
        List<Integer> pageNumbers = getPageNumbers(contactsPage, currentPage);

        ContactsResponse contactsResponse = new ContactsResponse(contactsPage, pageNumbers);

        return contactsResponse;
    }

    public ContactsResponse searchByName(Optional<Integer> page, Optional<Integer> size, String name) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        Sort sortOrder = Sort.by(Sort.Direction.ASC, "name");
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize, sortOrder);

        Page<Contact> contactsPage
                = contactRepo.findByNameContaining(name, pageable);
        List<Integer> pageNumbers = getPageNumbers(contactsPage, currentPage);

        ContactsResponse contactsResponse = new ContactsResponse(contactsPage, pageNumbers);

        return contactsResponse;
    }

    private List<Integer> getPageNumbers(Page<Contact> contactsPage, int currentPage) {
        List<Integer> pageNumbers = null;
        int totalPages = contactsPage.getTotalPages();
        if (totalPages > 0) {

            int rangeStart = (currentPage + 7) > totalPages && totalPages - 6 > 0 ? totalPages - 6 : currentPage;
            int rangeEnd = (currentPage + 7) > totalPages ? totalPages : currentPage + 6;

            pageNumbers = IntStream.rangeClosed(rangeStart, rangeEnd)
                    .boxed()
                    .collect(Collectors.toList());
        }
        return pageNumbers;
    }
}
