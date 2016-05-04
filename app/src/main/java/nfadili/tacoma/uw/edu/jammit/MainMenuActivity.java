package nfadili.tacoma.uw.edu.jammit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import java.util.concurrent.ExecutionException;

import model.UserAccount;

public class MainMenuActivity extends AppCompatActivity {

    private static final String PROFILE_URL
            = "http://cssgate.insttech.washington.edu/~_450atm1/Android/getProfiles.php";


    public UserAccount mAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

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

        //Instaniate the current user's account as an object.
        mAccount = UserAccount.parseProfileQueryJSON(queryResult);
        Log.e("INSTATIATE", mAccount.toString());

        Button mEditProfileButton = (Button) findViewById(R.id.edit_profile_button);
        final Intent edit = new Intent(this, EditProfileActivity.class);
        mEditProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(edit);
            }
        });

        Button mSearchMusicianButton = (Button) findViewById(R.id.search_musicians_button);
        final Intent search = new Intent(this, SearchMusicianActivity.class);
        mSearchMusicianButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(search);
            }
        });

        Button mLogoutButton = (Button) findViewById(R.id.action_logout);
        final Intent loginActivity = new Intent(this, LoginActivity.class);
        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(logoutUser()) {
                    startActivity(loginActivity);
                }
            }
        });
    }

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
