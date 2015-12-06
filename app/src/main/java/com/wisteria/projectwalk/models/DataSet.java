package com.wisteria.projectwalk.models;

import java.util.ArrayList;

/**
 * A DataSet represents a set of data for an indicator (i.e Total Population)
 * it consists of a list of valid Country objects (countries with more than 4 Entry),
 * and the name of the DataSet itself.
 */
public class DataSet {

    /** A list of Country objects that have valid Entries for this DataSet. */
    ArrayList<Country> countries = new ArrayList<>();

    /** The indicator value of the set of data being stored. */
    String setName;

    /**
     * @param indicatorName The indicator value.
     */
    public void setName(String indicatorName){
        setName = indicatorName;
    }

    /**
     * @return The name of indicator of this DataSet.
     */
    public String getSetName(){
        return setName;
    }

    /**
     * Adds a Country object to the list of Countries.
     * @param Country A Country object holding at least 4 valid Entry objects specific to this DataSet.
     */
    public void addCountry(Country Country){
        countries.add(Country);
    }

    /**
     * @return A list of all Country objects specific to this DataSet.
     */
    public ArrayList<Country> getCountries(){
        return countries;
    }

    /**
     *  Loops through all Country objects held, returns the specific Country
     *  based on the parameter provided, if not Country is found returns null.
     * @param countryName A name of a country  i.e "France".
     * @return A Country object, if no Country is found returns null.
     */
    public Country getCountry(String countryName){
        for(Country country : countries) {
            if (country.getCountryName().equals(countryName)) {
                return country;
            }
        }
        return null;
    }

}
