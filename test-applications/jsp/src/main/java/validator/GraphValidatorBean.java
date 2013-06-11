package validator;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.Max;
import org.hibernate.validator.Min;
import org.hibernate.validator.Valid;

public class GraphValidatorBean {

	private final List<TestValidable> beans;	

	@Valid
	public List<TestValidable> getBeans() {
		return beans;
	}
	
	public GraphValidatorBean() {
		beans = new ArrayList<TestValidable>(1);
		beans.add(new ValidatorBean());				
	}
	@Max(value=50,message="total length > 50")
	@Min(value=10,message="total length < 10")
	public int getTotal(){
		int total = 0;		
		for (TestValidable bean : beans) {
				total += bean.getText().toString().length() + bean.getDateValue().toString().length();			
		}
		return total;
	}
}
