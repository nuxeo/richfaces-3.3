/**
 * 
 */
package org.richfaces.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nick Belaevski
 *         mailto:nbelaevski@exadel.com
 *         created 24.07.2007
 *
 */
public class Project extends Entry {
	private List srcDirs = new ArrayList();

	public List getSrcDirs() {
		return srcDirs;
	}

	public void setSrcDirs(List srcDirs) {
		this.srcDirs = srcDirs;
	}
	
	public void addSrcDir(SourceDirectory directory) {
		this.srcDirs.add(directory);
		directory.setParent(this);
	}
	
	public void addDir(Directory directory) {
		this.dirs.add(directory);
		directory.setParent(this);
	}

	private List dirs = new ArrayList();

	public List getDirs() {
		return dirs;
	}

	public void setDirs(List dirs) {
		this.dirs = dirs;
	}

	@Override
	public void addEntry(Entry entry) {
		if (entry instanceof Directory) {
			addDir((Directory) entry);
		} else {
			addSrcDir((SourceDirectory) entry);
		}
	}

	@Override
	public void removeEntry(Entry entry) {
		dirs.remove(entry);
		srcDirs.remove(entry);
	}
}
