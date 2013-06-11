package org.richfaces.test.staging;

import static org.junit.Assert.*;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletException;

import org.junit.Test;
import org.richfaces.test.staging.RequestChain;
import org.richfaces.test.staging.ServerResource;
import org.richfaces.test.staging.ServletContainer;
import org.richfaces.test.staging.StaticServlet;
import org.richfaces.test.staging.StagingServletContext;

public class ServletTest {

	@Test
	public void testIsApplicable() {
		StaticServlet staticServlet = new StaticServlet();
		RequestChain servlet = new ServletContainer("/foo/*",staticServlet);
		assertTrue(servlet.isApplicable("/foo/bar.jsf"));
		assertFalse(servlet.isApplicable("/foz/bar.jsf"));
		assertFalse(servlet.isApplicable("bar"));
		servlet = new ServletContainer("*.jsf",staticServlet);
		assertTrue(servlet.isApplicable("/foo/bar.jsf"));
		assertFalse(servlet.isApplicable("bar"));
		try {
			servlet = new ServletContainer(".jsf",staticServlet);
		} catch (IllegalArgumentException e) {
			return;
		}
		assertFalse(true);
	}

	@Test
	public void testGetServletPath() {
		StaticServlet staticServlet = new StaticServlet();
		RequestChain servlet = new ServletContainer("/foo/*",staticServlet);
		assertEquals("/foo/", servlet.getServletPath("/foo/bar.jsf"));
		assertNull(servlet.getServletPath("/foz/bar.jsf"));
		servlet = new ServletContainer("*.jsf",staticServlet);
		assertEquals("/foo/bar.jsf", servlet.getServletPath("/foo/bar.jsf"));
	}

	@Test
	public void testGetPathInfo() {
		StaticServlet staticServlet = new StaticServlet();
		RequestChain servlet = new ServletContainer("/foo/*",staticServlet);
		assertEquals("bar.jsf", servlet.getPathInfo("/foo/bar.jsf"));
		assertNull(servlet.getPathInfo("/foz/bar.jsf"));
		servlet = new ServletContainer("*.jsf",staticServlet);
		assertNull(servlet.getPathInfo("/foo/bar.jsf"));

	}

	@Test
	public void testInit() throws ServletException {
		StaticServlet staticServlet = new StaticServlet();
		RequestChain servlet = new ServletContainer("/foo/*",staticServlet);
		StagingServletContext context = new StagingServletContext(){

			public String getMimeType(String file){
				return null;
			}

			@Override
			protected ServerResource getServerResource(String path) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			protected void valueBound(ServletContextAttributeEvent event) {
				// TODO Auto-generated method stub
				
			}

			@Override
			protected void valueReplaced(ServletContextAttributeEvent event) {
				// TODO Auto-generated method stub
				
			}

			@Override
			protected void valueUnbound(
					ServletContextAttributeEvent servletContextAttributeEvent) {
				// TODO Auto-generated method stub
				
			}
			
		};
		servlet.init(context);
		assertSame(context,staticServlet.getServletContext());
	}

}
