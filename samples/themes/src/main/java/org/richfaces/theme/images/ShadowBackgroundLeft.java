/**
 * 
 */
package org.richfaces.theme.images;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics2D;


/**
 * @author asmirnov
 *
 */
public class ShadowBackgroundLeft extends BaseShadowGradient {

	
	public ShadowBackgroundLeft(int height) {
		super(15, height, height, "headerBackgroundColor", "headerGradientColor");
	}
	
	public ShadowBackgroundLeft() {
		this(450);
	}

	@Override
	protected void paintGradient(Graphics2D g2d, Data data) {
		paintBackground(g2d, data);
		ShadowData shadowData = (ShadowData) data;
		Dimension dimensions = getDimensions(null);
		if(null != shadowData.getShadowColor()){
//			g2d.setColor(new Color(shadowData.getShadowColor()));
		    GradientPaint gragient = new GradientPaint( 0.0f,0.0f, new Color(shadowData.getShadowColor()&0xffffff,true),(float) MARGIN*2, 0,  new Color(shadowData.getShadowColor()));
		    g2d.setPaint(gragient);
			g2d.fillRect(0, 0, (int) (MARGIN*2.0), dimensions.height);
		}
		if(null != shadowData.getBorderColor()){
			g2d.setColor(new Color(shadowData.getBorderColor()));
			g2d.fillRect((int) MARGIN, 0, (int) (dimensions.width-MARGIN),  dimensions.height);
		}

	}

	protected void paintBackground(Graphics2D g2d, Data data) {
		super.paintGradient(g2d, data);
	}

}
