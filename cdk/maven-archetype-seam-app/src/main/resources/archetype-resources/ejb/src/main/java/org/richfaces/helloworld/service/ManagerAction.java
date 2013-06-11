package org.richfaces.helloworld.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.richfaces.helloworld.domain.Person;

@Stateless
@Name("manager")
public class ManagerAction implements Manager {
	@In @Out
	private Person person;
	@Out
	private List<Person> fans;
	@PersistenceContext
	private EntityManager em;

	public String sayHello() {
		em.persist(person);
		person = new Person();
		fans = em.createQuery("select p from Person p").getResultList();
		return null;
	}
}