package com.wisteria.projectwalk.models;

import java.util.ArrayList;

/**
 * The datasource, from which the leaderboard gets it's data
 *
 * Created by martinkubat on 06/12/15.
 */
public interface LeaderboardDataSource {
    public Entry entryForCountry(Country country);
    public ArrayList<Entry> getEntries();
}
