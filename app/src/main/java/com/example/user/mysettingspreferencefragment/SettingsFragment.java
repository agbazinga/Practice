package com.example.user.mysettingspreferencefragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.widget.Toast;

/**
 * Created by User on 7/1/2016.
 */
public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    private PreferenceScreen mAbout;
    private Preference mHomeAddress;
    private Preference mWorkAddress;
    private SwitchPreference mEnableLogSwitch;
    private SwitchPreference mNFCSwitch;
    private EditTextPreference mEditTextPreference;
    private Context mContext;
    private SharedPreferences sharedPreferences;

    private final String KEY_HOME_ADDRESS = "home_address";
    private final String KEY_WORK_ADDRESS = "work_address";
    private final String KEY_ENABLE_LOG = "enable_log";
    private final String KEY_ENABLE_NFC = "enable_nfc";
    private final String KEY_ABOUT = "about";


    private OnFragmentInteractionListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.main_settings);
        mContext = getActivity();
        // PreferenceManager.setDefaultValues(mContext, R.xml.main_settings, false);
        mAbout = (PreferenceScreen) findPreference(KEY_ABOUT);
        mEditTextPreference = (EditTextPreference) findPreference("dummy_pref_screen_edit_text");
        mEnableLogSwitch = (SwitchPreference) findPreference(KEY_ENABLE_LOG);
        mNFCSwitch = (SwitchPreference) findPreference(KEY_ENABLE_NFC);
        mHomeAddress = findPreference(KEY_HOME_ADDRESS);
        mWorkAddress = findPreference(KEY_WORK_ADDRESS);

        sharedPreferences = getActivity().getSharedPreferences("AppData", Context.MODE_PRIVATE);
//        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        init();



    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {

        if (mAbout == preference) {
            startActivity(new Intent(getActivity(), About.class));
        } else {
            return super.onPreferenceTreeClick(preferenceScreen, preference);
        }

        return false;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        Toast.makeText(mContext, "Shared Preference Changed : Key : " + key, Toast.LENGTH_SHORT).show();
        if (key.equals("dummy_pref_screen_edit_text")) {
            mEditTextPreference.setSummary(sharedPreferences.getString(key, "Default Value"));
        } else if (key.equals(KEY_ABOUT)) {
            mAbout.setSummary(sharedPreferences.getString(key, "Default About"));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updatePreferenceSummary();
    }

    @Override
    public void onPause() {
        super.onPause();


    }

    public interface OnFragmentInteractionListener {
        void OnFragmentInteraction();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    private void init() {
        // Set default values and remove some preferences.

        if (true/*NfcAdapter.getDefaultAdapter(mContext) != null*/) {
            mNFCSwitch.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    final boolean nfcEnabled = (Boolean) newValue;
                    if (nfcEnabled)
                        mNFCSwitch.setSummary("NFC Settings have been enabled.");
                    else
                        mNFCSwitch.setSummary("NFC Settings have been disabled.");
                    return true;
                }
            });
        } else {
            removePreference(KEY_ENABLE_NFC);
        }

        mEnableLogSwitch.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                final boolean logEnabled = (Boolean) newValue;

                if (logEnabled)
                    mEnableLogSwitch.setSummary("Log Settings have been enabled.");
                else
                    mEnableLogSwitch.setSummary("Log Settings have been disabled.");
                return true;
            }
        });

    }

    private void updatePreferenceSummary() {
        mHomeAddress.setSummary(sharedPreferences.getString("home_address", "Enter your home address."));
        mWorkAddress.setSummary(sharedPreferences.getString("work_address", "Enter your work address."));
    }

    private void removePreference(String key) {
        Preference pref = findPreference(key);

        if (pref != null)
            getPreferenceScreen().removePreference(pref);
    }
}