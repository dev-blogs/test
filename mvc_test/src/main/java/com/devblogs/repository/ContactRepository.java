package com.devblogs.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import com.devblogs.model.Contact;

public interface ContactRepository extends PagingAndSortingRepository<Contact, Long> {
}