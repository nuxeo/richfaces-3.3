/**
 * 
 */
package org.richfaces.theme.images;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import org.richfaces.renderkit.html.Base2WayGradient;
import org.richfaces.renderkit.html.BaseGradient;
import org.richfaces.renderkit.html.images.GradientType;
import org.richfaces.renderkit.html.images.GradientType.BiColor;

/**
 * @author asmirnov
 *
 */
public class FooterBackground extends Base2WayGradient {

	public FooterBackground() {
		super(1, 96, 48,"panelBorderColor","generalBackgroundColor", false);
	}
	

}
