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
import javax.faces.component.UIComponentBase;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;

import org.richfaces.component.UIListShuttle;
import org.richfaces.component.UIOrderingBaseComponent;
import org.richfaces.renderkit.html.images.ListShuttleIconCopy;
import org.richfaces.renderkit.html.images.ListShuttleIconCopyAll;
import org.richfaces.renderkit.html.images.ListShuttleIconCopyAllDisabled;
import org.richfaces.renderkit.html.images.ListShuttleIconCopyDisabled;
import org.richfaces.renderkit.html.images.ListShuttleIconRemove;
import org.richfaces.renderkit.html.images.ListShuttleIconRemoveAll;
import org.richfaces.renderkit.html.images.ListShuttleIconRemoveAllDisabled;
import org.richfaces.renderkit.html.images.ListShuttleIconRemoveDisabled;

public class ListShuttleControlsHelper {
	
	private static final String COPY_ALL_TITLE = "copyAllTitle";

    private static final String COPY_TITLE = "copyTitle";

    private static final String REMOVE_TITLE = "removeTitle";

    private static final String REMOVE_ALL_TITLE = "removeAllTitle";

    private final static String FACET_COPY_ALL = "copyAllControl";
	
	private final static String FACET_REMOVE_ALL = "removeAllControl";
	
	private final static String FACET_COPY = "copyControl";
	
	private final static String FACET_REMOVE = "removeControl";
	
	private final static String FACET_DIS_COPY_ALL = FACET_COPY_ALL + "Disabled";
	
	private final static String FACET_DIS_REMOVE_ALL = FACET_REMOVE_ALL + "Disabled";
	
	private final static String FACET_DIS_COPY = FACET_COPY + "Disabled";
	
	private final static String FACET_DIS_REMOVE = FACET_REMOVE + "Disabled";
	
	public final static String FACET_CAPTION = "caption";
	
	private final static String ATTRIBUTE_CE_ONCOPYALLCLICK = "oncopyallclick";
	
	private final static String ATTRIBUTE_CE_ONREMOVECLICK = "onremoveclick";
	
	private final static String ATTRIBUTE_CE_ONCOPYCLICK = "oncopyclick";
	
	private final static String ATTRIBUTE_CE_ONREMOVEALLCLICK = "onremoveallclick";
	
	public final static String ATTRIBUTE_SOURCE_CAPTION_LABEL = "sourceCaptionLabel";
	
	public final static String ATTRIBUTE_TARGET_CAPTION_LABEL = "targetCaptionLabel";
	
	private final static String ATTRIBUTE_CLASS_COPY_ALL_CONTROL = FACET_COPY_ALL + "Class";
	
	private final static String ATTRIBUTE_CLASS_REMOVE_ALL_CONTROL = FACET_REMOVE_ALL + "Class";
	
	private final static String ATTRIBUTE_CLASS_REMOVE_CONTROL = FACET_REMOVE + "Class";
	
	private final static String ATTRIBUTE_CLASS_COPY_CONTROL = FACET_COPY + "Class";
	
	private final static String ATTRIBUTE_CLASS_DISABLED_CONTROL = "disabledControlClass";
	
	private final static String DIS_CONTROL_ID_PREFIX = "dis";
	
	public final static String CONTROL_ID_COPY_ALL = "copyAll";

	public final static String CONTROL_ID_COPY = "copy";
	
	public final static String CONTROL_ID_REMOVE = "remove";
	
	public final static String CONTROL_ID_REMOVE_ALL = "removeAll";
	
	private final static String DEFAULT_LABEL_COPY_ALL = "Copy all";
	private final static String DEFAULT_LABEL_COPY = "Copy";
	private final static String DEFAULT_LABEL_REMOVE = "Remove";
	private final static String DEFAULT_LABEL_REMOVE_ALL = "Remove All";
	
	public final static String DISABLED_STYLE_PREF = "-disabled";
	
	private final static String NAME_COPYALL = "copyAll";
	private final static String NAME_COPYALL_DIS = "disabledCopy";
	private final static String NAME_COPY = "copy";
	private final static String NAME_COPY_DIS = "disabledCopy";
	private final static String NAME_REMOVEALL = "removeAll";
	private final static String NAME_REMOVEALL_DIS = "disabledCopyAll";
	private final static String NAME_REMOVE_DIS = "disabledRemove";
	private final static String NAME_REMOVE = "remove";
	
	public final static String BUNDLE_COPY_ALL_LABEL = "RICH_LIST_SHUTTLE_COPY_ALL_LABEL";
	public final static String BUNDLE_COPY_LABEL = "RICH_LIST_SHUTTLE_COPY_LABEL";
	public final static String BUNDLE_REMOVE_ALL_LABEL = "RICH_LIST_SHUTTLE_REMOVE_ALL_LABEL";
	public final static String BUNDLE_REMOVE_LABEL = "RICH_LIST_SHUTTLE_REMOVE_LABEL";
	
	protected static final OrderingComponentRendererBase.ControlsHelper[] HELPERS = new OrderingComponentRendererBase.ControlsHelper[] {
		new OrderingComponentRendererBase.ControlsHelper(NAME_COPYALL, BUNDLE_COPY_ALL_LABEL, DEFAULT_LABEL_COPY_ALL, ListShuttleIconCopyAll.class.getName(), FACET_COPY_ALL,
						   "-copyall", ATTRIBUTE_CLASS_COPY_ALL_CONTROL, "", 
						   CONTROL_ID_COPY_ALL, ATTRIBUTE_CE_ONCOPYALLCLICK, true, "copyAll".concat(OrderingComponentControlsHelper.CONTROL_LABEL_ATTRIBUTE_SUFFIX), COPY_ALL_TITLE) {

			public boolean isRendered(FacesContext context, UIComponent listComponent) {
				return ((UIListShuttle) listComponent).isFastMoveControlsVisible();
			}
			
		},
		new OrderingComponentRendererBase.ControlsHelper(NAME_COPYALL_DIS, BUNDLE_COPY_ALL_LABEL, DEFAULT_LABEL_COPY_ALL, ListShuttleIconCopyAllDisabled.class.getName(), FACET_DIS_COPY_ALL,
						   "-disabled", ATTRIBUTE_CLASS_DISABLED_CONTROL, DISABLED_STYLE_PREF, 
						   DIS_CONTROL_ID_PREFIX.concat(CONTROL_ID_COPY_ALL), null, false, "copyAll".concat(OrderingComponentControlsHelper.CONTROL_LABEL_ATTRIBUTE_SUFFIX), COPY_ALL_TITLE) {

			public boolean isRendered(FacesContext context, UIComponent listComponent) {
				return ((UIListShuttle) listComponent).isFastMoveControlsVisible();
			}
			
		},
		new OrderingComponentRendererBase.ControlsHelper(NAME_COPY, BUNDLE_COPY_LABEL, DEFAULT_LABEL_COPY, ListShuttleIconCopy.class.getName(), FACET_COPY,
						   "-copy", ATTRIBUTE_CLASS_COPY_CONTROL, "", 
						   CONTROL_ID_COPY, ATTRIBUTE_CE_ONCOPYCLICK ,true, "copy".concat(OrderingComponentControlsHelper.CONTROL_LABEL_ATTRIBUTE_SUFFIX), COPY_TITLE) {

			public boolean isRendered(FacesContext context, UIComponent listComponent) {
				return ((UIListShuttle) listComponent).isMoveControlsVisible();
			}
			
		},
		new OrderingComponentRendererBase.ControlsHelper(NAME_COPY_DIS, BUNDLE_COPY_LABEL, DEFAULT_LABEL_COPY, ListShuttleIconCopyDisabled.class.getName(), FACET_DIS_COPY,
						   "-disabled", ATTRIBUTE_CLASS_DISABLED_CONTROL, DISABLED_STYLE_PREF,
						   DIS_CONTROL_ID_PREFIX.concat(CONTROL_ID_COPY), null, false, "copy".concat(OrderingComponentControlsHelper.CONTROL_LABEL_ATTRIBUTE_SUFFIX), COPY_TITLE) {

			public boolean isRendered(FacesContext context, UIComponent listComponent) {
				return ((UIListShuttle) listComponent).isMoveControlsVisible();
			}
			
		},
		new OrderingComponentRendererBase.ControlsHelper(NAME_REMOVE, BUNDLE_REMOVE_LABEL, DEFAULT_LABEL_REMOVE, ListShuttleIconRemove.class.getName(), FACET_REMOVE,
						   "-remove", ATTRIBUTE_CLASS_REMOVE_CONTROL, "",
						   CONTROL_ID_REMOVE, ATTRIBUTE_CE_ONREMOVECLICK, true, "remove".concat(OrderingComponentControlsHelper.CONTROL_LABEL_ATTRIBUTE_SUFFIX), REMOVE_TITLE) {

			public boolean isRendered(FacesContext context, UIComponent listComponent) {
				return ((UIListShuttle) listComponent).isMoveControlsVisible();
			}
			
		},
		new OrderingComponentRendererBase.ControlsHelper(NAME_REMOVE_DIS, BUNDLE_REMOVE_LABEL, DEFAULT_LABEL_REMOVE, ListShuttleIconRemoveDisabled.class.getName(), FACET_DIS_REMOVE,
						   "-disabled", ATTRIBUTE_CLASS_DISABLED_CONTROL, DISABLED_STYLE_PREF, 
						   DIS_CONTROL_ID_PREFIX.concat(CONTROL_ID_REMOVE), null, false, "remove".concat(OrderingComponentControlsHelper.CONTROL_LABEL_ATTRIBUTE_SUFFIX), REMOVE_TITLE) {

			public boolean isRendered(FacesContext context, UIComponent listComponent) {
				return ((UIListShuttle) listComponent).isMoveControlsVisible();
			}
			
		},
		new OrderingComponentRendererBase.ControlsHelper(NAME_REMOVEALL, BUNDLE_REMOVE_ALL_LABEL, DEFAULT_LABEL_REMOVE_ALL, ListShuttleIconRemoveAll.class.getName(), FACET_REMOVE_ALL,
						   "-removeall", ATTRIBUTE_CLASS_REMOVE_ALL_CONTROL, "",
						   CONTROL_ID_REMOVE_ALL, ATTRIBUTE_CE_ONREMOVEALLCLICK, true, "removeAll".concat(OrderingComponentControlsHelper.CONTROL_LABEL_ATTRIBUTE_SUFFIX), REMOVE_ALL_TITLE) {

			public boolean isRendered(FacesContext context, UIComponent listComponent) {
				return ((UIListShuttle) listComponent).isFastMoveControlsVisible();
			}
			
		},
		new OrderingComponentRendererBase.ControlsHelper(NAME_REMOVEALL_DIS, BUNDLE_REMOVE_ALL_LABEL, DEFAULT_LABEL_REMOVE_ALL, ListShuttleIconRemoveAllDisabled.class.getName(), FACET_DIS_REMOVE_ALL,
						   "-disabled", ATTRIBUTE_CLASS_DISABLED_CONTROL, DISABLED_STYLE_PREF,
						   DIS_CONTROL_ID_PREFIX.concat(CONTROL_ID_REMOVE_ALL), null, false, "removeAll".concat(OrderingComponentControlsHelper.CONTROL_LABEL_ATTRIBUTE_SUFFIX), REMOVE_ALL_TITLE) {

			public boolean isRendered(FacesContext context, UIComponent listComponent) {
				return ((UIListShuttle) listComponent).isFastMoveControlsVisible();
			}
			
		}
	};
}
