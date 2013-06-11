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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.model.SelectItem;

public class PickListTestBean {

    private String copyAllLabel = "MOVE ALL";

    private String copyLabel = "MOVE";

    private String removeLabel = "TAKE AWAY";

    private String removeAllLabel = "TAKE ALL AWAY";

    public static final List<String> ITEMS = Arrays.asList("ZHURIK", "MELESHKO", "LEONTIEV", "KOVAL", "KALYUZHNY", "DUDIK",
            "KOSTITSYN", "GRABOVSKI");

    private List<SelectItem> options;

    private List<String> result;

    private boolean switchByClick = false;

    public PickListTestBean() {
        reset();
    }

    /**
     * Gets value of copyAllLabel field.
     * 
     * @return value of copyAllLabel field
     */
    public String getCopyAllLabel() {
        return copyAllLabel;
    }

    /**
     * Gets value of copyLabel field.
     * 
     * @return value of copyLabel field
     */
    public String getCopyLabel() {
        return copyLabel;
    }

    /**
     * Gets value of removeLabel field.
     * 
     * @return value of removeLabel field
     */
    public String getRemoveLabel() {
        return removeLabel;
    }

    /**
     * Gets value of removeAllLabel field.
     * 
     * @return value of removeAllLabel field
     */
    public String getRemoveAllLabel() {
        return removeAllLabel;
    }

    /**
     * Gets value of options field.
     * 
     * @return value of options field
     */
    public List<SelectItem> getOptions() {
        return options;
    }

    /**
     * Set a new value for options field.
     * 
     * @param options
     *                a new value for options field
     */
    public void setOptions(List<SelectItem> options) {
        this.options = options;
    }

    /**
     * Gets value of result field.
     * 
     * @return value of result field
     */
    public List<String> getResult() {
        return result;
    }

    /**
     * Set a new value for result field.
     * 
     * @param result
     *                a new value for result field
     */
    public void setResult(List<String> result) {
        this.result = result;
    }

    /**
     * Gets value of switchByClick field.
     * @return value of switchByClick field
     */
    public boolean isSwitchByClick() {
        return switchByClick;
    }

    /**
     * Set a new value for switchByClick field.
     * @param switchByClick a new value for switchByClick field
     */
    public void setSwitchByClick(boolean switchByClick) {
        this.switchByClick = switchByClick;
    }

    public void initSwitchByClickTest() {
        reset();
        setSwitchByClick(true);
    }

    public void reset() {
        options = new ArrayList<SelectItem>();
        for (String player : ITEMS) {
            options.add(new SelectItem(player, player));
        }

        // preselected items
        result = new ArrayList<String>();
        result.add(ITEMS.get(0));
        result.add(ITEMS.get(1));

        switchByClick = false;
    }

}
