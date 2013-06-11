/**
 * 
 */
package org.richfaces.theme.images;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;

import org.richfaces.theme.images.BaseShadowGradient.ShadowData;



/**
 * @author asmirnov
 *
 */
public class ShadowFooterBackground extends BaseShadowGradient {

	public ShadowFooterBackground() {
		super(15, 72, 65,"panelBorderColor", "headerGradientColor",false);
	}
	
	@Override
	protected void paintGradient(Graphics2D g2d, Data data) {
		Dimension dim = getDimensions(null,data);
		// Shadow
		ShadowData shadowData = (ShadowData) data;
		if(null != shadowData.getShadowColor()){
			g2d.setColor(new Color(shadowData.getShadowColor()));
//			g2d.fillRect(0, (int) (dim.height-MARGIN),  dim.width, (int) MARGIN);
		    GradientPaint gragient = new GradientPaint(0.0f, (float) dim.getHeight(), new Color(shadowData.getHeaderGradientColor()), 0.0f, (float)(dim.getHeight()-MARGIN*2), new Color(shadowData.getShadowColor()));
		    g2d.setPaint(gragient);
			g2d.fillRect(0, (int) (dim.height-MARGIN),  dim.width, (int) MARGIN);			
		}
		g2d.translate(0.0, -MARGIN);
		paintGradientContent(g2d, data);
	}

	protected void paintGradientContent(Graphics2D g2d, Data data) {
		// Paint gradient itself
		super.paintGradient(g2d, data);
	}
	
}
