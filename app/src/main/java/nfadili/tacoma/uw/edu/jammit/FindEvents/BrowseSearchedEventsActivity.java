package nfadili.tacoma.uw.edu.jammit.FindEvents;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import model.BandOpening;
import model.EventListing;
import nfadili.tacoma.uw.edu.jammit.FindBand.BandListFragment;
import nfadili.tacoma.uw.edu.jammit.R;


public class BrowseSearchedEventsActivity extends AppCompatActivity implements EventListFragment.OnEventListFragmentInteractionListener{

    private String mCity;
    private String mResult;
    private ArrayList<EventListing> mEvents;

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

        mCity = getIntent().getStringExtra("City");
        mResult = getIntent().getStringExtra("Result");

        mEvents = parseEventsIntoList(mResult);
        Log.e("mRes toString: ", mResult);
        Log.e("mEv toString: ", mEvents.toString());
        //finish();
//        final Button showEventsButton = (Button) findViewById(R.id.find_events_button);
//        showEventsButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String result = showEvents();
//                Log.e("Query: ", result);
//                finish();
//            }
//        });

        if (findViewById(R.id.fragment_container6)!= null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container6, new EventListFragment())
                    .commit();
        }
    }

    @Override
    public void onEventListFragmentInteraction(int position) {

    }
}
