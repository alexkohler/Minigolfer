package kohlerbear.com.minigolfer;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

import com.github.machinarius.preferencefragment.PreferenceFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String KEY_PREF_COLOR_SCHEME = "pref_colorScheme";

    public static Fragment newInstance(/*String param1, String param2*/) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Set up a listener whenever a key changes
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        // Unregister the listener whenever a key changes
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
    {
        if (key.equals(KEY_PREF_COLOR_SCHEME)) {
            String colorSchemePref = key;
            String actionBarColorString = "#607D8B";
            switch (colorSchemePref)
            {
                case "Teal":
                    actionBarColorString = "#607D8B";
                    break;
                case "Orange":
                    actionBarColorString = "#FF5722";
                    break;
                case "Indigo":
                    actionBarColorString = "#3F51B5";
                    break;
                case "Red":
                    actionBarColorString = "#F44336";
                    break;
                default:
                    actionBarColorString = "#607D8B";//Anything goes awry just go with teal
                    break;
            }
//            ((ActionBarActivity)getActivity()).getSupportActionBar().setBackgroundDrawable(getResources().getColor(R.color.ple));
        }

    }

}
