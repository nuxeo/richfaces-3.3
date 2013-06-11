 /*
 * IUsersService.java		Date created: 18.09.2008
 * Last modified by: $Author$
 * $Revision$	$Date$
 */
package org.richfaces.regressionarea.seam.interfaces;

import java.util.List;

import javax.ejb.Local;

import org.richfaces.regressionarea.seam.entity.User;

/**
 * TODO Class description goes here.
 * @author Andrey Markavtsov
 *
 */

@Local
public interface IUsersService {
	
	void destroy();
	
	List<User> getUsers();

}
