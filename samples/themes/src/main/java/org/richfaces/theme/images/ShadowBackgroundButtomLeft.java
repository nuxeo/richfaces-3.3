/**
 * 
 */
package org.richfaces.theme.images;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

/**
 * @author asmirnov
 *
 */
public class ShadowBackgroundButtomLeft extends ShadowBackgroundLeft {
	
	public ShadowBackgroundButtomLeft() {
		super(1);
	}
	
	@Override
	protected void paintBackground(Graphics2D g2d, Data data) {
		Dimension dimensions = getDimensions(null);
		g2d.setColor(new Color(data.getHeaderGradientColor()));
		g2d.fillRect(0, 0, dimensions.width,  dimensions.height);
	}

}
