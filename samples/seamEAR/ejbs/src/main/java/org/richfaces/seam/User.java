/**
 * 
 */
package org.richfaces.seam;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

/**
 * @author asmirnov
 *
 */
@Entity
@Name("user")
@Scope(ScopeType.SESSION)
@Table(name="users")
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3165725145949171858L;

	private String username;
	
	private String password;

	private String name;
	   public User(String name, String password, String username)
	   {
	      this.name = name;
	      this.password = password;
	      this.username = username;
	   }
	   
	   public User() {};
	   
	   @NotNull @Length(min=5, max=15)
	   public String getPassword()
	   {
	      return password;
	   }

	   public void setPassword(String password)
	   {
	      this.password = password;
	   }
	   
	   @NotNull
	   public String getName()
	   {
	      return name;
	   }

	   public void setName(String name)
	   {
	      this.name = name;
	   }
	   
	   @Id @NotNull @Length(min=5, max=15)
	   public String getUsername()
	   {
	      return username;
	   }

	   public void setUsername(String username)
	   {
	      this.username = username;
	   }
}
