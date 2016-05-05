package nfadili.tacoma.uw.edu.jammit;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * Activity that allows a user to browse through a list of users that matched a previously
 * entered set of parameters to meet their band-finding needs.
 * 
 */
public class BrowseSearchedActivity extends AppCompatActivity implements SearchListFragment.OnSearchListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_searched);

        if (findViewById(R.id.fragment_container2)!= null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container2, new SearchListFragment())
                    .commit();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        String instrument = getIntent().getStringExtra("Instrument");
        String city = getIntent().getStringExtra("City");
        String age = getIntent().getStringExtra("Age");
        String style = getIntent().getStringExtra("Style");

        //SEARCH!

    }

    @Override
    public void onSearchListFragmentInteraction(int position) {
        // Capture the student fragment from the activity layout
        ViewProfileFragment viewProfile = (ViewProfileFragment) getSupportFragmentManager().findFragmentById(R.id.view_prof_frag);
        //profParamFragment.setParameter(parameter);
        if (viewProfile != null) {
            // If courseItem frag is available, we're in two-pane layout...
            // Call a method in the student fragment to update its username
            viewProfile.updateProfileView(position);
        } else {
            // If the frag is not available, we're in the one-pane layout and must swap frags...
            // Create fragment and give it an argument for the selected student
            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            viewProfile = new ViewProfileFragment();
            Bundle args = new Bundle();
            args.putInt(ViewProfileFragment.ARG_POSITION, position);
            viewProfile.setArguments(args);
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container2, viewProfile)
                    .addToBackStack(null);

            // Commit the transaction
            transaction.commit();
        }
    }
}
