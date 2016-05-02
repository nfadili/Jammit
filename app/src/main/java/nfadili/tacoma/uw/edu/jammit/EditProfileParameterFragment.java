package nfadili.tacoma.uw.edu.jammit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import nfadili.tacoma.uw.edu.jammit.editcontent.EditProfileContent;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the

 * to handle interaction events.
 */
public class EditProfileParameterFragment extends Fragment {

    public static final String ARG_POSITION = "POSITION" ;
    //private OnFragmentInteractionListener mListener;
    private EditProfileContent.ProfileParameter mParameter;
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

//    public void setParameter(int id) {
//
//    }
    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

    public void updateProfileParameterView(int pos) {
//        TextView courseIdTextView = (TextView) getActivity().findViewById(R.id.prof_param_id);
//        courseIdTextView.setText((CharSequence) String.valueOf(EditProfileContent.ITEMS.get(pos).id));
        TextView courseTitleTextView = (TextView) getActivity().findViewById(R.id.prof_param_content);
        courseTitleTextView.setText((CharSequence) EditProfileContent.ITEMS.get(pos).content);
        TextView courseShortDescTextView = (TextView) getActivity().findViewById(R.id.prof_param_details);
        courseShortDescTextView.setText((CharSequence) EditProfileContent.ITEMS.get(pos).details);
        EditText edit = (EditText) getActivity().findViewById(R.id.editText);
        Button submitButton = (Button) getActivity().findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getActivity().getApplicationContext(), "Changes Submitted!", Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
