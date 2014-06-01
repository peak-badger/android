package com.kiodev.trailbadger.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;


public class SavePeakActivity extends ActionBarActivity {

    public static final String TAG = SavePeakActivity.class.getSimpleName();

    Peak mThisPeak;

    private TextView mPeakTitle;
    private TextView mPeakLat;
    private TextView mPeakLng;
    private TextView mPeakHeightFt;
    private TextView mPeakHeightM;

    private ImageButton mSaveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_peak);

        Intent i = getIntent();
        String peak = i.getStringExtra(MainActivity.EXTRA_PEAK);

        Log.d(TAG, "peak is: " + peak);

        try {
            JSONObject mtnJsonObj = new JSONObject(peak);

            // Initialize Peak
            Double lat = mtnJsonObj.optDouble("latitude");
            Double lng = mtnJsonObj.optDouble("longitude");
            mThisPeak = new Peak(lat, lng);

            // Set Peak params
            mThisPeak.setName(mtnJsonObj.optString("name"));
            mThisPeak.setHeightFeet(mtnJsonObj.optDouble("feet"));
            mThisPeak.setHeightMeter(mtnJsonObj.optDouble("meters"));

            // Load data to view
            mPeakTitle = (TextView) findViewById(R.id.TextView_peakTitle);
            mPeakTitle.setText(mThisPeak.getName());

            mPeakLat = (TextView) findViewById(R.id.TextView_peakLat);
            mPeakLat.setText(mThisPeak.getLat().toString());

            mPeakLng = (TextView) findViewById(R.id.TextView_peakLng);
            mPeakLng.setText(mThisPeak.getLng().toString());

            mPeakHeightFt = (TextView) findViewById(R.id.TextView_peakHeightFt);
            DecimalFormat formatter = new DecimalFormat("#,###");
            mPeakHeightFt.setText(formatter.format(mThisPeak.getHeightFeet()));

            mSaveButton = (ImageButton) findViewById(R.id.Button_save);
            mSaveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Add Peak to model
                    MyHistory.get(SavePeakActivity.this).addPeak(mThisPeak);
                    Log.d(TAG, "Added Peak: " + MyHistory.get(SavePeakActivity.this).getPeak(mThisPeak.getId()).toString());
                    Toast.makeText(SavePeakActivity.this, R.string.peak_saved, Toast.LENGTH_SHORT).show();
                }
            });

        } catch (JSONException e) {
            Log.e(TAG, "JSON error: ", e);
            Toast.makeText(this, "Sorry, something went wrong. Try again", Toast.LENGTH_SHORT).show();
        }

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
