package org.richfaces.samples.extdt.model.impl;

import java.io.Serializable;
import java.util.Date;

/**
 * @author pkawiak
 */

public class DemoPatient implements Serializable{

    private Integer id;
    private String firstName;
    private String lastName;
    private Date admissionDate;
    
    DemoPatient(Integer id, String firstName, String lastName, Date admissionDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.admissionDate = admissionDate;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the admissionDate
     */
    public Date getAdmissionDate() {
        return admissionDate;
    }

    /**
     * @param admissionDate the admissionDate to set
     */
    public void setAdmissionDate(Date admissionDate) {
        this.admissionDate = admissionDate;
    }
    
    
    
}
