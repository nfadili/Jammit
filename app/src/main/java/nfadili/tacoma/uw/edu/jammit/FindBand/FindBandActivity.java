package nfadili.tacoma.uw.edu.jammit.FindBand;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import nfadili.tacoma.uw.edu.jammit.R;

public class FindBandActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_band);

        final EditText byInstrument = (EditText) findViewById(R.id.edittext_search_band_instrument);
        final EditText byCity = (EditText) findViewById(R.id.edittext_search_band_city);
        final EditText byStyle = (EditText) findViewById(R.id.edittext_search_band_style);


        Button mSearchButton = (Button) findViewById(R.id.search_band_button);
        final Intent search = new Intent(this, BrowseSearchedBandsActivity.class);
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.e("Add result1: ", byInstrument.getText().toString());
//                Log.e("Add result2: ", byCity.getText().toString());
//                Log.e("Add result3: ", byStyle.getText().toString());

                search.putExtra("Instrument", byInstrument.getText().toString());
                search.putExtra("City", byCity.getText().toString());
                search.putExtra("Style", byStyle.getText().toString());

                startActivity(search);
            }
        });
    }

}
