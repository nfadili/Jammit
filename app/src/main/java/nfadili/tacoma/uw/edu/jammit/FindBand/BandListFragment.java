package nfadili.tacoma.uw.edu.jammit.FindBand;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


import model.BandOpening;

import nfadili.tacoma.uw.edu.jammit.R;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnBandListFragmentInteractionListener}
 * interface.
 */
public class BandListFragment extends Fragment {


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

    /**
     * Method to parse a JSON string into a usable List of BandOpenings
     *
     * @param result
     * @return the ArrayList of BandOpenings
     */
    private ArrayList<BandOpening> parseResult(String result) {
        ArrayList<BandOpening> parsedList = new ArrayList<BandOpening>();

        try {
            JSONArray array = new JSONArray(result);
            JSONObject profile;

            for(int i = 0; i < array.length(); i++) {
                profile = array.getJSONObject(i);
                String name = profile.getString("name");
                String email = profile.getString("email");
                String instruments = profile.getString("instruments");
                String city = profile.getString("city");
                String styles = profile.getString("styles");
                String description = profile.getString("description");
                String title = profile.getString("headline");
                parsedList.add(new BandOpening(email, name, title, instruments, styles, city, description));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return parsedList;
    }

    /**
     * Method to take BandOpenings from the database and trim down the results based on
     * search criteria.
     *
     * @param oldList
     * @return A new list of BandOpenings fitting user's search criteria
     */
    private ArrayList<BandOpening> trimResults(ArrayList<BandOpening> oldList) {
        ArrayList<BandOpening> trimmedList = new ArrayList<BandOpening>();

        String targetCity = ((BrowseSearchedBandsActivity) getActivity()).getmCity();
        String targetStyle = ((BrowseSearchedBandsActivity) getActivity()).getmStyle();
        String targetInstrument = ((BrowseSearchedBandsActivity) getActivity()).getmInstrument();

        for (int i = 0; i < oldList.size(); i++) {
            if (targetInstrument != "" && oldList.get(i).getmInstrument().contains(targetInstrument)) {

                    if (targetCity != "" && oldList.get(i).getmCity().contains(targetCity)) {
                        if (targetStyle != "" && oldList.get(i).getmStyle().contains(targetStyle)) {
                            trimmedList.add(oldList.get(i));
                        }
                    }

            }
        }

        return trimmedList;

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bandlist_list, container, false);

        String bands = ((BrowseSearchedBandsActivity) getActivity()).getResult();
        Log.e("string bands", bands);
        ArrayList<BandOpening> selectedBands = parseResult(bands);

        Log.e("parsed bands tostring", selectedBands.toString());

        selectedBands = trimResults(selectedBands);
        Log.e("Selected bands tostring", selectedBands.toString());
        ((BrowseSearchedBandsActivity) getActivity()).setmBands(selectedBands);

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

        void onBandListFragmentInteraction(int position);
    }
}
