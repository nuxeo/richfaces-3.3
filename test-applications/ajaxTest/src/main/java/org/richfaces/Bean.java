package org.richfaces;


import java.io.Serializable;

public class Bean implements Serializable {

    private String text;
    
   public Bean() {}
    
   public String getText() { return text;}
   
   public void setText(String name) { this.text = name; }
   
}
