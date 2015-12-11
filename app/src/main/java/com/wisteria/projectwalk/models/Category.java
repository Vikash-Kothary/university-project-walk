package com.wisteria.projectwalk.models;


/**
 * An Enum representing a category (indicator) of data.
 */
    public enum Category{

        ForestArea("Forest"), C02Emissions("C02"), FossilFuel("Fuel");

        String type;

        Category (String type){
            this.type = type;
        }

    }


