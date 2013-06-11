package org.richfaces.demo.common;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

public class ComponentNavigator {
	private String lastCompId = null;
	private List components = null;
	private ComponentDescriptor currentComponent;
	private List componentGroups = null;
	private Object activeTab;
	
	public boolean getHasCurrentComponent() {
		return currentComponent!=null;
	}
	
	public Object getActiveTab() {
		return activeTab;
	}
	public void setActiveTab(Object activeTab) {
		this.activeTab = activeTab;
	}
	public ComponentDescriptor getCurrentComponent() {
		String id = getComponentParam();
		if (id!=null) {
			setCurrentComponent(findComponentById(id));
			lastCompId = id;
		} else if (lastCompId!=null) {
			setCurrentComponent(findComponentById(lastCompId));
		} else {
			String uri = getComponentUri();
			setCurrentComponent(findComponentByUri(uri));
		}
		return currentComponent;
	}
	private String getComponentUri() {
		FacesContext fc = FacesContext.getCurrentInstance();
		return ((HttpServletRequest)fc.getExternalContext().getRequest()).getRequestURI();
	}
	private String getComponentParam() {
		FacesContext fc = FacesContext.getCurrentInstance();
		String param = (String) fc.getExternalContext().getRequestParameterMap().get("c");
		if (param!=null && param.trim().length()>0) {
			return param;
		} else {
			return null;
		}
	}
	
	private List components_() {
		if (components==null) {
			loadComponents();
		}
		return components;
	}
	public ComponentDescriptor findComponentByUri(String uri) {
		Iterator it = components_().iterator();
		while (it.hasNext()) {
			ComponentDescriptor component = (ComponentDescriptor) it.next();
			if (uri.endsWith(component.getDemoLocation())) {
				return component;
			}
		}
		return null;
	}
	public ComponentDescriptor findComponentById(String id) {
		Iterator it = components_().iterator();
		while (it.hasNext()) {
			ComponentDescriptor component = (ComponentDescriptor) it.next();
			if (component.getId().equals(id)) {
				return component;
			}
		}
		return null;
	}
	
	public void setCurrentComponent(ComponentDescriptor currentComponent) {
		if (currentComponent==null) {
			setActiveTab(null);
			this.currentComponent = (ComponentDescriptor)components_().get(0);
		}
		if (this.currentComponent!=null && currentComponent!=null & !this.currentComponent.equals(currentComponent)) {
			setActiveTab(null);
		} else if (this.currentComponent==null || currentComponent==null) {
			setActiveTab(null);
		}
		this.currentComponent = currentComponent;
	}

	public List getComponentGroups() {
		return componentGroups;
	}
	public void setComponentGroups(List componentGroups) {
		this.componentGroups = componentGroups;
	}
	
	private List getFilteredComponents(String group) {
		List ret = new ArrayList();
		Iterator it = getComponents().iterator();
		while (it.hasNext()) {
			ComponentDescriptor desc = (ComponentDescriptor) it.next();
			if (desc.getGroup().equals(group)) {
				ret.add(desc);
			}
		}
		return ret;
	}
	
	public List getRichDragDropComponents() {
		return getFilteredComponents("richDragDrop");
	}
	public List getRichDataIterators() {
		return getFilteredComponents("richDataIterators");
	}
	public List getRichMenu() {
		return getFilteredComponents("richMenu");
	}
	public List getRichTree() {
		return getFilteredComponents("richTree");
	}
	public List getRichInputs() {
		return getFilteredComponents("richInputs");
	}
	public List getRichOutputs() {
		return getFilteredComponents("richOutputs");
	}
	public List getAjaxSupport() {
		return getFilteredComponents("ajaxSupport");
	}
	public List getRexc() {
		return getFilteredComponents("rexc");
	}
	public List getAjaxResources() {
		return getFilteredComponents("ajaxResources");
	}
	public List getAjaxOutput() {
		return getFilteredComponents("ajaxOutput");
	}
	public List getAjaxMisc() {
		return getFilteredComponents("ajaxMisc");
	}
	public List getRichMisc() {
		return getFilteredComponents("richMisc");
	}
	
	public List getComponents() {
		Iterator it = components_().iterator();
		ComponentDescriptor cur = getCurrentComponent();
		while (it.hasNext()) {
			ComponentDescriptor desc = (ComponentDescriptor) it.next();
			if (desc.equals(cur)) {
				desc.setCurrent(true);
			} else {
				desc.setCurrent(false);
			}
		}
		return components;
	}

	public void setComponents(List components) {
		this.components = components;
	}
	
	private void loadComponents() {
		Properties props = new Properties();
		List temp = new ArrayList();
		try {
			InputStream is = this.getClass().getClassLoader().getResourceAsStream("/org/richfaces/demo/common/components.properties");
			props.load(is);
		} catch (Exception e) {
			throw new FacesException(e);
		}
		Set entries = props.entrySet();
		Iterator it = entries.iterator();
		while (it.hasNext()) {
			Map.Entry e = (Map.Entry) it.next();
			ComponentDescriptor desc = new ComponentDescriptor();
			desc.setId(e.getKey().toString().trim());
			StringTokenizer toc = new StringTokenizer(e.getValue().toString(),",");
//			#id=name,captionImage,iconImage,devGuideLocation,tldDocLocation,javaDocLocation
			desc.setGroup(toc.nextToken().trim());
			desc.setName(toc.nextToken().trim());
			desc.setIconImage(toc.nextToken().trim());
			desc.setCaptionImage(toc.nextToken().trim());
			desc.setDevGuideLocation(toc.nextToken().trim());
			desc.setTldDocLocation(toc.nextToken().trim());
			desc.setJavaDocLocation(toc.nextToken().trim());
			desc.setDemoLocation(toc.nextToken().trim());
			temp.add(desc);
		}
		Collections.sort(temp, new Comparator() {
			public int compare(Object o1, Object o2) {
				ComponentDescriptor d1 = (ComponentDescriptor)o1;
				ComponentDescriptor d2 = (ComponentDescriptor)o2;
				return d1.getName().compareTo(d2.getName());
			}});
		setComponents(temp);
		setCurrentComponent((ComponentDescriptor) temp.get(0));
	}
}
