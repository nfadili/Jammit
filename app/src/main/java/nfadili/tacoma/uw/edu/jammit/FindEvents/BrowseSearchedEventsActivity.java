package nfadili.tacoma.uw.edu.jammit.FindEvents;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


import model.EventListing;
import model.UserAccount;

import nfadili.tacoma.uw.edu.jammit.MainMenuActivity;
import nfadili.tacoma.uw.edu.jammit.R;


public class BrowseSearchedEventsActivity extends AppCompatActivity implements EventListFragment.OnEventListFragmentInteractionListener{

    private String mCity;
    private String mResult;
    private ArrayList<EventListing> mEvents;

    public UserAccount mAccount;

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent action;
        switch(item.getItemId()){
            case R.id.logout_overflow:
                // your action goes here
                Log.e("BACK TO", "LOGOUT");
                if (logoutUser()) {
                    action = new Intent(this, MainMenuActivity.class);
                    action.putExtra("finish", true);
                    action.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(action);
                }
                return true;
            case R.id.action_main:
                // your action goes here
                Log.e("BACK TO", "MAIN");
                action = new Intent(this, MainMenuActivity.class);
                action.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                action.putExtra("loggedInEmail", mAccount.getEmail());
                startActivity(action);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    /**
     * Logs out current user
     *
     * @return true
     */
    public boolean logoutUser() {
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.LOGIN_PREFS), Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(getString(R.string.LOGGEDIN), false).commit();
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menubar, menu);

        return true;
    }

    /**
     * Parses JSON string into usable List of EventListings
     *
     * @param result
     * @return the ArrayList of EventListings from the database
     */
    private ArrayList<EventListing> parseEventsIntoList(String result) {
        ArrayList<EventListing> selectedEvents = new ArrayList<EventListing>();
        selectedEvents = parseEvents(result);

        ArrayList<EventListing> trimmedList = new ArrayList<EventListing>();
        for (int i = 0; i < selectedEvents.size(); i++) {
            if (mCity != "" && selectedEvents.get(i).getmCity().contains(mCity)) {
                trimmedList.add(selectedEvents.get(i));
            }
        }
        return trimmedList;
    }

    private ArrayList<EventListing> parseEvents(String result) {
        ArrayList<EventListing> parsedList = new ArrayList<EventListing>();

        try {
            JSONArray array = new JSONArray(result);
            JSONObject profile;

            for(int i = 0; i < array.length(); i++) {
                profile = array.getJSONObject(i);
                String name = profile.getString("name");
                String email = profile.getString("email");

                String city = profile.getString("city");

                String description = profile.getString("description");
                String title = profile.getString("title");
                String date = profile.getString("date");
                parsedList.add(new EventListing(email, name, city, title, description, date));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return parsedList;
    }

    public ArrayList<EventListing> getmEvents() {
        return mEvents;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_searched_events);

        mAccount = (UserAccount) getIntent().getSerializableExtra("Profile");

        mCity = getIntent().getStringExtra("City");
        mResult = getIntent().getStringExtra("Result");

        mEvents = parseEventsIntoList(mResult);
        Log.e("mRes toString: ", mResult);
        Log.e("mEv toString: ", mEvents.toString());


        if (findViewById(R.id.fragment_container6)!= null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container6, new EventListFragment())
                    .commit();
        }
    }

    @Override
    public void onEventListFragmentInteraction(int position) {
        // Capture the student fragment from the activity layout
        EventDetailsFragment eventListing = (EventDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.event_details_frag);
        //profParamFragment.setParameter(parameter);
        if (eventListing != null) {
            // If courseItem frag is available, we're in two-pane layout...
            // Call a method in the student fragment to update its username
            eventListing.updateViews(position);
        } else {
            // If the frag is not available, we're in the one-pane layout and must swap frags...
            // Create fragment and give it an argument for the selected student
            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            eventListing = new EventDetailsFragment();
            Bundle args = new Bundle();
            args.putInt(EventDetailsFragment.ARG_POSITION, position);
            eventListing.setArguments(args);
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container6, eventListing)
                    .addToBackStack(null);

            // Commit the transaction
            transaction.commit();
        }
    }
}
