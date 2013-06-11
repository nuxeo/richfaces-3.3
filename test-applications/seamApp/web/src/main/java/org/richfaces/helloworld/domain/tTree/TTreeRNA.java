package org.richfaces.helloworld.domain.tTree;

import java.util.ArrayList;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.richfaces.helloworld.domain.tTree.data.Dir;
import org.richfaces.helloworld.domain.tTree.data.Package;


@Name("tTreeRNA")
@Scope(ScopeType.SESSION)
public class TTreeRNA {
	private ArrayList<Dir> treeRNAroots;

	public TTreeRNA() {
		treeRNAroots = new ArrayList<Dir>();
		ArrayList<Dir> dirsArr = new ArrayList<Dir>();
		ArrayList<Dir> subDirsArr = new ArrayList<Dir>();
		ArrayList<Package> packArr = new ArrayList<Package>();
		ArrayList<Package> subPackArr = new ArrayList<Package>();

		treeRNAroots.clear();
		dirsArr.clear();
		for (int j = 0; j < 4; j++) {
			packArr.clear();
			subDirsArr.clear();
			for (int k = 0; k < 5; k++) {
				packArr.add(new Package("package #" + j + " " + k));
			}
			for (int f = 0; f < 4; f++) {
				subPackArr.clear();
				for (int l = 0; l < 5; l++) {
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
}
