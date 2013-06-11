
package org.richfaces.demo.converters;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.richfaces.demo.converters.utils.ConverterUtils;
import org.richfaces.demo.datagrid.model.Channel;
import org.richfaces.demo.datagrid.model.Issue;
import org.richfaces.demo.datagrid.model.JiraUser;

/**
 * @author Anton Belevich
 *
 */
public class JiraUserConverter implements Converter, StateHolder {

	private List <? super JiraUser> jiraUsersList = new ArrayList<JiraUser>(); 
	
	private Channel channel;
	
	private boolean transientFlag;
	
		
	public JiraUserConverter() {
		transientFlag = false;
	}

	public Object getAsObject(FacesContext context, UIComponent component,
			String value) throws ConverterException{
		
		JiraUser user = findUser(value);
//		System.out.println("JiraUserConverter.getAsObject()");
		if( user == null){
			FacesMessage errorMessage = ConverterUtils.createTypeErrorMessage(value,null,ConverterUtils.ERROR_2);
			throw new ConverterException(errorMessage);
		}
		return user;
	}

	public String getAsString(FacesContext context, UIComponent component,
			Object value) throws ConverterException{
		
		JiraUser jiraUser = null;
		
		if(value instanceof JiraUser){
			jiraUser = (JiraUser)value;
		}else{
			FacesMessage errorMessage = ConverterUtils.createTypeErrorMessage(value.getClass().getName(), JiraUser.class.getName(),ConverterUtils.ERROR_1);
			throw new ConverterException(errorMessage);
		}
		
		return jiraUser.getName();
	}

	public boolean isTransient() {
		return transientFlag;
	}

	public void restoreState(FacesContext context, Object state) {
		Object [] values = (Object[])state;
		jiraUsersList = (List<? super JiraUser>)values[0]; 
	}

	public Object saveState(FacesContext context) {
		Object [] values = new Object[1];
		values [0] = jiraUsersList;
		return values;
	}

	public void setTransient(boolean transientFlag) {
		this.transientFlag = transientFlag;
	}
	
	private void createJiraUsersList(Channel channel){
		
		List issuesList = channel.getIssues();
		for (Iterator iter = issuesList.iterator(); iter.hasNext();) {
			Issue element = (Issue) iter.next();
			JiraUser jiraUser = element.getAssignee(); 
		
			if((jiraUser != null) && (!jiraUsersList.contains(jiraUser))){
				jiraUsersList.add(jiraUser);
			}
		}
	}
	
	public JiraUser findUser(String  user){
		
		for (Iterator iter = jiraUsersList.iterator(); iter.hasNext();) {
			JiraUser jiraUser = (JiraUser) iter.next();
			String name = jiraUser.getName();
			if(name != null){
				if(name.equals(user)){
					return jiraUser;	
				}
			
			}
		}
		
		
	
		return null;
	}
	
	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
		createJiraUsersList(channel);
	}
	
	
}
