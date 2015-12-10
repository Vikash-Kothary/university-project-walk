package com.wisteria.projectwalk.models;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

/**
 * The manager
 */

public class Manager implements LeaderboardDataSource, Observer, YearSliderDelegate, YearSliderDataSource {
    final private String TAG = "Manager";

    private static Manager sharedInstance = new Manager();
    HashMap<String,HashMap<Integer,ArrayList<Entry>>> allEntries;

    private ManagerCallback managerCallback;

    public static Manager getInstance() {
        return sharedInstance;
    }

    private int currentYear = 2004;
    private int minYear = 1960;
    private int maxYear = 2015;
    private Country usersCountry;

    private DataHandler dataHandler;

    private Category category = Category.ForestArea;

    Context activity;
    public void setContext(Context context){

        activity = context;
    }

    public void initManager(){
        dataHandler = new DataHandler(activity);
        dataHandler.addObserver(this);
        dataHandler.retrieveNewData(category, currentYear, currentYear, AsyncTask.SERIAL_EXECUTOR);

        dataHandler.retrieveNewData(category, minYear, maxYear, AsyncTask.SERIAL_EXECUTOR);
        Category[] categories = {Category.C02Emissions, Category.ForestArea, Category.FossilFuel};
        for (int i = 0; i < 3; i++) {
            if (categories[i] != category)
                dataHandler.retrieveNewData(category, minYear, maxYear, AsyncTask.SERIAL_EXECUTOR);
        }
    }

    public int getCurrentYear() {
        return currentYear;
    }

    public void setCurrentYear(int currentYear) {
        Log.i(TAG, "Setting current year to "+currentYear);
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

        ArrayList<Entry> entries = (ArrayList) allEntries.get(category.type).get(currentYear);

//        if (entries != null) {
            return entries.get(ranking - 1);
//        }
//        return null;

    }

    public ArrayList<Entry> getEntries() {
        return allEntries.get(category.type + currentYear);
    }

    /**
     * Returns the entry for a country
     * @param country (Country)
     * @return the entry
     */
    public Entry entryForCountry(Country country) {
        ArrayList<Entry> entries = allEntries.get(category.type).get(currentYear);

        if (entries == null)
            return null;

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
        if (allEntries.get(category.type).get(currentYear) != null) {
            managerCallback.dataIsReady(category, currentYear);
            return;
        }

        // TODO Prioritize this somehow
        dataHandler.retrieveNewData(category, currentYear);
    }

    public void setManagerCallback(ManagerCallback managerCallback) {
        this.managerCallback = managerCallback;
    }

    DataHandler handler;
    @Override
    public void update(Observable observable, Object data) {
        Log.i("Manager", "update");

        handler = (DataHandler) observable;

        allEntries = (HashMap) handler.getHashMap().clone();

        ArrayList<Entry> entries = (ArrayList) allEntries.get(category.type).get(currentYear);

        Collections.sort(entries,compareEntries);
        Collections.reverse(entries);

        if(entries.get(0).getPercentage() > 100) {

            for (Entry entry : entries) {
                entry.setTempPercentage();
                entry.setPercentage(entry.getTempPercentage() / entries.get(0).getTempPercentage() * 100);
            }

        }

        Log.i("Total number ", "" + allEntries.size());

        managerCallback.dataIsReady(category, currentYear);

    }

    Comparator<Entry> compareEntries = new Comparator<Entry>(){

        public int compare(Entry entry1, Entry entry2) {
            return Double.compare(entry1.getPercentage(), entry2.getPercentage());
        }
    };

    public ArrayList<Integer> getAvailableYears() {

        List<Integer>[] years = new List[]{
                Arrays.asList(1990, 1991, 1992, 1993, 1994, 1995, 1996, 1997, 1998, 1999, 2000, 2001, 2002, 2003, 2004, 2005, 2006, 2007, 2008, 2009, 2010, 2011, 2012),
                Arrays.asList(1990, 1991, 1992, 1993, 1994, 1995, 1996, 1997, 1998, 1999, 2000, 2001, 2002, 2003, 2004, 2005, 2006, 2007, 2008, 2009, 2010, 2011, 2012),
                Arrays.asList(1990, 1991, 1992, 1993, 1994, 1995, 1996, 1997, 1998, 1999, 2000, 2001, 2002, 2003, 2004, 2005, 2006, 2007, 2008, 2009, 2010, 2011, 2012)
        };

        return new ArrayList<>(years[category.ordinal()]);
    }

    public Category getCategory() {
        return category;
    }

}
