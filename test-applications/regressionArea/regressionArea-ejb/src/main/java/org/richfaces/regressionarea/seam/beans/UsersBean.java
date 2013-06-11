 /*
 * UsersBean.java		Date created: 18.09.2008
 * Last modified by: $Author$
 * $Revision$	$Date$
 */
package org.richfaces.regressionarea.seam.beans;

import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.richfaces.regressionarea.seam.entity.User;
import org.richfaces.regressionarea.seam.interfaces.IUsersService;
import org.richfaces.regressionarea.seam.session.SessionExpired;


/**
 * TODO Class description goes here.
 * @author Andrey Markavstov
 *
 */

@Name("usersBean")
@Scope(ScopeType.SESSION)
@SessionExpired
public class UsersBean {
	
	@In(create=true, required=true)
	private IUsersService usersService;
	
	@SuppressWarnings("unchecked")
	public List<User> getUsers() {
		return usersService.getUsers();
	}
	
}
