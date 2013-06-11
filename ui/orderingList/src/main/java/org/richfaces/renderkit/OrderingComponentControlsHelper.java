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
package org.richfaces.renderkit;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.richfaces.component.UIOrderingBaseComponent;
import org.richfaces.renderkit.html.images.OrderingListIconBottom;
import org.richfaces.renderkit.html.images.OrderingListIconBottomDisabled;
import org.richfaces.renderkit.html.images.OrderingListIconDown;
import org.richfaces.renderkit.html.images.OrderingListIconDownDisabled;
import org.richfaces.renderkit.html.images.OrderingListIconTop;
import org.richfaces.renderkit.html.images.OrderingListIconTopDisabled;
import org.richfaces.renderkit.html.images.OrderingListIconUp;
import org.richfaces.renderkit.html.images.OrderingListIconUpDisabled;

public class OrderingComponentControlsHelper {
	
	private final static String FACET_TOP = "topControl";
	
	private final static String FACET_BOTTOM = "bottomControl";
	
	private final static String FACET_UP = "upControl";
	
	private final static String FACET_DOWN = "downControl";
	
	private final static String FACET_DIS_TOP = "topControlDisabled";
	
	private final static String FACET_DIS_BOTTOM = "bottomControlDisabled";
	
	private final static String FACET_DIS_UP = "upControlDisabled";
	
	private final static String FACET_DIS_DOWN = "downControlDisabled";
	
	public final static String FACET_CAPTION = "caption";
	
	private final static String ATTRIBUTE_CE_ONORDERCHANGED = "onorderchanged";
	
	private final static String ATTRIBUTE_CE_ONTOPCLICK = "ontopclick";
	
	private final static String ATTRIBUTE_CE_ONDOWNCLICK = "ondownclick";
	
	private final static String ATTRIBUTE_CE_ONUPCLICK = "onupclick";
	
	private final static String ATTRIBUTE_CE_ONBOTTOMCLICK = "onbottomclick";
	
	public final static String ATTRIBUTE_CAPTION_LABEL = "captionLabel";
	
	private final static String ATTRIBUTE_CLASS_TOP_CONTROL = "topControlClass";
	
	private final static String ATTRIBUTE_CLASS_BOTTOM_CONTROL = "bottomControlClass";
	
	private final static String ATTRIBUTE_CLASS_DOWN_CONTROL = "downControlClass";
	
	private final static String ATTRIBUTE_CLASS_UP_CONTROL = "upControlClass";
	
	private final static String ATTRIBUTE_CLASS_DISABLED_CONTROL = "disabledControlClass";
	
	private final static String DIS_CONTROL_ID_PREFIX = "dis";
	
	private final static String CONTROL_ID_UP = "up";
	
	private final static String CONTROL_ID_DOWN = "down";
	
	private final static String CONTROL_ID_TOP = "first";
	
	private final static String CONTROL_ID_BOTTOM = "last";
	
	public static final String CONTROL_LABEL_ATTRIBUTE_SUFFIX = "ControlLabel";
	public static final String CONTROL_ALT_ATTRIBUTE_POSTFIX = "Title";
	
	private final static String DEFAULT_LABEL_TOP = "First";
	private final static String DEFAULT_LABEL_UP = "Up";
	private final static String DEFAULT_LABEL_DOWN = "Down";
	private final static String DEFAULT_LABEL_BOTTOM = "Last";
	
	public final static String DISABLED_STYLE_PREF = "-disabled";
	
	protected static final OrderingComponentRendererBase.ControlsHelper[] HELPERS = new OrderingComponentRendererBase.ControlsHelper[] {
		new OrderingComponentRendererBase.ControlsHelper("top", "RICH_SHUTTLES_TOP_LABEL", DEFAULT_LABEL_TOP, OrderingListIconTop.class.getName(), FACET_TOP,
						   "-top", ATTRIBUTE_CLASS_TOP_CONTROL, "", 
						   CONTROL_ID_TOP, ATTRIBUTE_CE_ONTOPCLICK, true, "top".concat(CONTROL_LABEL_ATTRIBUTE_SUFFIX), getTitle("top")) {

			public boolean isRendered(FacesContext context, UIComponent listComponent) {
				return ((UIOrderingBaseComponent) listComponent).isFastOrderControlsVisible();
			}
			
		},
		new OrderingComponentRendererBase.ControlsHelper("disabledTop", "RICH_SHUTTLES_TOP_LABEL", DEFAULT_LABEL_TOP, OrderingListIconTopDisabled.class.getName(), FACET_DIS_TOP,
						   "-disabled", ATTRIBUTE_CLASS_DISABLED_CONTROL, DISABLED_STYLE_PREF, 
						   DIS_CONTROL_ID_PREFIX.concat(CONTROL_ID_TOP), null, false, "top".concat(CONTROL_LABEL_ATTRIBUTE_SUFFIX), getTitle("top")) {

			public boolean isRendered(FacesContext context, UIComponent listComponent) {
				return ((UIOrderingBaseComponent) listComponent).isFastOrderControlsVisible();
			}
			
		},
		new OrderingComponentRendererBase.ControlsHelper("up", "RICH_SHUTTLES_UP_LABEL", DEFAULT_LABEL_UP, OrderingListIconUp.class.getName(), FACET_UP,
						   "-up", ATTRIBUTE_CLASS_UP_CONTROL, "", 
						   CONTROL_ID_UP, ATTRIBUTE_CE_ONUPCLICK ,true, "up".concat(CONTROL_LABEL_ATTRIBUTE_SUFFIX), getTitle("up")) {

			public boolean isRendered(FacesContext context, UIComponent listComponent) {
				return ((UIOrderingBaseComponent) listComponent).isOrderControlsVisible();
			}
			
		},
		new OrderingComponentRendererBase.ControlsHelper("disabledUp", "RICH_SHUTTLES_UP_LABEL", DEFAULT_LABEL_UP, OrderingListIconUpDisabled.class.getName(), FACET_DIS_UP,
						   "-disabled", ATTRIBUTE_CLASS_DISABLED_CONTROL, DISABLED_STYLE_PREF,
						   DIS_CONTROL_ID_PREFIX.concat(CONTROL_ID_UP), null, false, "up".concat(CONTROL_LABEL_ATTRIBUTE_SUFFIX), getTitle("up")) {

			public boolean isRendered(FacesContext context, UIComponent listComponent) {
				return ((UIOrderingBaseComponent) listComponent).isOrderControlsVisible();
			}
			
		},
		new OrderingComponentRendererBase.ControlsHelper("down", "RICH_SHUTTLES_DOWN_LABEL", DEFAULT_LABEL_DOWN, OrderingListIconDown.class.getName(), FACET_DOWN,
						   "-down", ATTRIBUTE_CLASS_DOWN_CONTROL, "",
						   CONTROL_ID_DOWN, ATTRIBUTE_CE_ONDOWNCLICK, true, "down".concat(CONTROL_LABEL_ATTRIBUTE_SUFFIX), getTitle("down")) {

			public boolean isRendered(FacesContext context, UIComponent listComponent) {
				return ((UIOrderingBaseComponent) listComponent).isOrderControlsVisible();
			}
			
		},
		new OrderingComponentRendererBase.ControlsHelper("disabledDown", "RICH_SHUTTLES_DOWN_LABEL", DEFAULT_LABEL_DOWN, OrderingListIconDownDisabled.class.getName(), FACET_DIS_DOWN,
						   "-disabled", ATTRIBUTE_CLASS_DISABLED_CONTROL, DISABLED_STYLE_PREF, 
						   DIS_CONTROL_ID_PREFIX.concat(CONTROL_ID_DOWN), null, false, "down".concat(CONTROL_LABEL_ATTRIBUTE_SUFFIX), getTitle("down")) {

			public boolean isRendered(FacesContext context, UIComponent listComponent) {
				return ((UIOrderingBaseComponent) listComponent).isOrderControlsVisible();
			}
			
		},
		new OrderingComponentRendererBase.ControlsHelper("bottom", "RICH_SHUTTLES_BOTTOM_LABEL", DEFAULT_LABEL_BOTTOM, OrderingListIconBottom.class.getName(), FACET_BOTTOM,
						   "-bottom", ATTRIBUTE_CLASS_BOTTOM_CONTROL, "",
						   CONTROL_ID_BOTTOM, ATTRIBUTE_CE_ONBOTTOMCLICK, true, "bottom".concat(CONTROL_LABEL_ATTRIBUTE_SUFFIX), getTitle("bottom")) {

			public boolean isRendered(FacesContext context, UIComponent listComponent) {
				return ((UIOrderingBaseComponent) listComponent).isFastOrderControlsVisible();
			}
			
		},
		new OrderingComponentRendererBase.ControlsHelper("disabledBottom", "RICH_SHUTTLES_BOTTOM_LABEL", DEFAULT_LABEL_BOTTOM, OrderingListIconBottomDisabled.class.getName(), FACET_DIS_BOTTOM,
						   "-disabled", ATTRIBUTE_CLASS_DISABLED_CONTROL, DISABLED_STYLE_PREF,
						   DIS_CONTROL_ID_PREFIX.concat(CONTROL_ID_BOTTOM), null, false, "bottom".concat(CONTROL_LABEL_ATTRIBUTE_SUFFIX), getTitle("bottom")) {

			public boolean isRendered(FacesContext context, UIComponent listComponent) {
				return ((UIOrderingBaseComponent) listComponent).isFastOrderControlsVisible();
			}
			
		}
	};
	
    private static String getTitle(String name) {
        return name.concat(CONTROL_ALT_ATTRIBUTE_POSTFIX);
    }
    

}
