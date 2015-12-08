package com.wisteria.projectwalk.models;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

/**
 * The manager
 */

public class Manager implements LeaderboardDataSource, Observer {
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

        DataHandler dataHandler = new DataHandler();
        dataHandler.addObserver(this);

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
        // check if entries are  present in hashmap for this category and year
        // if not get the data from the api
        // add it to the hashmap
        ArrayList<Entry> entries;
       // allEntries.put(category.type+currentYear, entries);
        // sort it with comparator

        managerCallback.dataIsReady(category, currentYear);
    }


    public void setManagerCallback(ManagerCallback managerCallback) {
        this.managerCallback = managerCallback;
    }

    DataHandler handler;
    @Override
    public void update(Observable observable, Object data) {
        handler = (DataHandler) observable;

        allEntries = handler.getHashMap();

        Iterator iterator = allEntries.entrySet().iterator();

        while(iterator.hasNext()) {
            Map.Entry pair = (Map.Entry) iterator.next();
            ArrayList<Entry> entries = (ArrayList<Entry>) pair.getValue();

            Collections.sort(entries, compareEntries);
            Collections.reverse(entries);

            for (Entry entry : entries) {
                Log.e(pair.getKey() + "", entry.getPercentage() + " <- val   " + entry.getCountry().getCountryName());
            }

        }

    }

    Comparator<Entry> compareEntries = new Comparator<Entry>(){

        public int compare(Entry task1, Entry task2) {
            return Double.compare(task1.getPercentage(), task2.getPercentage());
        }
    };



}
