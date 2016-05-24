package model;


public class EventListing {
    private String mPoster;
    private String mPosterEmail;
    private String mCity;
    private String mDateTime;
    private String mHeadline;
    private String mDescription;

    public EventListing() {
        mPoster = "";
        mPosterEmail = "";
        mCity = "";
        mDateTime = "";
        mHeadline = "";
        mDescription = "";
    }

    public EventListing(String poster, String posterEmail, String city, String dateTime, String headline, String description) {
        mPoster = poster;
        mPosterEmail = posterEmail;
        mCity = city;
        mDateTime = dateTime;
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
    public String getmDateTime() { return mDateTime; }
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
    public void setmDateTime(String newParam) { mDateTime = newParam; }
    public void setmHeadline(String newParam) { mHeadline = newParam;
    }
    public void setmDescription(String newParam) {
        mDescription = newParam;
    }
}
