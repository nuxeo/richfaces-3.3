/**
 * 
 */
package org.richfaces.theme.images;

import java.awt.Graphics2D;

/**
 * @author asmirnov
 *
 */
public class ShadowFooterRight extends ShadowFooterLeft {
	
	@Override
	protected void paintGradient(Graphics2D g2d, Data data) {
	    //x -> -x, y -> y
	    g2d.transform(createFlipTransform());
		super.paintGradient(g2d, data);
	}

}
