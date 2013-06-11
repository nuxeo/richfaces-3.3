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
public class SourceDirectory extends Directory {
	private List packages = new ArrayList();

	public List getPackages() {
		return packages;
	}

	public void setPackages(List packages) {
		this.packages = packages;
	}
	
	public void addPackage(Package pkg) {
		this.packages.add(pkg);
		pkg.setParent(this);
	}

	@Override
	public void addEntry(Entry entry) {
		if (entry instanceof Package) {
			addPackage((Package) entry);
		} else {
			super.addEntry(entry);
		}
	}

	@Override
	public void removeEntry(Entry entry) {
		packages.remove(entry);
		super.removeEntry(entry);
	}
}
