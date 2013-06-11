package org.richfaces.helloworld.domain.panel;

import javax.faces.event.ValueChangeEvent;
import org.richfaces.helloworld.domain.util.componentInfo.ComponentInfo;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.richfaces.component.html.HtmlPanel;

@Name("panel")
@Scope(ScopeType.SESSION)
public class Panel {

	private boolean rendered;

	private String width;
	private String height;
	private String[] title ;
	private HtmlPanel htmlPanel = null;

	public HtmlPanel getHtmlPanel() {
		return htmlPanel;
	}

	public void setHtmlPanel(HtmlPanel htmlPanel) {
		this.htmlPanel = htmlPanel;
	}
	
	public String add(){
		ComponentInfo info = ComponentInfo.getInstance();
		info.addField(htmlPanel);
		return null;
	}

	public Panel() {
		rendered = false;
		width = "350px";
		height = "400px";
		title = new String[] {"Titles_0", "Titles_1", "Titles_2"};
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public boolean isRendered() {
		return rendered;
	}

	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String[] getTitle() {
		return title;
	}

	public void setTitle(String[] title) {
		this.title = title;
	}
	
	public void makeTitle(ValueChangeEvent event){
		String t = event.getNewValue().toString();
		if(t.equalsIgnoreCase(""))
			for(int i = 0; i < title.length; i++) 
				title[i] = "Titles_" + i;
		else
			for(int i = 0; i < title.length; i++) 
				title[i] = t + "_" + i;  
	}
	
	public void bTest1(){
		setWidth("500px");
		setHeight("300px");
		setTitle(new String [] {"Test1", "Test 1", "Test_1", "Test-1"});
	}
	
	public void bTest2(){
		setWidth("500px");
		setHeight("600px");
		setTitle(new String [] {"Test2", "Test 2", "Test_2", "Test-2"});
	}
	
	public void bTest3(){
		setWidth("50%");
		setHeight("300px");
		setTitle(new String [] {"Test3", "Test 3", "Test_3", "Test-3"});
	}
	
	public void bTest4(){
		setWidth("400px");
		setHeight("10%");
		setTitle(new String [] {"Test4", "Test 4", "Test_4", "Test-4"});
	}
	
	public void bTest5(){
		setWidth("400px");
		setHeight("400px");
		setTitle(new String [] {"Test5", "Test 5", "Test_5", "Test-5"});
	}

}
