/**
 * License Agreement.
 *
 *  JBoss RichFaces - Ajax4jsf Component Library
 *
 * Copyright (C) 2007  Exadel, Inc.
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

package org.richfaces;

import java.util.Random;

	
	
	
	public class Entry {
		
		private Random random = new Random();
		private int number1;
		private int number2;
		private int number3;
		private Entry entry;
		
		Entry (Entry entry,int a){
			this.entry = entry;
			this.number1 = a*10+1;
			this.number2 = a*10+2;
			this.number3 = a*10+3;
		}
		public Entry getEntry() {
			return entry;
		}
		public void setEntry(Entry entry) {
			this.entry = entry;
		}
		public int getNumber1() {
			return number1;
		}
		public void setNumber1(int number1) {
			this.number1 = number1;
		}
		public int getNumber2() {
			return number2;
		}
		public void setNumber2(int number2) {
			this.number2 = number2;
		}
		public int getNumber3() {
			return number3;
		}
		public void setNumber3(int number3) {
			this.number3 = number3;
		}		
	}



