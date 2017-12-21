/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.kalyan.didyoufeel;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Displays the perceived strength of a single earthquake event based on responses from people who
 * felt the earthquake.
 */
public class MainActivity extends AppCompatActivity implements
        SwipeRefreshLayout.OnRefreshListener,LoaderManager.LoaderCallbacks<ArrayList<earthquake>>{

    private ListView list1;
    private String[] planet;
    private String[][] planets = {{"time","time-asc","magnitude","magnitude-asc"},{"5","10","15","20"},{"1","3","5","7"}};
    MyAlert myAlert;
    private String orderby="time",limit="5",minmagnitude="1";

    /** URL for earthquake data from the USGS dataset */
    private  String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby="+orderby+"&limit="+limit+
                    "&minmagnitude="+minmagnitude;

    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout linear;

    private final int LODER_REF = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earthquake);

        // Perform the HTTP request for earthquake data and process the response

        // Update the information displayed to the user.
        linear  = (LinearLayout) findViewById(R.id.linear);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(this);

        list1 = (ListView) findViewById(R.id.list1);
        //Button button= (Button) findViewById(R.id.button);
        myAlert = new MyAlert();
        planet =  new String[]{"orderby","limit","minmagnitude"};
        ArrayAdapter adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1,planet);
        list1.setAdapter(adapter);
        list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                myAlert.Title(planet[position]);
                myAlert.Array(planets[position]);
                myAlert.show(getFragmentManager(),"my alert");
            }
        });

    }

    @Override
    public void onRefresh() {
        orderby = myAlert.getOrderby();
        limit = myAlert.getLimit();
        minmagnitude = myAlert.getMinmagnitude();

        USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby="+orderby+"&limit="+limit+
                "&minmagnitude="+minmagnitude;

        Bundle queryBundle = new Bundle();
        queryBundle.putString("url",USGS_REQUEST_URL);

       LoaderManager manager = getSupportLoaderManager();
        Loader loader = manager.getLoader(LODER_REF);
        if(loader==null){
            manager.initLoader(LODER_REF,queryBundle,this);
        }
        else {
            manager.restartLoader(LODER_REF,queryBundle,this);
        }
    }

    @Override
    public Loader<ArrayList<earthquake>> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<ArrayList<earthquake>>(this) {
            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                if(args==null){
                    return;
                }
                forceLoad();
                linear.setVisibility(View.VISIBLE);
            }

            @Override
            public ArrayList<earthquake> loadInBackground() {
                String url = args.getString("url");
                if(url!=null) {
                    ArrayList<earthquake> earthquakes = QueryUtils.fetchEarthquakeData(url);
                    return earthquakes;
                }
                return null;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<earthquake>> loader, final ArrayList<earthquake> data) {
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        if(data.size()>0){
            Adapter adapter = new Adapter(
                    MainActivity.this,data);

            earthquakeListView.setAdapter(adapter);

            earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                    browserIntent.setData(Uri.parse(data.get(position).getUri()));
                    startActivity(browserIntent);
                }
            });
        }
        else{
            Toast.makeText(getApplicationContext(),"No Data Available with MINIMUM MAGNITUDE : "+minmagnitude,Toast.LENGTH_LONG).show();
        }

        swipeRefreshLayout.setRefreshing(false);
        linear.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<earthquake>> loader) {

    }
}
