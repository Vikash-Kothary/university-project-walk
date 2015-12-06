package com.wisteria.projectwalk;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class DataHandler {

    /** An ArrayList containing DataSets, sets are added post AsynTask execution */
    ArrayList<DataSet> allData = new ArrayList<>();

    /** All the indicators that will be requested */
    String[] indicators = {"/indicators/SP.POP.TOTL?date=2000:2015&format=JSON&per_page=4000","/indicators/NY.GNP.PCAP.CD?date=2000:2015&format=JSON&per_page=4000",
            "/indicators/NY.GNP.ATLS.CD?date=2000:2015&format=JSON&per_page=4000","/indicators/SI.POV.DDAY?date=2000:2015&format=JSON&per_page=4000",
            "/indicators/SI.DST.10TH.10?date=2000:2015&format=JSON&per_page=4000", "/indicators/SI.DST.05TH.20?date=2000:2015&format=JSON&per_page=4000",
            "/indicators/SI.DST.FRST.10?date=2000:2015&format=JSON&per_page=4000", "/indicators/SI.DST.FRST.20?date=2000:2015&format=JSON&per_page=4000",
            "/indicators/SI.DST.02ND.20?date=2000:2015&format=JSON&per_page=4000", "/indicators/SI.DST.03RD.20?date=2000:2015&format=JSON&per_page=4000",
            "/indicators/SI.DST.04TH.20?date=2000:2015&format=JSON&per_page=4000"};

    ProgressDialog progressDialog;

    /** Total number of AsyncTasks running in parallel */
    int AsyncCounter = 0;

    /**
     *  Loops through all indicators, creates a separate AsyncTask for each one
     *  Executes the AsyncTask using a Thread Pool (parallel)
     * @param context the main activity, used to display progressDialog
     */
    public DataHandler(Context context){
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Loading data...");
        progressDialog.show();

        for(String indicator : indicators) {
            new RetrieveData().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,indicator);
        }
    }

    /**
     * Invoked postExecution of AsyncTask
     * Adds generated DataSet to an ArrayList of datasets
     * @param dataSet Contains a list of Country objects holding data based on the indicator used
     */
    protected void addDataSet(DataSet dataSet){
        Log.e("ADDED NEW DATA", dataSet.setName + "   WITH SIZE: " + dataSet.countries.size());
        allData.add(dataSet);
    }

    /**
     * To be removed, used to demo loading of data for a specific country and year
     *
     * @param pais the name of the country
     * @param year the year requested
     * @return a String holding all the data held for country and an specific year
     */
    public String printData(String pais, int year) {


        String dataLoaded = ("Data for " + "<b>"+ pais + "</b>" + " for the year " + year + " : <br />");

        for (DataSet set : allData) {

            Boolean dataForYear = false;

            dataLoaded += ("<b>Indicator - </b>" + set.getSetName() + "<br />");

                Country country = set.getCountry(pais);

            if(country != null) {
                for (Entry entry : country.getEntries()) {

                    if(entry.getYear() == year) {
                        dataLoaded += ("Value - " + entry.getValue() + "<br />");
                        dataForYear = true;
                        break;
                    }

                }
                if(!dataForYear){
                    dataLoaded += ("No data for year: " + year + "<br />");
                }

            }
            else{
                dataLoaded += ("No data for " + pais + "<br />");
            }
        }

        return dataLoaded;

    }

    /**
     * Requests data using the provided indicators
     */
    private class RetrieveData  extends AsyncTask<String,Void,DataSet> {

        @Override
        protected DataSet doInBackground(String... params) {

            BufferedReader br;
            URL url;
            String line;
            String newURL = "http://api.worldbank.org/countries" + params[0];
            DataSet dataSet = new DataSet();

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

                    String indicator = insideJSON.getJSONObject(0).getJSONObject("indicator").getString("value");
                    dataSet.setName(indicator);

                    String countryName = insideJSON.getJSONObject(0).getJSONObject("country").getString("value");

                    ArrayList<Entry> entries = new ArrayList<>();

                    for (int x = 0; x < insideJSON.length(); x++) {

                        JSONObject object = insideJSON.getJSONObject(x);
                        String tempCountry = insideJSON.getJSONObject(x).getJSONObject("country").getString("value");

                        if(countryName.equals(tempCountry)){
                            String value = object.getString("value");
                            if(!value.equals("null")){
                                entries.add(new Entry(Integer.parseInt(object.getString("date")),value.replaceAll("\"","")));
                            }
                        }
                        else{
                            Country country = new Country(countryName,entries);
                            if(country.getSize() > 4) {
                                dataSet.addCountry(country);
                            }
                            //new ArrayList
                            entries = new ArrayList<>();
                            countryName = tempCountry;
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            AsyncCounter++;
            return dataSet;
        }

        @Override
        protected void onPostExecute(DataSet dataSet) {
            super.onPostExecute(dataSet);
            addDataSet(dataSet);
            if(AsyncCounter == indicators.length){
                progressDialog.cancel();
                System.gc();
            }
        }
    }






}