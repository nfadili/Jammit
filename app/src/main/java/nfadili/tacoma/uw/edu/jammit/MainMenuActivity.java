package nfadili.tacoma.uw.edu.jammit;

import android.content.Intent;
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
    }
//    @Override
//    public void onFragmentInteraction() {
//
//    }
}
