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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.richfaces.renderkit.html.SuggestionBoxRenderer;

public class SuggestionBox implements Serializable {
    private boolean usingSuggestObjects;
    
    private String property;

    private ArrayList cities;

    private ArrayList data;
    private ArrayList tokens;

    private String rows;
    private String first;
    private String cellspacing;
    private String cellpadding;
    private String minchars;
    private String frequency;
    private String rules;
    private boolean check;
    private String shadowDepth = Integer.toString(SuggestionBoxRenderer.SHADOW_DEPTH);
    private String border = "1";
    private String width = "200";
    private String height = "150";
    private String shadowOpacity = "4";
    private String onsubmit = "true";

    private static final String [] cit =    {"Abba", "Abbeville", "Acworth", "Adairsville", "Adel", "Adrian", "Ailey", "Alamo", "Alapaha", "Albany", "Allenhurst", "Alma", "Alpharetta", "Alston", "Amboy", "Ambrose", "Americus", "Appling", "Arlington", "Ashburn", "Athens", "Athens-Clarke County", "Atkinson", "Atlanta", "Attapulgus", "Auburn", "Augusta", "Augusta-Richmond County", "Austell", "Avondale Estates", "Axson"};
    private static final double [] prices = {0.9,     2.31,       5.707,     8.0,           10.01,  50.50,    99.99,   80.08,   101.01,    222.22,   200.0,        345.67, 456.78,       500.1,    999.99,  999.99,    999.99,     999.99,    999.99,      999.99,    999.99,   999.99,                 999.99,     999.99,    999.99,       999.99,   999.99,    999.99,                    999.99,    999.99,             999.99};

    public SuggestionBox() {
        this.rows = "0";
        this.first = "0";
        this.cellspacing = "2";
        this.cellpadding = "2";
        this.minchars = "1";
        this.frequency = "0";
        this.rules = "none";

        setCities(getAllData());
    }

    public List autocomplete(Object suggest) {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage("SuggestionBox.autocomplete(" + suggest + ")");
        context.addMessage(null, message);
        
	String pref = (String)suggest;
        ArrayList result = new ArrayList();

        Iterator iterator = getAllData().iterator();
        while (iterator.hasNext()) {
            Data elem = (Data) iterator.next();
            if ((elem != null && elem.getText().toLowerCase().indexOf(pref.toLowerCase()) == 0) || "".equals(pref))
            {
                result.add(elem);
            }
        }
        return result;
    }

    public ArrayList getCities() {
        return cities;
    }

    public void setCities(ArrayList cities) {
        this.cities = cities;
    }

    public ArrayList getAllData() {
        ArrayList result = new ArrayList();
        for (int i = 0; i < cit.length; i++) {
            Data data = new Data(cit[i], String.valueOf(i + 1), prices[i]);
            result.add(data);
        }
        return result;
    }

    public ArrayList getData() {
        return data;
    }

    public void setData(ArrayList data) {
        this.data = data;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getCellpadding() {
        return cellpadding;
    }

    public void setCellpadding(String cellpadding) {
        this.cellpadding = cellpadding;
    }

    public String getCellspacing() {
        return cellspacing;
    }

    public void setCellspacing(String cellspacing) {
        this.cellspacing = cellspacing;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public String getFirst() {
        return first;
    }

    public int getIntFirst() {
        return Integer.parseInt(getFirst());
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getFrequency() {
        return frequency;
    }

    public double getDoubleFrequency() {
        return Double.parseDouble(getFrequency());
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getMinchars() {
        return minchars;
    }

    public void setMinchars(String minchars) {
        this.minchars = minchars;
    }

    public String getRows() {
        return rows;
    }

    public void setRows(String rows) {
        this.rows = rows;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public ArrayList getTokens() {
        return tokens;
    }

    public void setTokens(ArrayList tokens) {
        this.tokens = tokens;
    }

    public void OnSelect() {
        System.out.print("Onselect works!!!");

    }

    public String getShadowDepth() {
        return shadowDepth;
    }

    public void setShadowDepth(String shadowDepth) {
        this.shadowDepth = shadowDepth;
    }

    public String getBorder() {
        return border;
    }

    public void setBorder(String border) {
        this.border = border;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getShadowOpacity() {
        return shadowOpacity;
    }

    public void setShadowOpacity(String shadowOpacity) {
        this.shadowOpacity = shadowOpacity;
    }

    public String getOnsubmit() {
        return onsubmit;
    }

    public void setOnsubmit(String onsubmit) {
        this.onsubmit = onsubmit;
    }

    public String getOnsubmitFunction() {
        if ("false".equalsIgnoreCase(onsubmit)) return null;
        return "alert(this.value); return " + onsubmit + ";";
    }

    public void setOnsubmitFunction(String onsubmit) {
    }

    public boolean isUsingSuggestObjects() {
        return usingSuggestObjects;
    }

    public void setUsingSuggestObjects(boolean usingSuggestObjects) {
        this.usingSuggestObjects = usingSuggestObjects;
    }
}
