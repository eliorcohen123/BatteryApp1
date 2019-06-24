package com.eliorcohen123456.batteryapp.BroadcastReceiverAndService;

import android.app.Notification;
import android.app.NotificationChannel;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.os.Build;
import android.support.v4.app.NotificationCompat;

import android.app.NotificationManager;
import android.app.PendingIntent;

import com.eliorcohen123456.batteryapp.Classes.MainActivity;
import com.eliorcohen123456.batteryapp.R;

// BroadcastReceiver are check if the phone are charged
public class MyBroadcastReceiverBattery extends BroadcastReceiver {

    private NotificationManager notificationManager;

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        assert action != null;
        switch (action) {
            case Intent.ACTION_POWER_CONNECTED: {
                final int NOTIFY_ID = 1; // ID of notification
                String id = "1"; // default_channel_id
                String title = "BatteryApp"; // Default Channel
                PendingIntent pendingIntent;
                NotificationCompat.Builder builder;
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
                    builder = new NotificationCompat.Builder(context, id);
                    intent = new Intent(context, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    pendingIntent = PendingIntent.getActivity(context, 1, intent, 0);
                    builder.setContentTitle("BatteryApp")
                            .setContentText("The phone are charged")  // required
                            .setSmallIcon(R.drawable.batterypic)  // required
                            .setDefaults(Notification.DEFAULT_ALL)
                            .setAutoCancel(true)
                            .setContentIntent(pendingIntent)
                            .setTicker("BatteryApp")
                            .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                } else {
                    builder = new NotificationCompat.Builder(context, id);
                    intent = new Intent(context, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    pendingIntent = PendingIntent.getActivity(context, 1, intent, 0);
                    builder.setContentTitle("BatteryApp")
                            .setContentText("The phone are charged")  // required
                            .setSmallIcon(R.drawable.batterypic)  // required
                            .setDefaults(Notification.DEFAULT_ALL)
                            .setAutoCancel(true)
                            .setContentIntent(pendingIntent)
                            .setTicker("BatteryApp")
                            .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                            .setPriority(Notification.PRIORITY_HIGH);
                }
                Notification notification = builder.build();
                notificationManager.notify(NOTIFY_ID, notification);
                break;
            }
            case Intent.ACTION_POWER_DISCONNECTED: {
                final int NOTIFY_ID = 1; // ID of notification
                String id = "1"; // default_channel_id
                String title = "BatteryApp"; // Default Channel
                PendingIntent pendingIntent;
                NotificationCompat.Builder builder;
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
                    builder = new NotificationCompat.Builder(context, id);
                    intent = new Intent(context, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    pendingIntent = PendingIntent.getActivity(context, 1, intent, 0);
                    builder.setContentTitle("BatteryApp")
                            .setContentText("The phone are disconnected from charge")  // required
                            .setSmallIcon(R.drawable.batterypic)  // required
                            .setDefaults(Notification.DEFAULT_ALL)
                            .setAutoCancel(true)
                            .setContentIntent(pendingIntent)
                            .setTicker("BatteryApp")
                            .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                } else {
                    builder = new NotificationCompat.Builder(context, id);
                    intent = new Intent(context, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    pendingIntent = PendingIntent.getActivity(context, 1, intent, 0);
                    builder.setContentTitle("BatteryApp")
                            .setContentText("The phone are disconnected from charge")  // required
                            .setSmallIcon(R.drawable.batterypic)  // required
                            .setDefaults(Notification.DEFAULT_ALL)
                            .setAutoCancel(true)
                            .setContentIntent(pendingIntent)
                            .setTicker("BatteryApp")
                            .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                            .setPriority(Notification.PRIORITY_HIGH);
                }
                Notification notification = builder.build();
                notificationManager.notify(NOTIFY_ID, notification);
                break;
            }
            case Intent.ACTION_BATTERY_LOW:
                final int NOTIFY_ID = 1; // ID of notification
                String id = "1"; // default_channel_id
                String title = "BatteryApp"; // Default Channel
                PendingIntent pendingIntent;
                NotificationCompat.Builder builder;
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
                    builder = new NotificationCompat.Builder(context, id);
                    intent = new Intent(context, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    pendingIntent = PendingIntent.getActivity(context, 1, intent, 0);
                    builder.setContentTitle("BatteryApp")
                            .setContentText("Your battery is low, Charge your phone :)")  // required
                            .setSmallIcon(R.drawable.batterypic)  // required
                            .setDefaults(Notification.DEFAULT_ALL)
                            .setAutoCancel(true)
                            .setContentIntent(pendingIntent)
                            .setTicker("BatteryApp")
                            .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                } else {
                    builder = new NotificationCompat.Builder(context, id);
                    intent = new Intent(context, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    pendingIntent = PendingIntent.getActivity(context, 1, intent, 0);
                    builder.setContentTitle("BatteryApp")
                            .setContentText("Your battery is low, Charge your phone :)")  // required
                            .setSmallIcon(R.drawable.batterypic)  // required
                            .setDefaults(Notification.DEFAULT_ALL)
                            .setAutoCancel(true)
                            .setContentIntent(pendingIntent)
                            .setTicker("BatteryApp")
                            .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                            .setPriority(Notification.PRIORITY_HIGH);
                }
                Notification notification = builder.build();
                notificationManager.notify(NOTIFY_ID, notification);
                break;
        }
    }

}
