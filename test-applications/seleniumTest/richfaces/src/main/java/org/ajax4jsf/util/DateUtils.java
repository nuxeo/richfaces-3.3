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
package org.ajax4jsf.util;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public abstract class DateUtils {

    /**
     * Returns <code>Date</code> object according to given <code>dateString</code>
     * @param dateString
     * @param pattern
     * @param locale
     * @return
     */
    public static Date date(String dateString, String pattern, Locale locale) {
        Date retVal = null;
        try {
            retVal = new SimpleDateFormat(pattern, locale).parse(dateString);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return retVal;
    }

    public static String month(Date date, Locale locale) {
        Calendar c = Calendar.getInstance(locale);
        c.setTime(date);
        DateFormatSymbols symbols = new DateFormatSymbols(locale);
        return symbols.getMonths()[c.get(Calendar.MONTH)];
    }

    public static int year(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.YEAR);
    }

}
