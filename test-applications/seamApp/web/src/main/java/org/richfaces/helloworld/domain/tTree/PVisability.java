package org.richfaces.helloworld.domain.tTree;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

@Name("pVisability")
@Scope(ScopeType.SESSION)
public class PVisability {
	private boolean tTreeSubviewID;
	private boolean tTreePropertySubviewID;
	private boolean tTreeStraightforwardSubviewID;
	private boolean tTreeDefaultSubviewID;
	
	public PVisability() {
		tTreeSubviewID = true;
		tTreePropertySubviewID = false;
		tTreeStraightforwardSubviewID = false;
		tTreeDefaultSubviewID = false;
	}
	
	public boolean istTreeDefaultSubviewID() {
		return tTreeDefaultSubviewID;
	}

	public void settTreeDefaultSubviewID(boolean treeDefaultSubviewID) {
		tTreeDefaultSubviewID = treeDefaultSubviewID;
	}
	
	public boolean istTreeSubviewID() {
		return tTreeSubviewID;
	}
	
	public void settTreeSubviewID(boolean treeSubviewID) {
		tTreeSubviewID = treeSubviewID;
	}
	
	public boolean istTreePropertySubviewID() {
		return tTreePropertySubviewID;
	}
	
	public void settTreePropertySubviewID(boolean treePropertySubviewID) {
		tTreePropertySubviewID = treePropertySubviewID;
	}
	
	public boolean istTreeStraightforwardSubviewID() {
		return tTreeStraightforwardSubviewID;
	}
	
	public void settTreeStraightforwardSubviewID(
			boolean treeStraightforwardSubviewID) {
		tTreeStraightforwardSubviewID = treeStraightforwardSubviewID;
	}
}
