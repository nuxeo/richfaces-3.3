/*
 * FileData.java		Date created: 22.02.2008
 * Last modified by: $Author$
 * $Revision$	$Date$
 */

package org.richfaces;

/**
 * TODO Class description goes here.
 * @author "Andrey Markavtsov"
 *
 */
public class FileData {
    private String name;
    
   
   
    public FileData(String name) {
	this.name = name;
    }


    /**
     * @return the name
     */
    public String getName() {
        return name;
    }


    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
  
    
}