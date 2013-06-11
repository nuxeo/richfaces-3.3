package tTree.data;

import java.util.ArrayList;

public class Dir {
	private String name;
	private ArrayList<Package> packages;
	private ArrayList<Dir> dirs;
	
	public Dir(String name, ArrayList<Package> packages) {
		this.name = name;
		this.packages = packages;
	}
	
	public Dir(String name, ArrayList<Package> packages, ArrayList<Dir> dirs) {
		this.name = name;
		this.packages = packages;
		this.dirs = dirs;
	}
	
	public ArrayList<Dir> getDirs() {
		return dirs;
	}

	public void setDirs(ArrayList<Dir> dirs) {
		this.dirs = dirs;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Package> getPackages() {
		return packages;
	}

	public void setPackages(ArrayList<Package> packages) {
		this.packages = packages;
	}
}
