package queue;

import java.util.ArrayList;
import java.util.Date;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

public class QueueComponent {
	private String inputValue;
	private String inputQueue;		
	private ArrayList<SelectItem> queues;
	private Date calendarValue;
	private String calendarQueue;
	private boolean checkboxValue;
	private String checkboxQueue;	
	private String dfsQueue;
	private Object dataScrollerValue;
	private String dataScrollerQueue;
	private String radioValue;
	private String radioQueue;
	private String selectMenuValue;
	private String selectMenuQueue;
	private String suggestionValue;
	private String suggestionQueue;
	
	public void sleepThread(ActionEvent e){
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public String getSuggestionValue() {
		return suggestionValue;
	}

	public void setSuggestionValue(String suggestionValue) {
		this.suggestionValue = suggestionValue;
	}

	public String getSuggestionQueue() {
		return suggestionQueue;
	}

	public void setSuggestionQueue(String suggestionQueue) {
		this.suggestionQueue = suggestionQueue;
	}

	public String getSelectMenuValue() {
		return selectMenuValue;
	}

	public void setSelectMenuValue(String selectMenuValue) {
		this.selectMenuValue = selectMenuValue;
	}

	public String getSelectMenuQueue() {
		return selectMenuQueue;
	}

	public void setSelectMenuQueue(String selectMenuQueue) {
		this.selectMenuQueue = selectMenuQueue;
	}

	public String getRadioValue() {
		return radioValue;
	}

	public void setRadioValue(String radioValue) {
		this.radioValue = radioValue;
	}

	public String getRadioQueue() {
		return radioQueue;
	}

	public void setRadioQueue(String radioQueue) {
		this.radioQueue = radioQueue;
	}

	public Object getDataScrollerValue() {
		return dataScrollerValue;
	}

	public void setDataScrollerValue(Object dataScrollerValue) {
		this.dataScrollerValue = dataScrollerValue;
	}

	public String getDataScrollerQueue() {
		return dataScrollerQueue;
	}

	public void setDataScrollerQueue(String dataScrollerQueue) {
		this.dataScrollerQueue = dataScrollerQueue;
	}

	public String getDfsQueue() {
		return dfsQueue;
	}

	public void setDfsQueue(String dfsQueue) {
		this.dfsQueue = dfsQueue;
	}

	public boolean isCheckboxValue() {
		return checkboxValue;
	}

	public void setCheckboxValue(boolean checkboxValue) {
		this.checkboxValue = checkboxValue;
	}

	public String getCheckboxQueue() {
		return checkboxQueue;
	}

	public void setCheckboxQueue(String checkboxQueue) {
		this.checkboxQueue = checkboxQueue;
	}	

	public Date getCalendarValue() {
		return calendarValue;
	}

	public void setCalendarValue(Date calendarValue) {
		this.calendarValue = calendarValue;
	}

	public String getCalendarQueue() {
		return calendarQueue;
	}

	public void setCalendarQueue(String calendarQueue) {
		this.calendarQueue = calendarQueue;
	}

	public QueueComponent(){
		this.inputValue = "";
		this.inputQueue = "org.richfaces.global_queue";	
		this.calendarValue = new Date();
		this.calendarQueue = "namedQueue";
		this.checkboxValue = false;
		this.checkboxQueue = "formQueue";	
		this.dataScrollerValue = null;
		this.dataScrollerQueue = "default";
		this.radioValue = "org.richfaces.global_queue";
		this.radioQueue = "org.richfaces.global_queue";		
		this.dfsQueue = "namedQueue";
		this.selectMenuValue = "apple";
		this.selectMenuQueue = "formQueue";
		this.suggestionValue = "";
		this.suggestionQueue = "default";
		String[] qu = {"org.richfaces.global_queue", "namedQueue", "formQueue", "default"};		
		queues = new ArrayList<SelectItem>();
		for(int i=0; i<qu.length; i++){
			queues.add(new SelectItem(qu[i], qu[i]));
		}			
	}
	
	public String getInputQueue() {
		return inputQueue;
	}

	public void setInputQueue(String inputQueue) {
		this.inputQueue = inputQueue;
	}	

	public String getInputValue() {
		return inputValue;
	}

	public void setInputValue(String inputValue) {
		this.inputValue = inputValue;
	}	

	public ArrayList<SelectItem> getQueues() {
		return queues;
	}

	public void setQueues(ArrayList<SelectItem> queues) {
		this.queues = queues;
	}
}
