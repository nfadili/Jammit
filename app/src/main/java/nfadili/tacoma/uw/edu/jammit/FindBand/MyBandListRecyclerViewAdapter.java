package nfadili.tacoma.uw.edu.jammit.FindBand;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import model.BandOpening;
import nfadili.tacoma.uw.edu.jammit.FindBand.BandListFragment.OnBandListFragmentInteractionListener;
import nfadili.tacoma.uw.edu.jammit.R;


import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link BandOpening} and makes a call to the
 * specified {@link OnBandListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyBandListRecyclerViewAdapter extends RecyclerView.Adapter<MyBandListRecyclerViewAdapter.ViewHolder> {

    //private final List<DummyItem> mValues;
    private final List<BandOpening> mBands;
    private final OnBandListFragmentInteractionListener mListener;

    public MyBandListRecyclerViewAdapter(List<BandOpening> bands, OnBandListFragmentInteractionListener listener) {
        mBands = bands;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_bandlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        /*
         * TODO - write stuff to show up in recycler
         */
//        holder.mItem = mValues.get(position);
//        holder.mIdView.setText(mValues.get(position).id);
        holder.mContentView.setText(mBands.get(position).getmHeadline());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onBandListFragmentInteraction(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBands.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
//        public final TextView mIdView;
        public final TextView mContentView;


        public ViewHolder(View view) {
            super(view);
            mView = view;
//            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
