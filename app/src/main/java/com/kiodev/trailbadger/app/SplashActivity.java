package com.kiodev.trailbadger.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;


public class SplashActivity extends Activity {

    public static final String TAG = SplashActivity.class.getSimpleName();
    public static JSONObject PEAK_DATA = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.splash_progress);
        progressBar.setVisibility(View.VISIBLE);

        // Load the JSON Object
        loadJSONFromAsset();

        progressBar.setVisibility(View.INVISIBLE);

        // Once complete, kick off rest of app
        Intent i = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(i);
    }

    public JSONObject loadJSONFromAsset() {
        JSONObject jsonObject = PEAK_DATA;

        // Only load JSON once
        if (jsonObject == null) {
            String jsonString = null;
            Log.d(TAG, "loadJSONFromAsset()");

            try {

                InputStream is = getAssets().open("_index.geojson");

                int size = is.available();

                byte[] buffer = new byte[size];

                is.read(buffer);

                is.close();

                jsonString = new String(buffer, "UTF-8");
                try {
                    PEAK_DATA = new JSONObject(jsonString);
                } catch (JSONException e) {
                    Log.e(TAG, "JSON Error: ", e);
                }

            } catch (IOException ex) {
                ex.printStackTrace();
                return null;
            }
        }

        return jsonObject;
    }

}
