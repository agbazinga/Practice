package com.example.user.mysettingspreferencefragment;

import android.app.NotificationManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.widget.Toast;


public class SettingsActivity extends AppCompatActivity implements SettingsFragment.OnFragmentInteractionListener {

    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Settings Toolbar");
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getFragmentManager().beginTransaction().replace(R.id.settings_content_separate, new SettingsFragment()).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {

            Intent intent = new Intent();
            try {
                intent.setComponent(new ComponentName("com.example.bazinga.samplelist", "com.example.bazinga.samplelist.ui.activity.StandardActivity"));
               // intent.setAction("com.example.bazinga.samplelist.action.LAUNCH");
                startActivity(intent);
                Toast.makeText(this, "Volume Down", Toast.LENGTH_SHORT).show();
            } catch (ActivityNotFoundException e) {
                Toast.makeText(this, "ActivityNotFoundException", Toast.LENGTH_SHORT).show();
            }

        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void OnFragmentInteraction() {

    }
}
