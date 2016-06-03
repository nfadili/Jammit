package nfadili.tacoma.uw.edu.jammit.EditProfile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import nfadili.tacoma.uw.edu.jammit.R;
import nfadili.tacoma.uw.edu.jammit.editcontent.EditProfileContent;


/**
 * Fragment that allows a user to change a given part of their profile.
 *
 */
public class EditProfileParameterFragment extends Fragment {

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
             updateProfileParameterView(args.getInt(ARG_POSITION));
        } else if (mCurrentPosition != -1) {
            // Set article based on saved instance state defined during onCreateView
            updateProfileParameterView(mCurrentPosition);
        }
    }

    public EditProfileParameterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_profile_parameter, container, false);
    }

    /**
     * Uses a passed integer to help generate the fragment's TextViews
     *
     * @param pos
     */
    public void updateProfileParameterView(final int pos) {
        TextView contentTextView = (TextView) getActivity().findViewById(R.id.prof_param_content);
        contentTextView.setText((CharSequence) EditProfileContent.ITEMS.get(pos).content);
        TextView detailsTextView = (TextView) getActivity().findViewById(R.id.prof_param_details);
        detailsTextView.setText((CharSequence) EditProfileContent.ITEMS.get(pos).details);
        final EditText edit = (EditText) getActivity().findViewById(R.id.editText);
        final Button submitButton = (Button) getActivity().findViewById(R.id.submit_button);
        final EditProfileParameterFragment that = this;

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int param = pos;
                String text = edit.getText().toString();
                if (text != "") {
                    switch (param) {
                        case 2: ((EditProfileActivity) getActivity()).getmAccount().setmName(text);
                            break;
                        case 3: ((EditProfileActivity) getActivity()).getmAccount().setmInstruments(text);
                            break;
                        case 4: ((EditProfileActivity) getActivity()).getmAccount().setmStyles(text);
                            break;
                        case 5: ((EditProfileActivity) getActivity()).getmAccount().setmAge(text);
                            break;
                        case 6: ((EditProfileActivity) getActivity()).getmAccount().setmBio(text);
                            break;
                        case 7: ((EditProfileActivity) getActivity()).getmAccount().setmCity(text);
                            break;
                        default:
                            break;
                    }
                }
                ((EditProfileActivity) getActivity()).sendUpdate();

                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();
            }
        });
    }
}
