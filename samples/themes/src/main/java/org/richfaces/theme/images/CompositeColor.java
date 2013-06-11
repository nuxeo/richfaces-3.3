/**
 * 
 */
package org.richfaces.theme.images;

import java.awt.Color;

/**
 * @author asmirnov
 *
 */
@SuppressWarnings("serial")
public class CompositeColor extends Color {
	
	private double mix = 0.0f;
	private Color shadowColor;
	
	public CompositeColor(int base, int shadow) {
		super(base);
		
		this.shadowColor = new Color(shadow);
	}

	/**
	 * @return the mix
	 */
	public double getMix() {
		return mix;
	}

	/**
	 * @param mix the mix to set
	 */
	public void setMix(double mix) {
		this.mix = mix;
	}
	
	
	public Color getMixedColor(){
		return new Color(getRed(),getGreen(),getBlue(),getAlpha());
	}
	
	protected int mix(int from, int to){
		return (int)((double)from*(1.0-mix)+(double)to*mix);
	}
	
	
	@Override
	public int getRed() {
		return mix(super.getRed(),shadowColor.getRed());
	}
	
	@Override
	public int getGreen() {
		return mix(super.getGreen(),shadowColor.getGreen());
	}
	
	@Override
	public int getBlue() {
		return mix(super.getBlue(),shadowColor.getBlue());
	}

	@Override
	public int getAlpha() {
		return mix(super.getAlpha(),shadowColor.getAlpha());
	}
	
	@Override
	public int getRGB() {
		// TODO Auto-generated method stub
		return super.getRGB();
	}
}
