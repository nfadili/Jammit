package model;

/**
 * Created by Riley Gratzer on 5/23/16.
 */
public class BandOpening {

    private String mPoster;
    private String mPosterEmail;
    private String mCity;
    private String mInstrument;
    private String mStyle;
    private String mHeadline;
    private String mDescription;

    public BandOpening() {
        mPoster = "";
        mPosterEmail = "";
        mCity = "";
        mInstrument = "";
        mStyle = "";
        mHeadline = "";
        mDescription = "";
    }

    public BandOpening(String poster, String posterEmail, String city, String instrument, String style, String headline, String description) {
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