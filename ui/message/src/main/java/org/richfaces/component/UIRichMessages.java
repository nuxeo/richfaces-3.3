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
package org.richfaces.component;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.el.ELException;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIComponent;
import javax.faces.component.UIMessages;
import javax.faces.context.FacesContext;

import org.ajax4jsf.component.AjaxOutput;
import org.ajax4jsf.renderkit.RendererUtils;

/**
 * @author Anton Belevich
 * 
 */
public abstract class UIRichMessages extends UIMessages implements AjaxOutput {

	public static final String COMPONENT_TYPE = "org.richfaces.component.RichMessages";

	private String forVal = null;
	private boolean updated = false;

	private List<FacesMessageWithId> renderedMessages;

	/**
	 * <p>
	 * Return the client identifier of the component for which this component
	 * represents associated message(s) (if any).
	 * </p>
	 */
	public String getFor() {

		if (this.forVal != null) {
			return (this.forVal);
		}
		ValueExpression ve = getValueExpression("for");
		if (ve != null) {
			try {
				return ((String) ve.getValue(getFacesContext().getELContext()));
			} catch (ELException e) {
				throw new FacesException(e);
			}
		} else {
			return (null);
		}

	}

	/**
	 * <p>
	 * Set the client identifier of the component for which this component
	 * represents associated message(s) (if any). This property must be set
	 * before the message is displayed.
	 * </p>
	 * 
	 * @param newFor
	 *            The new client id
	 */
	public void setFor(String newFor) {

		forVal = newFor;

	}

	public boolean isUpdated() {
		return updated;
	}

	public boolean isAjaxRendered() {
		return true;
	}

	public Iterator<FacesMessage> getMessages(FacesContext context) {
		String forInputId = getFor();
		if (null == this.renderedMessages || null != forInputId) {
			List<String> severenities = getSeverenities();
			renderedMessages = new ArrayList<FacesMessageWithId>();
			if (isGlobalOnly()) {
				addMessagesForId(context, null, severenities);
			} else if (null != forInputId) {
				UIComponent componentFor = RendererUtils.getInstance()
						.findComponentFor(context, this, forInputId);
				if (null != componentFor) {
					addMessagesForId(context,
							componentFor.getClientId(context), severenities);
				}
			} else {
				Iterator<String> clientIdsWithMessages = context
						.getClientIdsWithMessages();
				while (clientIdsWithMessages.hasNext()) {
					addMessagesForId(context, clientIdsWithMessages.next(),
							severenities);
				}
			}
		}
		;
		final Iterator<FacesMessageWithId> iterator = this.renderedMessages
				.iterator();
		return new Iterator<FacesMessage>() {

			public boolean hasNext() {
				return iterator.hasNext();
			}

			public FacesMessage next() {
				return iterator.next().getMessage();
			}

			public void remove() {
			}
		};
	}

	public void updateMessages(FacesContext context, String forId) {
		updated = true;
		if (null == renderedMessages) {
			renderedMessages = new ArrayList<FacesMessageWithId>();
		} else {
			Iterator<FacesMessageWithId> renderedMessageIterator = renderedMessages
					.iterator();
			while (renderedMessageIterator.hasNext()) {
				String clientId = renderedMessageIterator.next().getClientId();
				if ((null == forId && null == clientId)
						|| (null != forId && forId.equals(clientId))) {
					renderedMessageIterator.remove();
				}
			}
		}
		addMessagesForId(context, forId, getSeverenities());
	}

	/**
	 * @param context
	 * @param forId
	 * @param severenities
	 *            TODO
	 */
	protected void addMessagesForId(FacesContext context, String forId,
			List<String> severenities) {
		Iterator<FacesMessage> messages = context.getMessages(forId);
		while (messages.hasNext()) {
			FacesMessage message = messages.next();
			if (severenities.size() == 0 || severenities.contains("ALL")
					|| isApplicableMessage(severenities, message)) {
				renderedMessages.add(new FacesMessageWithId(forId, message));
			}
		}
	}

	/**
	 * @param severenities
	 * @param message
	 * @return
	 */
	protected boolean isApplicableMessage(List<String> severenities,
			FacesMessage message) {
		if (severenities.size() == 0 || severenities.contains("ALL")) {
			return true;
		}
		Severity severity = message.getSeverity();
		for (Object key : FacesMessage.VALUES_MAP.keySet()) {
			Severity sev = (Severity) FacesMessage.VALUES_MAP.get(key);
			if (0 == sev.compareTo(severity)) {
				return severenities.contains(key);
			}
		}
		return false;
	}

	public void setAjaxRendered(boolean ajaxRendered) {
		if (!ajaxRendered) {
			new IllegalArgumentException();
		}
	}

	@Override
	public void encodeBegin(FacesContext context) throws IOException {
		if (!isUpdated()) {
			setRenderedMessages(null);
		}
		super.encodeBegin(context);
	}

	public abstract String getLevel();

	public abstract void setLevel(String level);

	public List<String> getSeverenities() {
		String level = getLevel();
		List<String> severenities;
		if (null != level) {
			String[] levels = level.split(",");
			severenities = new ArrayList<String>(levels.length);
			for (int i = 0; i < levels.length; i++) {
				String levelName = levels[i].toUpperCase().trim();
				severenities.add(levelName);
			}
		} else {
			severenities = Collections.emptyList();
		}
		return severenities;
	}

	private Object[] values;

	public Object saveState(FacesContext context) {

		if (values == null) {
			values = new Object[3];
		}

		values[0] = super.saveState(context);
		values[1] = this.forVal;
		values[2] = saveAttachedState(context, getRenderedMessages());
		return (values);

	}

	@SuppressWarnings("unchecked")
	public void restoreState(FacesContext context, Object state) {

		values = (Object[]) state;
		super.restoreState(context, values[0]);
		forVal = (String) values[1];
		setRenderedMessages((List<FacesMessageWithId>) restoreAttachedState(
				context, values[2]));
	}

	/**
	 * @author asmirnov
	 * 
	 */
	@SuppressWarnings("serial")
	public static class FacesMessageWithId implements Serializable {
		private FacesMessage message;

		private String clientId;

		/**
		 * @param clientId
		 * @param message
		 */
		public FacesMessageWithId(String clientId, FacesMessage message) {
			this.message = message;
			this.clientId = clientId;
		}

		/**
		 * @return the message
		 */
		public FacesMessage getMessage() {
			return message;
		}

		/**
		 * @return the clientId
		 */
		public String getClientId() {
			return clientId;
		}

		/**
		 * <p>
		 * Persist {@link FacesMessageWithId} artifacts,
		 * including the non serializable <code>Severity</code>.
		 * </p>
		 */
		private void writeObject(ObjectOutputStream out) throws IOException {
			if (clientId != null) {
				out.writeBoolean(true);
				out.writeUTF(clientId);
			} else {
				out.writeBoolean(false);
			}

			out.writeInt(message.getSeverity().getOrdinal());
			
			String summary = message.getSummary();
			if (summary != null) {
				out.writeBoolean(true);
				out.writeUTF(summary);
			} else {
				out.writeBoolean(false);
			}
			
			String detail = message.getDetail();
			if (detail != null) {
				out.writeBoolean(true);
				out.writeUTF(detail);
			} else {
				out.writeBoolean(false);
			}
		}

		/**
		 * <p>
		 * Reconstruct {@link FacesMessageWithId} from
		 * serialized artifacts.
		 * </p>
		 */
		private void readObject(ObjectInputStream in) throws IOException,
				ClassNotFoundException {
			
			if (in.readBoolean()) {
				clientId = in.readUTF();
			}

			Severity severity = FacesMessage.SEVERITY_INFO;
			int ordinal = in.readInt();
			if (ordinal >= 0 && ordinal < FacesMessage.VALUES.size()) {
				severity = (Severity) FacesMessage.VALUES.get(ordinal);
			}
			
			String summary = null;
			if (in.readBoolean()) {
				summary = in.readUTF();
			}
			
			String detail = null;
			if (in.readBoolean()) {
				detail = in.readUTF();
			}
			
			message = new FacesMessage(severity, summary, detail);
		}

	}

	/**
	 * @return the renderedMessages
	 */
	protected List<FacesMessageWithId> getRenderedMessages() {
		return renderedMessages;
	}

	/**
	 * @param renderedMessages
	 *            the renderedMessages to set
	 */
	protected void setRenderedMessages(List<FacesMessageWithId> renderedMessages) {
		this.renderedMessages = renderedMessages;
	}

}
