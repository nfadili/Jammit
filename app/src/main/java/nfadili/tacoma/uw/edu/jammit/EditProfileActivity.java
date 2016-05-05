package nfadili.tacoma.uw.edu.jammit;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import model.UserAccount;

public class EditProfileActivity extends AppCompatActivity implements EditProfileListFragment.OnListFragmentInteractionListener {

    public UserAccount mAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        mAccount = (UserAccount) getIntent().getSerializableExtra("Profile");

        Toast.makeText(getApplicationContext(), "City " + mAccount.getmCity(), Toast.LENGTH_SHORT)
                .show();
        if (findViewById(R.id.fragment_container)!= null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, new EditProfileListFragment())
                    .commit();

        }

    }

    @Override
    public void onListFragmentInteraction(int parameter) {
        // Capture the student fragment from the activity layout
        EditProfileParameterFragment profParamFragment = (EditProfileParameterFragment) getSupportFragmentManager().findFragmentById(R.id.editprofparam_frag);
        //profParamFragment.setParameter(parameter);
        if (parameter == 0) {
            Toast.makeText(getApplicationContext(), "Changes Submitted!", Toast.LENGTH_SHORT)
                    .show();
            finish();
        } else if (parameter == 1) {
            Toast.makeText(getApplicationContext(), "Image functionality coming soon!", Toast.LENGTH_SHORT)
                    .show();
        } else if (profParamFragment != null) {
            // If courseItem frag is available, we're in two-pane layout...
            // Call a method in the student fragment to update its username
            profParamFragment.updateProfileParameterView(parameter);
        } else {
            // If the frag is not available, we're in the one-pane layout and must swap frags...
            // Create fragment and give it an argument for the selected student
            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            profParamFragment = new EditProfileParameterFragment();
            Bundle args = new Bundle();
            args.putInt(EditProfileParameterFragment.ARG_POSITION, parameter);
            profParamFragment.setArguments(args);
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, profParamFragment)
                    .addToBackStack(null);

            // Commit the transaction
            transaction.commit();
        }
    }
    public UserAccount getmAccount() {
        return mAccount;
    }
}
