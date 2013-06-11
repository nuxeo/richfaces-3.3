/**
 * License Agreement.
 *
 *  JBoss RichFaces - Ajax4jsf Component Library
 *
 * Copyright (C) 2007  Exadel, Inc.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License version 2.1 as published by the Free Software Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA
 */

package org.richfaces;
import java.util.ArrayList;

import javax.faces.event.ActionEvent;

import org.ajax4jsf.component.html.HtmlAjaxCommandLink;
import org.richfaces.event.DataScrollerEvent;

public class TestBean {

	private ArrayList data;
	private ArrayList data1;
	private ArrayList data2;
	private ArrayList data3;
	private ArrayList data4;
	private ArrayList data5;
	private ArrayList data6;
	
	private ArrayList jsAPIScrollerData;
	
	public boolean renderIfSinglePage;
	public int rows;
	public int maxpage;
    private int actionCount;
    private int eventCount;


    public TestBean () {
        renderIfSinglePage=true;

	        renderIfSinglePage=true;
          	rows=5;
	        maxpage=10;

		data = new ArrayList();
        for (int i = 0; i < 10; i++) {
            data.add(new Entry(null, i));
        }

		data1 = new ArrayList();
        for (int i = 0; i < 11; i++) {
            data1.add(new Entry(null, i));
        }

		data2 = new ArrayList();
        for (int i = 0; i < 12; i++) {
            data2.add(new Entry(null, i));
        }

		data3 = new ArrayList();
        for (int i = 0; i < 13; i++) {
            data3.add(new Entry(null, i));
        }

		data4 = new ArrayList();
        for (int i = 0; i < 14; i++) {
            data4.add(new Entry(null, i));
        }

		data5 = new ArrayList();
        for (int i = 0; i < 15; i++) {
            data5.add(new Entry(null, i));
        }

		data6 = new ArrayList();
        for (int i = 0; i < 16; i++) {
            data6.add(new Entry(null, i));
        }

        jsAPIScrollerData = new ArrayList();
        for (int i = 0; i < 50; i++) {
			jsAPIScrollerData.add(i);
		}

	}

    /**
     * ActionListener to test {@link http://jira.jboss.com/jira/browse/RF-1834}
     * 
     * @param e - ActionEvent
     */
    public void doSort(ActionEvent e){
        HtmlAjaxCommandLink a = (HtmlAjaxCommandLink)e.getComponent();
        System.out.println("ActionListener :" + a.getValue().toString());
    }

     public void setRenderIfSinglePage(boolean renderIfSinglePage){
		this.renderIfSinglePage = renderIfSinglePage;
     }

     public boolean isRenderIfSinglePage() {
		return this.renderIfSinglePage;
     }

     public void setRows(int rows){

 		this.rows = rows;
      }

      public int getRows() {

 		return this.rows;
      }

     public void setMaxpage(int maxpage){

 		this.maxpage = maxpage;
      }

      public int getMaxpage() {
 		return this.maxpage;
      }

      public void onAction(ActionEvent actionEvent) {
    	  System.out.println("TestBean.onAction() " + actionEvent);
          actionCount++;
      }


    public void doScroll(DataScrollerEvent event) {

        String oldScrolVal = event.getOldScrolVal();
        String newScrolVal = event.getNewScrolVal();
        System.out.println("Old  Value = " + oldScrolVal + "  " + "New  Value = " + newScrolVal);
        eventCount++;
   }


	public ArrayList getData() {
		return data;
	}



	public void setData(ArrayList data) {
		this.data = data;
	}


	public ArrayList getData1() {
		return data1;
	}



	public void setData1(ArrayList data) {
		this.data1 = data;
	}
	public ArrayList getData2() {
		return data2;
	}



	public void setData2(ArrayList data) {
		this.data2 = data;
	}
	public ArrayList getData3() {
		return data3;
	}



	public void setData3(ArrayList data) {
		this.data3 = data;
	}
	public ArrayList getData4() {
		return data4;
	}



	public void setData4(ArrayList data) {
		this.data4 = data;
	}
	public ArrayList getData5() {
		return data5;
	}



	public void setData5(ArrayList data) {
		this.data5 = data;
	}

	public ArrayList getData6() {
		return data6;
	}

	public void setData6(ArrayList data) {
		this.data6 = data;
	}

    public int getActionCount() {
        return actionCount;
    }

    public void setActionCount(int actionCount) {
        this.actionCount = actionCount;
    }

    public int getEventCount() {
        return eventCount;
    }

    public void setEventCount(int eventCount) {
        this.eventCount = eventCount;
    }

    public void addEntries() {
    	for (int i = 0; i < 5; i++) {
			data.add(new Entry(null, data.size()));
		}
    }

    public void removeEntries() {
    	if (data.size() >= 5) {
        	for (int i = 0; i < 5; i++) {
    			data.remove(data.size() - 1);
    		}
    	}
    }
    
    public ArrayList getJsAPIScrollerData() {
		return jsAPIScrollerData;
	}
}