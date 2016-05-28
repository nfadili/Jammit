package nfadili.tacoma.uw.edu.jammit.PostBandOpening;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import model.UserAccount;
import nfadili.tacoma.uw.edu.jammit.R;

public class PostBandActivity extends AppCompatActivity {

    public final static String ADD_EVENT_URL = "http://cssgate.insttech.washington.edu/~_450atm1/Android/addBandOpening.php?";


    private UserAccount mAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_band);

        //TODO: Populate with EditText fields

        final BandOpening bandOpening = new BandOpening();
        final EditText editHeadline = (EditText) findViewById(R.id.post_band_headline_edittext);
        final EditText editStyle = (EditText) findViewById(R.id.post_band_style_edittext);
        final EditText editCity = (EditText) findViewById(R.id.post_band_city_edittext);
        final EditText editInstrument = (EditText) findViewById(R.id.post_band_instruments_needed_edittext);
        final EditText editWriteup = (EditText) findViewById(R.id.post_band_writeup_edittext);

        final Button proceedButton = (Button) findViewById(R.id.post_band_submit_button);
        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bandOpening.setmHeadline(editHeadline.getText().toString());
                bandOpening.setmStyle(editStyle.getText().toString());
                bandOpening.setmCity(editCity.getText().toString());
                bandOpening.setmInstrument(editInstrument.getText().toString());
                bandOpening.setmDescription(editWriteup.getText().toString());
                bandOpening.setmPoster(mAccount.getmName());
                bandOpening.setmPosterEmail(mAccount.getEmail());

//                Log.e("Add result1: ", bandOpening.getmHeadline());
//                Log.e("Add result2: ", bandOpening.getmStyle());
//                Log.e("Add result3: ", bandOpening.getmCity());
//                Log.e("Add result4: ", bandOpening.getmInstrument());
//                Log.e("Add result5: ", bandOpening.getmDescription());
                //Need to get poster Info too
                createPosting(bandOpening);
                finish();
            }
        });

        //mOpening = new BandOpening();

//        if (findViewById(R.id.fragment_container3)!= null) {
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.fragment_container3, new PostBandInitialFragment())
//                    .commit();
//        }
    }

    //@Override
    public void createPosting(BandOpening bandOpening) {
        String urlString = "";
        try {
            //Builds INSERT url
            urlString = ADD_EVENT_URL + "email=" + bandOpening.getmPosterEmail() + "&" +
                    "name=" + URLEncoder.encode(bandOpening.getmPoster(), "UTF-8") + "&" +
                    "title=" + URLEncoder.encode(bandOpening.getmHeadline(), "UTF-8") + "&" +
                    "instrument=" + URLEncoder.encode(bandOpening.getmInstrument(), "UTF-8") + "&" +
                    "style=" + URLEncoder.encode(bandOpening.getmStyle(), "UTF-8") + "&" +
                    "city=" + URLEncoder.encode(bandOpening.getmCity(), "UTF-8") + "&" +
                    "description=" + URLEncoder.encode(bandOpening.getmDescription(), "UTF-8");
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
        Toast.makeText(getApplicationContext(), "Band Opening Posted!.", Toast.LENGTH_LONG)
                .show();
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.fragment_container3, new PostBandWriteupFragment())
//                .commit();
    }

//    @Override
//    public void onPostBandWriteupFragmentInteraction(int position) {
//        Toast.makeText(getApplicationContext(), "This should take me out of activity.", Toast.LENGTH_LONG)
//                .show();
//        finish();
//    }

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
