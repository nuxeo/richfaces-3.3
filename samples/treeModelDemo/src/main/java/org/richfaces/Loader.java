/**
 * 
 */
package org.richfaces;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.faces.FacesException;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.xmlrules.DigesterLoader;
import org.richfaces.component.UITree;
import org.richfaces.component.UITreeNode;
import org.richfaces.event.DropEvent;
import org.richfaces.model.Entry;
import org.xml.sax.SAXException;

/**
 * @author Nick Belaevski mailto:nbelaevski@exadel.com created 25.07.2007
 * 
 */
public class Loader {
	private List projects;
	
	public synchronized List getProjects() {
		if (projects == null) {
			projects = Loader.load();
		}
		return projects;
	}
	
	private static List load() throws FacesException {
		URL url = Loader.class.getResource("tree-model-rules.xml");
		Digester digester = DigesterLoader.createDigester(url);
		List list = new ArrayList();
		digester.push(list);
		InputStream stream = Loader.class.getResourceAsStream("/org/richfaces/model/tree-model-data.xml");
		try {
			digester.parse(stream);
			return list;
		} catch (IOException e) {
			throw new FacesException(e.getMessage(), e);
		} catch (SAXException e) {
			throw new FacesException(e.getMessage(), e);
		} finally {
			try {
				stream.close();
			} catch (IOException e) {
				throw new FacesException(e.getMessage(), e);
			}
		}
	}
	
	public void trashDrop(DropEvent dropEvent) {
		UITree tree = ((UITreeNode) dropEvent.getDraggableSource()).getUITree();
		Entry rowData = (Entry) tree.getRowData(dropEvent.getDragValue());
		
		if (rowData.getParent() != null) {
			rowData.getParent().removeEntry(rowData);
		} else {
			projects.remove(rowData);
		}

		tree.addRequestKey(tree.getParentRowKey(dropEvent.getDragValue()));
	}

	public void treeDrop(DropEvent dropEvent) {
		UITree tree = ((UITreeNode) dropEvent.getComponent()).getUITree();

		Object dragValue = dropEvent.getDragValue();
		Object dropValue = dropEvent.getDropValue();
		
		Entry dragEntry = (Entry) tree.getRowData(dragValue);
		Entry dropEntry = (Entry) tree.getRowData(dropValue);
		
		dragEntry.getParent().removeEntry(dragEntry);
		dropEntry.addEntry(dragEntry);
		
		tree.setRowKey(dragValue);
		tree.clearTreeNodeState();
		
		tree.addRequestKey(tree.getParentRowKey(dragValue));
		tree.addRequestKey(dropValue);
	}
}
