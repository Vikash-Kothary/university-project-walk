package com.wisteria.projectwalk.models;


/**
 * An Entry consists of a int year and a Number value
 * Used to represent a record of data at point of time for a country
 */
public class Entry {

    /** The year of this Entry*/
    private int year;

    /**
     * The value of this Entry.
     * Using Generic Number because the value could be an int or a float.
     */
    private Number value;

    private int ranking;
    private Country country;
    private double percentage;

    private double diffPercentage;

    public Entry(int ranking, int year, Country country, double percentage, double diffPercentage) {
        this.ranking = ranking;
        this.year = year;
        this.country = country;
        this.percentage = percentage;
        this.diffPercentage = diffPercentage;
    }

    /**
     * @param year The year of this Entry.
     * @param value The value, as String, to be converted.
     */
    public Entry(int year , String value){
        this.year = year;

        if(value.contains(".")){
            this.value = Float.parseFloat(value);
        }
         else{
            this.value = Long.parseLong(value);
        }
    }

    /**
     * @return The value as Number, use instanceof to find if its int or float.
     */
    public Number getValue(){
        return value;
    }

    /**
     * @return The year of this Entry.
     */
    public int getYear(){
        return year;
    }



    public Country getCountry() {
        return country;
    }



    public double getDiffPercentage() {
        return diffPercentage;
    }

    public double getPercentage() {
        return percentage;
    }

}
