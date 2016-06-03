package nfadili.tacoma.uw.edu.jammit;

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
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import model.UserAccount;
import nfadili.tacoma.uw.edu.jammit.EditProfile.EditProfileActivity;
import nfadili.tacoma.uw.edu.jammit.FindBand.FindBandActivity;
import nfadili.tacoma.uw.edu.jammit.FindEvents.FindEventsActivity;
import nfadili.tacoma.uw.edu.jammit.FindMusicians.SearchMusicianActivity;
import nfadili.tacoma.uw.edu.jammit.PostBandOpening.PostBandActivity;
import nfadili.tacoma.uw.edu.jammit.ScheduleActivity.ScheduleEventActivity;

/**
 * Activity for the main menu of Jammit. Contains buttons that link to it's features.
 *
 */
public class MainMenuActivity extends AppCompatActivity {

    public static String[] STYLE_ARRAY = new String[]{"", "Rock", "Pop", "Metal", "Prog", "Rap", "Hip-Hop", "R & B", "Punk", "Classical", "Jazz", "Fusion", "Electronic", "Dubstep", "Techno"};
    public static String[] INSTRUMENT_ARRAY = new String[]{"", "Guitar", "Bass", "Drums", "Vocals", "Keyboards", "Brass", "Woodwind", "Percussion"};;

    private static final String PROFILE_URL
            = "http://cssgate.insttech.washington.edu/~_450atm1/Android/getProfiles.php";


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menubar, menu);

        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        boolean finish = getIntent().getBooleanExtra("finish", false);
        if (finish) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

            finish();
            return;
        }

        Intent intent = getIntent();
        String accountEmail = intent.getExtras().getString("loggedInEmail");

        VerifyUserAccountTask task = new VerifyUserAccountTask();
        String authString = PROFILE_URL + "?email=" + accountEmail;
        String queryResult = "";
        try {
            queryResult = task.execute(new String[]{authString}).get();
            Log.e("THIS RIGHT HERE", queryResult);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //Instantiate the current user's account as an object.
        mAccount = UserAccount.parseProfileQueryJSON(queryResult);
        Log.e("INSTATIATE", mAccount.toString());

        TextView nameView = (TextView) findViewById(R.id.welcome_user_main);
        nameView.setText((CharSequence) "Hello, " + mAccount.getmName());

        setButtons();

    }

    /**
     * Helper method to set up button functionality on the main menu.
     */
    private void setButtons() {
        Button mViewProfileButton = (Button) findViewById(R.id.view_profile_button);
        final Intent viewProfile = new Intent(this, ViewProfileActivity.class);

        mViewProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewProfile.putExtra("Profile", mAccount);
                startActivity(viewProfile);
            }
        });

        Button mEditProfileButton = (Button) findViewById(R.id.edit_profile_button);
        final Intent edit = new Intent(this, EditProfileActivity.class);

        mEditProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit.putExtra("Profile", mAccount);
                startActivity(edit);
            }
        });

        Button mSearchMusicianButton = (Button) findViewById(R.id.search_musicians_button);
        final Intent search = new Intent(this, SearchMusicianActivity.class);
        mSearchMusicianButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search.putExtra("Profile", mAccount);
                startActivity(search);
            }
        });

        Button mPostBandButton = (Button) findViewById(R.id.post_band_button);
        final Intent post = new Intent(this, PostBandActivity.class);

        mPostBandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                post.putExtra("Profile", mAccount);
                startActivity(post);
            }
        });
        Button mFindBandButton = (Button) findViewById(R.id.find_band_button);
        final Intent findBand = new Intent(this, FindBandActivity.class);

        mFindBandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findBand.putExtra("Profile", mAccount);
                startActivity(findBand);
            }
        });
        Button mScheduleEventButton = (Button) findViewById(R.id.schedule_event_button);
        final Intent schedule = new Intent(this, ScheduleEventActivity.class);

        mScheduleEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                schedule.putExtra("Profile", mAccount);
                startActivity(schedule);
            }
        });
        Button mFindEventsButton = (Button) findViewById(R.id.find_events_button);
        final Intent findEvents = new Intent(this, FindEventsActivity.class);

        mFindEventsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findEvents.putExtra("Profile", mAccount);
                startActivity(findEvents);
            }
        });

        Button mLogoutButton = (Button) findViewById(R.id.action_logout);
        final Intent loginActivity = new Intent(this, LoginActivity.class);
        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(logoutUser()) {
                    loginActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(loginActivity);
                }
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        String accountEmail = intent.getExtras().getString("loggedInEmail");

        VerifyUserAccountTask task = new VerifyUserAccountTask();
        String authString = PROFILE_URL + "?email=" + accountEmail;
        String queryResult = "";
        try {
            queryResult = task.execute(new String[]{authString}).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //Instantiate the current user's account as an object.
        mAccount = UserAccount.parseProfileQueryJSON(queryResult);
        TextView nameView = (TextView) findViewById(R.id.welcome_user_main);
        nameView.setText((CharSequence) "Hello, " + mAccount.getmName());

        Log.e("Account on Resume: ", mAccount.toString());
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

    private class VerifyUserAccountTask extends AsyncTask<String, Void, String> {
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
                    response = "Unable to connect with user database, Reason: "
                            + e.getMessage();
                }
                finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            // Something wrong with the network or the URL.
            if (result.startsWith("Unable to")) {
                Log.e("MainMenuActivity", result.toString());
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG)
                        .show();
                return;
            }

            // Displays result info. For debugging
            if (result != null) {
                Log.e("RESULT", result.toString());
            }
        }


    }

}
