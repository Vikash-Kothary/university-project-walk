package com.wisteria.projectwalk.models;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
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

    public void setContext(Context context) {

        this.context = context;
    }

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

    public int getCurrentYear() {
        return currentYear;
    }

    public void setCurrentYear(int currentYear) {
        Log.i(TAG, "Setting current year to " + currentYear);
        this.currentYear = currentYear;
        populateEntries(category, currentYear);

    }

    public void setCategory(Category category) {
        this.category = category;
        populateEntries(category, currentYear);
    }

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
     *
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
     *
     * @param category    {Category}
     * @param currentYear {int}
     */
    public void populateEntries(Category category, int currentYear) {
        // check if entries are  present in hashmap for this category and year
        if (getEntries(category, currentYear) != null) {
            managerCallback.dataIsReady(category, currentYear);
            return;
        }

        if (category == Category.Average) {
            calculateAverages();
        } else {
            // TODO Prioritize this somehow
            dataHandler.retrieveNewData(category, currentYear);
        }

    }

    public void setManagerCallback(ManagerCallback managerCallback) {
        this.managerCallback = managerCallback;
    }

    @Override
    public void update(Observable observable, Object data) {
        Log.i("Manager", "update");

        managerCallback.dataIsReady(category, currentYear);

    }

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

    public Country getUsersCountry() {
        return usersCountry;
    }

    public void setUsersCountry(Country usersCountry) {
        this.usersCountry = usersCountry;
    }

    public void calculateAverages() {
        ArrayList<HashMap<Integer, ArrayList<Entry>>> hashMaps = new ArrayList<HashMap<Integer, ArrayList<Entry>>>();


        hashMaps.add(dataHandler.getHashMap().get(Category.ForestArea.type));
        hashMaps.add(dataHandler.getHashMap().get(Category.C02Emissions.type));
        hashMaps.add(dataHandler.getHashMap().get(Category.FossilFuel.type));

        Log.i(TAG, "Datahashmap: " + dataHandler.getHashMap());
        Log.i(TAG, "HashMaps: "+hashMaps);

        HashMap<Country, ArrayList<Entry>> entriesForCountry = new HashMap<>();


        for (int i = 0; i < hashMaps.size(); i++) {

            if (hashMaps.get(i) == null)
                continue;

            Collection<ArrayList<Entry>> allEntries = hashMaps.get(i).values();
            for (ArrayList<Entry> entries :
                    allEntries) {
                for (Entry entry :
                        entries) {
                    ArrayList<Entry> a = entriesForCountry.get(entry.getCountry());
                    if (a != null) {
                        a.add(entry);
                    } else {
                        ArrayList<Entry> b = new ArrayList<>();
                        b.add(entry);
                        entriesForCountry.put(entry.getCountry(), b);
                    }
                }
            }
        }


        Log.i(TAG, "entriesForCountry: "+entriesForCountry);


        HashMap<Integer, ArrayList<Entry>> averageEntries = new HashMap<>();

        for (Map.Entry<Country, ArrayList<Entry>> entriesMap :
                entriesForCountry.entrySet()) {
            Country country = entriesMap.getKey();
            // all the entries for this country
            ArrayList<Entry> entries = entriesMap.getValue();


            Log.i(TAG, "random entries: "+entries);

            // Create a hashmap to categorize by year
            HashMap<Integer, ArrayList<Entry>> entriesByYear = new HashMap<>();

            // loop through all the entries
            for (Entry entry:
                 entries) {

                // Gets a reference for the arraylist at a year
                ArrayList<Entry> entriesForYear = entriesByYear.get(entry.getYear());
                if (entriesForYear != null) {
                    entriesForYear.add(entry);
                } else {
                    ArrayList<Entry> b = new ArrayList<>();
                    b.add(entry);
                    entriesByYear.put(entry.getYear(), b);
                }
            }


            Log.i(TAG, "entriesByYear: "+entriesByYear);

            for (Map.Entry<Integer, ArrayList<Entry>> entrySet : entriesByYear.entrySet()) {

                int year = entrySet.getKey();
                ArrayList<Entry> entriesForThisSpecificYear = entrySet.getValue();
                double value = 0;
                int n = 0;


                Log.i(TAG, "entriesForThisSpecificYear"+entriesForThisSpecificYear);

                for (Entry entry: entriesForThisSpecificYear) {
                    value += entry.getPercentage();
                    n++;
                }

                if (n != 0) {
                    double average = value / n;

                    Entry entry = new Entry(year, country, average);

                    Log.i(TAG, "New fresh Entry: "+entry);

                    ArrayList<Entry> averageEntriesAlreadyThere = averageEntries.get(year);
                    if (averageEntriesAlreadyThere != null) {
                        averageEntriesAlreadyThere.add(entry);
                    } else {
                        ArrayList<Entry> b = new ArrayList<>();
                        b.add(entry);
                        averageEntries.put(year, b);
                    }

                    ArrayList<Entry> arrayList = averageEntries.get(year);
                    Collections.sort(arrayList, compareEntries);
                    Collections.reverse(arrayList);
                }

            }
        }

        // Todo make new averages for averages

        Log.i(TAG, "Average entries should be "+averageEntries);



        dataHandler.getHashMap().put(Category.Average.type, averageEntries);



    }

    Comparator<Entry> compareEntries = new Comparator<Entry>(){
        public int compare(Entry entry1, Entry entry2) {
            return Double.compare(entry1.getPercentage(), entry2.getPercentage());
        }
    };

}
