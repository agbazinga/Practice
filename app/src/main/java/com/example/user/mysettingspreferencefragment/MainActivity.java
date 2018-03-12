package com.example.user.mysettingspreferencefragment;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.AlarmClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements TestListener {

    private final String MY_ACTION = "com.example.user.ACTION";

    private final TestReceiver testReceiver = new TestReceiver();

    private IntentFilter testIntentFilter;
    private Context mContext;
    private TextView mTextview;
    private EditText mSearchEditText;
    private ImageView mSearchCancelImageView;
    private SearchView mSearchView;
    private EditText mSearchInnerEditText;
    private RelativeLayout mSelectAllContainer;
    private CheckBox mSelectAllCheckBox;
    private DTMFParam param;
    private AlarmManager alarmManager;
    private PendingIntent alarmIntent;

    private PackageManager mPackageManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mContext = this;
        Log.d("BAZY", "OnCreate Called");
        param = new DTMFParam();

        mTextview = (TextView) findViewById(R.id.main_text_view);
        mSearchEditText = (EditText) findViewById(R.id.search_edit_text);
        mSearchCancelImageView = (ImageView) findViewById(R.id.search_cancel_image_view);
        mSearchCancelImageView.setOnClickListener(mSearchCancelImageViewOnClickListener);

        mSearchView = (SearchView) findViewById(R.id.searchView);
        int id = mSearchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        mSearchInnerEditText = (EditText) mSearchView.findViewById(id);

        mSelectAllContainer = (RelativeLayout) findViewById(R.id.select_all_wrapper);
        mSelectAllContainer.setOnClickListener(mSelectAllOnClickListener);

        mSelectAllCheckBox = (CheckBox) findViewById(R.id.toggle_selection_check);
        mPackageManager = getPackageManager();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                showDialog();
                AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                if (alarmManager != null)
                    alarmManager.cancel(alarmIntent);
            }
        });


        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(android.R.attr.colorPrimaryDark, typedValue, true);
        Log.d("ABHI", "typedValue resource id" + typedValue.resourceId + " typed value data" + typedValue.data);
        mTextview.setTextColor(getResources().getColor(typedValue.resourceId));
        testIntentFilter = new IntentFilter();
        testIntentFilter.addAction(MY_ACTION);
        testIntentFilter.addAction(AudioManager.RINGER_MODE_CHANGED_ACTION);


        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("ABHI", "onQueryTextSubmit :" + query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        mSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.toString().length() > 0) {
                    mSearchCancelImageView.setVisibility(View.VISIBLE);
                    mSearchEditText.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
                } else {
                    mSearchCancelImageView.setVisibility(View.INVISIBLE);
                    mSearchEditText.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_search_api_holo_light, 0, 0, 0);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    View.OnClickListener mSearchCancelImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mSearchView.setIconified(false);
            mSearchView.setQuery(mSearchEditText.getText().toString(), false);
            // mSearchInnerEditText.selectAll();
            mSearchEditText.setText("");
            mSearchEditText.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_search_api_holo_light, 0, 0, 0);

        }
    };

    View.OnClickListener mSelectAllOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d("gaffy", "parent clicked");
            mSelectAllCheckBox.setChecked(!mSelectAllCheckBox.isChecked());
        }
    };

    public void buttonClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.testButton:
                int uid = -1;
                try {
                    uid = mPackageManager.getApplicationInfo("com.android.phone", 0).uid;
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }

                //param.setParamFromXML(this, "airtel");

                mTextview.setText("Parsed : " + param.getBoost() + " " + param.getMultiplyingFactor() + " " + param.getThreshold());

                //    mContext.setTheme(R.style.DefaultAppTheme);
                Toast.makeText(mContext, "Button clicked :" + uid, Toast.LENGTH_SHORT).show();
                //    mSearchEditText.setCursorVisible(false);
                //  mSearchView.clearFocus();

                DisplayMetrics dm = getResources().getDisplayMetrics();

                Log.d("ccazy", "density " + dm.density);
                Log.d("ccazy", "densityDpi " + dm.densityDpi);
                Log.d("ccazy", "heightPixels " + dm.heightPixels);
                Log.d("ccazy", "widthPixels " + dm.widthPixels);
//                alarmIntent = PendingIntent.getBroadcast(this, 0, new Intent("com.example.ALARM"), PendingIntent.FLAG_UPDATE_CURRENT);
//                alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//                long interval = SystemClock.elapsedRealtime() + 10 * 1000;
//                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 5000, 5000, alarmIntent);
                //testReflection();

//                Intent in = new Intent(this, SettingsActivityStudio.class);
//                startActivity(in);
//                WifiManager wifiManager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
//                wifiManager.setWifiEnabled(false);

                //sendBroadcast(new Intent("com.example.foo.ACTION1"));

                buildNotification();


                try {
                    //   mTextview.setText("Hello Ok");
                    mPackageManager.getPackageInfo("com.irctc.fot", PackageManager.GET_ACTIVITIES);
                } catch (PackageManager.NameNotFoundException e) {
                    // mTextview.setText("Hello Not Ok");
                }

                //   createAlarm("custom alarm", 21, 30);
                break;
        }
    }

    private void testReflection() {
        try {

            Class SettingsReflection = Class.forName("android.provider.Settings");
            Log.d("ABHI", "Settings Main Class Found");
            Class SettingsSubClass[] = SettingsReflection.getDeclaredClasses();
            Class SecureReflection = SettingsReflection.getDeclaringClass();

            for (Class InnerClass : SettingsSubClass) {
                if (InnerClass.getSimpleName().equals("Secure"))
                    SecureReflection = InnerClass;
            }
        } catch (ClassNotFoundException e) {
            Log.d("ABHI", "ClassNotFoundException");
        }
    }

    private void buildNotification() {
        RemoteViews r = new RemoteViews(getPackageName(), R.layout.custom_notification);
        r.setInt(R.id.viewdetails, "setBackgroundResource", R.drawable.ripple_round_rectangle);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(android.R.drawable.ic_lock_lock);
        mBuilder.setContentTitle("Notification Title");
        mBuilder.setContentText("This is a sample notification content text");
        mBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText("This is a sample notification content text of big text style which would be completely visible by expanding the notification."));
        mBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_beach_access_black_24dp));
        mBuilder.setColor(getResources().getColor(R.color.colorPrimary, getTheme()));
        Notification notification = mBuilder.build();
        //   notification.bigContentView = r;
        mNotificationManager.notify(99, notification);

    }

    private void showDialog() {
        AlertDialog.Builder mAlertDialogBuilder = new AlertDialog.Builder(this);
        mAlertDialogBuilder.setTitle("Alert Dialog");
        mAlertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        mAlertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        AlertDialog mAlertDialog = mAlertDialogBuilder.create();
        mAlertDialog.setCanceledOnTouchOutside(false);
        mAlertDialog.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("BAZY", "onResume Called");
        registerReceiver(testReceiver, testIntentFilter);
        TestListenerController.addToListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("BAZY", "onPause Called");
        unregisterReceiver(testReceiver);
        TestListenerController.removeFromListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("BAZY", "OnDestroy Called");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d("BAZY", "OnConfiguration changed");

    }

    @Override
    public void onChange() {

        Log.d("ABHI", "on change called");
        mSearchEditText.setText("Hello");
    }

    private final class TestReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(MY_ACTION)) {
                Toast.makeText(context, "My Custom Action", Toast.LENGTH_SHORT).show();
                boolean wake = intent.getBooleanExtra("wake", true);
                PowerManager mPMS = (PowerManager) getSystemService(Context.POWER_SERVICE);
                PowerManager.WakeLock wakeLock = mPMS.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG");
                Log.d("TEST", "Wake : " + wake);
                if (wake) {
                    wakeLock.acquire(500);
                } else {
//                    if (wakeLock != null && wakeLock.isHeld()) {
//                        wakeLock.release();
//                    }
                }
            }
        }
    }


    private void createAlarm(String message, int hours, int minutes) {
        Intent alarmIntent = new Intent(AlarmClock.ACTION_SET_ALARM)
                .putExtra(AlarmClock.EXTRA_MESSAGE, message)
                .putExtra(AlarmClock.EXTRA_HOUR, hours)
                .putExtra(AlarmClock.EXTRA_MINUTES, minutes);

        if (alarmIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(alarmIntent);
        }
    }

}
