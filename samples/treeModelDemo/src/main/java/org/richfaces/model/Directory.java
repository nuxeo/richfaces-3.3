/**
 * 
 */
package org.richfaces.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Nick Belaevski
 *         mailto:nbelaevski@exadel.com
 *         created 24.07.2007
 *
 */
public class Directory extends Entry {

    
	private Map directories = new LinkedHashMap();
    
    	public void addDir(Directory directory) {
    		this.directories.put(new StringKey(directory.getName()), directory);
    		directory.setParent(this);
    	}
    
    	public Map getDirectories() {
    		return directories;
    	}
    
    	public void setDirectories(Map directories) {
    		this.directories = directories;
    	}
    
    	public static class StringKey {
    	    private String s;
    	    
    	    public StringKey(String s) {
    		super();
    		this.s = s;
    	    }
    
    	    @Override
    	    public String toString() {
    	        return s;
    	    }
    
    	    @Override
    	    public int hashCode() {
    		final int prime = 31;
    		int result = 1;
    		result = prime * result + ((s == null) ? 0 : s.hashCode());
    		return result;
    	    }
    
    	    @Override
    	    public boolean equals(Object obj) {
    		if (this == obj)
    		    return true;
    		if (obj == null)
    		    return false;
    		if (getClass() != obj.getClass())
    		    return false;
    		StringKey other = (StringKey) obj;
    		if (s == null) {
    		    if (other.s != null)
    			return false;
    		} else if (!s.equals(other.s))
    		    return false;
    		return true;
    	    }
    
    	    
    	}

//    	private List directories = new ArrayList();
//
//	public List getDirectories() {
//		return directories;
//	}
//
//	public void setDirectories(List directories) {
//		this.directories = directories;
//	}
//
//	public void addDir(Directory directory) {
//		this.directories.add(directory);
//		directory.setParent(this);
//	}

	private List files = new ArrayList();;

	public List getFiles() {
	    return files;
	}

	public void setFiles(List files) {
	    this.files = files;
	}

	public void addFile(File file) {
		this.files.add(file);
		file.setParent(this);
	}

	@Override
	public void addEntry(Entry entry) {
		if (entry instanceof File) {
			addFile((File) entry);
		} else {
			addDir((Directory) entry);
		}
	}

	@Override
	public void removeEntry(Entry entry) {
		files.remove(entry);
		directories.remove(new StringKey(entry.getName()));
	}
}
