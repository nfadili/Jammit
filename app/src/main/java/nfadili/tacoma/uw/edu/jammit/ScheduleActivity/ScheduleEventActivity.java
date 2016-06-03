package nfadili.tacoma.uw.edu.jammit.ScheduleActivity;

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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

import model.EventListing;
import model.UserAccount;
import nfadili.tacoma.uw.edu.jammit.MainMenuActivity;
import nfadili.tacoma.uw.edu.jammit.R;

public class ScheduleEventActivity extends AppCompatActivity {

    public final static String ADD_EVENT_URL = "http://cssgate.insttech.washington.edu/~_450atm1/Android/addEvent.php?";


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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_event);

        mAccount =  (UserAccount) getIntent().getSerializableExtra("Profile");

        final EventListing eventListing = new EventListing();

        final EditText editHeadline = (EditText) findViewById(R.id.sched_event_headline_edittext);

        final EditText editCity = (EditText) findViewById(R.id.sched_event_city_edittext);

        final EditText editWriteup = (EditText) findViewById(R.id.sched_event_bio_edittext);

        final DatePicker date = (DatePicker) findViewById(R.id.sched_event_datepicker);
        final TimePicker time = (TimePicker) findViewById(R.id.sched_event_timepicker);


        final Button proceedButton = (Button) findViewById(R.id.sched_event_submit_button);
        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventListing.setmPosterEmail(mAccount.getEmail());
                eventListing.setmPoster(mAccount.getmName());
                eventListing.setmHeadline(editHeadline.getText().toString());
                eventListing.setmCity(editCity.getText().toString());
                eventListing.setmDescription(editWriteup.getText().toString());
                eventListing.setmDateTime(formatDateTimeString(date, time));
                scheduleEvent(eventListing);
                finish();
            }
        });
    }

    /**
     * Method for Scheduling an EventListing and sending it to the database
     *
     * @param eventListing
     */
    public void scheduleEvent(EventListing eventListing) {
        String urlString = "";
        try {
            //Builds INSERT url
            urlString = ADD_EVENT_URL + "email=" + eventListing.getmPosterEmail() + "&" +
                    "name=" + URLEncoder.encode(eventListing.getmPoster(), "UTF-8") + "&" +
                    "city=" + URLEncoder.encode(eventListing.getmCity(), "UTF-8") + "&" +
                    "title=" + URLEncoder.encode(eventListing.getmHeadline(), "UTF-8") + "&" +
                    "description=" + URLEncoder.encode(eventListing.getmDescription(), "UTF-8") + "&" +
                    "date=" + URLEncoder.encode(eventListing.getmDateTime(), "UTF-8");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("URL: ", urlString);
        AddNewEventTask task = new AddNewEventTask();
        String result = "";
        try {
            //Visit url
            result = task.execute(urlString).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Log.e("Add event result: ", result);
        Toast.makeText(getApplicationContext(), "Event Posted!.", Toast.LENGTH_LONG)
                .show();

    }


    /**
     * This AsyncTask handles adding an empty profile to the `Profile` table.
     */
    private class AddNewEventTask extends AsyncTask<String, Void, String> {

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

    /**
     * Helper method for getting information from date and time pickers and formatting
     * a string to the format used by mySql
     *
     * @param date
     * @param time
     * @return a mySql formatted DATETIME string
     */
    public String formatDateTimeString(DatePicker date, TimePicker time) {
        StringBuilder dateTime = new StringBuilder();

        dateTime.append(date.getYear());
        dateTime.append("-");
        if (date.getMonth() < 10) {
            dateTime.append(0);
        }
        dateTime.append(date.getMonth());
        dateTime.append("-");
        if (date.getDayOfMonth() < 10) {
            dateTime.append(0);
        }
        dateTime.append(date.getDayOfMonth());
        dateTime.append(" ");
        if (time.getCurrentHour() < 10) {
            dateTime.append(0);
        }
        dateTime.append(time.getCurrentHour());
        dateTime.append(":");
        if (time.getCurrentMinute() < 10) {
            dateTime.append(0);
        }
        dateTime.append(time.getCurrentMinute());
        dateTime.append(":00");

        return dateTime.toString();
    }
}
