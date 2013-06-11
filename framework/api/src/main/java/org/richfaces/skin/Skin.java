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

package org.richfaces.skin;

import javax.faces.context.FacesContext;

/**
 * Main interface for configurable parameters.
 * 
 * @author asmirnov@exadel.com (latest modification by $Author: alexsmirnov $)
 * @version $Revision: 1.1.2.1 $ $Date: 2007/01/09 18:59:43 $
 * 
 */
public interface Skin {
	// parameters names constants

//	 parameters names constants
	
	/**
	 * used for define url with general CSS file for current skin.
	 */
	public static final String generalStyleSheet = "generalStyleSheet";

	/**
	 * used for define url with extended CSS file for current skin.
	 */
	public static final String extendedStyleSheet = "extendedStyleSheet";

	/**
	 * used for defining a header background color in the panel, a tabpanel active tab 
	 * background color, a bar background color for ddmenu, tables background color, 
	 * buttons background color. Default value #1D7DB3.               
	 */
	public static final String headerBackgroundColor = "headerBackgroundColor";

	/**
	 * used for defining color of the titles of the top level of the menu, the panel header
	 * color, the table header color, color of the title of the active tab in the tabpanel,
	 * color of the title on the buttons. Default value #FFFFFF. 
	 */
	public static final String headTextColor = "headTextColor";

	/**
	 * used for defining the background color of the bar under the header in the panel and 
	 * the tabpanel, color of the border for the top level of the ddmenu, background 
	 * highlight color of the selected item in the dropdown list boxes of the ddmenu, 
	 * background color of the footer in the tables, color of the text for the inactive and 
	 * disabled tab in the tabpanel, color of the border of the buttons. Default value 
	 * #BFD7E4.
	 */
	public static final String selectBackgroundColor = "selectBackgroundColor";

	/**
	 * used for defining the background color of the basic area of the panel and tabpanels,
	 * background color of the dropdown list boxes of ddmenu. Default value #BFD7E4.
	 */
	public static final String generalBackgroundColor = "generalBackgroundColor";

	/**
	 * used for defining color of the basic text, color of the text in the dropdown list 
	 * boxes of ddmenu, color of the right and top borders for the controls like text, 
	 * textArea, secret. Default value #000000.
	 */
	public static final String generalTextColor = "generalTextColor";

	/**
	 * used for defining shadow background color of the panels, color of the bottom and right
	 * borders for the dropdown list box in ddmenu. Default value #AFB1B2.
	 */
	public static final String shadowBackgroundColor = "shadowBackgroundColor";

	/**
	 * used for defining the seamlessness of the tip shadow. Default value 2.
	 */
	public static final String shadowOpacity = "shadowOpacity";

	/**
	 * used for defining  tables border color, color of the bottom and left controls like 
	 * text, textArea, secret, color of the top and left borders for the dropdown list box in
	 * the ddmenu. Default value #CCCCCC.
	 */
	public static final String tableBorderColor = "tableBorderColor";

	/**
	 * used for defining tables and controls background color. Default value #FFFFFF.
	 */
	public static final String tableBackgroundColor = "tableBackgroundColor";

	/**
	 * Font size for the displaying panels headers and top level of the ddmenu. Default 
	 * value 12px.
	 */
	public static final String headerSizeFont = "headerSizeFont";

	/**
	 * Font name for the displaying panels headers and top level of the ddmenu.Default value
	 * Arial, Verdana.
	 */
	public static final String headerFamilyFont = "headerFamilyFont";

	/**
	 * Font size for displaying tab titles. Default value 11px.
	 */
	public static final String tabSizeFont = "tabSizeFont";

	/**
	 * Font name for displaying tab titles. Default value Arial, Verdana.
	 * 
	 */
	public static final String tabFamilyFont = "tabFamilyFont";

	/**
	 * Rounding-off radius of the tabs corners. Default value 5px.
	 */
	public static final String tabRadiusCorner = "tabRadiusCorner";

	/**
	 * Font size for displaying buttons titles. Default value 11px.
	 */
	public static final String buttonSizeFont = "buttonSizeFont";

	/**
	 * Font name for displaying buttons titles. Default value Arial, Verdana.
	 */
	public static final String buttonFamilyFont = "buttonFamilyFont";

	/**
	 * Rounding-off radius of the buttons corners. Default value 13px.
	 * 
	 */
	public static final String buttonRadiusCorner = "buttonRadiusCorner";

	/**
	 * Rounding-off radius of the panels corners. Default value 5px.
	 */
	public static final String panelRadiusCorner = "panelRadiusCorner";

	/**
	 * Style of the text displaying on the button. Default value normal.
	 * 
	 */
	public static final String buttonStyleFont = "buttonStyleFont";

	/**
	 * Depth of the text displaying on the button. Default value bold.
	 */
	public static final String buttonWeightFont = "buttonWeightFont";

	/**
	 * Style of the text displaying on the active (selected) tabs.Default value normal.
	 */
	public static final String activetabStyleFont = "activetabStyleFont";

	/**
	 * Depth of the text displaying on the active (selected) tabs.Default value bold.
	 */
	public static final String activetabWeightFont = "activetabWeightFont";

	/**
	 * Style of of the text displaying on the inactive (unselected) tabs. Default value 
	 * normal.
	 */
	public static final String tabStyleFont = "tabStyleFont";

	/**
	 * Depth of the text displaying on the inactive (unselected) tabs.Default value normal.
	 */
	public static final String tabWeightFont = "tabWeightFont";

	/**
	 * Style of the text displaying on the disabled tabs.Default value normal.
	 */
	public static final String disabledTabStyleFont = "disabledTabStyleFont";

	/**
	 * Depth of the text displaying on the disabled tabs.Default value normal.
	 */
	public static final String disabledTabWeightFont = "disabledTabWeightFont";
	
	/**
	 * Color for selected checkbox or selectOneRadio.
	 */
	public static final String selectControlColor  = "selectControlColor";
	
	/**
	 * Responsible for the borders color of the radiobutton and checkbox and color of light
	 * part of the fields and text area border. Default value #B0B0B0.
	 */
	public static final String controlBorderColor  = "controlBorderColor";

	/**
	 * Parameter responsible for 3D Look of panels and buttons.
	 */
	
	public static final String interfaceLevel3D = "interfaceLevel3D";

	/**
	 * defines the layout of the tabs in the panel. Possible values Top. Left. Bottom, Right.
	 * Default value ???. 
	 */
	public static final String preferableTabPosition = "preferableTabPosition";
	
	/**
	 * defines the variant of text writing. Values - Hor (horisontal position), Vert 
	 * (vertical position - letters are arranged into column), VertCW (vertical
	 * position with text rotation anticlockwise). Default value Hor.
	 */
	public static final String preferableTabTextOrientation = "preferableTabTextOrientation";
	/**
	 * text aligning in the tabs with the fixed tab length (hight). Values Left, Center,Right
	 * for the horizontal oriented tabs, values Top, center, Bottom - for vertical oriented 
	 * tabs. Default value center.
	 */
	

	public static final String preferableTabTextDirection  = "preferableTabTextDirection";

	
	/**
	 * 
	 */
	public static final String overAllBackground = "overAllBackground";
	/**
	 * 
	 */
	public static final String generalLinkColor = "generalLinkColor";

	public static final String panelTextColor = "panelTextColor";

	public static final String headerGradientColor = "headerGradientColor";

	public static final String additionalBackgroundColor = "additionalBackgroundColor";
	
	public static final String controlBackgroundColor = "controlBackgroundColor";
	
	public static final String generalSizeFont = "generalSizeFont";
	
	public static final String loadStyleSheets = "loadStyleSheets";

	public static final String gradientType = "gradientType";
	
	public static final String baseSkin = "baseSkin";

	// Preferable parameters
	/**
	 * Preferable parameters names for skin ( in common, for Preferable.Name
	 * parameter will PreferableName )
	 * 
	 * @author asmirnov@exadel.com (latest modification by $Author: alexsmirnov $)
	 * @version $Revision: 1.1.2.1 $ $Date: 2007/01/09 18:59:43 $
	 * 
	 */

	public interface Preferable {
		/**
		 * 
		 */
		public static final String dataSizeFont = "preferableDataSizeFont";

		/**
		 * 
		 */
		public static final String dataFamilyFont = "preferableDataFamilyFont";

		/**
		 * 
		 */
		public static final String panelBodyPadding = "preferablePanelBodyPadding";

		/**
		 * 
		 */
		public static final String headerWeightFont = "preferableHeaderWeightFont";

	}

	/**
	 * Get Rener Kit name for this skin.
	 * 
	 * @param context -
	 *            {@link FacesContext } for current request. need for
	 *            {@link javax.faces.el.ValueBinding} evaluation.
	 * @return RenderKitId for this skin, or <code>null</code> for default.
	 */
	public String getRenderKitId(FacesContext context);

	/**
	 * Get value for configuration parameter. If parameter set as EL-expression,
	 * calculate it value.
	 * 
	 * @param context -
	 *            {@link FacesContext } for current request.
	 * @param name
	 *            name of paremeter.
	 * @return value of parameter in config, or null
	 */
	public Object getParameter(FacesContext context, String name);
	
	/**
	 * Get value for configuration parameter. If parameter set as EL-expression,
	 * calculate it value.
	 * 
	 * @param context -
	 *            {@link FacesContext } for current request.
	 * @param name
	 *            name of paremeter.
	 * @param defaultValue - default value if parameter not present in Skin
	 * @return value of parameter in config, or null
	 */
	public Object getParameter(FacesContext context, String name, Object defaultValue);

	/**
	 * @param name
	 * @return
	 */
	public boolean containsProperty(String name);
	
	/**
	 * Calculate unique ( as possible ) code to identity this skin instance. Used for generate hash key in skin-depended resources
	 * @param context
	 * @return
	 */
	public int hashCode(FacesContext context);
}