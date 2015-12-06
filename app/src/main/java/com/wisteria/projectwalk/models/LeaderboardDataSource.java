package com.wisteria.projectwalk.models;

/**
 * Created by martinkubat on 06/12/15.
 */
public interface LeaderboardDataSource {
    public Entry entryForCountry(Country country);
    public Entry entryForRanking(int ranking);
}
