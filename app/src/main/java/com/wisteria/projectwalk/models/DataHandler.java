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
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Observable;
import java.util.concurrent.Executor;

public class DataHandler extends Observable {

    HashMap<String,HashMap<Integer,ArrayList<Entry>>> hashMap = new HashMap();
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

        new RetrieveData(category, minYear, maxYear, context).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

    }

    public void retrieveNewData(Category category, int minYear, int maxYear) {

        retrieveNewData(category, minYear, maxYear, AsyncTask.THREAD_POOL_EXECUTOR);

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
    private class RetrieveData extends AsyncTask<Object,Void,Void> {

        private String dataIndicator;
        private Category category;
        private Context context;
        private String[] allISOs = Locale.getISOCountries();
        private int minYear;
        private int maxYear;

        public RetrieveData(Category category, int minYear, int maxYear, Context context){
            Log.i("RetrieveData", "Retrieving data for "+category+", "+minYear+", "+maxYear);
            this.context = context;
            this.dataIndicator = getIndicator(category, minYear, maxYear);
            this.category = category;
            this.minYear = minYear;
            this.maxYear = maxYear;
        }

        @Override
        protected Void doInBackground(Object... params) {

            String dataCollected = "";

            BufferedReader input;
            File file;

                try {

                    file = new File(context.getCacheDir(), dataIndicator);

                    if(file.exists()) {

                        input = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                        String line;
                        StringBuilder cacheBuilder = new StringBuilder();
                        while ((line = input.readLine()) != null) {
                            cacheBuilder.append(line);
                        }

                        dataCollected = cacheBuilder.toString();

                    } else {

                            BufferedReader br;
                            URL url;
                            String line;
                            String newURL = "http://api.worldbank.org/countries" + dataIndicator;

                            url = new URL(newURL);
                            URLConnection connection = url.openConnection();

                            br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                            StringBuilder internetBuilder = new StringBuilder();
                            while ((line = br.readLine()) != null) {
                                internetBuilder.append(line);
                                dataCollected = internetBuilder.toString();
                                File infile;
                                FileOutputStream outputStream;
                                int year = 2004;
                                infile = new File(context.getCacheDir(), category.type+year);
                                outputStream = new FileOutputStream(infile);
                                outputStream.write(line.getBytes());
                                outputStream.close();
                            }

                    }

                    HashMap<Integer,ArrayList<Entry>> insideHashMap = new HashMap<>();

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

                            if (iso.equals(countryISO)) {

                                if(!insideHashMap.containsKey(date)){
                                    insideHashMap.put(date, new ArrayList<Entry>());
                                }

                                ArrayList<Entry> entries = (ArrayList) insideHashMap.get(date);
                                entries.add(new Entry(date, new Country(country), Double.parseDouble(value)));
                                break;
                            }
                        }
                    }
                }
                    hashMap.put(category.type, insideHashMap);
            }

            catch (Exception e){
                e.printStackTrace();
            }

            AsyncCounter++;
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dataLoaded();
        }
    }

}