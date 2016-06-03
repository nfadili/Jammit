package nfadili.tacoma.uw.edu.jammit.EditProfile;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.CheckBox;

import android.widget.LinearLayout;

import android.widget.TextView;


import nfadili.tacoma.uw.edu.jammit.MainMenuActivity;
import nfadili.tacoma.uw.edu.jammit.R;
import nfadili.tacoma.uw.edu.jammit.editcontent.EditProfileContent;

/**
 * Fragment for dealing with edit profile parameters dealing with check boxes.
 */
public class EditProfileOptionFragment extends Fragment {



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

    /**
     * Method to programmatically generate the views and check boxes for editting profile options.
     *
     * @param position
     */
    public void updateProfileParameterView(final int position) {
        final LinearLayout ll = (LinearLayout) getActivity().findViewById(R.id.edit_act_lin_layout);

        TextView contentTextView = (TextView) getActivity().findViewById(R.id.profile_option_frag);
        contentTextView.setText((CharSequence) EditProfileContent.ITEMS.get(position).content);

        String[] options = new String[]{};
        if (position == 3) {
            contentTextView.setText((CharSequence) "Please select from the instruments below:");
            options = MainMenuActivity.INSTRUMENT_ARRAY;

        } else if (position == 4) {
            contentTextView.setText((CharSequence) "Please select from the styles below:");
            options = MainMenuActivity.STYLE_ARRAY;

        }

        final CheckBox[] cbs = new CheckBox[options.length];
        for (int i = 1; i < options.length; i++) {
            cbs[i] = new CheckBox(getActivity());
            cbs[i].setText(options[i]);
            cbs[i].setId(i+200);
            cbs[i].setPadding(75, 10, 10, 10);
            cbs[i].setGravity(Gravity.CENTER);

            ll.addView(cbs[i]);
        }
        ((EditProfileActivity) getActivity()).hasCheckBoxes = true;

        final Button submitButton = new Button(getActivity());
        submitButton.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        submitButton.setText((CharSequence) "Submit");

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int param = position;

                String s = "";
                for (int i = 1; i < cbs.length; i++) {
                    if (cbs[i].isChecked()) {
                        if (s == "") {
                            s += cbs[i].getText().toString();
                        } else {
                            s = s + ", " + cbs[i].getText().toString();
                        }
                    }
                }
                Log.d("Resulting string", s);

                if (param == 3) {
                    ((EditProfileActivity) getActivity()).getmAccount().setmInstruments(s);
                } else if (param == 4) {
                    ((EditProfileActivity) getActivity()).getmAccount().setmStyles(s);
                }
                ll.removeAllViews();

                ((EditProfileActivity) getActivity()).sendUpdate();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();
            }
        });
        ll.addView(submitButton);
    }
    public EditProfileOptionFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_profile_option, container, false);
    }

}
