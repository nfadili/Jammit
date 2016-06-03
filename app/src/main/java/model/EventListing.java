package model;


import java.io.Serializable;
import java.util.Random;

/**
 * Model class representing Event postings inside of Jammit.
 */
public class EventListing implements Serializable{
    private String mPoster;
    private String mPosterEmail;
    private String mCity;
    private String mDateTime;
    private String mTitle;
    private String mDescription;

    /**
     * Constructor for testing purposes. Creates a semi random email address.
     */
    public EventListing() {
        Random rand = new Random();
        mPoster = "";
        mPosterEmail = "test@test.com" + String.valueOf(rand.nextInt() % 50); //semi random email generator
        mCity = "";
        mDateTime = "";
        mTitle = "";
        mDescription = "";
    }

    /**
     * Constructor for actual events posted within Jammit
     * @param posterEmail
     * @param poster
     * @param city
     * @param headline
     * @param description
     * @param dateTime
     */
    public EventListing(String posterEmail, String poster,  String city, String headline, String description, String dateTime) {
        mPoster = poster;
        mPosterEmail = posterEmail;
        mCity = city;
        mDateTime = dateTime;
        mTitle = headline;
        mDescription = description;
    }

    public String getmPoster() {
        return mPoster;
    }
    public String getmPosterEmail() {
        return mPosterEmail;
    }
    public String getmCity() {
        return mCity;
    }
    public String getmDateTime() { return mDateTime; }
    public String getmHeadline() {
        return mTitle;
    }
    public String getmDescription() {
        return mDescription;
    }

    public void setmPoster(String newParam) {
        mPoster = newParam;
    }
    public void setmPosterEmail(String newParam) {
        mPosterEmail = newParam;
    }
    public void setmCity(String newParam) {
        mCity = newParam;
    }
    public void setmDateTime(String newParam) { mDateTime = newParam; }
    public void setmHeadline(String newParam) { mTitle = newParam;
    }
    public void setmDescription(String newParam) {
        mDescription = newParam;
    }
}
