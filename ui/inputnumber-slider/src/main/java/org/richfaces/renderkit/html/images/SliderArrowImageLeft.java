/**
* License Agreement.
*
* JBoss RichFaces - Ajax4jsf Component Library
*
* Copyright (C) 2008 CompuGROUP Holding AG
* 
* This library is free software; you can redistribute it and/or
* modify it under the terms of the GNU Lesser General Public
* License version 2.1 as published by the Free Software Foundation.
*
* This library is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
* Lesser General Public License for more details.
*
* You should have received a copy of the GNU Lesser General Public
* License along with this library; if not, write to the Free Software
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA

*/

package org.richfaces.renderkit.html.images;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import org.ajax4jsf.resource.ResourceContext;
import org.richfaces.renderkit.html.images.SliderArrowImage;

/**
 * @author mpopiolek
 * 
 */
public class SliderArrowImageLeft extends SliderArrowImage {

    @Override
    protected void paint(ResourceContext context, Graphics2D g2d) {
        Integer color = (Integer) restoreData(context);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(new Color(color.intValue()));
        g2d.drawLine(4, 0, 4, 0);
        g2d.drawLine(3, 1, 4, 1);
        g2d.drawLine(2, 2, 4, 2);
        g2d.drawLine(1, 3, 4, 3);
        g2d.drawLine(2, 4, 4, 4);
        g2d.drawLine(3, 5, 4, 5);
        g2d.drawLine(4, 6, 4, 6);
    }
}
