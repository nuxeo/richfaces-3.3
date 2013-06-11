/**
 * 
 */
package org.richfaces.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nick Belaevski
 *         mailto:nbelaevski@exadel.com
 *         created 04.08.2007
 *
 */
public class ArchiveEntry extends Entry {
	private List archiveEntries = new ArrayList();
	
	public void addArchiveEntry(ArchiveEntry entry) {
		this.archiveEntries.add(entry);
	}
	
	public List getArchiveEntries() {
		return archiveEntries;
	}

	@Override
	public void addEntry(Entry entry) {
		addArchiveEntry((ArchiveEntry) entry);
	}

	@Override
	public void removeEntry(Entry entry) {
		archiveEntries.remove(entry);
	}
}
