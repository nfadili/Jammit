package nfadili.tacoma.uw.edu.jammit.FindBand;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import model.BandOpening;
import nfadili.tacoma.uw.edu.jammit.R;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnBandListFragmentInteractionListener}
 * interface.
 */
public class BandListFragment extends Fragment {

    // TODO: Customize parameters
    private int mColumnCount = 1;

    private OnBandListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BandListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bandlist_list, container, false);

        List<BandOpening> selectedBands = new ArrayList<BandOpening>();
        selectedBands.add(new BandOpening());
        selectedBands.get(0).setmHeadline("This is listing 0");

        selectedBands.add(new BandOpening());
        selectedBands.get(1).setmHeadline("This is listing 1");

        selectedBands.add(new BandOpening());
        selectedBands.get(2).setmHeadline("This is listing 2");
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyBandListRecyclerViewAdapter(selectedBands, mListener));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnBandListFragmentInteractionListener) {
            mListener = (OnBandListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnBandListFragmentInteractionListener");
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
    public interface OnBandListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onBandListFragmentInteraction(int position);
    }
}
