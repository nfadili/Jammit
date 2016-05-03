package nfadili.tacoma.uw.edu.jammit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);


        Button mEditProfileButton = (Button) findViewById(R.id.edit_profile_button);
        final Intent edit = new Intent(this, EditProfileActivity.class);
        mEditProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(edit);
            }
        });

        Button mSearchMusicianButton = (Button) findViewById(R.id.search_musicians_button);
        final Intent search = new Intent(this, SearchMusicianActivity.class);
        mSearchMusicianButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(search);
            }
        });

        Button mLogoutButton = (Button) findViewById(R.id.action_logout);
        final Intent loginActivity = new Intent(this, LoginActivity.class);
        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(logoutUser()) {
                    startActivity(loginActivity);
                }
            }
        });
    }

    public boolean logoutUser() {
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.LOGIN_PREFS), Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(getString(R.string.LOGGEDIN), false).commit();
        return true;
    }
//    @Override
//    public void onFragmentInteraction() {
//
//    }
}
