package nfadili.tacoma.uw.edu.jammit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import model.UserAccount;

/**
 * Activity for viewing one's own Profile.
 */
public class ViewProfileActivity extends AppCompatActivity {
    //private UserAccount mAccount;

    public UserAccount mAccount;

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent action;
        switch(item.getItemId()){
            case R.id.logout_overflow:
                // your action goes here
                Log.e("BACK TO", "LOGOUT");
                if (logoutUser()) {
                    action = new Intent(this, MainMenuActivity.class);
                    action.putExtra("finish", true);
                    action.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(action);
                }
                return true;
            case R.id.action_main:
                // your action goes here
                Log.e("BACK TO", "MAIN");
                action = new Intent(this, MainMenuActivity.class);
                action.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                action.putExtra("loggedInEmail", mAccount.getEmail());
                startActivity(action);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    /**
     * Logs out current user
     *
     * @return true
     */
    public boolean logoutUser() {
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.LOGIN_PREFS), Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(getString(R.string.LOGGEDIN), false).commit();
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menubar, menu);

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        mAccount = (UserAccount) getIntent().getSerializableExtra("Profile");

        TextView nameTextView = (TextView) findViewById(R.id.view_prof_name_act);
        nameTextView.setText((CharSequence) mAccount.getmName());

        TextView ageTextView = (TextView) findViewById(R.id.view_prof_age_act);
        ageTextView.setText((CharSequence) mAccount.getmAge());

        TextView cityTextView = (TextView) findViewById(R.id.view_prof_city_act);
        cityTextView.setText((CharSequence) mAccount.getmCity());

        TextView instrumentsTextView = (TextView) findViewById(R.id.view_prof_instruments_act);
        instrumentsTextView.setText((CharSequence) mAccount.getmInstruments());

        TextView styleTextView = (TextView) findViewById(R.id.view_prof_styles_act);
        styleTextView.setText((CharSequence) mAccount.getmStyles());

        TextView bioTextView = (TextView) findViewById(R.id.view_prof_bio_act);
        bioTextView.setText((CharSequence) mAccount.getmBio());

    }
}
