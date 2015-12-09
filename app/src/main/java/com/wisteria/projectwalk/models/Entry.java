package com.wisteria.projectwalk.models;


import android.widget.MultiAutoCompleteTextView;

import java.util.ArrayList;

/**
 * An Entry consists of a int year and a Number value
 * Used to represent a record of data at point of time for a country
 */
public class Entry {

    private int year;
    private int ranking;
    private Country country;
    private double percentage;
    private double diffPercentage;

    public Entry(int year, Country country, double percentage) {
        this.year = year;
        this.country = country;
        this.percentage = percentage;
        this.diffPercentage = diffPercentage;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public double getDiffPercentage() {
        return diffPercentage;
    }

    public void setDiffPercentage(double diffPercentage) {
        this.diffPercentage = diffPercentage;
    }

    public Country getCountry(){
        return country;
    }





}
