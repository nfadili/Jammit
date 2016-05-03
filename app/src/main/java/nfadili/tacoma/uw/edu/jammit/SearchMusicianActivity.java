package nfadili.tacoma.uw.edu.jammit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SearchMusicianActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_musician);

        Button mSearchButton = (Button) findViewById(R.id.search_by_instrument_button);
        final Intent search = new Intent(this, BrowseSearchedActivity.class);
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(search);
            }
        });
    }
}
