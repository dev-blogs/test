package com.devblogs.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.devblogs.domain.Contact;

public interface ContactRepository extends CrudRepository<Contact, Long> {
	
	@Query("select count(c) from Contact c")
	public Long countAllContacts();
}