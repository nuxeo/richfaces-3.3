package org.richfaces.regressionarea.issues.rf2644;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.ejb.Remove;
import javax.ejb.Stateful;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.datamodel.DataModelSelection;

@Stateful
@Name("rf2644")
@Scope(ScopeType.SESSION)
public class DataBean implements DataBeanLocal {

	@DataModel("rf2644Items")
	private List<Item> items = new ArrayList<Item>();

	@DataModelSelection
	private Item item;
	
	public DataBean() {
		super();
	}
	
	@Factory("rf2644Items")
	public void createList() {
		for (int i = 0; i < 5000; i++) {
			Item item = new Item();
			item.setName(UUID.randomUUID().toString());
			
			items.add(item);
		}
	}
	
	public Item getItem() {
		return item;
	}
	
	public void setItem(Item item) {
		this.item = item;
	}
	
	@Remove
	public void destroy() {
		
	}
}
