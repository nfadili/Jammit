package nfadili.tacoma.uw.edu.jammit.FindMusicians;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import model.UserAccount;
import nfadili.tacoma.uw.edu.jammit.R;


/**
 * A fragment used for viewing profiles of other Jammit users.
 */
public class ViewProfileFragment extends Fragment {

    public static final String ARG_POSITION = "POSITION" ;


    private int mCurrentPosition = -1;
    @Override public void onStart()
    {     super.onStart();

        // During startup, check if there are arguments passed to the fragment.
        // onStart is a good place to do this because the layout has already been
        // applied to the fragment at this point so we can safely call the method
        // below that sets the article text.
        Bundle args = getArguments();
        if (args != null) {
            // Set article based on argument passed in
            updateProfileView(args.getInt(ARG_POSITION));
        } else if (mCurrentPosition != -1) {
            // Set article based on saved instance state defined during onCreateView
            updateProfileView(mCurrentPosition);
        }
        //if (mUser != null) updateProfileViewWithAccount(mUser);
    }
    public ViewProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_profile, container, false);
    }


    /**
     * Uses a passed integer to populate the fragment's TextViews
     *
     * @param pos
     */
    public void updateProfileView(int pos) {
        updateProfileViewWithAccount(((BrowseSearchedActivity)getActivity()).mSelectedUsers.get(pos));
    }

    /**
     * Uses a passed UserAccount to populate the fragment's TextViews
     *
     * @param user
     */
    public void updateProfileViewWithAccount(UserAccount user) {
        TextView nameView = (TextView) getActivity().findViewById(R.id.view_prof_name);
        nameView.setText((CharSequence) user.getmName());

        TextView ageView = (TextView) getActivity().findViewById(R.id.view_prof_age);
        ageView.setText((CharSequence) user.getmAge());

        TextView cityView = (TextView) getActivity().findViewById(R.id.view_prof_city);
        cityView.setText((CharSequence) user.getmCity());

        TextView instrumentsView = (TextView) getActivity().findViewById(R.id.view_prof_instruments);
        instrumentsView.setText((CharSequence) user.getmInstruments());

        TextView stylesView = (TextView) getActivity().findViewById(R.id.view_prof_styles);
        stylesView.setText((CharSequence) user.getmStyles());

        TextView bioView = (TextView) getActivity().findViewById(R.id.view_prof_bio);
        bioView.setText((CharSequence) user.getmBio());
    }
}
