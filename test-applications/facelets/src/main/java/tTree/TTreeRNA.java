package tTree;

import java.util.ArrayList;

import tTree.data.Dir;
import tTree.data.Package;

public class TTreeRNA {
	private ArrayList<Dir> treeRNAroots;
	
	private String recursionOrder;

	public TTreeRNA() {
		recursionOrder = "first";
		
		treeRNAroots = new ArrayList<Dir>();
		ArrayList<Dir> dirsArr = new ArrayList<Dir>();
		ArrayList<Dir> subDirsArr = new ArrayList<Dir>();
		ArrayList<Package> packArr = new ArrayList<Package>();
		ArrayList<Package> subPackArr = new ArrayList<Package>();

		treeRNAroots.clear();
		dirsArr.clear();
		for (int j = 0; j < 2; j++) {
			packArr.clear();
			subDirsArr.clear();
			for (int k = 0; k < 3; k++) {
				packArr.add(new Package("package #" + j + " " + k));
			}
			for (int f = 0; f < 2; f++) {
				subPackArr.clear();
				for (int l = 0; l < 3; l++) {
					subPackArr.add(new Package("subPackage #" + j + " " + f
							+ " " + l));
				}
				subDirsArr.add(new Dir("subDir #" + j + " " + f,
						new ArrayList<Package>(subPackArr)));
			}
			dirsArr.add(new Dir("dir #" + j, new ArrayList<Package>(packArr),
					new ArrayList<Dir>(subDirsArr)));
		}
		treeRNAroots.add(new Dir("*** root ***", null, dirsArr));
	}

	public ArrayList<Dir> getTreeRNAroots() {
		return treeRNAroots;
	}

	public void setTreeRNAroots(ArrayList<Dir> treeRNAroots) {
		this.treeRNAroots = treeRNAroots;
	}

	public String getRecursionOrder() {
		return recursionOrder;
	}

	public void setRecursionOrder(String recursionOrder) {
		this.recursionOrder = recursionOrder;
	}
}
