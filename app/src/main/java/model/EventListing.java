package model;


public class EventListing {
    private String mPoster;
    private String mPosterEmail;
    private String mCity;
    private String mDateTime;
    private String mTitle;
    private String mDescription;

    public EventListing() {
        mPoster = "";
        mPosterEmail = "";
        mCity = "";
        mDateTime = "";
        mTitle = "";
        mDescription = "";
    }

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
