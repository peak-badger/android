package com.kiodev.trailbadger.app;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class LeaderBoardActivity extends ListActivity {

    public static final String PEAK_BADGER_URL = "http://peakbadger.herokuapp.com/leader_boards.json";
    public static final String TAG = LeaderBoardActivity.class.getSimpleName();

    private ArrayList<User> mUsers;
    private LeaderAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);

        // Set custom empty view
        View empty = findViewById(R.id.custom_empty_view);
        ListView displayList = (ListView) findViewById(android.R.id.list);
        displayList.setEmptyView(empty);

        // Create the Adapter
        mUsers = new ArrayList<User>();
        mAdapter = new LeaderAdapter(mUsers);
        mAdapter.notifyDataSetChanged();
        setListAdapter(mAdapter);

        getLeaderBoardData();

    }

    private void getLeaderBoardData(){
        // Create a single queue
        RequestQueue queue = Volley.newRequestQueue(this);

        // Define your request
        JsonObjectRequest getRequest = new JsonObjectRequest(PEAK_BADGER_URL, null,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        // Parse JSON here
                        Log.d(TAG, "JSON Object " + jsonObject.toString());
                        try {
                            JSONArray jsonArray = jsonObject.getJSONArray("users");

                            for(int i = 0; i < jsonArray.length(); i++){
                                JSONObject user = (JSONObject)jsonArray.get(i);

                                String name =user.optString("name");
                                Double elevation = user.optDouble("vertical_height");

                                Log.d(TAG, "name: " + name);
                                Log.d(TAG, "elev: " + elevation.toString());

                                mUsers.add(new User(name, elevation));
                            }

                            mAdapter.notifyDataSetChanged();

                        } catch (JSONException e){
                            Log.e(TAG, "JSONException ", e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse( VolleyError volleyError ){
                        // Errors
                    }
                }
        );

        // Add request to queue
        queue.add(getRequest);
}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.leader_board, menu);
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

    private class LeaderAdapter extends ArrayAdapter<User> {

        public LeaderAdapter(ArrayList<User> users) {
            super(LeaderBoardActivity.this, 0, users);
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
                convertView = LeaderBoardActivity.this.getLayoutInflater().inflate(
                        R.layout.list_item_leader_board, null);
            }

            // Configure the view for this specific peak
            User u = getItem(position);

            TextView titleTextView = (TextView) convertView.findViewById(R.id.user_name);
            titleTextView.setText(u.getName());

            TextView elevTextView = (TextView) convertView.findViewById(R.id.height);
            DecimalFormat formatter = new DecimalFormat("#,###");
            elevTextView.setText( formatter.format(u.getFeet()) );

            ImageView userImageView = (ImageView) convertView.findViewById(R.id.ImagView_user);
            userImageView.setImageResource(u.getImgResId());

            return convertView;
        }

    }

}
