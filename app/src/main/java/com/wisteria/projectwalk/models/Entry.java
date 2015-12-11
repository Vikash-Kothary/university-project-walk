package com.wisteria.projectwalk.models;


import android.widget.MultiAutoCompleteTextView;

import java.util.ArrayList;

/**
 * An Entry consists of a int year and a Number value
 * Used to represent a record of data at point of time for a country
 */
public class Entry {

    public int getYear() {
        return year;
    }

    private int year;
    private Country country;
    private double percentage;

    /**
     * @return A temporary value representing the data for that particular date and country
     */
    public double getTempPercentage() {
        return tempPercentage;
    }

    /** A value used to hold a temporary Double value */
    private double tempPercentage;

    public void setTempPercentage(){
        tempPercentage = percentage;
    }


    public Entry(int year, Country country, double percentage) {
        this.year = year;
        this.country = country;
        this.percentage = percentage;
    }

    /**
     * Sets the country for this Entry
     * @param country The Country Object representing this country
     */
    public void setCountry(Country country) {
        this.country = country;
    }

    /**
     * @return the value for this entry
     */
    public double getPercentage() {
        return percentage;
    }

    /**
     * @param percentage sets the percentage
     */
    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }


    /**
     * @return Gets the Country of this Entry
     */
    public Country getCountry(){
        return country;
    }





}
