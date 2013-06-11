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

package dfs;

import java.math.BigDecimal;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Wesley
 * Date: Jan 26, 2007
 * Time: 8:21:36 AM
 * To change this template use File | Settings | File Templates.
 */
public class DemoInventoryItem implements Serializable {

    String make;
    String model;
    String stock;
    String vin;

    BigDecimal mileage;
    BigDecimal mileageMarket;
    Integer price;
    BigDecimal priceMarket;

    int daysLive;
    BigDecimal changeSearches;
    BigDecimal changePrice;

    BigDecimal exposure;
    BigDecimal activity;
    BigDecimal printed;
    BigDecimal inquiries;


    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public BigDecimal getMileage() {
        return mileage;
    }

    public void setMileage(BigDecimal mileage) {
        this.mileage = mileage;
    }

    public BigDecimal getMileageMarket() {
        return mileageMarket;
    }

    public void setMileageMarket(BigDecimal mileageMarket) {
        this.mileageMarket = mileageMarket;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public BigDecimal getPriceMarket() {
        return priceMarket;
    }

    public void setPriceMarket(BigDecimal priceMarket) {
        this.priceMarket = priceMarket;
    }

    public int getDaysLive() {
        return daysLive;
    }

    public void setDaysLive(int daysLive) {
        this.daysLive = daysLive;
    }

    public BigDecimal getChangeSearches() {
        return changeSearches;
    }

    public void setChangeSearches(BigDecimal changeSearches) {
        this.changeSearches = changeSearches;
    }

    public BigDecimal getChangePrice() {
        return changePrice;
    }

    public void setChangePrice(BigDecimal changePrice) {
        this.changePrice = changePrice;
    }

    public BigDecimal getExposure() {
        return exposure;
    }

    public void setExposure(BigDecimal exposure) {
        this.exposure = exposure;
    }

    public BigDecimal getActivity() {
        return activity;
    }

    public void setActivity(BigDecimal activity) {
        this.activity = activity;
    }

    public BigDecimal getPrinted() {
        return printed;
    }

    public void setPrinted(BigDecimal printed) {
        this.printed = printed;
    }

    public BigDecimal getInquiries() {
        return inquiries;
    }

    public void setInquiries(BigDecimal inquiries) {
        this.inquiries = inquiries;
    }
}
