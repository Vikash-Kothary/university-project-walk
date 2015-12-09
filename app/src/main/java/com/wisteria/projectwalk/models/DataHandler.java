package com.wisteria.projectwalk.models;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.provider.ContactsContract;
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Observable;
import java.util.Set;

public class DataHandler extends Observable {

    HashMap<String, ArrayList<Entry>> hashMap = new HashMap();
    private Context context;

    public String getIndicator(Category category, int year) {
        String[] categoryCodes = new String[]{
                "AG.LND.FRST.K2",
                "EN.ATM.CO2E.KT",
                "EG.USE.COMM.FO.ZS"
        };

        return "/indicators/"+categoryCodes[category.ordinal()]+"?date="+year+"&format=JSON&per_page=2000";

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

        new RetrieveData(category, year, context).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

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

        String dataIndicator;
        Category category;
        Context context;
        String[] allISOs = Locale.getISOCountries();
        int year;

        public RetrieveData(Category category, int year, Context context){
            this.context = context;
            this.dataIndicator = getIndicator(category, year);
            this.category = category;
            this.year = year;
        }

        @Override
        protected Void doInBackground(Object... params) {

            String dataCollected = "";

                BufferedReader input = null;
                File file = null;
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
                                infile = new File(context.getCacheDir(), category.type+year);
                                outputStream = new FileOutputStream(infile);
                                outputStream.write(line.getBytes());
                                outputStream.close();
                            }

                    }

                Log.wtf("DataHandler",dataCollected);


                JSONArray jsonArray = new JSONArray(dataCollected);
                JSONArray insideJSON = jsonArray.getJSONArray(1);

                for (int x = 0; x < insideJSON.length(); x++) {

                    JSONObject object = insideJSON.getJSONObject(x);
                    String country = insideJSON.getJSONObject(x).getJSONObject("country").getString("value");
                    String countryISO = insideJSON.getJSONObject(x).getJSONObject("country").getString("id");
                    String year = object.getString("date");
                    String value = object.getString("value");
                    if (!value.equals("null")) {
                        for (String iso : allISOs) {

                            if (iso.equals(countryISO)) {
                                String key = category.type + year;
                                if (!hashMap.containsKey(key)) {
                                    hashMap.put(key, new ArrayList<Entry>());
                                }
                                ArrayList<Entry> entries = (ArrayList) hashMap.get(key);
                                entries.add(new Entry((Integer.parseInt(year)), new Country(country), Double.parseDouble(value)));
                                break;
                            }
                        }
                    }
                }
            }

            catch (Exception e){

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