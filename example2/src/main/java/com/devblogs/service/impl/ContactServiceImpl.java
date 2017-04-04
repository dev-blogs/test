package com.devblogs.service.impl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import com.devblogs.domain.Contact;
import com.devblogs.service.ContactService;

@Service("contactService")
@Repository
@Transactional
public class ContactServiceImpl implements ContactService {
	@PersistenceContext
	private EntityManager em;
	@Autowired
	private TransactionTemplate transactionTemplate;

	@Transactional(readOnly=true)
	public List<Contact> findAll() {
		transactionTemplate.setReadOnly(true);
		return transactionTemplate.execute(new TransactionCallback<List<Contact>>() {
			public List<Contact> doInTransaction(TransactionStatus status) {
				return em.createNamedQuery("Contact.findAll", Contact.class).getResultList();
			}
		});
	}

	@Transactional(readOnly=true)
	public Contact findById(final Long id) {
		transactionTemplate.setReadOnly(true);
		return transactionTemplate.execute(new TransactionCallback<Contact>() {
			public Contact doInTransaction(TransactionStatus status) {
				TypedQuery<Contact> query = em.createNamedQuery("Contact.findById", Contact.class);
				query.setParameter("id", id);
				return query.getSingleResult();
			}
		});
	}

	public Contact save(final Contact contact) {
		transactionTemplate.setReadOnly(false);
		return transactionTemplate.execute(new TransactionCallback<Contact>() {
			public Contact doInTransaction(TransactionStatus status) {
				if (contact.getId() == null) {
		            em.persist(contact);
		        } else {
		            em.merge(contact);
		        }
				return contact;
			}
		});
	}

	@Transactional(propagation=Propagation.NEVER)
	public long countAll() {
		return 0;
	}
}