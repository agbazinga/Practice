package com.example.user.mysettingspreferencefragment;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    private final String ACTION = "com.example.foo.ACTION1";

    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        Toast.makeText(context, intent.getAction(), Toast.LENGTH_SHORT).show();

        Log.d("bazi", intent.getAction());
        if (intent.getAction().equals(ACTION)) {
            int speed = intent.getIntExtra("speed", 10);
            Log.d("bazi", "custom action :" + speed);
            buildNotification(context);
            //TestListenerController.notifyListener();
        } else if (intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();

            if (networkInfo != null) {
                Log.d("bazi", "networkInfo.isConnected() " + networkInfo.isConnected());
                Log.d("bazi", "networkInfo.isConnectedOrConnecting() " + networkInfo.isConnectedOrConnecting());
                Log.d("bazi", "networkInfo.getType() " + networkInfo.getType());
                Log.d("bazi", "networkInfo.getTypeName() " + networkInfo.getTypeName());
                Log.d("bazi", "networkInfo.getReason() " + networkInfo.getReason());
                Log.d("bazi", "networkInfo.getExtraInfo() " + networkInfo.getExtraInfo());
                Log.d("bazi", "networkInfo.isFailover() " + networkInfo.isFailover());

            }
        }
    }

    private void buildNotification(Context context) {
        RemoteViews customNotificationView = new RemoteViews(context.getPackageName(), R.layout.custom_notification);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setContentText("Tap here to launch main activity")
                .setAutoCancel(false)
                .setStyle(new NotificationCompat.BigTextStyle().bigText("This is a big text (BigTextStyle)")
                        .setBigContentTitle("Big Text Style Title").setSummaryText("Big Text Style Summary Text"))
                .setContentTitle("Sample Notification")
                .setNumber(3)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setSmallIcon(android.R.drawable.ic_lock_idle_alarm);



        Intent viewPagerIntent = new Intent(context, ViewPagerTest.class);
        //viewPagerIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent viewPagerPendingIntent = PendingIntent.getActivity(context, 0, viewPagerIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent aboutIntent = new Intent(context, About.class);
        //aboutIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent aboutPendingIntent = PendingIntent.getActivity(context, 0, aboutIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.addAction(android.R.drawable.ic_media_pause, "About", aboutPendingIntent);
        mBuilder.setSubText("This is a subtext");
        mBuilder.setContentIntent(viewPagerPendingIntent);

        NotificationManager mNotificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(1000, mBuilder.build());

        // min sdk 24
       /* Notification.Action aboutAction = new Notification.Action.Builder(Icon.createWithResource(context, android.R.drawable.ic_media_pause), "About", aboutPendingIntent).build();
        Notification notification = new Notification.Builder(context)
                .setSmallIcon(android.R.drawable.checkbox_on_background)
                .setCustomContentView(customNotificationView)
                .setStyle(new Notification.DecoratedCustomViewStyle())
                .addAction(aboutAction)
                .build();
        mNotificationManager.notify(1001, notification);*/

    }

}
