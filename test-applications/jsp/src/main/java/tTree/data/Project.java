package tTree.data;

import java.util.ArrayList;

public class Project {
	private String name;
	private ArrayList<Dir> srcDirs;
	
	public Project(String name, ArrayList<Dir> srcDirs) {
		this.name = name;
		this.srcDirs = srcDirs;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Dir> getSrcDirs() {
		return srcDirs;
	}

	public void setSrcDirs(ArrayList<Dir> srcDirs) {
		this.srcDirs = srcDirs;
	}
}
