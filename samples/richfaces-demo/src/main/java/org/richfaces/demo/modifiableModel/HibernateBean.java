/**
 * 
 */
package org.richfaces.demo.modifiableModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.faces.FacesException;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.classic.Session;
import org.richfaces.model.SortOrder;

/**
 * @author mikalaj
 *
 */
public class HibernateBean {

	private SessionFactory factory;
	
	private static final String[] CSV_FIELDS = {
		"issueType", "key", "summary",
		"assignee", "fixVersion", "reporter", "priority", "status",
		"resolution", "created", "updated"
	};
	
	private static final Method[] CSV_FIELDS_SETTERS;
	
	public String[] getCsvFields() {
		return CSV_FIELDS;
	}
	
	static {
		CSV_FIELDS_SETTERS = new Method[CSV_FIELDS.length];

		for (int i = 0; i < CSV_FIELDS.length; i++) {
			char[] cs = CSV_FIELDS[i].toCharArray();
			cs[0] = Character.toUpperCase(cs[0]);
			
			try {
				CSV_FIELDS_SETTERS[i] = DataItem.class.getMethod("set" + new String(cs), String.class);
			} catch (SecurityException e) {
				throw new FacesException(e.getMessage(), e);
			} catch (NoSuchMethodException e) {
				throw new FacesException(e.getMessage(), e);
			}
		}
	}
	
	public HibernateBean() {
		Configuration configuration = new Configuration();
		configuration.addResource("dataItem.hbm.xml");
		configuration.configure();

		factory = configuration.buildSessionFactory();
		Session session = factory.openSession();
		
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(
				new InputStreamReader(getClass().getResourceAsStream("/JIRA.csv")));

			String line;
			
			while ((line = reader.readLine()) != null) {
				String[] split = line.split(";");

				DataItem dataItem = new DataItem();
				for (int i = 0; i < split.length && i < CSV_FIELDS_SETTERS.length; i++) {
					try {
						CSV_FIELDS_SETTERS[i].invoke(dataItem, split[i]);
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				session.persist(dataItem);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		session.flush();
		session.close();
	}
	
	public SessionFactory getSessionFactory() {
		return factory;
	}
	
}
