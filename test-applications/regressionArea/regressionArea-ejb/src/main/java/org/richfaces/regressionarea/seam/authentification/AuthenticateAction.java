package org.richfaces.regressionarea.seam.authentification;

import java.io.Serializable;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Log;
import org.jboss.seam.web.Session;

/**
 * Used to authenticate user.
 *  
 * @author vgolub
 */
@Name("authenticator")
public class AuthenticateAction implements Serializable {

	/**
	 * Serialization constant.
	 */
    private static final long serialVersionUID = 3896347934570841355L;

	/**
	 * Log instance.
	 */
	@Logger 
	private Log log;
    
	/**
	 * {@link User} instance.
	 */
	@In(value = "user")
    private User user;
	
	/**
	 * Used to reload session.
	 * 
	 * @return outcome which redirects to login page
	 */
	public String reloadSession() {
		Session.instance().invalidate();
   		
       	return "reloaded";
	}
    
	/**
	 * Used to authenticate user.
	 * 
	 * @return outcome which redirects to home page
	 */
    public String authenticate() {
    	// TODO: implement
    	log.info("authenticating: " + user.getLogin());
        
        return "loginned";
    }
}
