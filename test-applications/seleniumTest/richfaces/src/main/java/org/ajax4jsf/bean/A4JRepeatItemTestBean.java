/**
 * License Agreement.
 *
 * JBoss RichFaces - Ajax4jsf Component Library
 *
 * Copyright (C) 2007 Exadel, Inc.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License version 2.1 as published by the Free Software Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 */ 
package org.ajax4jsf.bean;

/**
 * TODO Class description goes here.
 * @author Alexandr Levkovsky
 *
 */
public class A4JRepeatItemTestBean {

    private Integer number;

    private Integer output;
    
    private Integer input;
    
    private Integer rerenderedChangeCounter;
    
    private Integer notRerenderedChangeCounter;

    A4JRepeatItemTestBean(Integer index) {
	this.number = index;
	this.input = index;
	this.output = index;
	this.rerenderedChangeCounter = 0;
	this.notRerenderedChangeCounter = 0;
    }

    /**
     * @return the number
     */
    public Integer getNumber() {
	return number;
    }

    /**
     * @param number the number to set
     */
    public void setNumber(Integer number) {
	this.number = number;
    }

    /**
     * @return the output
     */
    public Integer getOutput() {
	return output;
    }

    /**
     * @param output the output to set
     */
    public void setOutput(Integer output) {
	this.output = output;
    }

    /**
     * @return the input
     */
    public Integer getInput() {
        return input;
    }

    /**
     * @param input the input to set
     */
    public void setInput(Integer input) {
        this.input = input;
    }

    /**
     * @return the rerenderedChangeCounter
     */
    public Integer getRerenderedChangeCounter() {
        return rerenderedChangeCounter;
    }

    /**
     * @param rerenderedChangeCounter the rerenderedChangeCounter to set
     */
    public void setRerenderedChangeCounter(Integer rerenderedChangeCounter) {
        this.rerenderedChangeCounter = rerenderedChangeCounter;
    }

    /**
     * @return the notRerenderedChangeCounter
     */
    public Integer getNotRerenderedChangeCounter() {
        return notRerenderedChangeCounter;
    }

    /**
     * @param notRerenderedChangeCounter the notRerenderedChangeCounter to set
     */
    public void setNotRerenderedChangeCounter(Integer notRerenderedChangeCounter) {
        this.notRerenderedChangeCounter = notRerenderedChangeCounter;
    }
    
    

}
