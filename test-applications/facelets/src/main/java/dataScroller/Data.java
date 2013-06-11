package dataScroller;

import java.util.Random;

public class Data {

	public String data0;
	public int data1;
	public String data2;
	public String data3;
	
	public Data(String data0, int data1) {
			this.data0 = data0;
			this.data1 = data1;
	}
	
	public Data(String data0, int data1, String data2, String data3) {
		this(data0, data1);
		this.data2 = data2;
		this.data3 = data3;
	}

	public String getData0() {
		return data0;
	}

	public void setData0(String data0) {
		this.data0 = data0;
	}

	public int getData1() {
		return data1;
	}
 
	public String getData2() {
		return data2;
	}

	public void setData2(String data2) {
		this.data2 = data2;
	}

	public String getData3() {
		return data3;
	}

	public void setData3(String data3) {
		this.data3 = data3;
	}

	public void setData1(int data1) {
		this.data1 = data1;
	}

	
	
	
}
