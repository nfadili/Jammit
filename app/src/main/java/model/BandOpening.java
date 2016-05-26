package model;

import java.io.Serializable;
import java.util.Random;

/**
 * Created by Riley Gratzer on 5/23/16.
 */
public class BandOpening implements Serializable {

    private String mPoster;
    private String mPosterEmail;
    private String mCity;
    private String mInstrument;
    private String mStyle;
    private String mHeadline;
    private String mDescription;

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
