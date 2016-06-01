package nfadili.tacoma.uw.edu.jammit.FindBand;

import android.os.AsyncTask;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import model.BandOpening;
import nfadili.tacoma.uw.edu.jammit.FindMusicians.ViewProfileFragment;
import nfadili.tacoma.uw.edu.jammit.R;

public class BrowseSearchedBandsActivity extends AppCompatActivity implements BandListFragment.OnBandListFragmentInteractionListener{

    public final static String FIND_BANDS_URL = "http://cssgate.insttech.washington.edu/~_450atm1/Android/openings.php";
    private ArrayList<BandOpening> mBands;

    private String mBandsString;
    private String mCity;
    private String mStyle;
    private String mInstrument;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_searched_bands);

        mCity = getIntent().getStringExtra("City");
        mStyle = getIntent().getStringExtra("Style");
        mInstrument = getIntent().getStringExtra("Instrument");

        //TODO: take result and display it in list. Filter by search?
        String result = showBands();
        Log.e("Query: ", result);
        mBandsString = result;
        Log.e("mBandsString = ", mBandsString);
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

        if (findViewById(R.id.fragment_container5)!= null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container5, new BandListFragment())
                    .commit();
        }
    }

    public String getmCity() {
        return mCity;
    }
    public String getmInstrument() {
        return mInstrument;
    }
    public String getmStyle() {
        return mStyle;
    }
    public ArrayList<BandOpening> getmBands() {
        return mBands;
    }

    public void setmBands(ArrayList<BandOpening> bands) {
        mBands = bands;
    }
    public String getResult() {
        return mBandsString;
    }
    private String showBands() {
        String urlString = "";
        try {
            //Builds INSERT url
            urlString = FIND_BANDS_URL;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("URL: ", urlString);
        FindBandsTask task = new FindBandsTask();
        String result = "";
        try {
            //Visit url
            result = task.execute(urlString).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * This AsyncTask handles adding an empty profile to the `Profile` table.
     */
    private class FindBandsTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            HttpURLConnection urlConnection = null;
            for (String url : urls) {
                try {
                    URL urlObject = new URL(url);
                    urlConnection = (HttpURLConnection) urlObject.openConnection();
                    InputStream content = urlConnection.getInputStream();
                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }

                } catch (Exception e) {
                    response = "Unable to connect with database, Reason: "
                            + e.getMessage();
                }
                finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }
            }
            Log.e("Response thing", response);
            return response;
        }

        /**
         *
         * @param result represents database insert result
         */
        @Override
        protected void onPostExecute(String result) {
            // Something wrong with the network or the URL.
            if (result.startsWith("Unable to")) {
                //Log.e("ScheduleEventActivity", result.toString());
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG)
                        .show();
                return;
            } else {
                Log.e("Not unable: ", result);
            }
        }
    }

        @Override
    public void onBandListFragmentInteraction(int position) {

            // Capture the student fragment from the activity layout
            BandDetailsFragment bandListing = (BandDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.band_details_frag);
            //profParamFragment.setParameter(parameter);
            if (bandListing != null) {
                // If courseItem frag is available, we're in two-pane layout...
                // Call a method in the student fragment to update its username
                bandListing.updateViews(position);
            } else {
                // If the frag is not available, we're in the one-pane layout and must swap frags...
                // Create fragment and give it an argument for the selected student
                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack so the user can navigate back
                bandListing = new BandDetailsFragment();
                Bundle args = new Bundle();
                args.putInt(ViewProfileFragment.ARG_POSITION, position);
                bandListing.setArguments(args);
                FragmentTransaction transaction = getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container5, bandListing)
                        .addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }
    }
}
