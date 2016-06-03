package nfadili.tacoma.uw.edu.jammit;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

/**
 * Screen for registering as a new Jammit user.
 */
public class RegisterActivity extends AppCompatActivity {

    /**
     * URL for adding a new user to the `User` table
     */
    private static final String REGISTER_URL
            = "http://cssgate.insttech.washington.edu/~_450atm1/Android/addUser.php";

    /**
     * URL for adding an empty profile into the `Profile` table.
     */
    private static final String EMPTY_PROFILE_URL
            = "http://cssgate.insttech.washington.edu/~_450atm1/Android/addProfile.php";

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private EditText mPasswordVerifyView;
    private View mProgressView;
    private View mLoginFormView;

    //db flag
    private boolean accountCreated = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mEmailView = (AutoCompleteTextView) findViewById(R.id.register_email_text);
        mPasswordView = (EditText) findViewById(R.id.register_password_text);
        mPasswordVerifyView = (EditText) findViewById(R.id.register_password_verify_text);
        mPasswordVerifyView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    //If the register attempt it successful, and empty profile entry is inserted in the database
                    attemptRegister();
                    if (accountCreated) {
                        addEmptyProfile();
                    }
                    return true;
                }
                return false;
            }
        });

        Button mRegisterButton = (Button) findViewById(R.id.register_button);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegister();
                if (accountCreated) {
                    addEmptyProfile();
                }
            }
        });

        mLoginFormView = findViewById(R.id.register_form);
        mProgressView = findViewById(R.id.register_progress);
    }

    /**
     * Utility method for adding an empty row entry into the `Profile` table in the database.
     * Only the email column is filled in.
     */
    private void addEmptyProfile() {
        InsertEmptyProfileTask task = new InsertEmptyProfileTask();
        String addProfileString = EMPTY_PROFILE_URL + "?email=" + mEmailView.getText().toString();
        String resultProfileAdd = "Unable to add profile.";
        try {
            resultProfileAdd = task.execute(new String[]{addProfileString}).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }



    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid login_email_text, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptRegister() {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the register attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        String passwordVerifier = mPasswordVerifyView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid login_password_text, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check if the two passwords match
        if(!password.equals(passwordVerifier)) {
            mPasswordView.setError(getString(R.string.error_mismatch_password));
            mPasswordView.setText("");
            mPasswordVerifyView.setText("");
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid login_email_text address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user register attempt.
            showProgress(true);
            InsertEmptyProfileTask task = new InsertEmptyProfileTask();
            String authString = REGISTER_URL + "?email=" + mEmailView.getText() + "&password=" + mPasswordView.getText();
            try {
                String resultAuth = task.execute(new String[]{authString}).get();
                if (resultAuth.contains("success")) {
                    accountCreated = true;
                }
                else {
                    accountCreated = false;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Determines if user's login_email_text is in the valid format
     * @param email
     * @return
     */
    private boolean isEmailValid(String email) {
        return email.contains("@") && email.contains(".");
    }

    /**
     * Determines if user's login_password_text is valid
     * @param password
     * @return validation result
     */
    private boolean isPasswordValid(String password) {
        return password.length() > 5;
    }

    /**
     * This AsyncTask handles adding an empty profile to the `Profile` table.
     */
    private class InsertEmptyProfileTask extends AsyncTask<String, Void, String> {

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
         * Temporarily shows a progress bar while the result is determined.
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
                Log.e("", result.toString());
            }
            // Everything is good, return to login activity.
            if (result.contains("success")) {
                Toast.makeText(getApplicationContext(), "User account created.", Toast.LENGTH_LONG)
                        .show();
                showProgress(false);
                finish();
            }
            else {
                showProgress(false);
                Toast.makeText(getApplicationContext(), "Unable to add user.", Toast.LENGTH_LONG)
                        .show();
            }
        }
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


}
