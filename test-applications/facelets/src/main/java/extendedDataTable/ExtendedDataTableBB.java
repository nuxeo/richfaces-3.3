package extendedDataTable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.richfaces.model.ExtendedTableDataModel;
import org.richfaces.model.selection.SimpleSelection;

/**
 * @author mvitenkov
 *
 */
public class ExtendedDataTableBB {
    
    private SimpleSelection selection = new SimpleSelection();
    private List<DemoPatient> selectedItems;
    private Comparator<DemoPatient> dateComparator;
    private String tableState = null;
    private ExtendedTableDataModel<DemoPatient> dataModel;
    private Integer patientsNumber = 100;
    private boolean a4jRendered = true;

    public boolean isA4jRendered() {
		return a4jRendered;
	}

	public void setA4jRendered(boolean rendered) {
		a4jRendered = rendered;
	}

	public ExtendedDataTableBB() {
    }
    
    public ExtendedTableDataModel<DemoPatient> getDataModel(){
        if (dataModel == null){
            dataModel = new ExtendedTableDataModel<DemoPatient>(new DemoPatientProvider(patientsNumber));
        }
        return dataModel;
    }
    
    public SimpleSelection getSelection() {
        return selection;
    }

    public void setSelection(SimpleSelection selection) {
        this.selection = selection;
    }
    
    public String takeSelection() {
        getSelectedItems().clear();
        Iterator<Object> iterator = getSelection().getKeys();
        while (iterator.hasNext()){
            Object key = iterator.next();
            selectedItems.add(getDataModel().getObjectByKey(key));
        }
        return null;
    }
    
    public Integer getPatientsNumber() {
        return patientsNumber;
    }

    public void setPatientsNumber(Integer patientsNumber) {
        if (patientsNumber != this.patientsNumber) {
            dataModel = new ExtendedTableDataModel<DemoPatient>(new DemoPatientProvider(patientsNumber));
        }
        this.patientsNumber = patientsNumber;
    }

    public List<DemoPatient> getSelectedItems() {
        if (selectedItems == null){
            selectedItems = new ArrayList<DemoPatient>();
        }
        return selectedItems;
    }

    public void setSelectedItems(List<DemoPatient> selectedItems) {
        this.selectedItems = selectedItems;
    }

    public String getTableState() {
        if (tableState == null){
            //try to get state from cookies
            Cookie[] cookies = ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getCookies();
            if (cookies != null){
                for (Cookie c : cookies){
                    if (c.getName().equals("g3demoTabelState")){
                        tableState = c.getValue();
                        break;
                    }
                }
            }
        }
        return tableState;
    }

    public void setTableState(String tableState) {
        this.tableState = tableState;
        //save state in cookies
        Cookie stateCookie = new Cookie("g3demoTabelState", this.tableState);
        ((HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse()).addCookie(stateCookie);
    }
    
    public Comparator<DemoPatient> getDateComparator(){
        if (dateComparator == null){
            dateComparator = new Comparator<DemoPatient>(){

                public int compare(DemoPatient o1, DemoPatient o2) {
                    return o1.getAdmissionDate().compareTo(o2.getAdmissionDate());
                }
                
            };
        }
        return dateComparator;
    }
    
}
