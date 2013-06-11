package org.richfaces.demo.datagrid.service;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.richfaces.demo.datagrid.model.Channel;
import org.richfaces.demo.datagrid.service.JiraService;

public class JiraServiceTest extends AbstractAjax4JsfTestCase {

	public JiraServiceTest(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public void setUp() throws Exception {
		super.setUp();
	}

	public void tearDown() throws Exception {
		super.tearDown();
	}

	public void testJiraService() {
		//fail("Not yet implemented");
	}

	public void testGetChannel() {
		Channel channel = new JiraService().getChannel();
		assertNotNull(channel);
	}

	public void testSetChannel() {
		//fail("Not yet implemented");
	}

	public void testGetInstance() {
		//fail("Not yet implemented");
	}

}
