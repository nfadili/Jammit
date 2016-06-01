package nfadili.tacoma.uw.edu.jammit.FindEvents;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
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

import nfadili.tacoma.uw.edu.jammit.FindBand.BrowseSearchedBandsActivity;
import nfadili.tacoma.uw.edu.jammit.R;

public class FindEventsActivity extends AppCompatActivity {

    final static String FIND_EVENTS_URL = "http://cssgate.insttech.washington.edu/~_450atm1/Android/events.php";


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menubar, menu);

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_events);

        final EditText byCity = (EditText) findViewById(R.id.edittext_search_event_city);

        //TODO: take result and display it in list. Maybe filter by date??
        final String result = showEvents();
        Log.e("Query: ", result);

        Button mSearchButton = (Button) findViewById(R.id.search_event_button);
        final Intent search = new Intent(this, BrowseSearchedEventsActivity.class);
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.e("Add result1: ", byInstrument.getText().toString());
//                Log.e("Add result2: ", byCity.getText().toString());
//                Log.e("Add result3: ", byStyle.getText().toString());

                //search.putExtra("Instrument", byInstrument.getText().toString());
                search.putExtra("City", byCity.getText().toString());
                search.putExtra("Result", result);
                //search.putExtra("Style", byStyle.getText().toString());

                startActivity(search);
            }
        });
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
    }

    private String showEvents() {
        String urlString = "";
        try {
            //Builds INSERT url
            urlString = FIND_EVENTS_URL;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("URL: ", urlString);
        FindEventsTask task = new FindEventsTask();
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
    private class FindEventsTask extends AsyncTask<String, Void, String> {

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
                Log.e("FindEventsActivity", result.toString());
            }
        }
    }
}
