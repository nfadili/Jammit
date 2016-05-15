package nfadili.tacoma.uw.edu.jammit.EditProfile;

import android.os.AsyncTask;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

import model.UserAccount;
import nfadili.tacoma.uw.edu.jammit.R;

/**
 * Activity that allows a user to edit one or more parts of their account profile.
 *
 */
public class EditProfileActivity extends AppCompatActivity implements EditProfileListFragment.OnListFragmentInteractionListener {

    /**
     * URL for login authentication with the database server.
     */
    private static final String UPDATE_PROFILE_URL
            = "http://cssgate.insttech.washington.edu/~_450atm1/Android/updateProfile.php";

    public UserAccount mAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        mAccount = (UserAccount) getIntent().getSerializableExtra("Profile");

        if (findViewById(R.id.fragment_container)!= null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, new EditProfileListFragment())
                    .commit();

        }

    }

    @Override
    public void onListFragmentInteraction(int parameter) {
        // Capture the student fragment from the activity layout
        EditProfileParameterFragment profParamFragment = (EditProfileParameterFragment) getSupportFragmentManager().findFragmentById(R.id.editprofparam_frag);
        //profParamFragment.setParameter(parameter);
        if (parameter == 0) {
            //UPDATE `Profile` table with changes
            Log.e("Account: ", mAccount.toString());
            String urlString = "";
            try {
                urlString = UPDATE_PROFILE_URL + "?email=" + mAccount.getEmail() + "&" +
                        "name=" + URLEncoder.encode(mAccount.getmName(), "UTF-8") + "&" +
                        "age=" + URLEncoder.encode(mAccount.getmAge(), "UTF-8") + "&" +
                        "instruments=" + URLEncoder.encode(mAccount.getmInstruments(), "UTF-8") + "&" +
                        "styles=" + URLEncoder.encode(mAccount.getmStyles(), "UTF-8") + "&" +
                        "city=" + URLEncoder.encode(mAccount.getmCity(), "UTF-8") + "&" +
                        "bio=" + URLEncoder.encode(mAccount.getmBio(), "UTF-8");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            Log.e("URL: ", urlString);
            UpdateProfileTask task = new UpdateProfileTask();
            String result = "";
            try {
                result = task.execute(urlString).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            Log.e("Update profile result: ", result);

            Toast.makeText(getApplicationContext(), "Changes Submitted!", Toast.LENGTH_SHORT)
                    .show();
            finish();
        } else if (parameter == 1) {
            Toast.makeText(getApplicationContext(), "Image functionality coming soon!", Toast.LENGTH_SHORT)
                    .show();
        } else if (profParamFragment != null) {
            // If courseItem frag is available, we're in two-pane layout...
            // Call a method in the student fragment to update its username
            profParamFragment.updateProfileParameterView(parameter);
        } else {
            // If the frag is not available, we're in the one-pane layout and must swap frags...
            // Create fragment and give it an argument for the selected student
            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            profParamFragment = new EditProfileParameterFragment();
            Bundle args = new Bundle();
            args.putInt(EditProfileParameterFragment.ARG_POSITION, parameter);
            profParamFragment.setArguments(args);
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, profParamFragment)
                    .addToBackStack(null);

            // Commit the transaction
            transaction.commit();
        }
    }

    /**
     * Gets the activity's UserAccount object.
     *
     * @return the user's account
     */
    public UserAccount getmAccount() {
        return mAccount;
    }

    /**
     * This AsyncTask handles account credential verification.
     */
    private class UpdateProfileTask extends AsyncTask<String, Void, String> {
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
            //Update the profile



            // Displays result info. For debugging
            if (result != null) {
                Log.e("", result.toString());
            }
        }

    }
}
