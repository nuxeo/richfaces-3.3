package org.docs.tree;

import org.richfaces.model.TreeNode;
import org.richfaces.component.xml.XmlTreeDataBuilder;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import java.io.IOException;

public class Hujations {

		private TreeNode data;

		public Hujations() throws SAXException, IOException {
			data = XmlTreeDataBuilder.build(new InputSource(getClass().getResourceAsStream("hujations.xml")));
		}

		public void setData(TreeNode data) {
			this.data = data;
		}

		public TreeNode getData() {
			return data;
		}
	}
