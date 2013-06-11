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

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.ajax4jsf.exception.FileUploadException;
import org.ajax4jsf.request.MultipartRequest;

/**
 * @author Nick Belaevski
 * @since 3.3.1
 */
public class FileUploadPhaselistener implements PhaseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3243506714022741982L;

	/* (non-Javadoc)
	 * @see javax.faces.event.PhaseListener#afterPhase(javax.faces.event.PhaseEvent)
	 */
	public void afterPhase(PhaseEvent event) {
	}

	/* (non-Javadoc)
	 * @see javax.faces.event.PhaseListener#beforePhase(javax.faces.event.PhaseEvent)
	 */
	public void beforePhase(PhaseEvent event) {
		FacesContext facesContext = event.getFacesContext();
		ExternalContext externalContext = facesContext.getExternalContext();
		Object request = (MultipartRequest) externalContext.getRequestMap().get(
				FileUploadConstants.FILE_UPLOAD_REQUEST_ATTRIBUTE_NAME);
		
		if (request instanceof MultipartRequest) {
			MultipartRequest multipartRequest = (MultipartRequest) request;
			try {
				multipartRequest.parseRequest();
				if (!multipartRequest.isDone()) {
					facesContext.responseComplete();
				} 
			} catch (FileUploadException e) {
				facesContext.responseComplete();
				throw e;
			}
		}
	}

	/* (non-Javadoc)
	 * @see javax.faces.event.PhaseListener#getPhaseId()
	 */
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}
