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
import java.io.FileReader;
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

    /**  A HashMap storing HashMaps    */
    public HashMap<String,HashMap<Integer,ArrayList<Entry>>> hashMap = new HashMap();

    /**  The Main Activity passed from MainActivity */
    private Context context;

    /**
     *
     * @param category The category (indicator) to be loaded
     * @param minYear The lowest year data will be pulled from
     * @param maxYear The maximum year data will be pulled from
     * @return A String comprised of the category, minimum and maximum year
     */
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

    /**
     * Creates a new AsyncTask using a thread pool that gets the data for a category and year
     * @param category The category (indicator) of the data
     * @param year The year of the data to be loaded
     */
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

    /**
     * @return A HashMap containing all the collected data.
     */
    public HashMap getHashMap (){
        return hashMap;
    }

    String data;
    Category category;

    /**
     * Notifies observers of a statechange
     */
    protected void dataLoaded(){
        setChanged();
        notifyObservers();

    }

    public void retriveFromCache() {

    }

    /**
     * Requests data using the provided indicators
     */
    private class RetrieveData extends AsyncTask<Object,Void,Void> {


        private String dataIndicator;
        private Category category;
        private Context context;

        /** An array containing all 2-character country codes*/
        private String[] allISOs = Locale.getISOCountries();
        private int minYear;
        private int maxYear;

        public RetrieveData(Category category, int minYear, int maxYear, Context context){

            this.context = context;
            this.dataIndicator = getIndicator(category, minYear, maxYear);
            this.category = category;
            this.minYear = minYear;
            this.maxYear = maxYear;
        }

        @Override
        protected Void doInBackground(Object... params) {


            int minFound = 10000;
            int maxFound = 0;

            String line = null;
            try {

                Category[] categories = new Category[] {Category.C02Emissions, Category.ForestArea, Category.ForestArea};
                for (Category lookupCategory :
                        categories) {
                    File cacheFile = new File(context.getCacheDir(), lookupCategory.type);
                    if(cacheFile.exists()) {
                        FileReader fileReader = new FileReader(cacheFile);

                        BufferedReader bufferedReader = new BufferedReader(fileReader);



                        HashMap<Integer, ArrayList<Entry>> insideHashMap = new HashMap<>();
                        while ((line = bufferedReader.readLine()) != null) {
                            System.out.println(line);

                            String[] arr = line.split(",");

                            Entry entry = new Entry(Integer.parseInt(arr[0]), new Country(arr[1]), Double.parseDouble(arr[2]));


                            ArrayList<Entry> entries = insideHashMap.get(entry.getYear());
                            if (entries != null) {
                                entries.add(entry);
                            } else {
                                ArrayList<Entry> newList = new ArrayList<>();
                                newList.add(entry);
                                insideHashMap.put(entry.getYear(), newList);
                            }


                            if (lookupCategory == category) {
                                minFound = Math.min(entry.getYear(), minFound);
                                maxFound = Math.max(entry.getYear(), maxFound);

                            }

                            hashMap.put(category.type, insideHashMap);

                        }

                        bufferedReader.close();

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
                    }
                }

            } catch (Exception e) {

            }

            if (minFound <= minYear && maxFound >= maxYear)
                return null;

            StringBuilder collectedData = new StringBuilder();
            StringBuilder cacheBuilder = new StringBuilder();

            File cacheFile = new File(context.getCacheDir(), category.type);
            if(cacheFile.exists()) {


                try {
                    BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(cacheFile)));

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

                String dataCollected = "";

                try {

                    BufferedReader br;
                    URL url;
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
                                    collectedData.append(value + "\n");
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

        /**
         * Sorts ArrayLists of Entry objects.
         */
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