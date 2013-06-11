package org.richfaces.regressionarea.seam.session;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.jboss.seam.annotations.intercept.AroundInvoke;
import org.jboss.seam.intercept.InvocationContext;

public class SessionExpiredInterceptor {

	@AroundInvoke
	public Object checkLoggedIn(InvocationContext invocation) throws Exception {
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();

		if (request.getRequestedSessionId() != null
				&& request.getSession().isNew()) {

			// the user is not logged in, fwd to login page
			return "login";

		} else {
			// the user is already logged in
			return invocation.proceed();
		}
	}

}