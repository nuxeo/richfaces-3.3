/** @package 

        Paint2D.java
        
        Copyright(c) exadel 2000
        
        Author: Alex Yanul
        Created: AY  7/30/2007 12:52:38 PM
*/        
package paint2D;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import org.richfaces.component.html.HtmlPaint2D;

import util.componentInfo.ComponentInfo;

public class Paint2D {

	private static float location = 150;
	
	PaintData data = new PaintData(); 
	private int width;
	private int height;
	private String title;
	private String align; // bottom, middle, top
	private String hspace;
	private String vspace;
	private String format; // jpeg|gif|png
	private String bgcolor;
	private String border;
	private boolean cacheable;
	private boolean rendered;
	private boolean style;
	private HtmlPaint2D htmlPaint2D = null;
	
	public void addHtmlPaint2D(){
		ComponentInfo info = ComponentInfo.getInstance();
		info.addField(htmlPaint2D);
	}
	
	public HtmlPaint2D getHtmlPaint2D() {
		return htmlPaint2D;
	}

	public void setHtmlPaint2D(HtmlPaint2D htmlPaint2D) {
		this.htmlPaint2D = htmlPaint2D;
	}

	public boolean isRerender() {
		return rendered;
	}

	public void setRerender(boolean rendered) {
		this.rendered = rendered;
	}

	public String getBorder() {
		return border;
	}

	public void setBorder(String border) {
		this.border = border;
	}

	public Paint2D() {
		cacheable = false;        
		width = 400;
		align = "left";
		height = 200;
		title = "Pain2D title";
		bgcolor = "white";
		rendered = true;
		border = "2px";
		style = false;
	}

	public String getStyleString() {
		if(style)
			return "style";
		else 
			return "";
	}
	
	public void paint(Graphics2D g2, Object obj) {
		data.setText(((PaintData) obj).getText());
		int testLenght = data.getText().length();
		int fontSize = testLenght < 8 ? 40 : 40 - (testLenght - 8);
		if (fontSize < 12) fontSize = 12;
		Font font = new Font("Serif", Font.HANGING_BASELINE, fontSize);
		g2.setFont(font);

		int x = 10;
		int y = fontSize * 5 / 2;
		g2.translate(x, y);

		g2.setPaint(Color.BLUE);
		g2.drawString(data.getText(), 0, 0);
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public String getAlign() {
		return align;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public String getHspace() {
		return hspace;
	}

	public void setHspace(String hspace) {
		this.hspace = hspace;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getVspace() {
		return vspace;
	}

	public void setVspace(String vspace) {
		this.vspace = vspace;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getBgcolor() {
		return bgcolor;
	}

	public void setBgcolor(String bgcolor) {
		this.bgcolor = bgcolor;
	}

	public float getLocation() {
		return location;
	}

	public void setLocation(float location) {
		this.location = location;
	}

	public boolean isRendered() {
		return rendered;
	}

	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}

	public boolean isStyle() {
		return style;
	}

	public void setStyle(boolean style) {
		this.style = style;
	}
	
	public void bTest1(){
		setAlign("top");
		setBgcolor("gray");
		setBorder("3");
		setFormat("gif");
		setHeight(300);
		setWidth(300);
		setVspace("15");
		setHspace("15");
		setTitle("Test1");
		//data.setText("Test1!");
	}
	
	public void bTest2(){
		setAlign("middle");
		setBgcolor("yellow");
		setBorder("1");
		setFormat("png");
		setHeight(400);
		setWidth(300);
		setVspace("0");
		setHspace("15");
		setTitle("Test2");
		//data.setText("Test2!");	
	}
	
	public void bTest3(){
		setAlign("left");
		setBgcolor("lime");
		setBorder("10");
		setFormat("jpeg");
		setHeight(300);
		setWidth(400);
		setVspace("15");
		setHspace("0");
		setTitle("Test3");
		//data.setText("Test3!");
	}
	
	public void bTest4(){
		setAlign("right");
		setBgcolor("teal");
		setBorder("3");
		setFormat("gif");
		setHeight(400);
		setWidth(600);
		setVspace("0");
		setHspace("0");
		setTitle("Test4");
		//data.setText("Test4!");
	}
	
	public void bTest5(){
		setAlign("botton");
		setBgcolor("fuchsia");
		setBorder("3");
		setFormat("png");
		setHeight(400);
		setWidth(450);
		setVspace("-5");
		setHspace("-5");
		setTitle("Test5");
		//data.setText("Test5!");
	}

	public boolean isCacheable() {
		return cacheable;
	}

	public void setCacheable(boolean cacheable) {
		this.cacheable = cacheable;
	}
}
