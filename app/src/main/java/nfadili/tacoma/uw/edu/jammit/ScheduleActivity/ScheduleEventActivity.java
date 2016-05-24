package nfadili.tacoma.uw.edu.jammit.ScheduleActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import nfadili.tacoma.uw.edu.jammit.R;

public class ScheduleEventActivity extends AppCompatActivity implements ScheduleEventInitialFragment.OnScheduleEventInitialFragmentInteractionListener,
                                                        ScheduleEventBioFragment.OnScheduleEventBioFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_event);

        if (findViewById(R.id.fragment_container4)!= null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container4, new ScheduleEventInitialFragment())
                    .commit();
        }
    }

    @Override
    public void onScheduleEventInitialFragmentInteraction(int position) {
        Toast.makeText(getApplicationContext(), "This should take to the next fragment.", Toast.LENGTH_LONG)
                .show();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container4, new ScheduleEventBioFragment())
                .commit();
    }

    @Override
    public void onScheduleEventBioFragmentInteraction(int position) {
        Toast.makeText(getApplicationContext(), "This should take me out of activity.", Toast.LENGTH_LONG)
                .show();
        finish();
    }
}
