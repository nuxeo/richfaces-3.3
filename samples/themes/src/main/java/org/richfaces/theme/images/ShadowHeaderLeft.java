/**
 * 
 */
package org.richfaces.theme.images;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;

import org.richfaces.theme.images.BaseShadowGradient.ShadowData;

/**
 * @author asmirnov
 *
 */
public class ShadowHeaderLeft extends ShadowHeaderBackground {
	
	@Override
	protected void paintGradient(Graphics2D g2d, Data data) {
		// Shadow
		Dimension dim = getDimensions(null, data);
		ShadowData shadowData = (ShadowData) data;
		if(null != shadowData.getShadowColor()){
			CompositeColor color = new CompositeColor(shadowData.getHeaderGradientColor(),shadowData.getShadowColor());
			for(int dx=0;dx < MARGIN;dx++){
				int radius = SHADOW_RADIUS+(int)MARGIN-dx;
				color.setMix((double)dx/MARGIN/2.0);
				g2d.setColor(color.getMixedColor());
				g2d.drawRoundRect(dx, dx, dim.width*2, dim.height*2,radius,radius);
			}
		}
		// Paint gradient itself
		g2d.translate(0.0, MARGIN);
		RoundRectangle2D clip = new RoundRectangle2D.Double(MARGIN,0,dim.getWidth()*2.0,dim.getHeight()*2.0,RADIUS,RADIUS);
		g2d.setClip(clip);
		super.paintGradientContent(g2d, data);
	}

}
