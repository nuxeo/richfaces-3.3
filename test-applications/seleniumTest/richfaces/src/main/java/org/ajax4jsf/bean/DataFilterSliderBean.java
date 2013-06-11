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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.richfaces.event.DataFilterSliderEvent;

public class DataFilterSliderBean {

    public static class Planet implements Serializable{
    	
       	private static final long serialVersionUID = -5401892327427120802L;

		private String name;

        private double gravity;

        Planet(String n, double g) {
            name = n;
            gravity = g;
        }

        public String getName() {
            return name;
        }

        public double getGravity() {
            return gravity;
        }
    }

    private List<Planet> planets;

    private String eventSnapshot = "";

    private Integer startRange = 0;

    private Integer endRange = 40;

    private Integer increment = 1;

    private boolean storeResults = true;

    private Integer handleValue = 20;

    public DataFilterSliderBean() {
        planets = new ArrayList<Planet>();
        planets.add(new Planet("Sun", 27.94));
        planets.add(new Planet("Mercury", 0.3770));
        planets.add(new Planet("Venus", 0.9032));
        planets.add(new Planet("Earth", 1.0));
        planets.add(new Planet("Moon", 0.1655));
        planets.add(new Planet("Mars", 0.3895));
        planets.add(new Planet("Jupiter", 2.640));
        planets.add(new Planet("Saturn", 1.139));
        planets.add(new Planet("Uranus", 0.917));
        planets.add(new Planet("Neptune", 1.148));
        planets.add(new Planet("Pluto", 0.0621));
    }

    public List<Planet> getPlanets() {
        return planets;
    }

    public void doSlide(DataFilterSliderEvent event) {
        Integer oldSliderVal = event.getOldSliderVal();
        Integer newSliderVal = event.getNewSliderVal();
        eventSnapshot = "DataFilterSliderEvent[" + oldSliderVal + "," + newSliderVal + "]";
    }

    public String getEventSnapshot() {
        return eventSnapshot;
    }

    /**
     * Gets value of handleValue field.
     * @return value of handleValue field
     */
    public Integer getHandleValue() {
        return handleValue;
    }

    /**
     * Set a new value for handleValue field.
     * @param handleValue a new value for handleValue field
     */
    public void setHandleValue(Integer handleValue) {
        this.handleValue = handleValue;
    }

    /**
     * Gets value of startRange field.
     * @return value of startRange field
     */
    public Integer getStartRange() {
        return startRange;
    }

    /**
     * Set a new value for startRange field.
     * @param startRange a new value for startRange field
     */
    public void setStartRange(Integer startRange) {
        this.startRange = startRange;
    }

    /**
     * Gets value of endRange field.
     * @return value of endRange field
     */
    public Integer getEndRange() {
        return endRange;
    }

    /**
     * Set a new value for endRange field.
     * @param endRange a new value for endRange field
     */
    public void setEndRange(Integer endRange) {
        this.endRange = endRange;
    }

    /**
     * Gets value of increment field.
     * @return value of increment field
     */
    public Integer getIncrement() {
        return increment;
    }

    /**
     * Set a new value for increment field.
     * @param increment a new value for increment field
     */
    public void setIncrement(Integer increment) {
        this.increment = increment;
    }

    /**
     * Gets value of storeResults field.
     * @return value of storeResults field
     */
    public boolean isStoreResults() {
        return storeResults;
    }

    /**
     * Set a new value for storeResults field.
     * @param storeResults a new value for storeResults field
     */
    public void setStoreResults(boolean storeResults) {
        this.storeResults = storeResults;
    }

    public String action() {
        return null;
    }

    public void initIncrementTest() {
        reset();
        increment = 5;
    }

    public void initStoreResultsTest() {
        reset();
        storeResults = false;
    }

    public String reset() {
        eventSnapshot = "";
        handleValue = 20;
        increment = 1;
        storeResults = true;
        return null;
    }

}
