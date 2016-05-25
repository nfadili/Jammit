package nfadili.tacoma.uw.edu.jammit.PostBandOpening;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import nfadili.tacoma.uw.edu.jammit.R;

public class PostBandActivity extends AppCompatActivity implements PostBandInitialFragment.OnPostBandInitialFragmentInteractionListener,
                                                                PostBandWriteupFragment.OnPostBandWriteupFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_band);

        if (findViewById(R.id.fragment_container3)!= null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container3, new PostBandInitialFragment())
                    .commit();
        }
    }

    @Override
    public void onPostBandInitialFragmentInteraction(int position) {
        Toast.makeText(getApplicationContext(), "This should take to the next fragment.", Toast.LENGTH_LONG)
                .show();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container3, new PostBandWriteupFragment())
                .commit();
    }

    @Override
    public void onPostBandWriteupFragmentInteraction(int position) {
        Toast.makeText(getApplicationContext(), "This should take me out of activity.", Toast.LENGTH_LONG)
                .show();
        finish();
    }
}