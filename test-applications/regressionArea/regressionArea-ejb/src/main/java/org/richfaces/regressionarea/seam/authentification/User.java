package org.richfaces.regressionarea.seam.authentification;

import java.io.Serializable;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

/**
 * Class represents user session entity.
 *  
 * @author vgolub
 */
@Name("user")
@Scope(ScopeType.SESSION)
public class User implements Serializable {

	/**
	 * Serialization constant.
	 */
	private static final long serialVersionUID = -8534340080474811527L;

	/**
	 * User login.
	 */
	private String login;
	
	/**
	 * User password.
	 */
	private String password;
	
	/**
	 * Property used to remember user login if true.
	 */
	private boolean rememberMe;

	/**
	 * Constructor.
	 * 
	 * @param login user login
	 * 
	 * @param password user password
	 */
	public User(String login, String password) {
		this.setLogin(login);
		this.setPassword(password);
	}

	/**
	 * Default constructor.
	 */
	public User() {
	}

	/**
	 * Setter for the user login.
	 * 
	 * @param login the user login.
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * Getter for the user login.
	 * 
	 * @return the user login.
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * Setter for the user password.
	 * 
	 * @param password the user password.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Getter for the user password.
	 * 
	 * @return the user password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Setter for the user rememberMe property. 
	 * 
	 * @param rememberMe the user rememberMe flag.
	 */
	public void setRememberMe(boolean rememberMe) {
		this.rememberMe = rememberMe;
	}

	/**
	 * Getter for the user rememberMe property.
	 * 
	 * @return the user rememberMe property.
	 */
	public boolean isRememberMe() {
		return rememberMe;
	}
}
