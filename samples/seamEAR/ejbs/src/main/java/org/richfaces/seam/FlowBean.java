/**
 * 
 */
package org.richfaces.seam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Init;
import javax.ejb.Remove;
import javax.ejb.Stateful;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

/**
 * @author asmirnov
 *
 */
@Stateful
@Name("flow")
@Scope(ScopeType.SESSION)
public class FlowBean implements Flow {
	
	private List<String> _lista ;
	
	/* (non-Javadoc)
	 * @see org.richfaces.seam.Flow#getTime()
	 */
	public Date getTime(){
		return new Date(System.currentTimeMillis());
	}

	/* (non-Javadoc)
	 * @see org.richfaces.seam.Flow#getLista()
	 */
	public List<String> getLista() {
		return _lista;
	}

	/**
	 * @param lista the lista to set
	 */
	public void setLista(List<String> lista) {
		_lista = lista;
	}
	
	/* (non-Javadoc)
	 * @see org.richfaces.seam.Flow#fillList()
	 */
	public String fillList(){
		getLista().add(getTime().toString());
		return "FALSE";
	}
	
	@Init
	public void init(){
		setLista(new ArrayList<String>());
	}

	@Remove
	public void destroy(){
		setLista(null);
	}
}
