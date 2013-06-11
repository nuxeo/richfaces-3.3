/**
 * 
 */
package validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.hibernate.validator.Max;
import org.hibernate.validator.Size;
import org.hibernate.validator.Valid;
import org.richfaces.component.UIGraphValidator;
import util.componentInfo.ComponentInfo;
import util.data.Data;
/**
 * @author mvitenkov
 *
 */
public class DataBean  {
	
	private final List<Validable> beans;
	private UIGraphValidator graphValidatorComponent = null;
	private String bindLabel;
	private boolean rendered;	
	@Size(min=2,max=5,message="Size validation failed!!!!")
	private ArrayList<Data> data;
	private int length;
	
	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public ArrayList<Data> getData() {
		return data;
	}

	public void setData(ArrayList<Data> data) {
		this.data = data;
	}

	public boolean isRendered() {
		return rendered;
	}

	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}

	public void checkBinding(ActionEvent actionEvent){
		FacesContext context = FacesContext.getCurrentInstance();
		bindLabel = graphValidatorComponent.getClientId(context).toString();
	}
	
	public String add(){
		ComponentInfo info = ComponentInfo.getInstance();
		info.addField(graphValidatorComponent);
		return null;
	}
	public String addNewItem() {
		if (length < 0)
			length = 0;
		if (data.size() > length)
			for (int i = length; i < data.size();)
				data.remove(i);
		else{
			Random r = new Random();
			for (int i = data.size() + 1; i <= length; i++)
				data.add(new Data(i, r.nextInt(1000), Data.Random(6), r.nextInt(10000) + 98389, Data.Random(r.nextInt(10) + 1), r.nextInt(500000), Data.statusIcon[i % 5], Data.Random(3), false));
		}
		return null;
	}
	
	public String getBindLabel() {
		return bindLabel;
	}

	public void setBindLabel(String bindLabel) {
		this.bindLabel = bindLabel;
	}

	public UIGraphValidator getGraphValidatorComponent() {
		return graphValidatorComponent;
	}

	public void setGraphValidatorComponent(UIGraphValidator graphValidatorComponent) {
		this.graphValidatorComponent = graphValidatorComponent;
	}
	
	/**
	 * @return the beans
	 */
	@Valid
	public List<Validable> getBeans() {
		return beans;
	}

	public DataBean() {
		beans = new ArrayList<Validable>(6);
		beans.add(new NotNullBean());
		beans.add(new NotEmptyBean());
		beans.add(new LengthBean());
		beans.add(new MinBean());
		beans.add(new MaxBean());
		beans.add(new MinMaxBean());
		this.bindLabel = "Click Binding";
		this.rendered = true;
		this.data = new ArrayList<Data>();		
		Random r = new Random();		
		for(int i = 0; i < 10; i++)
			data.add(new Data(i, r.nextInt(1000), Data.Random(6), r.nextInt(10000) + 98389, Data.Random(r.nextInt(10) + 1), r.nextInt(500000), Data.statusIcon[i % 5], Data.Random(3), false)); //new Data(i, Data.Random(5), Data.statusIcon[i % 5], Data.Random(6), false));
	}

	@Max(value=20,message="Total value should be less then 20")
	public int getTotal(){
		int total = 0;
		for (Validable bean : beans) {
			total += bean.getIntValue();
		}
		return total;
	}



}
