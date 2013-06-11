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
package org.ajax4jsf.bean.validation;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.Max;
import org.hibernate.validator.Min;
import org.hibernate.validator.NotNull;

/**
 * renamed and copied from demo
 */
public class OrderOfDay {
    public OrderOfDay() {
        activities.add(new Activity("Sport", 0));
        activities.add(new Activity("Entertainment", 0));
        activities.add(new Activity("Sleeping", 0));
        activities.add(new Activity("Games", 0));
    }

    private List<Activity> activities = new ArrayList<Activity>();

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    @NotNull
    @Min(value = 1, message = "Please fill at least one entry")
    @Max(value = 24, message = "Only 24h in a day!")
    public Integer getTotalTime() {
        Integer retVal = 0;
        for (Activity activity : activities) {
            retVal += activity.getTime();
        }
        return retVal;
    }
    
    @NotNull
    @Max(value = 10, message = "Are you sure you have power for this??!!")
    public Integer getSportTime() {
        return activities.get(0).getTime();
    }

}
