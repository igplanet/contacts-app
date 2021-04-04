/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kn.app.model;

import com.kn.app.entity.Contact;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

/**
 *
 * @author oghomwen.aigbedion
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactsResponse {

    private Page<Contact> contactPage;
    private List<Integer> pageNumbers;
}
