package com.wisteria.projectwalk.models;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Set;

public class DataHandler extends Observable {

    HashMap<String, ArrayList<Entry>> hashMap = new HashMap();

    /** All the indicators that will be requested */
    String[] indicators = new String[]{
            "/indicators/AG.LND.FRST.K2?date=2000:2015&format=JSON&per_page=4000",
            "/indicators/EN.ATM.CO2E.KT?date=2000:2015&format=JSON&per_page=4000",
            "/indicators/EG.USE.COMM.FO.ZS?date=2000:2015&format=JSON&per_page=4000"
    };
    Category[] categories = new Category[]{
            Category.ForestArea,
            Category.C02Emissions,
            Category.FossilFuel
    };


    /** Total number of AsyncTasks running in parallel */
    int AsyncCounter = 0;

    /**
     *  Loops through all indicators, creates a separate AsyncTask for each one
     *  Executes the AsyncTask using a Thread Pool (parallel)
     */
    public DataHandler(){

            for(int i = 0; i<indicators.length;i++) {
                new RetrieveData(categories[i]).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, indicators[i]);
            }
    }


    public HashMap getHashMap (){
        return hashMap;
    }

    protected void dataLoaded(){

        setChanged();
        notifyObservers();

    }


    /**
     * Requests data using the provided indicators
     */
    private class RetrieveData  extends AsyncTask<Object,Void,Void> {

        String dataIndicator;
        public RetrieveData(Category category){

            dataIndicator = category.type;

        }

        @Override
        protected Void doInBackground(Object... params) {

            BufferedReader br;
            URL url;
            String line;
            String newURL = "http://api.worldbank.org/countries" + params[0];

            try {

                url = new URL(newURL);
                URLConnection connection = url.openConnection();

                br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                while ((line = br.readLine()) != null) {

                    if (line.contains("message") || line.contains("pages\":0")) {
                        br.close();
                        break;
                    }

                    JSONArray jsonArray = new JSONArray(line);
                    JSONArray insideJSON = jsonArray.getJSONArray(1);

                    for (int x = 0; x < insideJSON.length(); x++) {

                        JSONObject object = insideJSON.getJSONObject(x);
                        String country = insideJSON.getJSONObject(x).getJSONObject("country").getString("value");

                        String year = object.getString("date");

                        String value = object.getString("value");
                        if(!value.equals("null")) {

                            String key = dataIndicator+year;
                            if (!hashMap.containsKey(key)) {
                                hashMap.put(key, new ArrayList<Entry>());

                            }

                            ArrayList<Entry> entries = (ArrayList) hashMap.get(key);
                            entries.add(new Entry((Integer.parseInt(year)),new Country(country),Double.parseDouble(value)));

//                            set.addEntry(new Entry(new Country(country)));
                        }

                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            AsyncCounter++;
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(AsyncCounter == indicators.length) {
                dataLoaded();
            }
        }
    }







}