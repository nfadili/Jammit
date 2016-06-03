package nfadili.tacoma.uw.edu.jammit.FindBand;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import model.UserAccount;
import nfadili.tacoma.uw.edu.jammit.LoginActivity;
import nfadili.tacoma.uw.edu.jammit.MainMenuActivity;
import nfadili.tacoma.uw.edu.jammit.R;

public class FindBandActivity extends AppCompatActivity {

    public UserAccount mAccount;

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent action;
        switch(item.getItemId()){
            case R.id.logout_overflow:
                // your action goes here
                Log.e("BACK TO", "LOGOUT");
                if (logoutUser()) {
                    action = new Intent(this, MainMenuActivity.class);
                    action.putExtra("finish", true);
                    action.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(action);
                }
                return true;
            case R.id.action_main:
                // your action goes here
                Log.e("BACK TO", "MAIN");
                action = new Intent(this, MainMenuActivity.class);
                action.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                action.putExtra("loggedInEmail", mAccount.getEmail());
                startActivity(action);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    /**
     * Logs out current user
     *
     * @return true
     */
    public boolean logoutUser() {
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.LOGIN_PREFS), Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(getString(R.string.LOGGEDIN), false).commit();
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menubar, menu);

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_band);

        mAccount = (UserAccount) getIntent().getSerializableExtra("Profile");

        //final EditText byInstrument = (EditText) findViewById(R.id.edittext_search_band_instrument);
        final EditText byCity = (EditText) findViewById(R.id.edittext_search_band_city);
        //final EditText byStyle = (EditText) findViewById(R.id.edittext_search_band_style);

        final Spinner instrSpin = (Spinner) findViewById(R.id.instrument_spinner);
        final Spinner styleSpin = (Spinner) findViewById(R.id.style_spinner);

        ArrayAdapter<String> instrAdapter = new  ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, MainMenuActivity.INSTRUMENT_ARRAY);
        instrSpin.setAdapter(instrAdapter);


        ArrayAdapter<String> styleAdapter = new  ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, MainMenuActivity.STYLE_ARRAY);
        styleSpin.setAdapter(styleAdapter);


        Button mSearchButton = (Button) findViewById(R.id.search_band_button);
        final Intent search = new Intent(this, BrowseSearchedBandsActivity.class);
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.e("Add result1: ", byInstrument.getText().toString());
//                Log.e("Add result2: ", byCity.getText().toString());
//                Log.e("Add result3: ", byStyle.getText().toString());

                search.putExtra("Profile", mAccount);
                search.putExtra("Instrument", instrSpin.getSelectedItem().toString());
                search.putExtra("City", byCity.getText().toString());
                search.putExtra("Style", styleSpin.getSelectedItem().toString());

                startActivity(search);
            }
        });
    }
}
