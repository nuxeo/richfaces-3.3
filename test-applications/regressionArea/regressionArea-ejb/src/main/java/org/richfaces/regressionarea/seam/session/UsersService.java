 /*
 * UsersService.java		Date created: 18.09.2008
 * Last modified by: $Author$
 * $Revision$	$Date$
 */
package org.richfaces.regressionarea.seam.session;

import java.util.List;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.richfaces.regressionarea.seam.entity.User;
import org.richfaces.regressionarea.seam.interfaces.IUsersService;

/**
 * TODO Class description goes here.
 * @author Andrey Markavtsov 
 *
 */

@Stateful
@Name("usersService")
@Scope(ScopeType.APPLICATION)
@AutoCreate
@SessionExpired
public class UsersService implements IUsersService {
	
	@PersistenceContext(unitName = "seam", type = PersistenceContextType.EXTENDED)
	private EntityManager em;

	@Remove
	@Destroy
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("unchecked")
	public List<User> getUsers() {
		List<User> list = null;
		list = em.createQuery("from User").getResultList();
		return list;
	}

}
