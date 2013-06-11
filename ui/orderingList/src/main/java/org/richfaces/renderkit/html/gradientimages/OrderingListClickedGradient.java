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

package org.richfaces.renderkit.html.gradientimages;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import org.ajax4jsf.resource.ResourceContext;
import org.richfaces.renderkit.html.BaseGradient;
import org.richfaces.skin.Skin;

/**
 * @author Siarhej Chalipau
 *
 */
public class OrderingListClickedGradient extends BaseGradient {
	protected void paint(ResourceContext resourceContext, Graphics2D g2d) {
		g2d.transform(new AffineTransform(1., 0., 0., -1., 0., 15.));
		super.paint(resourceContext, g2d);
	}
	
	public OrderingListClickedGradient() {
		 super(7, 15, 9, "tabBackgroundColor", Skin.generalBackgroundColor);
	}

}
