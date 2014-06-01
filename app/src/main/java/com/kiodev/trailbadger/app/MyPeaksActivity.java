package com.kiodev.trailbadger.app;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyPeaksActivity extends ListActivity {

	public static final String TAG = MyPeaksActivity.class.getSimpleName();

    private ArrayList<Peak> mPeaks;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get singleton and then get list of crimes
        mPeaks = MyHistory.get(this).getPeaks();

		setContentView(R.layout.fragment_my_peaks);

        // Set custom empty view
        View empty = findViewById(R.id.custom_empty_view);
        ListView displayList = (ListView) findViewById(android.R.id.list);
        displayList.setEmptyView(empty);

        // Create the Adapter
        PeakAdapter adapter = new PeakAdapter(mPeaks);
        adapter.notifyDataSetChanged();
        setListAdapter(adapter);
	}

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onresume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        MyHistory.get(this).savePeaks();
    }

    private class PeakAdapter extends ArrayAdapter<Peak> {

        public PeakAdapter(ArrayList<Peak> peaks) {
            super(MyPeaksActivity.this, 0, peaks);
        }

        // Overriding this method is what allows us to use the custom list. This
        // is what gets called
        // behind the scenes when ListView and adapter have their conversations
        // about what to display
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Log.d(TAG, "getView: " + position);

            // If we weren't given a view, then inflate one
            if (convertView == null) {
                convertView = MyPeaksActivity.this.getLayoutInflater().inflate(
                        R.layout.list_item_peak, null);
            }

            // Configure the view for this specific peak
            Peak p = getItem(position);

            TextView titleTextView = (TextView) convertView.findViewById(R.id.peak_name);
            titleTextView.setText(p.getName());

            return convertView;
        }

    }

}
