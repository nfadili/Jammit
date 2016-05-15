package nfadili.tacoma.uw.edu.jammit;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PostBandInitialFragment.OnPostBandInitialFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class PostBandInitialFragment extends Fragment {

    private OnPostBandInitialFragmentInteractionListener mListener;

    public PostBandInitialFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();

        final Button proceedButton = (Button) getActivity().findViewById(R.id.post_band_initial_frag_button);
        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((PostBandActivity) getActivity()).onPostBandInitialFragmentInteraction(1);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post_band_initial, container, false);
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onPostBandInitialFragmentInteraction(1);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnPostBandInitialFragmentInteractionListener) {
            mListener = (OnPostBandInitialFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnPostBandInitialFragmentInteractionListener {

        void onPostBandInitialFragmentInteraction(int position);
    }
}
