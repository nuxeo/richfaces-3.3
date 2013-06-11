package org.richfaces.regressionarea.issues.rf5948;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

@Name("rf5948")
@Scope(ScopeType.APPLICATION)
public class Bean {
    private String text;
    
    public String getText() {
        return text;
    }
    
    public void setText(String text) {
        this.text = text;
    }
    
    public String getDuplicateText() {
        return  getText();
    }
    
    public void setDuplicateText(String duplicateText) {
        this.text = duplicateText;
    }
}
