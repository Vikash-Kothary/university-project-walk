package com.wisteria.projectwalk.models;

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
    }

    public Entry entryForRanking(int ranking) {
        ArrayList<Entry> entries = allEntries.get(category.type + currentYear);
        return (Entry) entries.get(ranking - 1);

    }

    public Entry entryForCountry(Country country) {
        ArrayList<Entry> entries = allEntries.get(category.type + currentYear);

        for (Entry entry: entries) {
            if (entry.getCountry().equals(country))
                return entry;
        }

        return null;
    }

    public void setCategory(Category category) {
        this.category = category;
    }


}
