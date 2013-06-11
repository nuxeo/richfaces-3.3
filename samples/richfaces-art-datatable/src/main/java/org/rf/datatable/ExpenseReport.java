package org.rf.datatable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class ExpenseReport {
	private List records = null;
	
	private boolean citySorted=true;
	private boolean mealsSorted=true;
	private boolean hotelsSorted=true;
	private boolean totalsSorted=true;
	private boolean transportSorted=true;
	
	public boolean isCitySorted() {
		return citySorted;
	}

	public void setCitySorted(boolean citySorted) {
		this.citySorted = citySorted;
	}

	public boolean isHotelsSorted() {
		return hotelsSorted;
	}

	public void setHotelsSorted(boolean hotelsSorted) {
		this.hotelsSorted = hotelsSorted;
	}

	public boolean isMealsSorted() {
		return mealsSorted;
	}

	public void setMealsSorted(boolean mealsSorted) {
		this.mealsSorted = mealsSorted;
	}

	public boolean isTotalsSorted() {
		return totalsSorted;
	}

	public void setTotalsSorted(boolean totalsSorted) {
		this.totalsSorted = totalsSorted;
	}

	public boolean isTransportSorted() {
		return transportSorted;
	}

	public void setTransportSorted(boolean transportSorted) {
		this.transportSorted = transportSorted;
	}

	public List getRecords() {
		if (records==null) {
			initRecords();
		}
		return records;
	}

	public void setRecords(List records) {
		this.records = records;
	} 
	
	public double getTotalMeals() {
		double ret = 0.0;
		Iterator it = getRecords().iterator();
		while (it.hasNext()) {
			ExpenseReportRecord record = (ExpenseReportRecord) it.next();
			ret+=record.getTotalMeals();
		}
		return ret;
	}
	public double getTotalHotels() {
		double ret = 0.0;
		Iterator it = getRecords().iterator();
		while (it.hasNext()) {
			ExpenseReportRecord record = (ExpenseReportRecord) it.next();
			ret+=record.getTotalHotels();
		}
		return ret;
	}
	public double getTotalTransport() {
		double ret = 0.0;
		Iterator it = getRecords().iterator();
		while (it.hasNext()) {
			ExpenseReportRecord record = (ExpenseReportRecord) it.next();
			ret+=record.getTotalTransport();
		}
		return ret;
	}
	public double getGrandTotal() {
		return getTotalMeals()+getTotalHotels()+getTotalTransport();
	}
	public int getRecordsCount() {
		return getRecords().size();
	}
	
	private void initRecords() {
		records = new ArrayList();
		ExpenseReportRecord rec;
		rec = new ExpenseReportRecord();
		rec.setCity("San Jose");
		rec.getItems().add(new ExpenseReportRecordItem("25-Aug-97",37.74,112.0,45.0));
		rec.getItems().add(new ExpenseReportRecordItem("26-Aug-97",27.28,112.0,45.0));
		records.add(rec);
		rec = new ExpenseReportRecord();
		rec.setCity("Seattle");
		rec.getItems().add(new ExpenseReportRecordItem("27-Aug-97",96.25,109.0,36.00));
		rec.getItems().add(new ExpenseReportRecordItem("28-Aug-97",35.0,109.0,36.0));
		records.add(rec);
		rec = new ExpenseReportRecord();
		rec.setCity("Las Vegas");
		rec.getItems().add(new ExpenseReportRecordItem("29-Aug-97",215.40,80.0,32.00));
		rec.getItems().add(new ExpenseReportRecordItem("30-Aug-97",100.50,80.0,32.0));
		rec.getItems().add(new ExpenseReportRecordItem("31-Aug-97",180.00,80.0,32.0));
		records.add(rec);
		rec = new ExpenseReportRecord();
		rec.setCity("New York");
		rec.getItems().add(new ExpenseReportRecordItem("01-Sep-97",120.40,104.0,50.00));
		rec.getItems().add(new ExpenseReportRecordItem("02-Sep-97",110.50,104.0,50.0));		
		records.add(rec);
	}
	
	public String sortCityNames(){
		Collections.sort(records, new Comparator(){
			public int compare(Object record, Object record2) {
				if (isCitySorted()) return ((ExpenseReportRecord)record).getCity().compareTo(((ExpenseReportRecord)record2).getCity());
				else return -((ExpenseReportRecord)record).getCity().compareTo(((ExpenseReportRecord)record2).getCity());
			}
	});
		setCitySorted(!isCitySorted());
		setHotelsSorted(true);
		setMealsSorted(true);
		setTotalsSorted(true);
		setTransportSorted(true);
		return null;
	}
	
	public String sortMeals(){
		Collections.sort(records, new Comparator(){
			public int compare(Object record, Object record2) {
				if ((((ExpenseReportRecord)record).getTotalMeals() > ((ExpenseReportRecord)record2).getTotalMeals())&& isMealsSorted())
					if (isMealsSorted()){
						return 1;}
					else{
						return -1;
					}
					else if (((ExpenseReportRecord)record).getTotalMeals() < ((ExpenseReportRecord)record2).getTotalMeals())
						if (isMealsSorted()){
							return -1;
						}
						else{
							return 1;
						}
					else return 0;
			}	
			 
	});
		setMealsSorted(!isMealsSorted());
		setHotelsSorted(true);
		setTotalsSorted(true);
		setTransportSorted(true);
		setCitySorted(true);
		return null;
	}

	public String sortTransport(){
		Collections.sort(records, new Comparator(){
			public int compare(Object record, Object record2) {
				if (((ExpenseReportRecord)record).getTotalTransport() > ((ExpenseReportRecord)record2).getTotalTransport())
					if (isTransportSorted())
						return 1;
					else
						return -1;
					else if (((ExpenseReportRecord)record).getTotalTransport() < ((ExpenseReportRecord)record2).getTotalTransport())
						if (isTransportSorted())
							return -1;
						else
							return 1;
					else return 0;
			}	
			 
	});
		setTransportSorted(!isTransportSorted());
		setMealsSorted(true);
		setHotelsSorted(true);
		setTotalsSorted(true);
		setCitySorted(true);
		return null;
	}

	public String sortHotels(){
		Collections.sort(records, new Comparator(){
			public int compare(Object record, Object record2) {
				if (((ExpenseReportRecord)record).getTotalHotels() > ((ExpenseReportRecord)record2).getTotalHotels())
					if (isHotelsSorted())
						return 1;
					else 
						return -1;
					else if (((ExpenseReportRecord)record).getTotalHotels() < ((ExpenseReportRecord)record2).getTotalHotels())
						if (isHotelsSorted())
							return -1;
						else 
							return 1;
					else return 0;
			}	
			 
	});
		setHotelsSorted(!isHotelsSorted());
		setMealsSorted(true);
		setTotalsSorted(true);
		setCitySorted(true);
		setTransportSorted(true);
		return null;
	}

	public String sortTotal(){
		Collections.sort(records, new Comparator(){
			public int compare(Object record, Object record2) {
				if (((ExpenseReportRecord)record).getTotal() > ((ExpenseReportRecord)record2).getTotal())
						if (isTotalsSorted())
							return 1;
						else 
							return -1;
					else if (((ExpenseReportRecord)record).getTotal() < ((ExpenseReportRecord)record2).getTotal() )
						if (isTotalsSorted())
							return -1;
						else 
							return 1;
					else return 0;
			}	
			 
	});
		setTotalsSorted(!isTotalsSorted());
		setMealsSorted(true);
		setCitySorted(true);
		setTransportSorted(true);
		setHotelsSorted(true);
		return null;
	}
	
}
