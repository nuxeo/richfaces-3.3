package org.richfaces;

public class TestBean {
    
    boolean rendered = false;

    public boolean isRendered() {
        return rendered;
    }

    public void setRendered(boolean rendered) {
        this.rendered = rendered;
    }
    
    
    public void switchRendered() {
	if(rendered) {
	    rendered = false; 
	} else {
	    rendered = true;
	}
    }

}
