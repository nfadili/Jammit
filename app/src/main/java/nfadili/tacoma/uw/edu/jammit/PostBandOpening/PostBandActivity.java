package nfadili.tacoma.uw.edu.jammit.PostBandOpening;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

import model.BandOpening;
import model.EventListing;
import nfadili.tacoma.uw.edu.jammit.R;

public class PostBandActivity extends AppCompatActivity implements PostBandInitialFragment.OnPostBandInitialFragmentInteractionListener,
                                                                PostBandWriteupFragment.OnPostBandWriteupFragmentInteractionListener{

    public final static String ADD_EVENT_URL = "http://cssgate.insttech.washington.edu/~_450atm1/Android/addBandOpening.php?";


    private BandOpening mOpening;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_band);

        //TODO: Populate with EditText fields
        mOpening = new BandOpening();

        if (findViewById(R.id.fragment_container3)!= null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container3, new PostBandInitialFragment())
                    .commit();
        }
    }

    @Override
    public void onPostBandInitialFragmentInteraction(int position) {
        String urlString = "";
        try {
            //Builds INSERT url
            urlString = ADD_EVENT_URL + "email=" + mOpening.getmPosterEmail() + "&" +
                    "name=" + URLEncoder.encode(mOpening.getmPoster(), "UTF-8") + "&" +
                    "title=" + URLEncoder.encode(mOpening.getmHeadline(), "UTF-8") + "&" +
                    "instrument=" + URLEncoder.encode(mOpening.getmInstrument(), "UTF-8") + "&" +
                    "style=" + URLEncoder.encode(mOpening.getmStyle(), "UTF-8") + "&" +
                    "city=" + URLEncoder.encode(mOpening.getmCity(), "UTF-8") + "&" +
                    "description=" + URLEncoder.encode(mOpening.getmDescription(), "UTF-8");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("URL: ", urlString);
        AddNewBandOpeningTask task = new AddNewBandOpeningTask();
        String result = "";
        try {
            //Visit url
            result = task.execute(urlString).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Log.e("Add result: ", result);
        Toast.makeText(getApplicationContext(), "This should take to the next fragment.", Toast.LENGTH_LONG)
                .show();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container3, new PostBandWriteupFragment())
                .commit();
    }

    @Override
    public void onPostBandWriteupFragmentInteraction(int position) {
        Toast.makeText(getApplicationContext(), "This should take me out of activity.", Toast.LENGTH_LONG)
                .show();
        finish();
    }

    /**
     * This AsyncTask handles adding an empty profile to the `Profile` table.
     */
    private class AddNewBandOpeningTask extends AsyncTask<String, Void, String> {

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
            }
            // Displays result info. For debugging
            if (result != null) {
                Log.e("ScheduleEventActivity", result.toString());
            }
            // Everything is good, return to login activity.
            if (result.contains("success")) {
                Log.e("ScheduleEventActivity", "Event added.");

                finish();
            }
            else {
                Toast.makeText(getApplicationContext(), "Unable to add event.", Toast.LENGTH_LONG)
                        .show();
            }
        }
    }
}
