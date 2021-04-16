/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kn.app.service;

import com.kn.app.entity.Contact;
import com.kn.app.model.ContactsResponse;
import com.kn.app.repo.ContactRepo;
import com.kn.app.util.PageNumberUtil;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
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
    private PageNumberUtil pageNumberUtil;

    public ContactsResponse getContacts(Optional<Integer> page, Optional<Integer> size, Optional<String> name) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        Sort sortOrder = Sort.by(Sort.Direction.ASC, "name");
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize, sortOrder);

        Page<Contact> contactsPage;
        if (name.isPresent()) {
            contactsPage = contactRepo.findByNameContainingIgnoreCase(name.get(), pageable);
        } else {
            contactsPage = contactRepo.findAll(pageable);
        }
        List<Integer> pageNumbers = pageNumberUtil.determinePageNumbers(contactsPage, currentPage);

        ContactsResponse contactsResponse = new ContactsResponse(contactsPage, pageNumbers);

        return contactsResponse;
    }
}
