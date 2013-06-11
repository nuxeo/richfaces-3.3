package tTree;

import java.util.ArrayList;

import tTree.data.Dir;
import tTree.data.Package;
import tTree.data.Project;

public class TTreeNA {
	private ArrayList<Project> treeNA;

	public TTreeNA() {
		treeNA = new ArrayList<Project>();
		ArrayList<Dir> dirsArr = new ArrayList<Dir>();
		ArrayList<Package> packArr = new ArrayList<Package>();

		treeNA.clear();
		for (int i = 0; i < 3; i++) {
			dirsArr.clear();
			for (int j = 0; j < 4; j++) {
				packArr.clear();
				for (int k = 0; k < 5; k++) {
					packArr.add(new Package("package #" + i + " " + j + " "	+ k));
				}
				dirsArr.add(new Dir("dir #" + i + " " + j, new ArrayList<Package>(packArr)));
			}
			treeNA.add(new Project("project #" + i, new ArrayList<Dir>(dirsArr)));
		}
	}

	public ArrayList<Project> getTreeNA() {
		return treeNA;
	}

	public void setTreeNA(ArrayList<Project> treeNA) {
		this.treeNA = treeNA;
	}
}
