package util.comparator;

import java.util.Comparator;

public class ColumnsComparator implements Comparator {

	public int compare(Object o1, Object o2) {
		String[] str1 = o1.toString().split(":");
		String[] str2 = o2.toString().split(":");
		int k = 0; 
		for (int i=0;i<str1.length;i++){
			try {
				k += str1[i].compareTo(str2[i]);				
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
		return k;
	}

}
