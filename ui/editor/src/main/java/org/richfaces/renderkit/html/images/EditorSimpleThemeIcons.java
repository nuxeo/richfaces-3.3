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
package org.richfaces.renderkit.html.images;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Date;

import javax.faces.context.FacesContext;

import org.ajax4jsf.resource.InternetResourceBuilder;
import org.ajax4jsf.resource.PngRenderer;
import org.ajax4jsf.resource.ResourceContext;

/**
 * Class for simple theme icons resource image of tinyMCE editor.
 * Used for richfaces custom skin of tinyMCE editor.
 * 
 * @author Alexandr Levkovsky
 *
 */
public class EditorSimpleThemeIcons extends EditorIcons{

	/** icons image dimensions */
	private Dimension dimension = new Dimension(40, 66);
	
	/**
	 * Class constructor 
	 */
	public EditorSimpleThemeIcons() {
		super();

		setRenderer(new PngRenderer());
		setLastModified(new Date(InternetResourceBuilder.getInstance()
				.getStartTime()));
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.resource.Java2Dresource#getDimensions(javax.faces.context.FacesContext, java.lang.Object)
	 */
	public Dimension getDimensions(FacesContext facesContext, Object data) {
		return dimension;
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.resource.Java2Dresource#getDimensions(org.ajax4jsf.resource.ResourceContext)
	 */
	protected Dimension getDimensions(ResourceContext resourceContext) {
		return dimension;
	}
	
	/* (non-Javadoc)
	 * @see org.ajax4jsf.resource.Java2Dresource#paint(org.ajax4jsf.resource.ResourceContext, java.awt.Graphics2D)
	 */
	@Override
	public void paint(ResourceContext context, Graphics2D graphics) {
		Object[] stored = (Object[]) restoreData(context);
		if (stored != null) {
			BufferedImage block1 = paintMainBlock(stored, false, false);
			BufferedImage separator = paintSeparatorBlock(stored);
			BufferedImage block2 = paintMainBlock(stored, true, false);
			BufferedImage block3 = paintMainBlock(stored, true, true);
			
			graphics.setBackground(Color.WHITE);
			graphics.clearRect(0, 0, 40, 66);
			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
			graphics.drawImage(block1, 0, 0, 22, 22, null);
			graphics.drawImage(separator, 22, 0, 5, 22, null);
			graphics.drawImage(block2, 0, 22, 22, 22, null);
			graphics.drawImage(block3, 0, 44, 22, 22, null);
		}

	}
		
}
