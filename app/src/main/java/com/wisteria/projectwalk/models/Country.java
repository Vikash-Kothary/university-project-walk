package com.wisteria.projectwalk.models;


/**
 *  Represents a Country
 */
public class Country {


    public Country(String countryName) {
        this.countryName = countryName;
    }

    /** The name of the country */
    private String countryName;

    /**
     * @return The name of the country
     */
    public String getCountryName(){
        return countryName;
    }


}


