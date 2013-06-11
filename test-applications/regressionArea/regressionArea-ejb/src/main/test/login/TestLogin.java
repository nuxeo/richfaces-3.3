 /*
 * TestLogin.java		Date created: May 15, 2008
 * Last modified by: $Author$
 * $Revision$	$Date$
 */
package login;

import org.jboss.seam.core.Manager;
import org.jboss.seam.mock.SeamTest;
import org.jboss.seam.security.Identity;
import org.jboss.seam.web.Session;
import org.testng.annotations.Test;

/**
 * TODO Class description goes here.
 * @author aareshchanka
 *
 */

public class TestLogin	extends SeamTest {

	    @Test
	    public void testAuthenticateComponent() throws Exception {
	        new ComponentTest() {
	            protected void testComponents() throws Exception {
	                setValue("#{identity.username}", "admin");
	                setValue("#{identity.password}", "admin");
	                Identity.instance().setPassword("admin");
	                Identity.instance().setUsername("admin");
	                setField(Identity.instance(), "password", "admin");
	                setField(Identity.instance(), "username", "admin");
	                invokeMethod("#{identity.login}");
	                assert invokeMethod("#{identity.login}").equals(true);
	                assert getValue("#{identity.username}").equals("admin");
	                assert getValue("#{identity.password}").equals("admin");
	            }
	        }.run();
	    }

	    @Test
	    public void testLogin() throws Exception {

	        new FacesRequest() {

	            @Override
	            protected void invokeApplication() {
	                assert !isSessionInvalid();
	                assert getValue("#{identity.loggedIn}").equals(false);
	            }

	        }.run();

	        new FacesRequest() {

	            @Override
	            protected void updateModelValues() throws Exception {
	                assert !isSessionInvalid();
	                setValue("#{identity.username}", "admin");
	                setValue("#{identity.password}", "admin");
	            }

	            @Override
	            protected void invokeApplication() {
	                invokeAction("#{identity.login}");
	            }

	            @Override
	            protected void renderResponse() {
	                assert getValue("#{user.username}").equals("admin");
	                assert getValue("#{user.password}").equals("admin");
	                assert !Manager.instance().isLongRunningConversation();
	                assert getValue("#{identity.loggedIn}").equals(true);
	            }

	        }.run();

	        new FacesRequest() {

	            @Override
	            protected void invokeApplication() {
	                assert !isSessionInvalid();
	                assert getValue("#{identity.loggedIn}").equals(true);
	            }

	        }.run();

	        new FacesRequest() {

	            @Override
	            protected void invokeApplication() {
	                assert !Manager.instance().isLongRunningConversation();
	                assert !isSessionInvalid();
	                invokeMethod("#{identity.logout}");
	                assert Session.instance().isInvalid();
	            }

	            @Override
	            protected void renderResponse() {
	                assert getValue("#{identity.loggedIn}").equals(false);
	                assert Session.instance().isInvalid();
	            }

	        }.run();

	    }

}
