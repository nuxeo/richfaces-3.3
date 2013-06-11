/**
 * 
 */
package org.richfaces.theme.images;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

import org.richfaces.renderkit.html.BaseGradient;

/**
 * @author asmirnov
 *
 */
public class HeaderBackground extends BaseGradient {

	public HeaderBackground() {
		super(1, 95, 80,"headerBackgroundColor","headerGradientColor", false);
	}
	
	@Override
	protected void paintGradient(Graphics2D g2d, Data data) {
		// Paint gradient itself
		super.paintGradient(g2d, data);
		// Paint lower bar
		Dimension dim = getDimensions(null, data);
		Integer headerBackgroundColor = data.getHeaderBackgroundColor();

		if(headerBackgroundColor != null && dim.height > getGradientHeight() && getGradientHeight() >=0){
			g2d.setPaint(null);
			g2d.setColor(new Color(headerBackgroundColor));
			g2d.fillRect(0,getGradientHeight(),dim.width,dim.height);
		}
	}
}
