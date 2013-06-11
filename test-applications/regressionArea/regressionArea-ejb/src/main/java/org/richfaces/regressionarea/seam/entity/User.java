 /*
 * User.java		Date created: 17.09.2008
 * Last modified by: $Author$
 * $Revision$	$Date$
 */
package org.richfaces.regressionarea.seam.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.jboss.seam.annotations.Name;

/**
 * TODO Class description goes here.
 * @author Andrey Markavtsov
 *
 */
@Entity
@Name("RichUser")
@Table(name="RichUser")
public class User {
	
	 @Id
	  @GeneratedValue
	   @Column(name = "ID")
	private Long id;
	
	 
	 @Column(name = "NAME")
	private String name;
	 
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}
