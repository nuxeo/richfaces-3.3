/**
 * License Agreement.
 *
 *  JBoss RichFaces - Ajax4jsf Component Library
 *
 * Copyright (C) 2007  Exadel, Inc.
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

package org.richfaces.renderkit.html;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.ajax4jsf.javascript.JSFunction;
import org.ajax4jsf.javascript.ScriptUtils;
import org.ajax4jsf.renderkit.AjaxRendererUtils;
import org.ajax4jsf.renderkit.RendererUtils.HTML;
import org.richfaces.component.UIPanelMenu;
import org.richfaces.component.UIPanelMenuGroup;
import org.richfaces.component.UIPanelMenuItem;
import org.richfaces.renderkit.PanelMenuRendererBase;

public class PanelMenuRenderer extends PanelMenuRendererBase {

	/* (non-Javadoc)
	 * @see org.ajax4jsf.framework.renderer.RendererBase#getComponentClass()
	 */
	
	protected Class getComponentClass() {
		return UIComponent.class;
	}
	
    // find and encode UIParameter's components
    public List encodeParams(FacesContext context, UIPanelMenuItem component) throws IOException {
    	
    	UIPanelMenuItem menuItem = component;
    	List params = new ArrayList();
    	StringBuffer buff = new StringBuffer();
    	
    	List children = menuItem.getChildren();
    	for (Iterator iterator = children.iterator(); iterator.hasNext();) {
    		UIComponent child = (UIComponent) iterator.next();
	
    		if(child instanceof UIParameter){
					
    			UIParameter param = (UIParameter)child;
				String name = param.getName();
				
				if (name != null) {
					Object value = param.getValue();
					buff.append("params[");
					buff.append(ScriptUtils.toScript(name));
					buff.append("] = ");
					buff.append(ScriptUtils.toScript(value));
					buff.append(";");
					params.add(buff.toString());
				}

			}
    	}
    	
    	return params;
    }
    
    @Override
    protected void preEncodeBegin(FacesContext context, UIComponent component) throws IOException {
        super.preEncodeBegin(context, component);

        // In case of encoding the UIPanelMenu in "expandSingle=true" mode
        // the value of "firstExpandedEncoded" attribute should be reset to
        // initial "false" state 
	if (component instanceof UIPanelMenu) {
	    UIPanelMenu panelMenu = (UIPanelMenu) component;
	    if (panelMenu.isExpandSingle()) {
		panelMenu.getAttributes().put("firstExpandedEncoded", false);
	    }
	}
    }

	
	public void insertScript(FacesContext context, UIComponent component)
			throws IOException {
		
		StringBuffer buffer		= new StringBuffer();
		StringBuffer panelMenu	= new StringBuffer();
		List flatList		 	= new LinkedList();
		Map levels			 	= new HashMap();
		
		Set itemNames = new HashSet();
		
		UIPanelMenu parentMenu = (UIPanelMenu)component;
		
		boolean expandSingle = parentMenu.isExpandSingle();
		
		String selectedChild = parentMenu.getSelectedName();
		
		flatten(component.getChildren(), flatList, levels, 0);
		
		panelMenu.append("var ids = new PanelMenu('")
					.append(component.getClientId(context).toString())
					.append("',")
					.append(new Boolean(expandSingle).toString())
					.append(",").append("'").append(selectedChild).append("'")
					.append(").getIds();\n");
		
		for (Iterator iter = flatList.iterator(); iter.hasNext();) {
			UIComponent child = (UIComponent) iter.next();
			if ((child instanceof UIPanelMenuItem)||(child instanceof UIPanelMenuGroup)) {
				boolean childDisabled;
				if (!((UIPanelMenu)component).isDisabled())
					childDisabled = child instanceof UIPanelMenuGroup ? ((UIPanelMenuGroup)child).isDisabled() : ((UIPanelMenuItem)child).isDisabled();
				else
					childDisabled = true;
				boolean childRendered = child instanceof UIPanelMenuGroup ? ((UIPanelMenuGroup)child).isRendered() : ((UIPanelMenuItem)child).isRendered();
				boolean parentRendered = true;
				if (! (child.getParent() instanceof UIPanelMenu))
					parentRendered = child.getParent() instanceof UIPanelMenuGroup ? ((UIPanelMenuGroup)child.getParent()).isRendered() : ((UIPanelMenuItem)child.getParent()).isRendered();
				if (!parentRendered){
					child.getAttributes().put("rendered",Boolean.FALSE);
				}
				
				//UIPanelMenu parentMenu = findMenu(child);
				
				String event = parentMenu.getEvent();
				if ("".equals(event))
					event = "click";
				else if (event.startsWith("on"))
					event = event.substring(2);
				
				String onopen = (child instanceof UIPanelMenuGroup) &&  !((UIPanelMenuGroup)child).isDisabled() && !isParentDisabled(child) ? 
						parentMenu.getOngroupexpand() + ";" + ((UIPanelMenuGroup)child).getOnexpand() : "";
				String onclose = (child instanceof UIPanelMenuGroup) && !((UIPanelMenuGroup)child).isDisabled() && !isParentDisabled(child) ? 
						parentMenu.getOngroupcollapse() + ";" + ((UIPanelMenuGroup)child).getOncollapse() : "";
				String hoveredStyle = (child instanceof UIPanelMenuGroup ? 
						parentMenu.getHoveredGroupStyle() : parentMenu.getHoveredItemStyle())
						+ ";" + (child instanceof UIPanelMenuGroup ? 
								((UIPanelMenuGroup)child).getHoverStyle() : ((UIPanelMenuItem)child).getHoverStyle());
				String hoveredClass = (child instanceof UIPanelMenuGroup ? 
						parentMenu.getHoveredGroupClass() : parentMenu.getHoveredItemClass())
						+ " " + (child instanceof UIPanelMenuGroup ? 
								((UIPanelMenuGroup)child).getHoverClass() : ((UIPanelMenuItem)child).getHoverClass());
				String [] hoveredStyles = hoveredStyle.split(";");
				String [] hoveredClasses = hoveredClass.split(" ");
				
				String mode = getItemMode(child);
				Object target = child.getAttributes().get("target");
				String targetString;
				if (null == target)
					targetString = "";
				else 
					targetString = target.toString();
				
				
				
				
				if (childRendered && parentRendered){
					if ( !isParentDisabled(child) ){
						String childName;
						if(child instanceof UIPanelMenuGroup){
							childName = ((UIPanelMenuGroup)child).getName();
						} else {
							childName = ((UIPanelMenuItem)child).getName();
						}
						
						if(itemNames.contains(childName)){
							throw new RuntimeException("Attibute \"name\" with value \"" + childName + "\" is already used in PanelMenu. It must be unique for every group/item.");
						} else {
							itemNames.add(childName);
						}
						
						buffer.append("var params = new Object();");

						if(child instanceof UIPanelMenuItem){
							List paramsList = encodeParams(context, (UIPanelMenuItem)child);
							if(!paramsList.isEmpty()){
								for (Iterator iterator = paramsList.iterator(); iterator.hasNext();) {
									buffer.append((String)iterator.next());
								}
							}
						}
						
						buffer
								.append("new PanelMenuItem(ids, params,{myId:'")
								.append((String) child.getClientId(context))
								.append("',parentId:'")
								.append((String) child.getParent().getClientId(context))
								.append("'},{type:" + (child instanceof UIPanelMenuItem ? "\"item\"":"\"node\""))
								.append(",onopen:"+("".equals(onopen) ? "\"\"" : "\"" + onopen + "\"")+",onclose:"+("".equals(onclose) ? "\"\"" : "\"" + onclose + "\""))
								.append(",event:\"" + event + "\"")
								.append(",mode:\"" + mode + "\"")
								.append(",target:\"" + targetString + "\"")
								.append(",disabled:" + new Boolean(childDisabled).toString())
								.append(",target:\"" + targetString + "\"")		
								.append(",name:\"" + childName + "\"")
								.append("},{");
							
						for (int i = 0; i < hoveredStyles.length; i++)
							if (!"".equals(hoveredStyles[i])) {
								String [] temp = hoveredStyles[i].split(":");
								String cssName = temp[0].trim();
								String cssValue = temp[1].trim();
								buffer.append("\"" + cssName + "\": \"" + cssValue + "\"");
								if (i != hoveredStyles.length - 1)
									buffer.append(",");
							}
						
						buffer.append("},");
						if (hoveredClasses.length > 0) {
							buffer.append("new Array(");
							for (int i = 0; i < hoveredClasses.length; i++)
								if (!"".equals(hoveredClasses[i])) {;
									buffer.append("\"" + hoveredClasses[i] + "\"");
									if (i != hoveredClasses.length - 1)
										buffer.append(",");
								}
							buffer.append("),");
						} else
							buffer.append("new Array(),");
	
						buffer.append(levels.get(child.getClientId(context)));
						switchOnImagesIfNeeded(context,child,buffer);
						
						addActionIfNeeded(context,child,buffer);
						
						setExpandedIfNeeded(context,child,buffer);
						
						addAjaxFunction(context,child,buffer);
						
						addOnItemHover(parentMenu.getOnitemhover(), child, buffer);
						
						String iconPos = "left";
						boolean isTopLevel = isTopLevel(child);
						if(child instanceof UIPanelMenuGroup){
							iconPos = isTopLevel ? parentMenu.getIconGroupTopPosition() : parentMenu.getIconGroupPosition();
						} else {
							iconPos = isTopLevel ? parentMenu.getIconItemTopPosition() : parentMenu.getIconItemPosition();
						}
						
						buffer.append(","+'"'+iconPos+'"');
						
						addImages(buffer,context,child,component.getClientId(context).toString());
						
						buffer.append(");\n");
					}
				} else {
					continue;
				}
			}
		}
		
		ResponseWriter writer = context.getResponseWriter();
		writer.startElement(HTML.SCRIPT_ELEM, component);
		writer.writeAttribute(HTML.id_ATTRIBUTE, "script" + component.getClientId(context), null);
		writer.writeAttribute(HTML.TYPE_ATTR, "text/javascript", null);
		writer.writeText(panelMenu, null);
		writer.writeText(buffer, null);
		writer.endElement(HTML.SCRIPT_ELEM);
	}
	
	public void flatten(List children, List flatList, Map levels,int initialLevel) {
		FacesContext context = FacesContext.getCurrentInstance();
		if (children != null) {
			for (Iterator iter = children.iterator(); iter.hasNext();) {
				UIComponent child = (UIComponent) iter.next();
				if (child instanceof UIPanelMenu){
					continue;
				}
				flatList.add(child);
				levels.put(child.getClientId(context), new Integer(initialLevel));
				flatten(child.getChildren(), flatList, levels, initialLevel + 1);
			}
		}
	}
	
	private void switchOnImagesIfNeeded(FacesContext context, UIComponent child, StringBuffer buffer)throws IOException {
		boolean isToplevel			= isTopLevel(child);
		String customIconOpened		= "";
		String customIconClosed		= "";

		UIPanelMenu panelMenu = findMenu(child);
		if(panelMenu == null){
			return;
		}
		String iconOpened			= isToplevel ? panelMenu.getIconExpandedTopGroup() : panelMenu.getIconExpandedGroup();
		String iconClosed			= isToplevel ? panelMenu.getIconCollapsedTopGroup() : panelMenu.getIconCollapsedGroup();

		try {
			customIconOpened = (String)child.getAttributes().get("iconOpened");
			customIconClosed = (String)child.getAttributes().get("iconClosed");
		} catch (Exception e) {}
		
		if (child instanceof UIPanelMenuItem){
			buffer.append(",false");
		} else {
			if (iconClosed.equals("custom")&&iconOpened.equals("custom")){
				if (customIconClosed.equals("")&&customIconOpened.equals("")){
					buffer.append(",false");
				} else {
					buffer.append(",true");
				}
			} else {
				buffer.append(",true");
			}
		}
	}
	
	private void addActionIfNeeded(FacesContext context,UIComponent child,StringBuffer buffer){
		//TODO by nick - dima - use CommandScriptBuilder
		if (child instanceof UIPanelMenuItem){
			if (((UIPanelMenuItem)child).getAction() == null){
				buffer.append(",false");	
			} else {
				buffer.append(",true");
			}
		} else {
			if (((UIPanelMenuGroup)child).getAction()!=null&&!((UIPanelMenuGroup)child).getAction().equals("")){
				buffer.append(",'panelMenuNodeAction'");
			} else {
				buffer.append(",false");
			}
		}
	}
	
	private void setExpandedIfNeeded(FacesContext context,UIComponent child,StringBuffer buffer){
		if(child instanceof UIPanelMenuItem){
			buffer.append(",false");
		} else {
			UIPanelMenuGroup group = (UIPanelMenuGroup)child;
			if(group.getValue() != null){
				buffer.append(",").append(group.getValue().toString());
			} else {
				PanelMenuGroupRenderer r =  (PanelMenuGroupRenderer)context.getRenderKit().getRenderer(group.getFamily(), group.getRendererType());
				boolean isNodeOpened;
				try {
					isNodeOpened = r.isOpened(context, child);
				} catch (IOException e) {
					isNodeOpened = false;
				}
				buffer.append(",").append(String.valueOf(isNodeOpened));
			}
		}
	}
	
	private void addImages(StringBuffer buffer,FacesContext context,UIComponent component,String id){
		UIPanelMenu panelMenu = findMenu(component);
		if(panelMenu == null){
			return;
		}
		boolean isTopLevel = isTopLevel(component);
		
		final String PANEL_MENU_SPACER_ICON = getIconByType(PANEL_MENU_SPACER_ICON_NAME, isTopLevel,context, component);

		if(component instanceof UIPanelMenuItem){
			UIPanelMenuItem item = (UIPanelMenuItem)component;
			
			String defaultItemIcon = null;
			String defaultItemIconSrc = null;
			String customItemIcon = null;
			String customIconSource = null;
			
			if(isTopLevel){
				if(item.isDisabled()){
					defaultItemIcon = panelMenu.getIconTopDisabledItem();
				} else {
					defaultItemIcon = panelMenu.getIconTopItem();
				}
				if(defaultItemIcon == null || defaultItemIcon.equals("")){
					if(item.isDisabled()){
						defaultItemIcon = panelMenu.getIconDisabledItem();
					} else {
						defaultItemIcon = panelMenu.getIconItem();
					}
				}
			} else {
				//isTopLevel == false
				if(defaultItemIcon == null || defaultItemIcon.equals("")){
					if(item.isDisabled()){
						defaultItemIcon = panelMenu.getIconDisabledItem();
					} else {
						defaultItemIcon = panelMenu.getIconItem();
					}
				}
			}
			
			if(defaultItemIcon != null && defaultItemIcon.equals("none")){
				defaultItemIconSrc = PANEL_MENU_SPACER_ICON;
			} else {
				defaultItemIconSrc = getIconByType(defaultItemIcon, isTopLevel,context, component);
			}
			
			customItemIcon = item.isDisabled() ? item.getIconDisabled() : item.getIcon();
			if(customItemIcon != null && customItemIcon.equals("none")){
				customIconSource = PANEL_MENU_SPACER_ICON;
			} else {
				customIconSource = getIconByType(customItemIcon, isTopLevel,context, component);
			}
			
			if(customItemIcon != null && !customItemIcon.equals("")){
				buffer.append(","+'"'+customIconSource+'"').append(","+'"'+customIconSource+'"'+" ");
			} else if (defaultItemIcon != null && !defaultItemIcon.equals("")){
				buffer.append(","+'"'+defaultItemIconSrc+'"').append(","+'"'+defaultItemIconSrc+'"'+" ");
			} else {
				buffer.append(","+'"'+PANEL_MENU_SPACER_ICON+'"').append(","+'"'+PANEL_MENU_SPACER_ICON+'"'+" ");
			}
			buffer.append(",\"\" ");
			
		} else if(component instanceof UIPanelMenuGroup){
			UIPanelMenuGroup group = (UIPanelMenuGroup)component;

			String defaultIconNodeClosed = isTopLevel ? (group.isDisabled() ? panelMenu.getIconTopDisableGroup() : panelMenu.getIconCollapsedTopGroup()) : (group.isDisabled() ? panelMenu.getIconDisabledGroup() : panelMenu.getIconCollapsedGroup());
			
			if(isTopLevel){
				if(group.isDisabled()){
					defaultIconNodeClosed = panelMenu.getIconTopDisableGroup();
					if(defaultIconNodeClosed == null || defaultIconNodeClosed.equals("")){
						defaultIconNodeClosed = panelMenu.getIconDisabledGroup();
					}
				} else {
					defaultIconNodeClosed = panelMenu.getIconCollapsedTopGroup();
					if(defaultIconNodeClosed == null || defaultIconNodeClosed.equals("")){
						defaultIconNodeClosed = panelMenu.getIconCollapsedGroup();
					}
				}
			} else {
				if(group.isDisabled()){
					defaultIconNodeClosed = panelMenu.getIconDisabledGroup();
				} else {
					defaultIconNodeClosed = panelMenu.getIconCollapsedGroup();
				}
			}
			
			String defaultIconNodeOpened = isTopLevel ? panelMenu.getIconExpandedTopGroup() : panelMenu.getIconExpandedGroup();
			
			if(isTopLevel){
				defaultIconNodeOpened = panelMenu.getIconExpandedTopGroup();
				if(defaultIconNodeOpened == null || defaultIconNodeOpened.equals("")){
					defaultIconNodeOpened = panelMenu.getIconExpandedGroup();
				}
			} else {
				defaultIconNodeOpened = panelMenu.getIconExpandedGroup();
			}

			String defaultIconNodeClosedSrc	= getIconByType(defaultIconNodeClosed, isTopLevel,context, component);
			String defaultIconNodeOpenedSrc	= getIconByType(defaultIconNodeOpened, isTopLevel,context, component);

			String iconExpanded = "";
			String iconCollapsed = "";
			String iconExpandedSource = "";
			String iconCollapsedSource = "";

			iconExpanded = group.isDisabled() ? group.getIconDisabled() : group.getIconExpanded();
			iconCollapsed = group.isDisabled() ? group.getIconDisabled() : group.getIconCollapsed();
			iconExpandedSource = getIconByType(iconExpanded,isTopLevel,context,component);
			iconCollapsedSource = getIconByType(iconCollapsed,isTopLevel,context,component);
			
			if(iconExpanded != null && !iconExpanded.equals("")){
				buffer.append(","+'"'+ iconExpandedSource + '"');
			} else if(defaultIconNodeOpened != null && !defaultIconNodeOpened.equals("")){
				buffer.append(","+'"'+defaultIconNodeOpenedSrc + '"');
			} else {
				buffer.append(","+'"'+PANEL_MENU_SPACER_ICON + '"');
			}
			
			if(iconCollapsed != null && !iconCollapsed.equals("")){
				buffer.append(","+'"'+iconCollapsedSource+'"');	
			} else if(defaultIconNodeClosed != null && !defaultIconNodeClosed.equals("")){
				buffer.append(","+'"'+defaultIconNodeClosedSrc+'"');
			} else {
				buffer.append(","+'"'+PANEL_MENU_SPACER_ICON + '"');
			}
		}
		buffer.append(",\"" + PANEL_MENU_SPACER_ICON + "\"");
	}
			
	protected void addAjaxFunction(FacesContext context, UIComponent child, StringBuffer buffer) {
		JSFunction function = AjaxRendererUtils.buildAjaxFunction(child,
                context);
        Map eventOptions = AjaxRendererUtils.buildEventOptions(context,
        		child);
        function.addParameter(eventOptions);
        
        buffer.append(",\"");
        function.appendScript(buffer);
        buffer.append("\"");
	}
	
	protected void addOnItemHover(String menuOnItemHover, UIComponent child, StringBuffer buffer) {
        buffer.append(",\"");
		if(child instanceof UIPanelMenuItem){
			if(menuOnItemHover != null && !menuOnItemHover.equals("")) buffer.append(menuOnItemHover);
		}
		buffer.append("\"");
	}
	
	public void renderChildren(FacesContext facesContext, UIComponent component)throws IOException {

		if(component instanceof UIPanelMenu){
			UIPanelMenu panelMenu = (UIPanelMenu)component;
			if(panelMenu.getChildCount() > 0){
				for (Iterator it = component.getChildren().iterator(); it.hasNext();) {
					UIComponent child = (UIComponent) it.next();
					if(child instanceof UIPanelMenuGroup) {
						UIPanelMenuGroup group = (UIPanelMenuGroup)child;
		
						if(panelMenu.isExpandSingle()) {	
							if(!(Boolean)panelMenu.getAttributes().get("firstExpandedEncoded")) {
								if(group.isExpanded()) {
									panelMenu.getAttributes().put("firstExpandedEncoded", true);
								}
							}else {
								group.setExpanded(false);
								if((Boolean)group.getValue()){
									group.setValue(null);
								}
							}
						}						
					}
					renderChild(facesContext, child);
				}
			}
		}
	}
	
	public void doDecode(FacesContext context, UIComponent component) {
		Map requestMap = context.getExternalContext().getRequestParameterMap();
		String menuClientId = component.getClientId(context);
		UIPanelMenu menu = ((UIPanelMenu)component);
		Object selectedItemName = requestMap.get(menuClientId + "selectedItemName");
		if(selectedItemName != null){
			menu.setSubmittedValue(selectedItemName);
		}
	}
	
}
