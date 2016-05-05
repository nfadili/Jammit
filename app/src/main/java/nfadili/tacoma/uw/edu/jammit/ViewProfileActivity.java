package nfadili.tacoma.uw.edu.jammit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import model.UserAccount;
import nfadili.tacoma.uw.edu.jammit.editcontent.EditProfileContent;

public class ViewProfileActivity extends AppCompatActivity {
    private UserAccount mAccount;

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
