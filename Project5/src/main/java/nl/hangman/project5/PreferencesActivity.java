package nl.hangman.project5;
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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getPreferenceManager().setSharedPreferencesName(
                "hangman_preferences");
        addPreferencesFromResource(R.xml.preferences);
        getPreferenceManager().getSharedPreferences().
            registerOnSharedPreferenceChangeListener(this);
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
        Log.d(TAG, key);
    }
}
