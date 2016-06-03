package nfadili.tacoma.uw.edu.jammit.PostBandOpening;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

import model.BandOpening;
import model.UserAccount;
import nfadili.tacoma.uw.edu.jammit.MainMenuActivity;
import nfadili.tacoma.uw.edu.jammit.R;

public class PostBandActivity extends AppCompatActivity {

    public final static String ADD_EVENT_URL = "http://cssgate.insttech.washington.edu/~_450atm1/Android/addBandOpening.php?";


    public UserAccount mAccount;

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent action;
        switch(item.getItemId()){
            case R.id.logout_overflow:
                // your action goes here
                Log.d("BACK TO", "LOGOUT");
                if (logoutUser()) {
                    action = new Intent(this, MainMenuActivity.class);
                    action.putExtra("finish", true);
                    action.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(action);
                }
                return true;
            case R.id.action_main:
                // your action goes here
                Log.d("BACK TO", "MAIN");
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_band);

        mAccount = (UserAccount) getIntent().getSerializableExtra("Profile");

        final BandOpening bandOpening = new BandOpening();
        final EditText editHeadline = (EditText) findViewById(R.id.post_band_headline_edittext);
        final EditText editCity = (EditText) findViewById(R.id.post_band_city_edittext);
        final EditText editWriteup = (EditText) findViewById(R.id.post_band_writeup_edittext);

        final Spinner instrSpin = (Spinner) findViewById(R.id.instrument_spinner);
        final Spinner styleSpin = (Spinner) findViewById(R.id.style_spinner);

        ArrayAdapter<String> instrAdapter = new  ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, MainMenuActivity.INSTRUMENT_ARRAY);
        instrSpin.setAdapter(instrAdapter);


        ArrayAdapter<String> styleAdapter = new  ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, MainMenuActivity.STYLE_ARRAY);
        styleSpin.setAdapter(styleAdapter);

        final Button proceedButton = (Button) findViewById(R.id.post_band_submit_button);
        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bandOpening.setmHeadline(editHeadline.getText().toString());
                bandOpening.setmStyle(styleSpin.getSelectedItem().toString());
                bandOpening.setmCity(editCity.getText().toString());
                bandOpening.setmInstrument(instrSpin.getSelectedItem().toString());
                bandOpening.setmDescription(editWriteup.getText().toString());
                bandOpening.setmPoster(mAccount.getmName());
                bandOpening.setmPosterEmail(mAccount.getEmail());

                createPosting(bandOpening);
                finish();
            }
        });

    }

    /**
     * Method to create a BandOpening and send it to the database
     *
     * @param bandOpening
     */
    public void createPosting(BandOpening bandOpening) {
        String urlString = "";
        try {
            //Builds INSERT url
            urlString = ADD_EVENT_URL + "email=" + bandOpening.getmPosterEmail() + "&" +
                    "name=" + URLEncoder.encode(bandOpening.getmPoster(), "UTF-8") + "&" +
                    "headline=" + URLEncoder.encode(bandOpening.getmHeadline(), "UTF-8") + "&" +
                    "instruments=" + URLEncoder.encode(bandOpening.getmInstrument(), "UTF-8") + "&" +
                    "styles=" + URLEncoder.encode(bandOpening.getmStyle(), "UTF-8") + "&" +
                    "city=" + URLEncoder.encode(bandOpening.getmCity(), "UTF-8") + "&" +
                    "description=" + URLEncoder.encode(bandOpening.getmDescription(), "UTF-8");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("URL: ", urlString);
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
        Log.d("Add result: ", result);
        Toast.makeText(getApplicationContext(), "Band Opening Posted!.", Toast.LENGTH_LONG)
                .show();

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
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG)
                        .show();
                return;
            }
            // Displays result info. For debugging
            if (result != null) {
                Log.d("ScheduleEventActivity", result.toString());
            }
            // Everything is good, return to login activity.
            if (result.contains("success")) {
                Log.d("ScheduleEventActivity", "Event added.");

                finish();
            }
            else {
                Toast.makeText(getApplicationContext(), "Unable to add event.", Toast.LENGTH_LONG)
                        .show();
            }
        }
    }
}
