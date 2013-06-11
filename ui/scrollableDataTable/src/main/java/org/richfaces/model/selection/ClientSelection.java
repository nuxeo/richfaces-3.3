/**
 * License Agreement.
 *
 * Rich Faces - Natural Ajax for Java Server Faces (JSF)
 *
 * Copyright (C) 2007 Exadel, Inc.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License version 2.1 as published by the Free Software Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA
 */
package org.richfaces.model.selection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class representing selection on client side.
 * Actually, it's renderer specific
 * @author Maksim Kaszynski
 *
 */
public class ClientSelection implements Serializable{
	
	private static final long serialVersionUID = 5855157282287053681L;

	public static final String FLAG_RESET = "x";
	
	public static final String FLAG_ALL = "a";
	
	private String selectionFlag;
	
	private int activeRowIndex = -1;
	
	private List<SelectionRange> ranges = new ArrayList<SelectionRange>();
	
	public ClientSelection() {
	}

	
	public void addRange(SelectionRange range) {
		ranges.add(range);
	}
	
	public boolean isSelected(int i) {
		boolean result = false;
		Iterator<SelectionRange> iterator = ranges.iterator();
		while (iterator.hasNext() && !result) {
			result |= ((SelectionRange) iterator.next()).within(i);
		}
		return result;
	}
	
	public List<SelectionRange> getRanges() {
		return ranges;
	}
	
	public void addIndex(int j) {
		if(this.isSelected(j)) return;
		
		
		
		SelectionRange firstRange = null;
		
		int s = ranges.size();
		
		int insertPosition = 0;
		int i;
		for(i = 0; i < s && insertPosition >= 0 ; i++) {
			
			firstRange = (SelectionRange) ranges.get(i);
			
			if (firstRange.getStartIndex() == j + 1) {
			
				firstRange.setStartIndex(j);
				insertPosition = -1;
			
			} else if (firstRange.getEndIndex() == j - 1) {
			
				firstRange.setEndIndex(j);
				
				if (i + 1 < s) {
					SelectionRange range2 = (SelectionRange) ranges.get(i + 1);
					
					if (range2.getStartIndex() == j || range2.getStartIndex() == j + 1) {
						
						ranges.remove(i + 1);
						
						firstRange.setEndIndex(range2.getEndIndex());
					}
				}
				
				insertPosition = -1;
			
			} else if (firstRange.getStartIndex() > j) {
				insertPosition = i;
			} else if (insertPosition == 0 && i == s - 1) {
				insertPosition = s;				
			}
			
		}

		if (insertPosition >= 0) {
			firstRange = new SelectionRange(j,j);
			ranges.add(insertPosition, firstRange);
			
			return;
		}
		

	}

	private boolean reset = false;
	public boolean isReset() {
		return reset;
	}
	
	private boolean selectAll = false;
	public boolean isSelectAll() {
		return selectAll;
	}


	public String getSelectionFlag() {
		return selectionFlag;
	}


	public void setSelectionFlag(String selectionFlag) {
		this.selectionFlag = selectionFlag;
		
		reset = false;
		selectAll = false;

		if (FLAG_ALL.equals(selectionFlag)) {
			selectAll = true;
		} else if (FLAG_RESET.equals(selectionFlag)) {
			reset = true;
		}
	}


	public int getActiveRowIndex() {
		return activeRowIndex;
	}


	public void setActiveRowIndex(int activeRowIndex) {
		this.activeRowIndex = activeRowIndex;
	}
}
