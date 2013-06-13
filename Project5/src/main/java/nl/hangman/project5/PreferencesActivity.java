package nl.hangman.project5;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.util.Log;

/**
 * Created by steven on 6/5/13.
 */
public class PreferencesActivity extends PreferenceActivity implements
    SharedPreferences.OnSharedPreferenceChangeListener {
    private static final String TAG = "PreferencesActivity";
    private SharedPreferences sPref;
    private boolean changedPrefs;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getPreferenceManager().setSharedPreferencesName(
                "hangman_preferences");
        addPreferencesFromResource(R.xml.preferences);
        getPreferenceManager().getSharedPreferences().
            registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        changedPrefs = false;
    }

    protected void onDestroy() {
        getPreferenceManager().getSharedPreferences().
            unregisterOnSharedPreferenceChangeListener(this);

        super.onDestroy();
    }

    protected void onResume() {
        super.onResume();
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        changedPrefs = true;
    }

    @Override
    public void onBackPressed() {
        if(changedPrefs == true) {
            Intent data = new Intent();
            data.putExtra("changedPrefs", true);
            setResult(RESULT_OK, data);

            super.onBackPressed();
        }else {
            super.onBackPressed();
        }
    }
}
