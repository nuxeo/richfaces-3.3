package org.richfaces.demo.tree.test;

import java.util.Iterator;

import org.richfaces.demo.tree.Library;

import junit.framework.TestCase;

public class LibraryTest extends TestCase {
	
	public void testLibrary() {
		Library library = new Library();
		Iterator it = library.getChildren();
		assertTrue(it.hasNext());
	}

}
