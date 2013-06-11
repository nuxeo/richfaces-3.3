package org.ajax4jsf;
import org.apache.myfaces.custom.tree2.TreeNodeBase;

/**
 * 
 */

/**
 * @author asmirnov
 *
 */
public class TestTreeModel extends TreeNodeBase {
    
    private String url;
    
    private String target="/xxx";
    private String description="Node";
    private boolean urlNode=true;

    /**
     * 
     */
    private static final long serialVersionUID = 8653793302556194481L;
    
    
    public TestTreeModel() {
	super();
	setLeaf(false);
	setType("desktop");
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the target
     */
    public String getTarget() {
        return target;
    }

    /**
     * @param target the target to set
     */
    public void setTarget(String target) {
        this.target = target;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the urlNode
     */
    public boolean isUrlNode() {
        return urlNode;
    }

    /**
     * @param urlNode the urlNode to set
     */
    public void setUrlNode(boolean urlNode) {
        this.urlNode = urlNode;
    }

}
