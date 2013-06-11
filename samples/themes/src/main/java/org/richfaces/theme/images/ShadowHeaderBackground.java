/**
 * 
 */
package org.richfaces.theme.images;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;



/**
 * @author asmirnov
 *
 */
public class ShadowHeaderBackground extends BaseShadowGradient {

	public ShadowHeaderBackground() {
		super(15, 95, 80,"headerGradientColor","headerBackgroundColor", false);
	}
	
	@Override
	protected void paintGradient(Graphics2D g2d, Data data) {
		// Shadow
		ShadowData shadowData = (ShadowData) data;
		if(null != shadowData.getShadowColor()){
//			g2d.setColor(new Color(shadowData.getHeaderGradientColor()));
		    GradientPaint gragient = new GradientPaint(0.0f, 0.0f, new Color(shadowData.getHeaderGradientColor()), 0, (float) MARGIN*2, new Color(shadowData.getShadowColor()));
		    g2d.setPaint(gragient);
			g2d.fillRect(0, 0, getDimensions(null).width, (int) MARGIN);			
		}
		g2d.translate(0.0, MARGIN);
		// Paint gradient itself
		paintGradientContent(g2d, data);
	}

	/**
	 * @param g2d
	 * @param data
	 */
	protected void paintGradientContent(Graphics2D g2d, Data data) {
		Dimension dim = getDimensions(null, data);
		super.paintGradient(g2d, data);
		// Paint lower bar
		Integer headerBackgroundColor = data.getHeaderBackgroundColor();

		if(headerBackgroundColor != null && dim.height > getGradientHeight() && getGradientHeight() >=0){
			g2d.setPaint(null);
			g2d.setColor(new Color(headerBackgroundColor));
			g2d.fillRect(0,getGradientHeight(),dim.width,dim.height);
		}
	}
	
}
