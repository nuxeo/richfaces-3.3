
package org.docs.tree;

import javax.faces.context.FacesContext;

import org.richfaces.component.xml.XmlTreeDataBuilder;
import org.richfaces.model.TreeNode;
import org.richfaces.model.TreeNodeImpl;
import org.xml.sax.InputSource;


public class Tree {
	
	

	private TreeNodeImpl<String> stationRoot = new TreeNodeImpl<String>();

	private TreeNodeImpl<String> stationNodes = new TreeNodeImpl<String>(); 

	private String[] kickRadioFeed = { "Hall & Oates - Kiss On My List",

	    "David Bowie - Let's Dance", "Lyn Collins - Think (About It)",

	    "Kim Carnes - Bette Davis Eyes",

	    "KC & the Sunshine Band - Give It Up" };
	
	
	private TreeNode data;
	
	
	
	
	


	public Tree() {
		

		
		
		stationRoot.setData("KickRadio");
		
		stationNodes.addChild(0, stationRoot);
		
		for (int i = 0; i < kickRadioFeed.length; i++){

		    TreeNodeImpl<String> child = new TreeNodeImpl<String>();

		    child.setData(kickRadioFeed[i]);

		    stationRoot.addChild(i, child);

		}
		
		
		
		FacesContext context = FacesContext.getCurrentInstance();

		try {
		data = XmlTreeDataBuilder.build(new InputSource(getClass().getResourceAsStream("stations.xml")));
		}catch (Exception e){}
	}







	public TreeNode getData() {
		return data;
	}







	public void setData(TreeNode data) {
		this.data = data;
	}







	public TreeNodeImpl<String> getStationRoot() {
		return stationRoot;
	}







	public void setStationRoot(TreeNodeImpl<String> stationRoot) {
		this.stationRoot = stationRoot;
	}







	public TreeNodeImpl<String> getStationNodes() {
		return stationNodes;
	}







	public void setStationNodes(TreeNodeImpl<String> stationNodes) {
		this.stationNodes = stationNodes;
	}



	
	
	
	



	

	
	

}
