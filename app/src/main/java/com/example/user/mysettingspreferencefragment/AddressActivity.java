package com.example.user.mysettingspreferencefragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddressActivity extends AppCompatActivity implements View.OnClickListener {

    private String mAddressType;
    private EditText mAddressEditText;
    private Button mDone;
    private Button mCancel;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        mAddressType = (String) getIntent().getExtras().get("address_type");
        sharedPreferences = getSharedPreferences("AppData", Context.MODE_PRIVATE);
        mAddressEditText = (EditText) findViewById(R.id.address_edit_text);
        mCancel = (Button) findViewById(R.id.cancel_button);
        mDone = (Button) findViewById(R.id.done_button);

        mCancel.setOnClickListener(this);
        mDone.setOnClickListener(this);

        mAddressEditText.setHint("Enter your " + mAddressType + " address");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        Intent intent = getIntent();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.cancel_button)
            finish();
        else if (id == R.id.done_button) {
            if (mAddressType != null && mAddressType.equals("home"))
                sharedPreferences.edit().putString("home_address", mAddressEditText.getText().toString()).commit();
            if (mAddressType != null && mAddressType.equals("work"))
                sharedPreferences.edit().putString("work_address", mAddressEditText.getText().toString()).commit();

            finish();
        }

    }
}
