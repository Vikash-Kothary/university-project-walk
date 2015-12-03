package com.teamwisteria.appname.models;

/**
 * Created by martinkubat on 03/12/15.
 */
public interface LeaderboardDataSource {
    public Country countryForRanking(int ranking, Category basedOn);
}
