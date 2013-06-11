/*
 *  Copyright
 *      Copyright (c) Exadel,Inc. 2006
 *      All rights reserved.
 *  
 *  History
 *      $Source: /cvs-master/intralinks-jsf-comps/web-projects/data-view-grid-demo/src/com/exadel/jsf/demo/datagrid/service/JiraService.java,v $
 *      $Revision: 1.5 $ 
 */

package org.richfaces.demo.datagrid.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import javax.el.ELContext;
import javax.el.ELResolver;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.digester.Digester;
import org.apache.commons.digester.ExtendedBaseRules;
import org.apache.commons.digester.Rule;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.richfaces.demo.datagrid.model.Channel;
import org.richfaces.demo.datagrid.model.Issue;
import org.richfaces.demo.datagrid.model.JiraUser;
import org.richfaces.demo.datagrid.model.Key;
import org.richfaces.demo.datagrid.model.Priority;
import org.richfaces.demo.datagrid.model.Status;
import org.richfaces.demo.datagrid.model.Type;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author Maksim Kaszynski
 *
 */
public class JiraService {

	public static final String ORG_RICHFACES_ISSUES = "org.richfaces.demo.MAX_ISSUES";

	private static final String KEY = "jiraService";
	
	private static final Log log = LogFactory.getLog(JiraService.class);
	
	/**
	 * 
	 */
	public JiraService() {
		super();
		log.trace("---> JiraService.JiraService()");
		init();
	}

	private Channel channel = null;
	public Channel getChannel() {
		
		if (channel == null) {
			init();
		}
		
		return channel;
	}
	public void setChannel(Channel channel) {
		this.channel = channel;
	}


	static class JiraCursedRule extends Rule {

		private String propertyName;

		public JiraCursedRule(String propertyName) {
			super();
			this.propertyName = propertyName;
		}

		@Override
		public void body(String namespace, String name, String text) throws Exception {
			Object bean = digester.peek();
			PropertyUtils.setSimpleProperty(bean, propertyName, text.trim());
			
		}

		@Override
		public void finish() throws Exception {
			// TODO Auto-generated method stub
			super.finish();
		}
	}

	
	private void init() {
		System.out.println("JiraService.init()");
		
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		String string = 
			externalContext.getInitParameter(ORG_RICHFACES_ISSUES);
		
		int defaultSize = -1;
		try {
			defaultSize = Integer.parseInt(string);
		} catch (Exception e) {
			System.out.println("Parameter " + ORG_RICHFACES_ISSUES + " not defined. Using default value -1.");
		}
		
		
		channel = new Channel(defaultSize);
		Digester digester = new Digester();
		digester.setValidating(false);
		digester.setNamespaceAware(false);
		digester.setEntityResolver(new EntityResolver() {
			public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
				// TODO Auto-generated method stub
				return null;
			}
		});
		digester.setRules(new ExtendedBaseRules());
		
		digester.push(channel);
		
		String path = "rss/channel";
		digester.addBeanPropertySetter(path + "/?");
		
		path = path + "/item";
		String issuePath = path;
		digester.addObjectCreate(path, Issue.class);
		digester.addSetProperties(path);
		digester.addBeanPropertySetter(path + "/?");
		digester.addSetNext(path, "addIssue");
		
		path = issuePath + "/key";
		digester.addObjectCreate(path, Key.class);
		digester.addSetProperties(path);
		digester.addRule(path, new JiraCursedRule("value"));
		digester.addBeanPropertySetter(path + "/?");
		digester.addSetNext(path, "setKey");
		
		path = issuePath + "/priority";
		digester.addObjectCreate(path, Priority.class);
		digester.addSetProperties(path);
		digester.addBeanPropertySetter(path + "/?");
		digester.addRule(path, new JiraCursedRule("name"));
		digester.addSetNext(path, "setPriority");
		
		path = issuePath + "/status";
		digester.addObjectCreate(path, Status.class);
		digester.addSetProperties(path);
		digester.addBeanPropertySetter(path + "/?");
		digester.addRule(path, new JiraCursedRule("name"));
		digester.addSetNext(path, "setStatus");

		path = issuePath + "/assignee";
		digester.addObjectCreate(path, JiraUser.class);
		digester.addSetProperties(path);
		digester.addBeanPropertySetter(path + "/?");
		digester.addRule(path, new JiraCursedRule("name"));
		digester.addSetNext(path, "setAssignee");
		
		path = issuePath + "/reporter";
		digester.addObjectCreate(path, JiraUser.class);
		digester.addSetProperties(path);
		digester.addBeanPropertySetter(path + "/?");
		digester.addRule(path, new JiraCursedRule("name"));
		digester.addSetNext(path, "setReporter");
		
		path = issuePath + "/type";
		digester.addObjectCreate(path, Type.class);
		digester.addSetProperties(path);
		digester.addBeanPropertySetter(path + "/?");
		digester.addRule(path, new JiraCursedRule("name"));
		digester.addSetNext(path, "setType");
		try {
			//InputStream stream; //= Thread.currentThread().getContextClassLoader().getResourceAsStream();
			//stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/WEB-INF/xmldata/jira.xml");
			//JarInputStream stream = getXMLInputStream();
			InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("jira-issues.jar");
			JarInputStream jarInputStream = new JarInputStream(stream);
			JarEntry entry = null;

			while((entry = jarInputStream.getNextJarEntry()) != null) {
				String name = entry.getName();
				if ("jira.xml".equals(name)) {
					digester.parse(jarInputStream);
					break;
				}
			}
			jarInputStream.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public static final JiraService getInstance() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ELContext elContext = facesContext.getELContext();
		ELResolver resolver = elContext.getELResolver();
		Object value = resolver.getValue(elContext, null, KEY);
		return (JiraService) value;
	}
}
