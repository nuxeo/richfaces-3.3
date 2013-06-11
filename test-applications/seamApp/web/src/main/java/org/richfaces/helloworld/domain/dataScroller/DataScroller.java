package org.richfaces.helloworld.domain.dataScroller;

import java.util.ArrayList;
import javax.faces.event.ActionEvent;
import org.richfaces.helloworld.domain.util.componentInfo.ComponentInfo;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.richfaces.component.html.HtmlDatascroller;
import org.richfaces.event.DataScrollerEvent;
import org.richfaces.model.Ordering;

import org.richfaces.helloworld.domain.util.data.Data;

@Name("dataScroller")
@Scope(ScopeType.SESSION)
public class DataScroller {
	
	private ArrayList dataTable;
	public Data tD;
	public String align;
	public String fastControls;
	public boolean render;
	public boolean renderIfSinglePage;
	public boolean limitToList; 
	public boolean renderTable;
	public int maxPages;
    private String action;
    private String actionListener;
    private boolean ajaxSingle = false;
    private String boundaryControls = "auto"; 
    private HtmlDatascroller htmlDatascroller= null;
    private String fastStep = "0";
    private String page = "10";
    private Object value = null;
    private String stepControls = "auto";
    private String sortMode;
	private boolean selfSorted;
	private Ordering sortOrder;
	private String filterValue;
	private String currentSortOrder;

	public HtmlDatascroller getHtmlDatascroller(){
		return htmlDatascroller;
	}
	
	public void setHtmlDatascroller(HtmlDatascroller htmlDatascroller){
		this.htmlDatascroller = htmlDatascroller;
	}
	
	public void addHtmlDatascroller(){
		ComponentInfo info = ComponentInfo.getInstance();
		info.addField(htmlDatascroller);
	}
    
	public String getSortMode() {
		return sortMode;
	}

	public void setSortMode(String sortMode) {
		this.sortMode = sortMode;
	}

	public boolean isSelfSorted() {
		return selfSorted;
	}

	public void setSelfSorted(boolean selfSorted) {
		this.selfSorted = selfSorted;
	}

	public String getFilterValue() {
		return filterValue;
	}

	public void setFilterValue(String filterValue) {
		this.filterValue = filterValue;
	}

	public Ordering getSortOrder() {
		return sortOrder;
	}

	public String getCurrentSortOrder() {
		return currentSortOrder;
	}

	public String act() {
		action = "action work!";
		return null;
	}

	public String actListener(ActionEvent e) {
		actionListener = "actionListener work!";
		return null;
	}
    
	public String getAction() {
		return action;
	}

	public String getActionListener() {
		return actionListener;
	}

	public boolean isRenderTable() {
		return renderTable;
	}

	public void setRenderTable(boolean renderTable) {
		this.renderTable = renderTable;
	}

	public String getFastControls() {
		return fastControls;
	}

	public void setFastControls(String fastControls) {
		this.fastControls = fastControls;
	}

	public int getMaxPages() {
		return maxPages;
	}

	public void setMaxPages(int maxPages) {
		this.maxPages = maxPages;
	}

	public DataScroller() {
		action = "---";
		actionListener = "---";
		fastControls = "auto";
		render = true;
		renderIfSinglePage = true;
		align = "left";
		maxPages = 20;
		limitToList = false;
		
		filterValue = "";
		sortMode = "single";
		sortOrder = Ordering.ASCENDING;
		selfSorted = true;
		currentSortOrder = "ASCENDING";		 
		dataTable = new ArrayList();
		
		for(int i=0;i<1000;i++)
		{
			
			dataTable.add(new Data(Data.Random(6),i));
		}	
				
	}
	
	public void setCurrentSortOrder(String currentSortOrder) {
		if("DESCENDING".equals(currentSortOrder)) this.sortOrder = Ordering.DESCENDING;
		else if("UNSORTED".equals(currentSortOrder)) this.sortOrder = Ordering.UNSORTED;
		else if("ASCENDING".equals(currentSortOrder)) this.sortOrder = Ordering.ASCENDING;
		this.currentSortOrder = currentSortOrder;
	}
	
	public void CutArray(){
		int k = dataTable.size()-1;
		for(int i = k ;i > (k+1)/2; i--)
		{
			dataTable.remove(i);						
		}
	}
	
	public void RestoreArray(){
		
		int k = dataTable.size()-1;
		for(int i = k;i < (k+1)*2;i++)
		{
			dataTable.add(new Data("Random",i));
		}	
	}
	
	public ArrayList getDataTable() {
		return dataTable;
	}

	public void setDataTable(ArrayList dataTable) {
		this.dataTable = dataTable;
	}
	
	public void ScrollerListener(DataScrollerEvent e){
		
		System.out.println("NewScrollValue: "+e.getNewScrolVal());
		System.out.println("OldScrollValue: "+e.getOldScrolVal());
	}

	public Data getTD() {
		return tD;
	}

	public void setTD(Data td) {
		tD = td;
	}

	public boolean isRender() {
		return render;
	}

	public void setRender(boolean render) {
		this.render = render;
	}

	public boolean isRenderIfSinglePage() {
		return renderIfSinglePage;
	}

	public void setRenderIfSinglePage(boolean renderIfSinglePage) {
		this.renderIfSinglePage = renderIfSinglePage;
	}

	public String getAlign() {
		return align;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public boolean isLimitToList() {
		return limitToList;
	}

	public void setLimitToList(boolean limitToList) {
		this.limitToList = limitToList;
	}

	public void bTest1(){
		setAlign("left");
		setFastControls("auto");
		setLimitToList(false);
		setMaxPages(20);
		setRenderIfSinglePage(true);
	}
	
	public void bTest2(){
		setAlign("right");
		setFastControls("show");
		setLimitToList(false);
		setMaxPages(30);
		setRenderIfSinglePage(false);
	}
	
	public void bTest3(){
		setAlign("center");
		setFastControls("hide");
		setLimitToList(false);
		setMaxPages(10);
		setRenderIfSinglePage(true);
	}
	
	public void bTest4(){
		setAlign("center");
		setFastControls("auto");
		setLimitToList(true);
		setMaxPages(30);
		setRenderIfSinglePage(false);
	}
	
	public void bTest5(){
		setAlign("left");
		setFastControls("show");
		setLimitToList(false);
		setMaxPages(40);
		setRenderIfSinglePage(true);
	}

	/**
	 * @return the ajaxSingle
	 */
	public boolean isAjaxSingle() {
		return ajaxSingle;
	}

	/**
	 * @param ajaxSingle the ajaxSingle to set
	 */
	public void setAjaxSingle(boolean ajaxSingle) {
		this.ajaxSingle = ajaxSingle;
	}

	/**
	 * @return the boundaryControls
	 */
	public String getBoundaryControls() {
		return boundaryControls;
	}

	/**
	 * @param boundaryControls the boundaryControls to set
	 */
	public void setBoundaryControls(String boundaryControls) {
		this.boundaryControls = boundaryControls;
	}

	/**
	 * @return the fastStep
	 */
	public String getFastStep() {
		return fastStep;
	}

	/**
	 * @param fastStep the fastStep to set
	 */
	public void setFastStep(String fastStep) {
		this.fastStep = fastStep;
	}

	/**
	 * @return the page
	 */
	public String getPage() {
		return page;
	}

	/**
	 * @param page the page to set
	 */
	public void setPage(String page) {
		this.page = page;
	}

	/**
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * @return the stepControls
	 */
	public String getStepControls() {
		return stepControls;
	}

	/**
	 * @param stepControls the stepControls to set
	 */
	public void setStepControls(String stepControls) {
		this.stepControls = stepControls;
	}

	public void setSortOrder(Ordering sortOrder) {
		this.sortOrder = sortOrder;
	}
}
