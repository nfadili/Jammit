package model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;


/**
 * Class representing a single instance of a Jammit user. Constants and fields match profile information
 * inside the `Profile` database. This class is also responsible for parsing the returned JSON string from
 * the LoginActity and the RegisterActivity. This parsing populates and instatiates the account object
 * for the login session of the app.
 */
public class UserAccount implements Serializable {

    /**
     * Constants representing columns names in the `Profile` database.
     */
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String NAME = "name";
    public static final String AGE = "age";
    public static final String INSTRUMENTS = "instruments";
    public static final String STYLES = "styles";
    public static final String CITY = "city";
    public static final String BIO = "bio";

    /**
     * Fields for authentication purposes.
     */
    private String mEmail;
    private String mPassword;
    private boolean authenticated;

    /**
     * Profile detail fields.
     */
    private String mName;
    private String mAge;
    private String mInstruments;
    private String mCity;
    private String mStyles;
    private String mBio;

    public UserAccount(String email, String password, boolean auth) {
        mEmail = email;
        mPassword = password;
        authenticated = auth;
        mName = "New User";
        mAge = "";
        mInstruments = "";
        mCity = "";
        mStyles = "";
        mBio = "";
    }

    public UserAccount() {
        mEmail = "";
        mPassword = "";
        authenticated = false;
        mName = "New User";
        mAge = "";
        mInstruments = "";
        mCity = "";
        mStyles = "";
        mBio = "";
    }

    public UserAccount(String name, String age, String instruments, String styles, String city, String bio) {
        mName = name;
        mAge = age;
        mInstruments = instruments;
        mStyles = styles;
        mCity = city;
        mBio = bio;
        mEmail = "";
        mPassword = "";
        authenticated = false;
    }


    /**
     * Parses the json string, returns a authenticated account if successful.
     * If unsuccessful, returns an invalid account to be handled by the calling Activity.
     * @return reason or null.
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

    /**
     * Parses the json string, returns an updated account object if successful.
     * If unsuccessful, returns an empty account to be handled by the calling Activity.
     * @return updated or empty account.
     */
    public static UserAccount parseProfileQueryJSON(String profileInfoJSON) {
        UserAccount account = new UserAccount();
        if (profileInfoJSON.contains("email")) {
            try {
                //This cuts off brackets surrounding the JSON object so that it can be passed into the constructor
                if (profileInfoJSON.charAt(0) == '[') {
                    profileInfoJSON = profileInfoJSON.substring(1,profileInfoJSON.length()-1);
                }
                //Parse JSON object
                JSONObject obj = new JSONObject(profileInfoJSON);
                account.setEmail(obj.getString(UserAccount.EMAIL));
                if (obj.getString(UserAccount.NAME) == null || obj.getString(UserAccount.NAME) == "null") { account.setmName("New User"); }
                else { account.setmName(obj.getString(UserAccount.NAME)); }
                if (obj.getString(UserAccount.AGE) == null || obj.getString(UserAccount.AGE) == "null") { account.setmAge("No Age"); }
                else { account.setmAge(obj.getString(UserAccount.AGE));}
                if (obj.getString(UserAccount.INSTRUMENTS) == null || obj.getString(UserAccount.INSTRUMENTS) == "null") { account.setmInstruments("No Instruments"); }
                else { account.setmInstruments(obj.getString(UserAccount.INSTRUMENTS)); }
                if (obj.getString(UserAccount.STYLES) == null || obj.getString(UserAccount.STYLES) == "null") { account.setmStyles("No Styles"); }
                else { account.setmStyles(obj.getString(UserAccount.STYLES)); }
                if (obj.getString(UserAccount.CITY) == null || obj.getString(UserAccount.CITY) == "null") { account.setmCity("No City"); }
                else { account.setmCity(obj.getString(UserAccount.CITY)); }
                if (obj.getString(UserAccount.BIO) == null || obj.getString(UserAccount.BIO) == "null") { account.setmBio("No Bio"); }
                else { account.setmBio(obj.getString(UserAccount.BIO)); }
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
}
