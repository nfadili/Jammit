package model;

import java.io.Serializable;
import java.util.Random;

/**
 * Model class for representing band openings within Jammit.
 */
public class BandOpening implements Serializable {

    private String mPoster;
    private String mPosterEmail;
    private String mCity;
    private String mInstrument;
    private String mStyle;
    private String mHeadline;
    private String mDescription;

    /**
     * Default constructor that generates a semi-random email for testing.
     */
    public BandOpening() {
        Random rand = new Random();
        mPoster = "";
        mPosterEmail = "test@test.com" + String.valueOf(rand.nextInt() % 50);   //semi random email generator
        mCity = "";
        mInstrument = "";
        mStyle = "";
        mHeadline = "";
        mDescription = "";
    }

    /**
     * Constructor for actual band opening posts.
     * @param posterEmail
     * @param poster
     * @param headline
     * @param instrument
     * @param style
     * @param city
     * @param description
     */
    public BandOpening(String posterEmail, String poster, String headline, String instrument, String style, String city, String description) {
        mPoster = poster;
        mPosterEmail = posterEmail;
        mCity = city;
        mInstrument = instrument;
        mStyle = style;
        mHeadline = headline;
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
    public String getmInstrument() {
        return mInstrument;
    }
    public String getmStyle() {
        return mStyle;
    }
    public String getmHeadline() {
        return mHeadline;
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
    public void setmInstrument(String newParam) {
        mInstrument = newParam;
    }
    public void setmStyle(String newParam) {
        mStyle = newParam;
    }
    public void setmHeadline(String newParam) {
        mHeadline = newParam;
    }
    public void setmDescription(String newParam) {
        mDescription = newParam;
    }
}
