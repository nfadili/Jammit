package nfadili.tacoma.uw.edu.jammit.FindEvents;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import model.EventListing;
import nfadili.tacoma.uw.edu.jammit.R;

/**

 */
public class EventDetailsFragment extends Fragment {

    public static final String ARG_POSITION = "POSITION" ;
    private int mCurrentPosition = -1;
    public String emailRecipient;
    public Button sendEmailButton;


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

        emailRecipient = "";

        sendEmailButton = (Button) getActivity().findViewById(R.id.view_event_send_email_button);
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
}
