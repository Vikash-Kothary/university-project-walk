package com.wisteria.projectwalk.models;

import android.content.Context;
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

public class Manager implements LeaderboardDataSource, Observer, YearSliderDelegate, YearSliderDataSource {
    private static Manager sharedInstance = new Manager();
    private HashMap<String, ArrayList<Entry>> allEntries;

    private ManagerCallback managerCallback;

    public static Manager getInstance() {
        return sharedInstance;
    }

    private int currentYear = 2004;
    private int minYear = 1980;
    private int maxYear = 2015;
    private Country usersCountry;

    private DataHandler dataHandler;

    private Category category = Category.FossilFuel;


    Context activity;
    public void setContext(Context context){

        activity = context;
    }

    public void initManager(){
        DataHandler dataHandler = new DataHandler(activity, minYear, maxYear);
        dataHandler.addObserver(this);
        dataHandler.retrieveNewData(category, currentYear);
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
        Log.i("Manager", "Getting entries for "+category.type + currentYear);

        ArrayList<Entry> entries = allEntries.get(category.type + currentYear);

//        if (entries != null) {
            return entries.get(ranking - 1);
//        }
//        return null;

    }

    /**
     * Returns the entry for a country
     * @param country (Country)
     * @return the entry
     */
    public Entry entryForCountry(Country country) {
        ArrayList<Entry> entries = allEntries.get(category.type + currentYear);

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
        if (allEntries.containsKey(category.type + currentYear)) {
            return;
        }

        dataHandler.retrieveNewData(category, currentYear);
        // if not get the data from the api
        // add it to the hashmap
        ArrayList<Entry> entries;
       // allEntries.put(category.type+currentYear, entries);
        // sort it with comparator



    }


    public void setManagerCallback(ManagerCallback managerCallback) {
        this.managerCallback = managerCallback;
    }

    DataHandler handler;
    @Override
    public void update(Observable observable, Object data) {
        handler = (DataHandler) observable;

        allEntries = (HashMap) handler.getHashMap().clone();

        Iterator iterator = allEntries.entrySet().iterator();

        Log.i("Total number ", "" + allEntries.size());

        while(iterator.hasNext()) {
            Map.Entry pair = (Map.Entry) iterator.next();
            ArrayList<Entry> entries = (ArrayList<Entry>) pair.getValue();

            Collections.sort(entries, compareEntries);
            Collections.reverse(entries);

            Log.i(pair.getKey() + "", "" + entries.size());

            if(entries.get(0).getPercentage() > 100){

                for(Entry entry : entries){

                    entry.setTempPercentage();
                    entry.setPercentage(entry.getTempPercentage() / entries.get(0).getTempPercentage() * 100);

                }

            }


        }

        managerCallback.dataIsReady(category, currentYear);

    }

    Comparator<Entry> compareEntries = new Comparator<Entry>(){

        public int compare(Entry entry1, Entry entry2) {
            return Double.compare(entry1.getPercentage(), entry2.getPercentage());
        }
    };

    public ArrayList<Integer> getAvailableYears() {
        ArrayList<Integer> arrayList = new ArrayList<>();

        for (int i = minYear; i <= maxYear; i++) {
            if (allEntries.get(category.type + i) != null)
                arrayList.add(i);
        }

        System.out.println("Available years: "+ arrayList.toString());

        return arrayList;
    }



    public Category getCategory() {
        return category;
    }
}
