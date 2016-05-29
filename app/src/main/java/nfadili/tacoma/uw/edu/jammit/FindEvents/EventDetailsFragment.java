package nfadili.tacoma.uw.edu.jammit.FindEvents;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import model.BandOpening;
import model.EventListing;
import nfadili.tacoma.uw.edu.jammit.FindBand.BrowseSearchedBandsActivity;
import nfadili.tacoma.uw.edu.jammit.R;

/**

 */
public class EventDetailsFragment extends Fragment {

    public static final String ARG_POSITION = "POSITION" ;
    //private OnFragmentInteractionListener mListener;
    private int mCurrentPosition = -1;


    public EventDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_details, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        // During startup, check if there are arguments passed to the fragment.
        // onStart is a good place to do this because the layout has already been
        // applied to the fragment at this point so we can safely call the method
        // below that sets the article text.
        Bundle args = getArguments();
        if (args != null) {
            // Set article based on argument passed in
            updateViews(args.getInt(ARG_POSITION));
        } else if (mCurrentPosition != -1) {
            // Set article based on saved instance state defined during onCreateView
            updateViews(mCurrentPosition);
        }

    }

    public void updateViews(int position) {
        EventListing event = ((BrowseSearchedEventsActivity)getActivity()).getmEvents().get(position);



        TextView headlineView = (TextView) getActivity().findViewById(R.id.event_headline);
        headlineView.setText((CharSequence) (event.getmHeadline()));

        TextView cityView = (TextView) getActivity().findViewById(R.id.event_city);
        cityView.setText((CharSequence) (event.getmCity()));

        TextView dateTimeView = (TextView) getActivity().findViewById(R.id.event_date_time);
        dateTimeView.setText((CharSequence) (event.getmDateTime()));

        TextView descriptionView = (TextView) getActivity().findViewById(R.id.event_description);
        descriptionView.setText((CharSequence) (event.getmDescription()));
        TextView posterView = (TextView) getActivity().findViewById(R.id.event_poster);
        posterView.setText((CharSequence) (event.getmPoster()));
        TextView emailView = (TextView) getActivity().findViewById(R.id.event_poster_email);
        emailView.setText((CharSequence) (event.getmPosterEmail()));

    }
//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
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
//
//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
