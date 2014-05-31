package com.kiodev.trailbadger.app;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyPeaksFragment extends ListFragment {

	public static final String TAG = MyPeaksFragment.class.getSimpleName();

    private ArrayList<Peak> mPeaks;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        // Get singleton and then get list of crimes
        mPeaks = MyHistory.get(getActivity()).getPeaks();

        // Create the Adapter
        PeakAdapter adapter = new PeakAdapter(mPeaks);
        setListAdapter(adapter);
    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

        ((PeakAdapter) getListAdapter()).notifyDataSetChanged();
		
		View rootView = inflater.inflate(R.layout.fragment_my_peaks,
				container, false);

        // Set custom empty view
        View empty = rootView.findViewById(R.id.custom_empty_view);
        ListView displayList = (ListView) rootView.findViewById(android.R.id.list);
        displayList.setEmptyView(empty);

        return rootView;
	}

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onresume");
        ((PeakAdapter) getListAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        MyHistory.get(getActivity()).savePeaks();
    }

    private class PeakAdapter extends ArrayAdapter<Peak> {

        public PeakAdapter(ArrayList<Peak> peaks) {
            super(getActivity(), 0, peaks);
        }

        // Overriding this method is what allows us to use the custom list. This
        // is what gets called
        // behind the scenes when ListView and adapter have their conversations
        // about what to display
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            // If we weren't given a view, then inflate one
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(
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
