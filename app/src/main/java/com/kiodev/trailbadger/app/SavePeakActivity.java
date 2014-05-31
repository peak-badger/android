package com.kiodev.trailbadger.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class SavePeakActivity extends ActionBarActivity {

    public static final String TAG = SavePeakActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_peak);

        Intent i = getIntent();
        Double lat = i.getDoubleExtra(MainActivity.EXTRA_LAT, 0);
        Double lng = i.getDoubleExtra(MainActivity.EXTRA_LNG, 0);

        Log.d(TAG, "Lat: " + lat);
        Log.d(TAG, "Lng: " + lng);


        Peak thisPeak = new Peak(lat, lng);
        thisPeak.setName(thisPeak.getId().toString());
        MyHistory.get(this).addPeak(thisPeak);
        
        Log.d(TAG, "Added Peak: " + MyHistory.get(this).getPeak(thisPeak.getId()).toString());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.save_peak, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
