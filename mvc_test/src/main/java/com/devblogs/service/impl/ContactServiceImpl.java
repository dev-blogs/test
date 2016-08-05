package com.devblogs.service.impl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.devblogs.model.Contact;
import com.devblogs.service.ContactService;

@Service("contactService")
@Repository
@Transactional
public class ContactServiceImpl implements ContactService {
	@PersistenceContext
	private EntityManager em;

	@Transactional(readOnly=true)
	public List<Contact> findAll() {
		return em.createQuery("from Contact c", Contact.class).getResultList();
	}

	@Transactional(readOnly=true)
	public Contact findById(Long id) {
		TypedQuery<Contact> query = em.createQuery("from Contact c where c.id = :id", Contact.class);
        query.setParameter("id", id);
        return query.getSingleResult();
	}

	public Contact save(Contact contact) {
		if (contact.getId() == null) {
            em.persist(contact);
        } else {
            em.merge(contact);
        }
        return contact;
	}
}