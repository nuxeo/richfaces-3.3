package org.richfaces.regressionarea.issues.rf2644;

import javax.ejb.Local;

@Local
public interface DataBeanLocal {

	public void createList();
	
	public void setItem(Item item);
	public Item getItem();

}
