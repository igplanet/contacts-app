/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kn.app.controller;

import com.kn.app.entity.Contact;
import com.kn.app.model.ContactsResponse;
import com.kn.app.service.ContactService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author oghomwen.aigbedion
 */
@Controller
public class ContactController {

    @Autowired
    private ContactService contactService;

    @RequestMapping(value = "/contacts", method = RequestMethod.GET)
    public String listContacts(
            Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {

        ContactsResponse response = contactService.getContacts(page, size);

        model.addAttribute("contactPage", response.getContactPage());

        if (response.getPageNumbers() != null) {
            model.addAttribute("pageNumbers", response.getPageNumbers());
        }

        return "index.html";
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search(
            Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size,
            @RequestParam("name") String name) {

        ContactsResponse response = contactService.searchByName(page, size, name);

        model.addAttribute("contactPage", response.getContactPage());
        model.addAttribute("name", name);

        if (response.getPageNumbers() != null) {
            model.addAttribute("pageNumbers", response.getPageNumbers());
        }

        return "search.html";
    }
}
