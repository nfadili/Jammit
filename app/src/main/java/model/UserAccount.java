package model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * Created by nabilfadili on 4/29/16.
 */
public class UserAccount implements Serializable {

    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";

    private String mEmail;
    private String mPassword;
    private boolean authenticated;

    public UserAccount(String email, String password, boolean auth) {
        mEmail = email;
        mPassword = password;
        authenticated = auth;
    }

    /**
     * Parses the json string, returns aa authenticated account if successful.
     * If unsuccessful, returns an invalid account to be handled by the LoginActivity.
     * @return reason or null if successful.
     */
    public static UserAccount parseUserAccountJSON(String userAccountJSON) {
        UserAccount account = null;
        if (userAccountJSON != null) {
            if (userAccountJSON.contains("success")) {
                try {
                    JSONObject obj = new JSONObject(userAccountJSON);
                    account = new UserAccount(obj.getString(UserAccount.EMAIL), "", true);
                } catch (JSONException e) {
                    Log.e("JSON PARSE ERROR", "Unable to parse data, Reason: " + e.getMessage());
                }
            }
            else {
                return new UserAccount("", "", false);
            }
        }
        return account;
    }


    public String getEmail() {
        return mEmail;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public void setPassword(String pwd) {
        mPassword = pwd;
    }

    public boolean getAuthenticated() {
        return authenticated;
    }

}
