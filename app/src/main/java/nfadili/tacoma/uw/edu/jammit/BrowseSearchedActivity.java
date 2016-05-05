package nfadili.tacoma.uw.edu.jammit;

import android.os.AsyncTask;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_searched);

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
        Log.e("AllSearchResults: ", result);
        ArrayList<UserAccount> allUsers = parseListOfProfilesJSON(result);
        mSelectedUsers = trimList(allUsers);
        Log.e("SearchResults: ", mSelectedUsers.toString());

        if (mSelectedUsers.size() == 0) {
            Toast.makeText(getApplicationContext(), "No users match search query.", Toast.LENGTH_LONG)
                    .show();
        }
        if (findViewById(R.id.fragment_container2)!= null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container2, new SearchListFragment())
                    .commit();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
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
                String name = profile.getString("name");
                String age = profile.getString("age");
                String instruments = profile.getString("instruments");
                String city = profile.getString("city");
                String styles = profile.getString("styles");
                String bio = profile.getString("bio");
                users.add(new UserAccount(name, age, instruments, styles, city, bio));

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
                Log.e("", result.toString());
            }
        }

    }
}
