package nfadili.tacoma.uw.edu.jammit.FindBand;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import nfadili.tacoma.uw.edu.jammit.R;

public class BrowseSearchedBandsActivity extends AppCompatActivity implements BandListFragment.OnBandListFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_searched_bands);

        if (findViewById(R.id.fragment_container5)!= null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container5, new BandListFragment())
                    .commit();
        }
    }

    @Override
    public void onBandListFragmentInteraction(int position) {

    }
}
