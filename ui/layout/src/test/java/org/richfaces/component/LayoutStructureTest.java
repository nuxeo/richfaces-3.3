package org.richfaces.component;

import java.util.List;

import javax.faces.component.UIComponent;

import org.richfaces.component.html.HtmlLayout;
import org.richfaces.component.html.HtmlLayoutPanel;

import junit.framework.TestCase;

public class LayoutStructureTest extends TestCase {
	
	protected UILayout createLayout(String leftWidth,String centerWidth,String rightWidth) {
		UILayout layout = new HtmlLayout();
		layout.setId("layout");
		List<UIComponent> children = layout.getChildren();
		UILayoutPanel panel = new HtmlLayoutPanel();
		panel.setPosition(LayoutPosition.left);
		panel.setId("left");
		panel.setWidth(leftWidth);
		children.add(panel);
		
		panel = new HtmlLayoutPanel();
		panel.setPosition(LayoutPosition.top);
		panel.setId("top");
		children.add(panel);
		
		panel = new HtmlLayoutPanel();
		panel.setPosition(LayoutPosition.right);
		panel.setId("right");
		panel.setWidth(rightWidth);
		children.add(panel);
		
		panel = new HtmlLayoutPanel();
		panel.setPosition(LayoutPosition.center);
		panel.setId("center");
		panel.setWidth(centerWidth);
		children.add(panel);		
		
		panel = new HtmlLayoutPanel();
		panel.setPosition(LayoutPosition.bottom);
		panel.setId("bottom");
		children.add(panel);
		return layout;
	}

	public void testSortPanels() throws Exception {
		LayoutStructure struct = new LayoutStructure(createLayout(null, null, null));
		assertEquals("top", struct.getTop().getId());
		assertEquals("bottom", struct.getBottom().getId());
		assertEquals("left", struct.getLeft().getId());
		assertEquals("right", struct.getRight().getId());
		assertEquals("center", struct.getCenter().getId());
	}
	
	public void testCalculateWidth() throws Exception {
		LayoutStructure struct = new LayoutStructure(createLayout(null, null, null));
		struct.calculateWidth();
		assertEquals("32.67%", struct.getWidth(struct.getLeft()));
		assertEquals("32.67%", struct.getWidth(struct.getCenter()));
		assertEquals("32.67%", struct.getWidth(struct.getRight()));
	}

	public void testCalculateWidth1() throws Exception {
		LayoutStructure struct = new LayoutStructure(createLayout("50%", null, null));
		struct.calculateWidth();
		assertEquals("49.00%", struct.getWidth(struct.getLeft()));
		assertEquals("24.50%", struct.getWidth(struct.getCenter()));
		assertEquals("24.50%", struct.getWidth(struct.getRight()));
	}
	public void testCalculateWidth2() throws Exception {
		LayoutStructure struct = new LayoutStructure(createLayout("50%", "*2", "*1"));
		struct.calculateWidth();
		assertEquals("49.00%", struct.getWidth(struct.getLeft()));
		assertEquals("32.67%", struct.getWidth(struct.getCenter()));
		assertEquals("16.33%", struct.getWidth(struct.getRight()));
	}
	public void testCalculateWidth3() throws Exception {
		LayoutStructure struct = new LayoutStructure(createLayout("50%", "20%", "10%"));
		struct.calculateWidth();
		assertEquals("61.25%", struct.getWidth(struct.getLeft()));
		assertEquals("24.50%", struct.getWidth(struct.getCenter()));
		assertEquals("12.25%", struct.getWidth(struct.getRight()));
	}
}
