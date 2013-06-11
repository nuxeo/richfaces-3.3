/**
 * 
 */
package org.richfaces;

import javax.faces.component.UIComponent;
import javax.faces.event.FacesEvent;

import org.richfaces.component.UITree;
import org.richfaces.component.UITreeNode;

/**
 * @author dmorozov
 *
 */
public class TreeContainer {

	/**
	 * Get tree component from event
	 * 
	 * @param event tree event
	 * @return tree component
	 */
	protected UITree getTree(FacesEvent event) {
		return getTree(event.getComponent());
	}

	/**
	 * Safe getter of tree component
	 * @param component component to process
	 * @return tree component
	 */
	protected UITree getTree(UIComponent component) {
		if (component instanceof UITree) {
			return ((UITree) component);
		}

		if (component instanceof UITreeNode) {
			return ((UITree) component.getParent());
		}

		return null;
	}
}
