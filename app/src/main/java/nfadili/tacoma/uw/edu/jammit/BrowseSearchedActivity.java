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
import java.util.concurrent.ExecutionException;

import model.UserAccount;
import nfadili.tacoma.uw.edu.jammit.search.SearchContent;

public class BrowseSearchedActivity extends AppCompatActivity implements SearchListFragment.OnSearchListFragmentInteractionListener {

    private final static String PROFILES_URL = "http://cssgate.insttech.washington.edu/~_450atm1/Android/profiles.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_searched);

        if (findViewById(R.id.fragment_container2)!= null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container2, new SearchListFragment())
                    .commit();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        String instrument = getIntent().getStringExtra("Instrument");
        String city = getIntent().getStringExtra("City");
        String age = getIntent().getStringExtra("Age");
        String style = getIntent().getStringExtra("Style");

        Toast.makeText(getApplicationContext(), "Instrument = " + instrument, Toast.LENGTH_SHORT)
                .show();
        Toast.makeText(getApplicationContext(), "City = " + city, Toast.LENGTH_SHORT)
                .show();
        Toast.makeText(getApplicationContext(), "Age = " + age, Toast.LENGTH_SHORT)
                .show();
        Toast.makeText(getApplicationContext(), "Style = " + style, Toast.LENGTH_SHORT)
                .show();

        String result = "";
        SelectProfilesTask task = new SelectProfilesTask();
        try {
            result = task.execute(PROFILES_URL).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Log.e("SearchResults: ", result);

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

    private UserAccount parseListOfProfilesJSON(String JSONString, String searchKey) {
        try {
            JSONArray array = new JSONArray(JSONString);
            for(int i = 0; i < array.length(); i++) {
                JSONObject profile = array.getJSONObject(i);
                String name = profile.getString("name");
                String age = profile.getString("age");
                String instruments = profile.getString("instruments");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new UserAccount();   //Instantiate??
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
