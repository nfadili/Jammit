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
    public static final String NAME = "name";
    public static final String AGE = "age";
    public static final String INSTRUMENTS = "instruments";
    public static final String STYLES = "styles";
    public static final String CITY = "city";
    public static final String BIO = "bio";

    private String mEmail;
    private String mPassword;
    private boolean authenticated;

    /* Profile details */
    private String mName;

    @Override
    public String toString() {
        return "UserAccount{" +
                "mEmail='" + mEmail + '\'' +
                ", mPassword='" + mPassword + '\'' +
                ", authenticated=" + authenticated +
                ", mName='" + mName + '\'' +
                ", mAge='" + mAge + '\'' +
                ", mInstruments='" + mInstruments + '\'' +
                ", mCity='" + mCity + '\'' +
                ", mStyles='" + mStyles + '\'' +
                ", mBio='" + mBio + '\'' +
                '}';
    }

    private String mAge;
    private String mInstruments;
    private String mCity;
    private String mStyles;
    private String mBio;


    public UserAccount(String email, String password, boolean auth) {
        mEmail = email;
        mPassword = password;
        authenticated = auth;
    }

    public UserAccount() {
        mEmail = "";
        mPassword = "";
        authenticated = false;
        mName = "";
        mAge = "";
        mInstruments = "";
        mCity = "";
        mStyles = "";
        mBio = "";
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

    public static UserAccount parseProfileQueryJSON(String profileInfoJSON) {
        UserAccount account = new UserAccount();
        if (profileInfoJSON != null) {
            try {
                //This cuts off brackets surrounding the JSON object so that it can be passed into the constructor
                if (profileInfoJSON.charAt(0) == '[') {
                    profileInfoJSON = profileInfoJSON.substring(1,profileInfoJSON.length()-1);
                }
                //Parse JSON object
                JSONObject obj = new JSONObject(profileInfoJSON);
                account.setEmail(obj.getString(UserAccount.EMAIL));
                account.setmName(obj.getString(UserAccount.NAME));
                account.setmAge(obj.getString(UserAccount.AGE));
                account.setmInstruments(obj.getString(UserAccount.INSTRUMENTS));
                account.setmStyles(obj.getString(UserAccount.STYLES));
                account.setmCity(obj.getString(UserAccount.CITY));
                account.setmBio(obj.getString(UserAccount.BIO));
                account.authenticated = true;
            } catch (JSONException e) {
                e.printStackTrace();
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

    public String getmBio() {return mBio;}

    public void setmBio(String mBio) {this.mBio = mBio;}

    public String getmName() {return mName;}

    public void setmName(String mName) {this.mName = mName;}

    public String getmAge() {return mAge;}

    public void setmAge(String mAge) {this.mAge = mAge;}

    public String getmInstruments() {return mInstruments;}

    public void setmInstruments(String mInstruments) {this.mInstruments = mInstruments;}

    public String getmCity() {return mCity;}

    public void setmCity(String mCity) {this.mCity = mCity;}

    public String getmStyles() {return mStyles;}

    public void setmStyles(String mStyles) {this.mStyles = mStyles;}

    public boolean getAuthenticated() {
        return authenticated;
    }

}
