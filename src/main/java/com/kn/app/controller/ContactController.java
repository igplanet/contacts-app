/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kn.app.controller;

import com.kn.app.model.ContactsResponse;
import com.kn.app.service.ContactService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String getContacts(
            Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size,
            @RequestParam("name") Optional<String> name) {

        ContactsResponse response = contactService.getContacts(page, size, name);

        model.addAttribute("contactPage", response.getContactPage());

        if (response.getPageNumbers() != null) {
            model.addAttribute("pageNumbers", response.getPageNumbers());
            if (name.isPresent()) {
                model.addAttribute("name", name.get());
            }
        }

        return "index.html";
    }
}
