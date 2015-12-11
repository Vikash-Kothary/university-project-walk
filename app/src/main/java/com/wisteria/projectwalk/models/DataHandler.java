package com.wisteria.projectwalk.models;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;
import java.util.Observable;
import java.util.concurrent.Executor;

public class DataHandler extends Observable {

    public HashMap<String,HashMap<Integer,ArrayList<Entry>>> hashMap = new HashMap();
    private Context context;

    public String getIndicator(Category category, int minYear, int maxYear) {
        String[] categoryCodes = new String[]{
                "AG.LND.FRST.K2",
                "EN.ATM.CO2E.KT",
                "EG.USE.COMM.FO.ZS"
        };

        return "/indicators/"+categoryCodes[category.ordinal()]+"?date="+minYear+":"+maxYear+"&format=JSON&per_page=10000";
    }

    /** Total number of AsyncTasks running in parallel */
    int AsyncCounter = 0;
    /**
     *  Loops through all indicators, creates a separate AsyncTask for each one
     *  Executes the AsyncTask using a Thread Pool (parallel)
     */
    public DataHandler(Context context){
        this.context = context;
    }

    public void retrieveNewData(Category category, int year) {

        retrieveNewData(category, year, year, AsyncTask.THREAD_POOL_EXECUTOR);

    }


    public void retrieveNewData(Category category, int minYear, int maxYear, Executor executor) {
        this.category = category;
        new RetrieveData(category, minYear, maxYear, context).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

    }

    public void retrieveNewData(Category category, int minYear, int maxYear) {

        retrieveNewData(category, minYear, maxYear, AsyncTask.THREAD_POOL_EXECUTOR);

    }

    public HashMap getHashMap (){
        return hashMap;
    }

    String data;
    Category category;
    protected void dataLoaded(){
        setChanged();
        notifyObservers();

    }

    /**
     * Requests data using the provided indicators
     */
    private class RetrieveData extends AsyncTask<Object,Void,Void> {

        private String dataIndicator;
        private Category category;
        private Context context;
        private String[] allISOs = Locale.getISOCountries();
        private int minYear;
        private int maxYear;

        public RetrieveData(Category category, int minYear, int maxYear, Context context){
            Log.i("RetrieveData", "Retrieving data for "+ category +", "+minYear+", "+maxYear);
            this.context = context;
            this.dataIndicator = getIndicator(category, minYear, maxYear);
            this.category = category;
            this.minYear = minYear;
            this.maxYear = maxYear;
        }

        @Override
        protected Void doInBackground(Object... params) {


            StringBuilder collectedData = new StringBuilder();
            StringBuilder cacheBuilder = new StringBuilder();

            File cacheFile = new File(context.getCacheDir(), category.type);
            if(cacheFile.exists()) {

                Log.wtf("YES", "DATA FOUND IN CACHE");

                try {
                    BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(cacheFile)));
                    String line;
                    while ((line = input.readLine()) != null) {
                        cacheBuilder.append(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


                String data[] = cacheBuilder.toString().split(",");

                Boolean dataLoaded = false;


                if (dataLoaded) {
                    return null;
                }
            }

            StringBuilder internetBuilder = new StringBuilder();

            for(int y = minYear; y <maxYear+1; y++) {


                String dataCollected = "";

                try {

                    BufferedReader br;
                    URL url;
                    String line;
                    String newURL = "http://api.worldbank.org/countries" + dataIndicator;
                    url = new URL(newURL);
                    URLConnection connection = url.openConnection();

                    br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                    while ((line = br.readLine()) != null) {
                        internetBuilder.append(line);
                    }

                    dataCollected = internetBuilder.toString();

                    HashMap<Integer, ArrayList<Entry>> insideHashMap = new HashMap<>();
                    JSONArray jsonArray = new JSONArray(dataCollected);
                    JSONArray insideJSON = jsonArray.getJSONArray(1);

                    for (int x = 0; x < insideJSON.length(); x++) {

                        JSONObject object = insideJSON.getJSONObject(x);
                        String country = insideJSON.getJSONObject(x).getJSONObject("country").getString("value");
                        String countryISO = insideJSON.getJSONObject(x).getJSONObject("country").getString("id");
                        int date = Integer.parseInt(object.getString("date"));
                        String value = object.getString("value");

                        if (!value.equals("null")) {

                            for (String iso : allISOs) {

                                if (iso.equals(countryISO) && !country.contains(".") && !country.contains(" ")) {

                                    if (!insideHashMap.containsKey(date)) {
                                        insideHashMap.put(date, new ArrayList<Entry>());
                                    }

                                    ArrayList<Entry> entries = (ArrayList) insideHashMap.get(date);
                                    entries.add(new Entry(date, new Country(country), Double.parseDouble(value)));
                                    collectedData.append(date+",");
                                    collectedData.append(country+",");
                                    collectedData.append(value + ",");
                                    break;
                                }
                            }

                        }
                    }

                    for(ArrayList<Entry> lists : insideHashMap.values()){

                        Collections.sort(lists,compareEntries);
                        Collections.reverse(lists);

                        if(lists.get(0).getPercentage() > 100) {
                            for (Entry entry : lists) {
                                entry.setTempPercentage();
                                entry.setPercentage(entry.getTempPercentage() / lists.get(0).getTempPercentage() * 100);
                            }

                        }
                    }

                    hashMap.put(category.type, insideHashMap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            File file = new File(context.getCacheDir(), category.type);
            FileOutputStream outputStream;

            try{
            outputStream = new FileOutputStream(file);
            outputStream.write(collectedData.toString().getBytes());

                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            AsyncCounter++;
            return null;
        }

        Comparator<Entry> compareEntries = new Comparator<Entry>(){
            public int compare(Entry entry1, Entry entry2) {
                return Double.compare(entry1.getPercentage(), entry2.getPercentage());
            }
        };

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dataLoaded();
        }
    }



}