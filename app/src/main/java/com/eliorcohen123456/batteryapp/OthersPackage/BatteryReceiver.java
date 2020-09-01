package com.eliorcohen123456.batteryapp.OthersPackage;

import android.app.Notification;
import android.app.NotificationChannel;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import android.app.NotificationManager;
import android.app.PendingIntent;

import com.eliorcohen123456.batteryapp.PagesPackage.MainActivity;
import com.eliorcohen123456.batteryapp.R;

public class BatteryReceiver extends BroadcastReceiver {

    private NotificationCompat.Builder builder;
    private NotificationManager notificationManager;
    private static PendingIntent pendingIntent;
    private int mProgressStatus = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        double level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        double percentage = level / scale;
        mProgressStatus = (int) ((percentage) * 100);
        if (mProgressStatus == 100) {
            final int NOTIFY_ID = 1; // ID of notification
            String id = "1"; // default_channel_id
            String title = "BFF Battery"; // Default Channel
            if (notificationManager == null) {
                notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel mChannel = notificationManager.getNotificationChannel(id);
                if (mChannel == null) {
                    mChannel = new NotificationChannel(id, title, importance);
                    mChannel.enableVibration(true);
                    mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                    notificationManager.createNotificationChannel(mChannel);
                }
                getBuilder(context, id);
            } else {
                getBuilder(context, id);
            }
            Notification notification = builder.build();
            notificationManager.notify(NOTIFY_ID, notification);
        }
    }

    private void getBuilder(Context context, String id) {
        builder = new NotificationCompat.Builder(context, id);
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        pendingIntent = PendingIntent.getActivity(context, 1, intent, 0);
        builder.setContentTitle("BFF Battery")
                .setContentText("Your battery is full, disconnect the charger :)")  // required
                .setSmallIcon(R.drawable.batterypic)  // required
                .setDefaults(Notification.DEFAULT_ALL)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setTicker("BFF Battery")
                .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                .setPriority(Notification.PRIORITY_HIGH);
    }

}
