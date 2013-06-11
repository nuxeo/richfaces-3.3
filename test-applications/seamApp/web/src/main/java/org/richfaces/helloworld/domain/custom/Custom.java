package org.richfaces.helloworld.domain.custom;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

@Name("custom")
@Scope(ScopeType.SESSION)
public class Custom {

	private int index;
	private boolean b0;
	private boolean b1;
	private boolean b2;
	private String s0;
	private String s1;
	private String s2;
	private int i0;
	private int i1;
	private int i2;

	public Custom() {
		this.index = 0;
		this.b0 = true;
		this.b1 = true;
		this.b2 = true;
		this.s0 = "s0";
		this.s1 = "s1";
		this.s2 = "s2";
		this.i0 = 0;
		this.i1 = 1;
		this.i2 = 2;
	}

	public Custom(int index) {
		this.index = index;
		this.b0 = true;
		this.b1 = true;
		this.b2 = true;
		this.s0 = "s0";
		this.s1 = "s1";
		this.s2 = "s2";
		this.i0 = 0;
		this.i1 = 1;
		this.i2 = 2;
	}	
	
	public Custom(int index, boolean b0, boolean b1, boolean b2, String s0, String s1,
			String s2, int i0, int i1, int i2) {
		this.index = index;
		this.b0 = b0;
		this.b1 = b1;
		this.b2 = b2;
		this.s0 = s0;
		this.s1 = s1;
		this.s2 = s2;
		this.i0 = i0;
		this.i1 = i1;
		this.i2 = i2;
	}



	public boolean isB0() {
		return b0;
	}

	public void setB0(boolean b0) {
		this.b0 = b0;
	}

	public boolean isB1() {
		return b1;
	}

	public void setB1(boolean b1) {
		this.b1 = b1;
	}

	public boolean isB2() {
		return b2;
	}

	public void setB2(boolean b2) {
		this.b2 = b2;
	}

	public String getS0() {
		return s0;
	}

	public void setS0(String s0) {
		this.s0 = s0;
	}

	public String getS1() {
		return s1;
	}

	public void setS1(String s1) {
		this.s1 = s1;
	}

	public String getS2() {
		return s2;
	}

	public void setS2(String s2) {
		this.s2 = s2;
	}

	public int getI0() {
		return i0;
	}

	public void setI0(int i0) {
		this.i0 = i0;
	}

	public int getI1() {
		return i1;
	}

	public void setI1(int i1) {
		this.i1 = i1;
	}

	public int getI2() {
		return i2;
	}

	public void setI2(int i2) {
		this.i2 = i2;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}
