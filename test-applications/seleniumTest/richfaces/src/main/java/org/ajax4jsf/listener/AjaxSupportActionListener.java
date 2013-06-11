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
package org.ajax4jsf.listener;

import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;

import org.ajax4jsf.bean.A4JSupport;
import org.ajax4jsf.bean.A4JSupport.Messages;
import org.ajax4jsf.util.FacesUtils;

public class AjaxSupportActionListener implements ActionListener, ValueChangeListener {
    
    public void processAction(ActionEvent e) {
	process(Messages.TEST_PASSED);
    }
    
    public void processValueChange(ValueChangeEvent ce) {
	process(Messages.TEST_PASSED);
    }
    
    private void process(String str) {
	A4JSupport bean = (A4JSupport) FacesUtils.getFacesBean(A4JSupport.BEAN_NAME);
	if (bean != null) {
	    bean.setData(str);
	    bean.setStatus(Messages.PROCESSING_STATUS);
	}
    }
}
