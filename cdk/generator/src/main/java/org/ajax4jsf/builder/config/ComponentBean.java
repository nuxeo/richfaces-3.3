/**
 * License Agreement.
 *
 * Rich Faces - Natural Ajax for Java Server Faces (JSF)
 *
 * Copyright (C) 2007 Exadel, Inc.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License version 2.1 as published by the Free Software Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA
 */

package org.ajax4jsf.builder.config;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;



/**
 * JavaBean to hold properties of component configuration.
 * At parsing configuration time, all propertiec can be set as attributes or
 * child elements of &lt;component&gt: element/.
 * @author asmirnov@exadel.com (latest modification by $Author: alexsmirnov $)
 * @version $Revision: 1.1.2.8 $ $Date: 2007/03/01 17:16:00 $
 *
 */
public class ComponentBean extends ComponentBaseBean {
	
	/**
	 * Component family
	 */
	private String _family;
	
	/**
	 * Component child facets, for faces-config.xml
	 */
	private Set<JsfBean> _facets = new HashSet<JsfBean>();
	
	/**
	 * Renderer description for this components
	 */
	private RendererBean renderer;
	
	/**
	 * Events used by this component. 
	 */
	private Set<EventBean> events = new HashSet<EventBean>();
	
	/**
	 * @return Returns the facet.
	 */
	public Set<JsfBean> getFacets() {
		return _facets;
	}

	/**
	 * @param facet The facet to set.
	 */
	public void addFacet(JsfBean facet) {
		_facets.add(facet);
	}

	/**
	 * @return Returns the family.
	 */
	public String getFamily() {
		return _family;
	}

	/**
	 * @param family The family to set.
	 */
	public void setFamily(String family) {
		_family = family;
	}

	/**
	 * @return Returns the renderer.
	 */
	public RendererBean getRenderer() {
		return renderer;
	}

	/**
	 * @param renderer The renderer to set.
	 */
	public void setRenderer(RendererBean renderer) {
		this.renderer = renderer;
		renderer.setParent(this);
	}

	/**
	 * @return the events
	 */
	public Set<EventBean> getEvents() {
		return this.events;
	}
	
	public void addEvent(EventBean event) {
		this.events.add(event);
		event.setParent(this);
	}

	@Override
	public TestClassHolder getTest() {
		return super.getTest();
	}
	
	@Override
	public void setTest(TestClassHolder test) {
		super.setTest(test);
		test.setClassname(getClassname() + "ComponentTest");
	}
	
	@Override
	/**
	 * Component checks
	 */
	public void checkProperties() throws ParsingException {
		super.checkProperties();
		if (null != getRenderer()) {
			getRenderer().checkProperties();
		}
		for (EventBean event : getEvents()) {
			event.checkProperties();
		}
		// Attempt to check component Family
		if (null == getFamily()) {
			try {
				Class<?> superClass = getLoader().loadClass(getSuperclass());
				Object componentInstance =  superClass
						.newInstance();
				String family = (String) PropertyUtils.getProperty(componentInstance, "family");
				setFamily(family);
				getLog().debug("Detect family of component as "+family);
			} catch (Exception e) {
				getLog().error("Error for create instance of component "+getSuperclass()+" exception: "+e.getClass().getName()+" with message "+e.getMessage());
				throw new ParsingException("'Family' property not set for component"+getName());
			}
		}
	}
}
