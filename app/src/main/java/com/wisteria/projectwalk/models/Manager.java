package com.wisteria.projectwalk.models;


public class Manager implements LeaderboardDataSource {
    private static Manager sharedInstance = new Manager();

    public static Manager getInstance() {
        return sharedInstance;
    }

    private int currentYear;
    private Country usersCountry;
    private DataHandler.Category category;

    private Manager() {


    }

    public int getCurrentYear() {
        return currentYear;
    }

    public void setCurrentYear(int currentYear) {
        this.currentYear = currentYear;
    }

    public DataHandler.Category getCategory() {
        return category;
    }

    public void setCategory(DataHandler.Category category) {
        this.category = category;
    }

    public Entry entryForRanking(int ranking) {

        return null;
    }

    public Entry entryForCountry(Country country) {

        return null;
    }
    
}
