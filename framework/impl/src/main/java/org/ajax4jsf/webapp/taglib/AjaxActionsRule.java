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

package org.ajax4jsf.webapp.taglib;

import javax.faces.component.ActionSource;
import javax.faces.component.ActionSource2;
import javax.faces.event.ActionEvent;
import javax.faces.event.MethodExpressionActionListener;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.el.LegacyMethodBinding;
import com.sun.facelets.tag.MetaRule;
import com.sun.facelets.tag.Metadata;
import com.sun.facelets.tag.MetadataTarget;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.util.FacesAPI;

/**
 * @author shura (latest modification by $Author: alexsmirnov $)
 * @version $Revision: 1.1.2.1 $ $Date: 2007/02/01 15:31:21 $
 *
 */
public class AjaxActionsRule extends MetaRule {

    public final static Class[] ACTION_SIG = new Class[0];

    public final static Class[] ACTION_LISTENER_SIG = new Class[] { ActionEvent.class };

    public final static class ActionMapper extends Metadata {

        private final TagAttribute attr;

        public ActionMapper(TagAttribute attr) {
            this.attr = attr;
        }

        public void applyMetadata(FaceletContext ctx, Object instance) {
            ((ActionSource) instance).setAction(new LegacyMethodBinding(
                    this.attr.getMethodExpression(ctx, String.class,
                            AjaxActionsRule.ACTION_SIG)));
        }
    }
    
    final static class ActionMapper2 extends Metadata {

        private final TagAttribute attr;

        public ActionMapper2(TagAttribute attr) {
            this.attr = attr;
        }

        public void applyMetadata(FaceletContext ctx, Object instance) {
            ((ActionSource2) instance).setActionExpression(this.attr
                    .getMethodExpression(ctx, String.class,
                    		AjaxActionsRule.ACTION_SIG));
        }

    }

    public final static class ActionListenerMapper extends Metadata {

        private final TagAttribute attr;

        public ActionListenerMapper(TagAttribute attr) {
            this.attr = attr;
        }

        public void applyMetadata(FaceletContext ctx, Object instance) {
            ((ActionSource) instance)
                    .setActionListener(new LegacyMethodBinding(this.attr
                            .getMethodExpression(ctx, null,
                                    AjaxActionsRule.ACTION_LISTENER_SIG)));
        }

    }
    
    final static class ActionListenerMapper2 extends Metadata {

        private final TagAttribute attr;

        public ActionListenerMapper2(TagAttribute attr) {
            this.attr = attr;
        }

        public void applyMetadata(FaceletContext ctx, Object instance) {
            ((ActionSource2) instance)
                    .addActionListener(new MethodExpressionActionListener(
                            this.attr.getMethodExpression(ctx, null,
                            		AjaxActionsRule.ACTION_LISTENER_SIG)));

        }

    }

    public final static AjaxActionsRule instance = new AjaxActionsRule();

    public AjaxActionsRule() {
        super();
    }

    public Metadata applyRule(String name, TagAttribute attribute,
            MetadataTarget meta) {
        if (meta.isTargetInstanceOf(ActionSource.class)) {
            boolean elSupport = FacesAPI.getComponentVersion(meta.getTargetClass()) >= 12;


            if ("action".equals(name)) {
            	if (elSupport && meta.isTargetInstanceOf(ActionSource2.class)) {
            		return new ActionMapper2(attribute);
            	} else {
            		return new ActionMapper(attribute);
            	}
            }

            if ("actionListener".equals(name)) {
            	 if (elSupport && meta.isTargetInstanceOf(ActionSource2.class)) {
                     return new ActionListenerMapper2(attribute);
                 } else {
                     return new ActionListenerMapper(attribute);
                 }
            }
        }
        return null;
    }

}
