package nfadili.tacoma.uw.edu.jammit;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import nfadili.tacoma.uw.edu.jammit.SearchListFragment.OnSearchListFragmentInteractionListener;
import nfadili.tacoma.uw.edu.jammit.search.SearchContent.SearchedProfileItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link SearchedProfileItem} and makes a call to the
 * specified {@link OnSearchListFragmentInteractionListener}.
 */
public class MySearchListRecyclerViewAdapter extends RecyclerView.Adapter<MySearchListRecyclerViewAdapter.ViewHolder> {

    private final List<SearchedProfileItem> mValues;
    private final OnSearchListFragmentInteractionListener mListener;

    public MySearchListRecyclerViewAdapter(List<SearchedProfileItem> items, OnSearchListFragmentInteractionListener listener) {
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
        holder.mContentView.setText(mValues.get(position).username);
        holder.mAgeView.setText(mValues.get(position).age);
        holder.mCityView.setText(mValues.get(position).city);

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
        public final TextView mContentView;
        public final TextView mAgeView;
        public final TextView mCityView;

        public SearchedProfileItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mContentView = (TextView) view.findViewById(R.id.name);
            mAgeView = (TextView) view.findViewById(R.id.age);
            mCityView = (TextView) view.findViewById(R.id.city);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
