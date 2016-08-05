package com.devblogs.service;

import java.util.List;
import com.devblogs.model.Contact;

public interface ContactService {
	public List<Contact> findAll();
	public Contact findById(Long id);
	public Contact save(Contact contact);
}