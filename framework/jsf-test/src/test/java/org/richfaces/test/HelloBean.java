package org.richfaces.test;

import java.io.Serializable;

public class HelloBean implements Serializable {

    private String name;
    
   public HelloBean() {}
    
   public String getName() { return name;}
   
   public void setName(String name) { this.name = name; }
   
}
