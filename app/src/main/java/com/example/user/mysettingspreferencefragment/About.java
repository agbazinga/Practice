package com.example.user.mysettingspreferencefragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class About extends AppCompatActivity {

    SharedPreferences mSharedPreferences;
    ListView languageList;
    ArrayList<String> supportedLocale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        languageList = (ListView) findViewById(R.id.language_list);

        supportedLocale = new ArrayList<>();
        supportedLocale.add("ar");
        supportedLocale.add("as");
        supportedLocale.add("az_AZ");
        supportedLocale.add("be");
        supportedLocale.add("bg");
        supportedLocale.add("bn_BD");
        supportedLocale.add("bn_IN");
        supportedLocale.add("ca");

        supportedLocale.add("cs");
        supportedLocale.add("da");
        supportedLocale.add("de");
        supportedLocale.add("el");
        supportedLocale.add("en_GB");
        supportedLocale.add("en_PH");
        supportedLocale.add("en_US");
        supportedLocale.add("en_ZG");

        supportedLocale.add("es_ES");
        supportedLocale.add("es_US");
        supportedLocale.add("et_EE");
        supportedLocale.add("eu_ES");
        supportedLocale.add("fa");
        supportedLocale.add("fi");
        supportedLocale.add("fr");
        supportedLocale.add("fr_CA");
        Collections.sort(supportedLocale);

        ArrayList<Locale> supportedLocaleList = new ArrayList<>(supportedLocale.size());
        ArrayList<String> formattedLocale = new ArrayList<>(supportedLocaleList.size());
        for (String s : supportedLocale) {
            Locale locale = Locale.forLanguageTag(s.replace("_", "-"));
            // Log.d("ABHI", locale.getDisplayLanguage(locale));
            //Log.d("ABHI", "" + supportedLocaleList.isEmpty());
            supportedLocaleList.add(locale);

            if (formattedLocale.isEmpty()) {
                formattedLocale.add(locale.getDisplayLanguage(locale));
            } else {
                if (supportedLocaleList.get(supportedLocaleList.size() - 1).getLanguage().equals(locale.getLanguage())) {

                    //Log.d("ABHI","get display name called");
                    formattedLocale.add(locale.getDisplayName(locale));
                } else {
                    formattedLocale.add(locale.getDisplayLanguage(locale));
                }
            }

        }

        Collections.sort(formattedLocale);

        Log.d("ABHI","en PH , en GB" + Locale.forLanguageTag("en-PH").getLanguage().equals(Locale.forLanguageTag("en-GB").getLanguage()));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "This is about device page", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                SharedPreferences.Editor mEditor = mSharedPreferences.edit();
                mEditor.putString("about", "About Summary");
                mEditor.commit();
            }
        });
    }

}
