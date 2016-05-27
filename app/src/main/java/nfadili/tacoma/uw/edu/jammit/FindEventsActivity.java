package nfadili.tacoma.uw.edu.jammit;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

public class FindEventsActivity extends AppCompatActivity {

    final static String FIND_EVENTS_URL = "http://cssgate.insttech.washington.edu/~_450atm1/Android/events.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_events);

        //TODO: take result and display it in list. Maybe filter by date??
        String result = showEvents();
        Log.e("Query: ", result);
        finish();
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
