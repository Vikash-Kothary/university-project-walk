package com.wisteria.projectwalk.models;

import java.util.ArrayList;

/**
 *  Represents a Country
 *  A Country will have a name, and a list of Entry objects
 */
public class Country {


    private int ranking;

    /** The name of the country */
    private String countryName;

    /** An ArrayList of Entry Objects */
    private ArrayList<Entry> countryData;

    /**
     * @param name The name of the country
     * @param entries A List of Entry objects, each one holding a year and its value.
     */
    public Country(String name, ArrayList<Entry> entries) {
        countryName = name;
        countryData = entries;
    }

    /**
     * @return an ArrayList containing all Entry objects for this country
     */
    public ArrayList<Entry> getEntries(){
        return countryData;
    }

    /**
     * @return The name of the country
     */
    public String getCountryName(){
        return countryName;
    }

    /**
     * @return The amount of Entry objects held
     */
    public int getSize(){
        return countryData.size();
    }


}


