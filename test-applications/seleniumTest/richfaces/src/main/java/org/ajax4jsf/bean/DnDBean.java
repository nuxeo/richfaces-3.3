/**
 * License Agreement.
 *
 * JBoss RichFaces - Ajax4jsf Component Library
 *
 * Copyright (C) 2007 Exadel, Inc.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License version 2.1 as published by the Free Software Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 */ 
package org.ajax4jsf.bean;

import java.util.ArrayList;
import java.util.List;

import org.richfaces.component.Dropzone;
import org.richfaces.event.DragEvent;
import org.richfaces.event.DropEvent;

/**
 * copied from demo and tailored
 */
public class DnDBean {
	
	public static final String DRAGLISTENER = "DragListener,";
	
	public static final String DROPLISTENER = "DropListener,";
	
	public static final String RICHDROPLISTENER = "RichDropListener,";
	
	public static final String RICHDRAGLISTENER = "RichDragListener,";
	
    private List<Framework> containerPHP;

    private List<Framework> containerCF;

    private List<Framework> containerDNET;

    private List<Framework> frameworks;
    
    private String status = "";
    
    private Object dropValue;
    
    private Object dragValue;
    
    private Boolean rendered;

    public DnDBean() {
        initList();
    }
    

    public List<Framework> getContainerPHP() {
        return containerPHP;
    }

    public List<Framework> getContainerCF() {
        return containerCF;
    }

    public List<Framework> getContainerDNET() {
        return containerDNET;
    }

    public List<Framework> getFrameworks() {
        return frameworks;
    }

    public void moveFramework(Object fm, Object family) {
        List<Framework> target = null;
        if ("PHP".equals(family)) {
            target = containerPHP;
        } else if ("DNET".equals(family)) {
            target = containerDNET;
        } else if ("CF".equals(family)) {
            target = containerCF;
        }

        if (null != target && frameworks.contains(fm)) {
            target.add((Framework) fm);
            frameworks.remove(fm);
        }
    }

    public String reset() {
    	status = "";
    	dropValue = null;
    	dragValue = null;
    	rendered = true;
        initList();
        return null;
    }
    
    public String testRendered() {
    	rendered = false;
    	return null;
    }

    private void initList() {
        frameworks = new ArrayList<Framework>();
        frameworks.add(new Framework("Flexible Ajax", "PHP"));
        frameworks.add(new Framework("ajaxCFC", "CF"));
        frameworks.add(new Framework("AJAXEngine", "DNET"));
        frameworks.add(new Framework("AjaxAC", "PHP"));
        frameworks.add(new Framework("MonoRail", "DNET"));
        frameworks.add(new Framework("wddxAjax", "CF"));
        frameworks.add(new Framework("AJAX AGENT", "PHP"));
        frameworks.add(new Framework("FastPage", "DNET"));
        frameworks.add(new Framework("JSMX", "CF"));
        frameworks.add(new Framework("PAJAJ", "PHP"));
        frameworks.add(new Framework("Symfony", "PHP"));
        frameworks.add(new Framework("PowerWEB", "DNET"));

        containerPHP = new ArrayList<Framework>();
        containerCF = new ArrayList<Framework>();
        containerDNET = new ArrayList<Framework>();
    }

    public void processDropMethod(DropEvent event) {
        Dropzone dropzone = (Dropzone) event.getComponent();
        moveFramework(event.getDragValue(), dropzone.getDropValue());
        status += DROPLISTENER;
    }

    public void processDragMethod(DragEvent event) {
    	event.getAcceptedTypes();
    	status += DRAGLISTENER;
        dragValue = event.getDragValue();
        dropValue = event.getDropValue();

    }

    /**
     * Framework
     */
    public static class Framework {
        private String name;

        private String family;

        public Framework(String name, String family) {
            super();
            this.name = name;
            this.family = family;
        }

        public String getFamily() {
            return family;
        }

        public void setFamily(String family) {
            this.family = family;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}


	/**
	 * @return the dropValue
	 */
	public Object getDropValue() {
		return dropValue;
	}


	/**
	 * @param dropValue the dropValue to set
	 */
	public void setDropValue(Object dropValue) {
		this.dropValue = dropValue;
	}


	/**
	 * @return the dragValue
	 */
	public Object getDragValue() {
		return dragValue;
	}


	/**
	 * @param dragValue the dragValue to set
	 */
	public void setDragValue(Object dragValue) {
		this.dragValue = dragValue;
	}


	public Boolean getRendered() {
		return rendered;
	}


	public void setRendered(Boolean rendered) {
		this.rendered = rendered;
	}
    
}
