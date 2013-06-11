package org.richfaces.theme.images;

import java.awt.geom.AffineTransform;

import javax.faces.context.FacesContext;

import org.ajax4jsf.util.Zipper2;
import org.richfaces.renderkit.html.BaseGradient;

public abstract class BaseShadowGradient extends BaseGradient {

	@SuppressWarnings("serial")
	protected static class ShadowData extends BaseGradient.Data {
		
		private Integer shadowColor;

		private Integer borderColor;
	
		/**
		 * @return the shadowColor
		 */
		protected Integer getShadowColor() {
			return shadowColor;
		}
	
		/**
		 * @param shadowColor the shadowColor to set
		 */
		protected void setShadowColor(Integer shadowColor) {
			this.shadowColor = shadowColor;
		}
		
		@Override
		public byte[] toByteArray() {
	            byte[] bs = super.toByteArray();
	            byte[] result = new byte[(bs != null ? bs.length : 0) + 8];
		    new Zipper2(result).addInt(shadowColor).addInt(borderColor).addBytes(bs);
	
		    return result;
		}

		/**
		 * @return the borderColor
		 */
		protected Integer getBorderColor() {
			return borderColor;
		}

		/**
		 * @param borderColor the borderColor to set
		 */
		protected void setBorderColor(Integer borderColor) {
			this.borderColor = borderColor;
		}
	
	}

	public static final AffineTransform FLIP_TRANSFORM = new AffineTransform(-1, 0, 0, 1, 0, 0);
	public static final double RADIUS = 7.5;
	public static final double MARGIN = 5.0;
	protected static final int SHADOW_RADIUS = (int) (RADIUS+MARGIN);

	public BaseShadowGradient(int width, int height, int gradientHeight,
			String baseColor, String gradientColor, boolean horizontal) {
		super(width, height, gradientHeight, baseColor, gradientColor,
				horizontal);
	}

	public BaseShadowGradient(int width, int height, int gradientHeight) {
		super(width, height, gradientHeight);
	}

	public BaseShadowGradient(int width, int height, int gradientHeight,
			String baseColor, String gradientColor) {
		super(width, height, gradientHeight, baseColor, gradientColor);
	}

	public BaseShadowGradient(int width, int height) {
		super(width, height);
	}

	public BaseShadowGradient(int width, int height, String baseColor,
			String gradientColor) {
		super(width, height, baseColor, gradientColor);
	}

	public BaseShadowGradient() {
		super();
	}

	public BaseShadowGradient(String baseColor, String gradientColor) {
		super(baseColor, gradientColor);
	}

	public BaseShadowGradient(int width, int height, int gradientHeight,
			boolean horizontal) {
		super(width, height, gradientHeight, horizontal);
	}

	public BaseShadowGradient(int width, int height, boolean horizontal) {
		super(width, height, horizontal);
	}

	public BaseShadowGradient(int width, int height, String baseColor,
			String gradientColor, boolean horizontal) {
		super(width, height, baseColor, gradientColor, horizontal);
	}

	public BaseShadowGradient(boolean horizontal) {
		super(horizontal);
	}

	protected String getShadowColor() {
		return "shadowBackgroundColor";
	}

	protected String getBorderColor(){
		return "panelBorderColor";
	}

	@Override
	protected Data createData() {
	    return new ShadowData();
	}

	@Override
	protected void saveData(FacesContext context, Data data, Object parameterData) {
	    super.saveData(context, data, parameterData);
	    ShadowData d = ((ShadowData) data);
	    d.setShadowColor(getShadowColor(context));
	    d.setBorderColor(getBorderColor(context));
	}

	protected Integer getShadowColor(FacesContext context) {
		return getColorValueParameter(context, getShadowColor(), false);
	}

	protected Integer getBorderColor(FacesContext context) {
		return getColorValueParameter(context, getBorderColor(), false);
	}

	protected void restoreData(Data data, Zipper2 zipper2) {
	if (zipper2.hasMore()) {
	    ((ShadowData) data).setShadowColor(zipper2.nextInt());
	    ((ShadowData) data).setBorderColor(zipper2.nextInt());
	    super.restoreData(data, zipper2);
	}
	}

	protected AffineTransform createFlipTransform() {
		return new AffineTransform(-1.0, 0.0, 0.0,  1.0, getDimensions(null).getWidth(),0.0);
	}

	public BaseShadowGradient(String baseColor, String gradientColor,
			boolean horizontal) {
		super(baseColor, gradientColor, horizontal);
	}

}