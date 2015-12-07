package com.wisteria.projectwalk.models;

import android.os.Handler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/**
 * The manager
 */


public class Manager implements LeaderboardDataSource {
    private static Manager sharedInstance = new Manager();
    private HashMap<String, ArrayList<Entry>> allEntries;

    private ManagerCallback managerCallback;

    public static Manager getInstance() {
        return sharedInstance;
    }

    private int currentYear;
    private Country usersCountry;
    private Category category;

    private Manager() {


    }




    public int getCurrentYear() {
        return currentYear;
    }

    public void setCurrentYear(int currentYear) {
        this.currentYear = currentYear;
        populateEntries(category, currentYear);

    }

    public void setCategory(Category category) {
        this.category = category;
        populateEntries(category, currentYear);
    }

    /**
     * Returns the entry at a ranking
     * @param ranking (int)
     * @return the entry
     */
    public Entry entryForRanking(int ranking) {
        ArrayList<Entry> entries = allEntries.get(category.type + currentYear);


        return (Entry) entries.get(ranking - 1);

    }

    /**
     * Returns the entry for a country
     * @param country (Country)
     * @return the entry
     */
    public Entry entryForCountry(Country country) {
        ArrayList<Entry> entries = allEntries.get(category.type + currentYear);


        for (Entry entry: entries) {
            if (entry.getCountry().equals(country))
                return entry;
        }

        return null;
    }

    /**
     * Gathers all the entries for a particular categore and year
     * @param category {Category}
     * @param currentYear {int}
     */
    public void populateEntries(Category category, int currentYear) {
        // get data
        ArrayList<Entry> entries =
        allEntries.put(category.type+currentYear, entries);
        // sort

        managerCallback.dataIsReady(category, currentYear);
    }



    public void setManagerCallback(ManagerCallback managerCallback) {
        this.managerCallback = managerCallback;
    }



}
