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

package org.richfaces.taglib;

import org.richfaces.component.UISuggestionBox;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.tag.MetaRule;
import com.sun.facelets.tag.MetaRuleset;
import com.sun.facelets.tag.Metadata;
import com.sun.facelets.tag.MetadataTarget;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.jsf.ComponentConfig;

/**
 * Component tag handler for Facelets.
 *
 * @author shura (latest modification by $Author: ishabalov $)
 * @version $Revision: 1.1.2.4 $ $Date: 2007/02/20 20:58:03 $
 */
public class SuggestionBoxTagHandler
        extends com.sun.facelets.tag.jsf.ComponentHandler {

    /**
     * Meta rule for tag handler.
     */
    private static final SuggestionBoxTagHandlerMetaRule metaRule =
            new SuggestionBoxTagHandlerMetaRule();

    /**
     * Constructor.
     *
     * @param config {@link com.sun.facelets.tag.jsf.ComponentConfig}
     */
    public SuggestionBoxTagHandler(final ComponentConfig config) {
        super(config);
    }

    /**
     * Creates metarules.
     *
     * @param type Class
     * @return {@link com.sun.facelets.tag.MetaRuleset}
     */
    protected final MetaRuleset createMetaRuleset(final Class type) {
        MetaRuleset m = super.createMetaRuleset(type);
        m.addRule(metaRule);
        return m;
    }

    /**
     * Meta rule implementation.
     *
     * @author shura (latest modification by $Author: ishabalov $)
     * @version $Revision: 1.1.2.4 $ $Date: 2007/02/20 20:58:03 $
     */
    static class SuggestionBoxTagHandlerMetaRule extends MetaRule {

        /**
         * Apply rule.
         *
         * @param name rule name
         * @param attribute {@link com.sun.facelets.tag.TagAttribute}
         * @param meta {@link com.sun.facelets.tag.TagAttribute}
         * @return metadata {@link com.sun.facelets.tag.Metadata}
         *
         * @see {@link com.sun.facelets.tag.MetaRule#applyRule(String,
         *      com.sun.facelets.tag.TagAttribute,
         *      com.sun.facelets.tag.MetadataTarget)}
         */
        public Metadata applyRule(final String name,
                                  final TagAttribute attribute,
                                  final MetadataTarget meta) {
            if (meta.isTargetInstanceOf(UISuggestionBox.class)) {
                if ("suggestionAction".equals(name)) {
                    return new SuggestionActionMapper(attribute);
                }

            }
            return null;
        }
    }

    /**
     * Meta data implementation.
     */
    static class SuggestionActionMapper extends Metadata {
        /**
         * Signature.
         */
        private static final Class[] SIGNATURE =
                new Class[]{java.lang.Object.class};

        /**
         * Action attribute.
         */
        private final TagAttribute action;

        /**
         * Sets attribute.
         *
         * @param attribute {@link com.sun.facelets.tag.TagAttribute}
         */
        public SuggestionActionMapper(final TagAttribute attribute) {
            action = attribute;
        }

        /**
         * Apply metadata.

         * @param context {@link javax.faces.context.FacesContext}
         * @param instance {@link java.lang.Object}
         *
         * @see {@link com.sun.facelets.tag.Metadata#applyMetadata(
         *      com.sun.facelets.FaceletContext, Object)}
         */
        public void applyMetadata(final FaceletContext context,
                                  final Object instance) {
            ((UISuggestionBox) instance)
                    .setSuggestionAction(this.action
                            .getMethodExpression(context, null, SIGNATURE));
        }
    }
}
