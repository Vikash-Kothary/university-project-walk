package com.wisteria.projectwalk.models;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

/**
 * The manager,this singleton class helps establish a connection between the front and backend.
 */
public class Manager implements LeaderboardDataSource, Observer, YearSliderDelegate, YearSliderDataSource {

    final private String TAG = "Manager";
    private static Manager sharedInstance = new Manager();
    private ManagerCallback managerCallback;

    public static Manager getInstance() {
        return sharedInstance;
    }

    private int currentYear = 2004;
    private int minYear = 1960;
    private int maxYear = 2015;
    private Category category = Category.ForestArea;
    private Country usersCountry;

    private DataHandler dataHandler;
    private Context context;

    /**
     *
     * @param context The Activity from the MainActivity
     */
    public void setContext(Context context) {

        this.context = context;
    }

    /**
     * Initializes the backend, begins retriving data, sets this as an observer to an instance of DataHandler
     */
    public void initManager() {
        dataHandler = new DataHandler(context);
        dataHandler.addObserver(this);
        dataHandler.retrieveNewData(category, currentYear, currentYear, AsyncTask.SERIAL_EXECUTOR);

        dataHandler.retrieveNewData(category, minYear, maxYear, AsyncTask.SERIAL_EXECUTOR);
        Category[] categories = {Category.C02Emissions, Category.ForestArea, Category.FossilFuel};
        for (int i = 0; i < 3; i++) {
            if (categories[i] != category)
                dataHandler.retrieveNewData(category, minYear, maxYear, AsyncTask.SERIAL_EXECUTOR);
        }
    }

    /**
     * Sets the current year selected by the user based on the position of the slider
     * @param currentYear The current year viewed by the user
     */
    public void setCurrentYear(int currentYear) {
        Log.i(TAG, "Setting current year to " + currentYear);
        this.currentYear = currentYear;
        populateEntries(category, currentYear);

    }

    /**
     *  Sets a category to be used when retriving sets of data.
     * @param category The Category (type of data) to be viewed by the user
     */
    public void setCategory(Category category) {
        this.category = category;
        populateEntries(category, currentYear);
    }

    /**
     * Gets an ArrayList of Entry objects based on the Category and year passed
     * @param category The category of the data
     * @param year The year to be retrieved
     * @return An ArrayList of Entry objects
     */
    public ArrayList<Entry> getEntries(Category category, int year) {
        HashMap<String, HashMap<Integer, ArrayList<Entry>>> categoryHashMap = (HashMap<String, HashMap<Integer, ArrayList<Entry>>>) dataHandler.getHashMap();
        if (categoryHashMap == null)
            return null;

        HashMap<Integer, ArrayList<Entry>> yearHashMap = categoryHashMap.get(category.type);
        if (yearHashMap == null)
            return null;

        return yearHashMap.get(year);
    }


    public ArrayList<Entry> getEntries() {
        return getEntries(category, currentYear);
    }


    /**
     *  Gets the colour of the bar chart based on the current Category
     * @return An in for the Color of the bar chart
     */
    public int colorForBar() {
        switch (category) {
            case ForestArea:
                return Color.parseColor("#50E399");
            case C02Emissions:
                return Color.parseColor("#CFCFCF");
            case FossilFuel:
                return Color.parseColor("#FFB446");
        }
        return Color.BLACK;
    }

    /**
     * Returns the entry for the user's country
     * @return the entry
     */
    public Entry entryForUsersCountry() {
        ArrayList<Entry> entries = getEntries();

        if (entries == null)
            return null;

        for (Entry entry : entries) {
            if (entry.getCountry().equals(usersCountry))
                return entry;
        }

        return null;
    }

    /**
     * Gathers all the entries for a particular categore and year
     * @param category    {Category}
     * @param currentYear {int}
     */
    public void populateEntries(Category category, int currentYear) {
        // check if entries are  present in hashmap for this category and year
        if (getEntries(category, currentYear) != null) {
            managerCallback.dataIsReady(category, currentYear);
            return;
        }
        // TODO Prioritize this somehow
        dataHandler.retrieveNewData(category, currentYear);
    }

    public void setManagerCallback(ManagerCallback managerCallback) {
        this.managerCallback = managerCallback;
    }

    @Override
    public void update(Observable observable, Object data) {
        Log.i("Manager", "update");

        managerCallback.dataIsReady(category, currentYear);

    }

    /**
     *  Gets an ArrayList with all avaliable years
     * @return An ArrayList containing all possible years of each of the 3 Indicators used
     */
    public ArrayList<Integer> getAvailableYears() {

        List<Integer>[] years = new List[]{
                Arrays.asList(1990, 1991, 1992, 1993, 1994, 1995, 1996, 1997, 1998, 1999, 2000, 2001, 2002, 2003, 2004, 2005, 2006, 2007, 2008, 2009, 2010, 2011, 2012),
                Arrays.asList(1990, 1991, 1992, 1993, 1994, 1995, 1996, 1997, 1998, 1999, 2000, 2001, 2002, 2003, 2004, 2005, 2006, 2007, 2008, 2009, 2010, 2011, 2012),
                Arrays.asList(1990, 1991, 1992, 1993, 1994, 1995, 1996, 1997, 1998, 1999, 2000, 2001, 2002, 2003, 2004, 2005, 2006, 2007, 2008, 2009, 2010, 2011, 2012)
        };

        ArrayList<Integer> yearsList = new ArrayList<>(years[category.ordinal()]);
        Collections.sort(yearsList);

        return yearsList;
    }

    /**
     * @return The Country of the user
     */
    public Country getUsersCountry() {
        return usersCountry;
    }

    /**
     * Sets the Country of the user
     * @param usersCountry The Country
     */
    public void setUsersCountry(Country usersCountry) {
        this.usersCountry = usersCountry;
    }


}
