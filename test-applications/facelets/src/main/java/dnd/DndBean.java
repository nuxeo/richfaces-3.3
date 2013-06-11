package dnd;


import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;

import org.richfaces.component.html.HtmlDragSupport;
import org.richfaces.component.html.HtmlDropSupport;
import org.richfaces.event.DragEvent;
import org.richfaces.event.DropEvent;

import util.componentInfo.ComponentInfo;



public class DndBean {

	private List types = new ArrayList();
	private Object dragValue;
	private Object testParam;
	private String actionDrop;
	private String actionListenerDrop;
	private String actionDrag;
	private String actionListenerDrag;
	private HtmlDragSupport htmlDrag = null;
	private HtmlDropSupport htmlDrop = null;

	public DndBean() {
		super();

		types.add("One");
		types.add("Two");
		
		actionDrag = "---";
		actionListenerDrag = "---";
		actionDrop = "---";
		actionListenerDrop = "---";
	}
	
	public String actListenerDrag(ActionEvent e) {
		actionListenerDrag = "actionListenerDrag work!";
		return null;
	}

	public String actListenerDrop(ActionEvent e) {
		actionListenerDrop = "actionListenerDrop work!";
		return null;
	}

	public void processDrop(DropEvent event) {
		System.out.println("Bean.processDrop()" + event.getDropValue());
		this.dragValue = event.getDragValue();
	}

	public void processDrag(DragEvent event) {
		System.out.println("Bean.processDrag()"+ event.getDropValue());
	}

	public List getTypes() {
		return types;
	}

	public String dragAction() {
		System.out.println("Bean.dragAction()");
		actionDrag = "actionDtag work!";
		return null;
	}

	public String dropAction() {
		System.out.println("Bean.dropAction()");
		actionDrop = "actionDrop work!";
		return null;
	}

	public Object getDragValue() {
		return dragValue;
	}

	public Object getTestParam() {
		return testParam;
	}

	public void setTestParam(Object testParam) {
		this.testParam = testParam;
		System.out.println("Bean.setTestParam()" + testParam);
	}

	public String getActionDrop() {
		return actionDrop;
	}

	public void setActionDrop(String actionDrop) {
		this.actionDrop = actionDrop;
	}

	public String getActionListenerDrop() {
		return actionListenerDrop;
	}

	public void setActionListenerDrop(String actionListenerDrop) {
		this.actionListenerDrop = actionListenerDrop;
	}

	public String getActionDrag() {
		return actionDrag;
	}

	public void setActionDrag(String actionDrag) {
		this.actionDrag = actionDrag;
	}

	public String getActionListenerDrag() {
		return actionListenerDrag;
	}

	public void setActionListenerDrag(String actionListenerDrag) {
		this.actionListenerDrag = actionListenerDrag;
	}

	public HtmlDragSupport getHtmlDrag() {
		return htmlDrag;
	}

	public void setHtmlDrag(HtmlDragSupport htmlDrag) {
		this.htmlDrag = htmlDrag;
	}

	public HtmlDropSupport getHtmlDrop() {
		return htmlDrop;
	}

	public void setHtmlDrop(HtmlDropSupport htmlDrop) {
		this.htmlDrop = htmlDrop;
	}
	
	public String add1(){
		ComponentInfo info1 = ComponentInfo.getInstance();
		info1.addField(htmlDrag);		
		return null;
	}
	
	public String add2(){
		ComponentInfo info1 = ComponentInfo.getInstance();
		info1.addField(htmlDrop);		
		return null;
	}
}