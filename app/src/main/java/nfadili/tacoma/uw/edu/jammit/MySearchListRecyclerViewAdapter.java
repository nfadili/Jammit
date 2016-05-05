package nfadili.tacoma.uw.edu.jammit;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import nfadili.tacoma.uw.edu.jammit.SearchListFragment.OnSearchListFragmentInteractionListener;
import model.UserAccount;

/**
 * {@link RecyclerView.Adapter} that can display a {@link UserAccount} and makes a call to the
 * specified {@link OnSearchListFragmentInteractionListener}.
 */
public class MySearchListRecyclerViewAdapter extends RecyclerView.Adapter<MySearchListRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<UserAccount> mValues;
    private final OnSearchListFragmentInteractionListener mListener;

    public MySearchListRecyclerViewAdapter(ArrayList<UserAccount> items, OnSearchListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_searchlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.mNameView.setText(mValues.get(position).getmName());
        holder.mAgeView.setText(mValues.get(position).getmAge());
        holder.mCityView.setText(mValues.get(position).getmCity());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onSearchListFragmentInteraction(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mNameView;
        public final TextView mAgeView;
        public final TextView mCityView;
//        public final TextView mInstrumentsView;
//        public final TextView mStylesView;
//        public final TextView mBioView;

        public UserAccount mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNameView = (TextView) view.findViewById(R.id.name);
            mAgeView = (TextView) view.findViewById(R.id.age);
            mCityView = (TextView) view.findViewById(R.id.city);
//            mNameView = (TextView) view.findViewById(R.id.instruments);
//            mAgeView = (TextView) view.findViewById(R.id.);
//            mCityView = (TextView) view.findViewById(R.id.city);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNameView.getText() + "'";
        }
    }
}
