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

package org.richfaces.renderkit;

import java.io.IOException;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.richfaces.component.Draggable;
import org.richfaces.component.Dropzone;
import org.richfaces.component.UIDndParam;
import org.richfaces.component.nsutils.NSUtils;
import org.richfaces.component.util.MessageUtil;
import org.richfaces.json.JSONException;
import org.richfaces.json.JSONObject;

/**
 * @author Nick Belaevski - nbelaevski@exadel.com
 * created 30.01.2007
 * 
 */
public class DnDParametersEncoder implements AttributeParametersEncoder {

	private static DnDParametersEncoder instance = null;

	private static final String DEFAULT = "default";
	private static final String DRAG = "drag";
	private static final String DROP = "drop";

	private DnDParametersEncoder() {

	}

	public static synchronized DnDParametersEncoder getInstance() {
		if (instance == null) {
			instance = new DnDParametersEncoder();
		}

		return instance;
	}

	private static final String MESSAGE_CAST = "DnD parameter [{0}] of type [{1}] applied to component [{2}] has been ignored because component doesn''t implement [{3}]!"; 
	private static final String MESSAGE_UNK_TYPE = "DnD parameter [{0}] is of unknown type [{1}]!"; 

	/* (non-Javadoc)
	 * @see org.richfaces.renderkit.AttributeParameterEncoder#doEncode(javax.faces.context.FacesContext, javax.faces.component.UIComponent)
	 */
	public void doEncode(FacesContext context, UIComponent component)
	throws IOException {

		Map params = doEncodeAsMap(context, component);
		ResponseWriter responseWriter = context.getResponseWriter();

		Set entrySet = params.entrySet();
		for (Iterator iterator = entrySet.iterator(); iterator.hasNext();) {
			Map.Entry entry = (Map.Entry) iterator.next();

			String name = (String) entry.getKey();
			JSONObject dndParams = (JSONObject) entry.getValue();

			if (dndParams != null) {
				responseWriter.writeAttribute(NSUtils.XMLNS_PREFIX + ":" + name + "dndparams", 
						dndParams.toString(), null);
			}
		}
	}


	private void renderChildren(FacesContext facesContext,
			UIComponent component) throws IOException {
		if (component.getChildCount() > 0) {
			for (Iterator it = component.getChildren().iterator(); it.hasNext();) {
				UIComponent child = (UIComponent) it.next();
				renderChild(facesContext, child);
			}
		}
	}

	private void renderChild(FacesContext facesContext, UIComponent child)
	throws IOException {
		if (!child.isRendered()) {
			return;
		}

		child.encodeBegin(facesContext);
		if (child.getRendersChildren()) {
			child.encodeChildren(facesContext);
		} else {
			renderChildren(facesContext, child);
		}
		child.encodeEnd(facesContext);
	}

	public String doEncodeAsString(FacesContext context, UIComponent component)
	throws IOException {

		JSONObject merged = new JSONObject();

		try {

			Map map = doEncodeAsMap(context, component);
			for (Iterator iterator = map.entrySet().iterator(); iterator.hasNext();) {
				Map.Entry entry = (Map.Entry) iterator.next();

				if (entry.getValue() != null) {
					JSONObject object = (JSONObject) entry.getValue();

					for (Iterator keys = object.keys(); keys.hasNext(); ) {
						String key = (String) keys.next();

						merged.put(key, object.get(key));
					}
				}
			}
		} catch (JSONException e) {
			IOException exception = new IOException(e.getMessage());
			exception.initCause(e.getCause());
			throw exception;
		}

		return merged.toString();
	}

	protected Map doEncodeAsMap(FacesContext context, UIComponent component) throws IOException {
		Map params = new LinkedHashMap();
		params.put(DEFAULT, new JSONObject());

		boolean isDraggable = component instanceof Draggable;
		if (isDraggable) {
			params.put(DRAG, new JSONObject());
		}

		boolean isDropzone = component instanceof Dropzone;
		if (isDropzone) {
			params.put(DROP, new JSONObject());
		}

		try {
			if (component.getChildCount() != 0) {
				List children = component.getChildren();

				for (Iterator iterator = children.iterator(); iterator.hasNext();) {
					Object object = (Object) iterator.next();

					if (object instanceof UIDndParam) {
						UIDndParam dndParam = (UIDndParam) object;
						String type = dndParam.getType();
						JSONObject dndParams = null;

						if (DRAG.equals(type)) {
							if (isDraggable) {
								dndParams = (JSONObject) params.get(DRAG);
							} else {
								String messageString = MessageFormat.format(MESSAGE_CAST, new Object[] {
										MessageUtil.getLabel(context, dndParam),
										type,
										MessageUtil.getLabel(context, component),
										Draggable.class.getSimpleName()
								});

								FacesMessage message = new FacesMessage(messageString, messageString);
								context.addMessage(component.getClientId(context), message);
							}
						} else if (DROP.equals(type)) {
							if (isDropzone) {
								dndParams = (JSONObject) params.get(DROP);
							} else {
								String messageString = MessageFormat.format(MESSAGE_CAST, new Object[] {
										MessageUtil.getLabel(context, dndParam),
										type,
										MessageUtil.getLabel(context, component),
										Dropzone.class.getSimpleName()
								});

								FacesMessage message = new FacesMessage(messageString, messageString);
								context.addMessage(component.getClientId(context), message);
							}
						} else if (type == null || type.length() == 0 || DEFAULT.equals(type)) {
							dndParams = (JSONObject) params.get(DEFAULT);
						} else {
							String messageString = MessageFormat.format(MESSAGE_UNK_TYPE, new Object[] {
									MessageUtil.getLabel(context, dndParam),
									type
							});

							throw new IllegalArgumentException(messageString);
						}

						if (dndParams != null) {
							if (dndParam.isRendered()) {

								ResponseWriter responseWriter = context.getResponseWriter();
								StringWriter dumpWriter = new StringWriter();

								try {
									context.setResponseWriter(responseWriter.cloneWithWriter(dumpWriter));

									if (dndParam.getChildCount() == 0) {
										context.getResponseWriter().writeText(dndParam.getValue(), null);
									} else {
										List paramChildren = dndParam.getChildren();

										for (Iterator paramIterator = paramChildren
												.iterator(); paramIterator.hasNext();) {

											UIComponent paramChild = (UIComponent) paramIterator.next();
											renderChild(context, paramChild);

										}	
									}

									context.getResponseWriter().flush();
								} finally {
									context.setResponseWriter(responseWriter);
								}

								String childContent = dumpWriter.getBuffer().toString();
								dndParams.put(dndParam.getName(), childContent);
							}
						}
					}
				}
			}

			Set entrySet = params.entrySet();
			for (Iterator iterator = entrySet.iterator(); iterator.hasNext();) {
				Map.Entry entry = (Map.Entry) iterator.next();

				JSONObject dndParams = (JSONObject) entry.getValue();

				if (dndParams.length() == 0) {
					entry.setValue(null);
				}
			}
		} catch (JSONException e) {
			IOException exception = new IOException(e.getMessage());
			exception.initCause(e.getCause());
			throw exception;
		}

		return params;
	}

}
