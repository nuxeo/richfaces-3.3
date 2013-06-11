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

import javax.faces.context.FacesContext;

import org.richfaces.component.Dropzone;
import org.richfaces.event.DragEvent;
import org.richfaces.event.DragListener;
import org.richfaces.event.DropEvent;
import org.richfaces.event.DropListener;

public class DnDListener implements DragListener, DropListener {

    private DnDBean instance;

    protected DnDBean getDnDBean() {
        if (null == instance) {
            FacesContext fc = FacesContext.getCurrentInstance();
            instance = (DnDBean) fc.getApplication().evaluateExpressionGet(fc, "#{dndBean}", DnDBean.class);
        }

        return instance;
    }

//
// DropListener implementation
//
    public void processDrag(DragEvent event) {
    	DnDBean bean = getDnDBean();
    	bean.setStatus(bean.getStatus() + DnDBean.RICHDRAGLISTENER);
    }

//
// DropListener implementation
//
    public void processDrop(DropEvent event) {
        Dropzone dropzone = (Dropzone) event.getComponent();
        DnDBean bean = getDnDBean();
        bean.moveFramework(event.getDragValue(), dropzone.getDropValue());
        bean.setStatus(bean.getStatus() + DnDBean.RICHDROPLISTENER);
    }

}
