package com.example.kalyan.didyoufeel;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;

/**
 * Created by KALYAN on 07-06-2017.
 */

public class EarthQuakeLoader extends AsyncTaskLoader<ArrayList<earthquake>> {
    String url;
    public EarthQuakeLoader(Context context,String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public ArrayList<earthquake> loadInBackground() {
        ArrayList<earthquake> earthquakes=QueryUtils.fetchEarthquakeData(url);
        return earthquakes;
    }
}
