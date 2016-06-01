package nfadili.tacoma.uw.edu.jammit.FindMusicians;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import nfadili.tacoma.uw.edu.jammit.MainMenuActivity;
import nfadili.tacoma.uw.edu.jammit.R;

/**
 * Activity that allows a user to search for other Jammit users that
 * match their preferences.
 *
 */
public class SearchMusicianActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menubar, menu);

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_musician);

        //final EditText byInstrument = (EditText) findViewById(R.id.edittext_search_by_instrument);
        final EditText byCity = (EditText) findViewById(R.id.edittext_search_by_city);
        final EditText byAge = (EditText) findViewById(R.id.edittext_search_by_age);
        //final EditText byStyle = (EditText) findViewById(R.id.edittext_search_by_style);

        final Spinner instrSpin = (Spinner) findViewById(R.id.instrument_spinner);
        final Spinner styleSpin = (Spinner) findViewById(R.id.style_spinner);

        ArrayAdapter<String> instrAdapter = new  ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, MainMenuActivity.INSTRUMENT_ARRAY);
        instrSpin.setAdapter(instrAdapter);


        ArrayAdapter<String> styleAdapter = new  ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, MainMenuActivity.STYLE_ARRAY);
        styleSpin.setAdapter(styleAdapter);

        Button mSearchButton = (Button) findViewById(R.id.search_button);
        final Intent search = new Intent(this, BrowseSearchedActivity.class);
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                search.putExtra("Instrument", instrSpin.getSelectedItem().toString());
                search.putExtra("City", byCity.getText().toString());
                search.putExtra("Age", byAge.getText().toString());
                search.putExtra("Style", styleSpin.getSelectedItem().toString());
                startActivity(search);
            }
        });
    }

}
