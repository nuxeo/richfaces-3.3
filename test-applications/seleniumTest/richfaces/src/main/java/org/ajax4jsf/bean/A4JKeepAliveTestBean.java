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

public class A4JKeepAliveTestBean {

    /* result */
    private Integer sum = 0;

    /* value to be added to result */
    private Integer augend = 5;

    /**
     * Gets value of sum field.
     * @return value of sum field
     */
    public Integer getSum() {
        return sum;
    }

    /**
     * Set a new value for sum field.
     * @param sum a new value for sum field
     */
    public void setSum(Integer sum) {
        this.sum = sum;
    }

    /**
     * Gets value of augend field.
     * @return value of augend field
     */
    public Integer getAugend() {
        return augend;
    }

    /**
     * Set a new value for augend field.
     * @param augend a new value for augend field
     */
    public void setAugend(Integer augend) {
        this.augend = augend;
    }

    public String doSum() {
        sum += augend;
        return null;
    }
}
