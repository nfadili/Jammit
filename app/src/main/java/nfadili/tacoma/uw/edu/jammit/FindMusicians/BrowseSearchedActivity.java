package nfadili.tacoma.uw.edu.jammit.FindMusicians;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import model.UserAccount;
import nfadili.tacoma.uw.edu.jammit.MainMenuActivity;
import nfadili.tacoma.uw.edu.jammit.MusicianDB;
import nfadili.tacoma.uw.edu.jammit.R;


/**
 * Activity that allows a user to browse through a list of users that matched a previously
 * entered set of parameters to meet their band-finding needs.
 *
 */
public class BrowseSearchedActivity extends AppCompatActivity implements SearchListFragment.OnSearchListFragmentInteractionListener {

    private final static String PROFILES_URL = "http://cssgate.insttech.washington.edu/~_450atm1/Android/profiles.php";

    private String mInstrument;
    private String mCity;
    private String mAge;
    private String mStyles;

    public ArrayList<UserAccount> mSelectedUsers;

    public ArrayList<UserAccount> mMusicianList;
    public MusicianDB mMusicianDB;

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
        setContentView(R.layout.activity_browse_searched);


        mMusicianDB = new MusicianDB(this);
        //mMusicianDB.deleteMusicians();
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()) {
            Toast.makeText(getApplicationContext(),
                    "No network connection available. Displaying locally stored data",
                    Toast.LENGTH_SHORT) .show();

            if (mMusicianDB == null) {
                mMusicianDB = new MusicianDB(this);
            }
            if (mMusicianList == null) {
                mMusicianList = mMusicianDB.getMusicians();
            }
            //Use db for results
            mSelectedUsers = new ArrayList(mMusicianList);
            if (mSelectedUsers.size() == 0) {
                Toast.makeText(getApplicationContext(), "No users match search query.", Toast.LENGTH_LONG)
                        .show();
            }
            if (findViewById(R.id.fragment_container2) != null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_container2, new SearchListFragment())
                        .commit();
            }
        }

        //Network connectivity established
        else {
            mInstrument = getIntent().getStringExtra("Instrument");
            mCity = getIntent().getStringExtra("City");
            mAge = getIntent().getStringExtra("Age");
            mStyles = getIntent().getStringExtra("Style");

            String result = "";
            SelectProfilesTask task = new SelectProfilesTask();
            try {
                result = task.execute(PROFILES_URL).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            Log.d("AllSearchResults: ", result);
            ArrayList<UserAccount> allUsers = parseListOfProfilesJSON(result);
            saveUsersToDB(allUsers);
            mSelectedUsers = trimList(allUsers);
            //mMusicianDB.deleteMusicians();
            Log.d("SearchResults: ", mSelectedUsers.toString());

            if (mSelectedUsers.size() == 0) {
                Toast.makeText(getApplicationContext(), "No users match search query.", Toast.LENGTH_LONG)
                        .show();
            }
            if (findViewById(R.id.fragment_container2) != null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_container2, new SearchListFragment())
                        .commit();
            }
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void saveUsersToDB(ArrayList<UserAccount> allUsers) {
        for(int i = 0; i < allUsers.size(); i++) {
            UserAccount user = allUsers.get(i);
            Log.d("USER", user.toString());
            mMusicianDB.insertMusician(user.getEmail(), user.getmName(), user.getmAge(),
                    user.getmInstruments(), user.getmStyles(), user.getmCity(), user.getmBio());
        }
    }

    /**
     * Method for taking search parameters and filtering out the undesirable elements from the
     * database;
     *
     * @param oldList
     * @return a filtered list of UserAccount objects
     */
    private ArrayList<UserAccount> trimList(List<UserAccount> oldList) {
        ArrayList<UserAccount> users = new ArrayList<UserAccount>();
        for (int i = 0; i < oldList.size(); i++) {
            if (mInstrument != "" && oldList.get(i).getmInstruments().contains(mInstrument)) {
                if (mAge != "" && oldList.get(i).getmAge().contains(mAge)) {
                    if (mCity != "" && oldList.get(i).getmCity().contains(mCity)) {
                        if (mStyles != "" && oldList.get(i).getmStyles().contains(mStyles)) {
                            users.add(oldList.get(i));
                        }
                    }
                }
            }
        }

        return users;

    }
    @Override
    public void onSearchListFragmentInteraction(int position) {
        // Capture the student fragment from the activity layout
        ViewProfileFragment viewProfile = (ViewProfileFragment) getSupportFragmentManager().findFragmentById(R.id.view_prof_frag);
        //profParamFragment.setParameter(parameter);
        if (viewProfile != null) {
            // If courseItem frag is available, we're in two-pane layout...
            // Call a method in the student fragment to update its username
            viewProfile.updateProfileView(position);
        } else {
            // If the frag is not available, we're in the one-pane layout and must swap frags...
            // Create fragment and give it an argument for the selected student
            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            viewProfile = new ViewProfileFragment();
            Bundle args = new Bundle();
            args.putInt(ViewProfileFragment.ARG_POSITION, position);
            viewProfile.setArguments(args);
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container2, viewProfile)
                    .addToBackStack(null);

            // Commit the transaction
            transaction.commit();
        }
    }

    /**
     * Takes a JSON string and turns it into a list of UserAccount objects
     *
     * @param JSONString
     * @return a list of UserAccounts
     */
    private ArrayList<UserAccount> parseListOfProfilesJSON(String JSONString) {
        ArrayList<UserAccount> users = new ArrayList<UserAccount>();
        try {
            JSONArray array = new JSONArray(JSONString);
            JSONObject profile;

            for(int i = 0; i < array.length(); i++) {
                profile = array.getJSONObject(i);
                String email = profile.getString("email");
                String name = profile.getString("name");
                String age = profile.getString("age");
                String instruments = profile.getString("instruments");
                String city = profile.getString("city");
                String styles = profile.getString("styles");
                String bio = profile.getString("bio");
                users.add(new UserAccount(email, name, age, instruments, styles, city, bio));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * This AsyncTask handles account credential verification.
     */
    private class SelectProfilesTask extends AsyncTask<String, Void, String> {
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

        /**
         * Temporarily shows a progress bar while the result is determined.
         * @param result represents database query result
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
                Log.d("", result.toString());
            }
        }

    }
}
