package org.richfaces.demo.datagrid.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.faces.event.ActionEvent;

import org.richfaces.component.UIDataTable;
import org.richfaces.component.UIScrollableDataTable;

public class TestGridDataModelBuilder{
	private ArrayList model1 = new ArrayList();
	private ArrayList model2 = new ArrayList();
	private ArrayList model3 = new ArrayList();
	private HashMap grids = new HashMap();
	private int size = 1000;
	private String grid = "grid1.xhtml";
	private UIDataTable table;
	
	private Integer i;
	
	public TestGridDataModelBuilder() {
		grids.put("grid1", "grid1.xhtml");
		grids.put("grid2", "grid2.xhtml");
		grids.put("grid3", "grid3.xhtml");
		for (int i = 0; i < 1000; i++) {
			model1.add(new Row("model1 - " + i));
			model2.add(new Row("model2 - " + i));
			model3.add(new Row("model3 - " + i));
		}
	}

	public ArrayList getModel1() {
		System.out.println("TestGridDataModelBuilder.getModel1()");
		model1.clear();
		for (int i = 0; i < size; i++) {
			model1.add(new Row("model1 - " + i));
		}		return model1;
	}

	public void setModel1(ArrayList model1) {
		this.model1 = model1;
	}

	public ArrayList getModel2() {
		return model2;
	}

	public void setModel2(ArrayList model2) {
		this.model2 = model2;
	}

	public ArrayList getModel3() {
		return model3;
	}

	public void setModel3(ArrayList model3) {
		this.model3 = model3;
	}

	public HashMap getGrids() {
		return grids;
	}

	public void setGrids(HashMap grids) {
		this.grids = grids;
	}

	public String getGrid() {
		return grid;
	}

	public void setGrid(String grid) {
		this.grid = grid;
	}
	
	public void rowSelected(ActionEvent event) {
        System.out.println("==============>>>>>>>>>>>>>>>>>>>>>> rowSelected");
        
        Iterator keys = ((UIScrollableDataTable)event.getComponent().getParent()).getSelection().getKeys();
        
        while(keys.hasNext())
        {
           System.out.println(keys.next());
        }
        System.out.println("Finished!");
    }

	public UIDataTable getTable() {
		return table;
	}

	public void setTable(UIDataTable table) {
		this.table = table;
	}
	public void test() {
    	System.out.print(table.getRowKey() + " -> ");
    	System.out.println(table.getRowIndex());
    }

	public Integer getI() {
		return i;
	}

	public void setI(Integer i) {
		this.i = i;
	}
	
    public boolean getRowType() {
    	int currentIndex = table.getRowIndex();
    	if (Integer.valueOf(currentIndex).equals(i)) {
    	    return true;
    	}
    	return false;
    }

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

}