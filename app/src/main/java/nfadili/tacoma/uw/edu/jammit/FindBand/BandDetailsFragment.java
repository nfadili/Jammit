package nfadili.tacoma.uw.edu.jammit.FindBand;


import android.content.Intent;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import model.BandOpening;
import nfadili.tacoma.uw.edu.jammit.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the

 * to handle interaction events.
 */
public class BandDetailsFragment extends Fragment {


    public static final String ARG_POSITION = "POSITION" ;
    public Button sendEmailButton;
    public String emailRecipient;

    private int mCurrentPosition = -1;

    public BandDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onStart() {
        super.onStart();;

        // During startup, check if there are arguments passed to the fragment.
        // onStart is a good place to do this because the layout has already been
        // applied to the fragment at this point so we can safely call the method
        // below that sets the article text.
        Bundle args = getArguments();
        emailRecipient = "";

        sendEmailButton = (Button) getActivity().findViewById(R.id.send_email_button);
        sendEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent email = new Intent(Intent.ACTION_SEND);
                String to = emailRecipient;
                String subject = "";
                String message = "";
                email.putExtra(Intent.EXTRA_EMAIL, new String[] { to });

                email.putExtra(Intent.EXTRA_SUBJECT, subject);
                email.putExtra(Intent.EXTRA_TEXT, message);

                // need this to prompts email client only
                email.setType("message/rfc822");

                startActivity(Intent.createChooser(email, "Choose an Email client :"));
            }
        });
        if (args != null) {
            // Set article based on argument passed in
            updateViews(args.getInt(ARG_POSITION));
        } else if (mCurrentPosition != -1) {
            // Set article based on saved instance state defined during onCreateView
            updateViews(mCurrentPosition);
        }

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_band_details, container, false);
    }

    /**
     * Method to update the fragment's textViews based on the passed parameter, which fetches
     * a band from the database.
     *
     * @param param
     */
    public void updateViews(int param) {

        BandOpening band = ((BrowseSearchedBandsActivity)getActivity()).getmBands().get(param);



        TextView headlineView = (TextView) getActivity().findViewById(R.id.band_headline);
        headlineView.setText((CharSequence) (band.getmHeadline()));
        TextView instrumentView = (TextView) getActivity().findViewById(R.id.band_instrument);
        instrumentView.setText((CharSequence) (band.getmInstrument()));
        TextView styleView = (TextView) getActivity().findViewById(R.id.band_style);
        styleView.setText((CharSequence) (band.getmStyle()));
        TextView cityView = (TextView) getActivity().findViewById(R.id.band_city);
        cityView.setText((CharSequence) (band.getmCity()));
        TextView descriptionView = (TextView) getActivity().findViewById(R.id.band_description);
        descriptionView.setText((CharSequence) (band.getmDescription()));
        TextView posterView = (TextView) getActivity().findViewById(R.id.band_poster);
        posterView.setText((CharSequence) (band.getmPoster()));
        TextView emailView = (TextView) getActivity().findViewById(R.id.band_poster_email);
        emailView.setText((CharSequence) (band.getmPosterEmail()));
        emailRecipient = band.getmPosterEmail();
    }

}
