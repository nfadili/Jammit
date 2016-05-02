package nfadili.tacoma.uw.edu.jammit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import nfadili.tacoma.uw.edu.jammit.search.SearchContent;

public class BrowseSearchedActivity extends AppCompatActivity implements SearchListFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_searched);
    }

    @Override
    public void onListFragmentInteraction(SearchContent.SearchedProfileItem item) {

    }
}
