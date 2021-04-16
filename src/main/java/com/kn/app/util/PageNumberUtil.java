/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kn.app.util;

import com.kn.app.entity.Contact;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

/**
 *
 * @author oghomwen.aigbedion
 */
@Component
public class PageNumberUtil {

    public List<Integer> determinePageNumbers(Page<Contact> contactsPage, int currentPage) {
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
