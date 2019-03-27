package com.eliorcohen123456.batteryapp;

import android.Manifest;
import android.app.NotificationManager;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class ChangeModePhone extends AppCompatActivity {

    private int brightness;
    private RadioGroup radioGroup1;
    private RadioButton radioButton1, radioButton2, radioButton3, radioButton4;
    private static final int CODE_WRITE_SETTINGS_PERMISSION = 1;
    private static final int REQUEST_CODE_READ_CONTACTS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !notificationManager.isNotificationPolicyAccessGranted()) {
            Intent intent = new Intent(android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
            startActivity(intent);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_CODE_READ_CONTACTS);
        }

        radioGroup1 = findViewById(R.id.radioGroupChange1);
        radioButton1 = findViewById(R.id.radioButtonChange1);
        radioButton2 = findViewById(R.id.radioButtonChange2);
        radioButton3 = findViewById(R.id.radioButtonChange3);
        radioButton4 = findViewById(R.id.radioButtonChange4);

        radioButton1.setText("Classic Mode:\n\nBrightnesses = 50%.  Vibration = ON.\nTime Screen = 30s.  Volume = OFF.\nWi-Fi = ON.  Bluetooth = OFF.");
        radioButton2.setText("Long-life:\n\nBrightnesses = 30%.  Vibration = OFF.\nTime Screen = 30s.  Volume = OFF.\nWi-Fi = OFF.  Bluetooth = OFF.");
        radioButton3.setText("Sleep:\n\nBrightnesses = 30%.  Vibration = ON.\nTime Screen = 30s.  Volume = OFF.\nWi-Fi = OFF.  Bluetooth = OFF.");
        radioButton4.setText("Max Mode:\n\nBrightnesses = 100%.  Vibration = OFF.\nTime Screen = 60s.  Volume = ON.\nWi-Fi = ON.  Bluetooth = ON.");

        boolean permission;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            permission = Settings.System.canWrite(this);
        } else {
            permission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_SETTINGS) == PackageManager.PERMISSION_GRANTED;
        }
        if (permission) {
            Settings.System.putInt(getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS_MODE,
                    Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);

            try {
                brightness = Settings.System.getInt(getContentResolver(),
                        Settings.System.SCREEN_BRIGHTNESS);
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, ChangeModePhone.CODE_WRITE_SETTINGS_PERMISSION);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_SETTINGS}, ChangeModePhone.CODE_WRITE_SETTINGS_PERMISSION);
            }
        }

        final SharedPreferences myPrefs = getSharedPreferences("generalChange", MODE_PRIVATE);  //you can give any name in place of generalChange to your preferences

        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButtonChange1) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (Settings.System.canWrite(ChangeModePhone.this)) {
                            Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, brightness);
                            WindowManager.LayoutParams layoutpars = getWindow().getAttributes();
                            layoutpars.screenBrightness = 0.5f;
                            getWindow().setAttributes(layoutpars);

                            android.provider.Settings.System.putInt(getContentResolver(),
                                    Settings.System.SCREEN_OFF_TIMEOUT, 30000);
                        } else {
                            Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
                            intent.setData(Uri.parse("package:" + getPackageName()));
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }

                    WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                    wifiManager.setWifiEnabled(true);

                    AudioManager am;
                    am = (AudioManager) getBaseContext().getSystemService(Context.AUDIO_SERVICE);
                    am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);

                    BluetoothAdapter bAdapter = BluetoothAdapter.getDefaultAdapter();
                    bAdapter.disable();

                    myPrefs.edit().putInt("selectedChange", 1).commit();
                } else if (checkedId == R.id.radioButtonChange2) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (Settings.System.canWrite(ChangeModePhone.this)) {
                            Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, brightness);
                            WindowManager.LayoutParams layoutpars = getWindow().getAttributes();
                            layoutpars.screenBrightness = 0.3f;
                            getWindow().setAttributes(layoutpars);

                            android.provider.Settings.System.putInt(getContentResolver(),
                                    Settings.System.SCREEN_OFF_TIMEOUT, 30000);
                        } else {
                            Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
                            intent.setData(Uri.parse("package:" + getPackageName()));
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }

                        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                        wifiManager.setWifiEnabled(false);

                        AudioManager am;
                        am = (AudioManager) getBaseContext().getSystemService(Context.AUDIO_SERVICE);
                        am.setRingerMode(AudioManager.RINGER_MODE_SILENT);

                        BluetoothAdapter bAdapter = BluetoothAdapter.getDefaultAdapter();
                        bAdapter.disable();
                    }
                    myPrefs.edit().putInt("selectedChange", 2).commit();
                } else if (checkedId == R.id.radioButtonChange3) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (Settings.System.canWrite(ChangeModePhone.this)) {
                            Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, brightness);
                            WindowManager.LayoutParams layoutpars = getWindow().getAttributes();
                            layoutpars.screenBrightness = 0.3f;
                            getWindow().setAttributes(layoutpars);

                            android.provider.Settings.System.putInt(getContentResolver(),
                                    Settings.System.SCREEN_OFF_TIMEOUT, 30000);
                        } else {
                            Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
                            intent.setData(Uri.parse("package:" + getPackageName()));
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }

                    WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                    wifiManager.setWifiEnabled(false);

                    AudioManager am;
                    am = (AudioManager) getBaseContext().getSystemService(Context.AUDIO_SERVICE);
                    am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);

                    BluetoothAdapter bAdapter = BluetoothAdapter.getDefaultAdapter();
                    bAdapter.disable();

                    myPrefs.edit().putInt("selectedChange", 3).commit();
                } else if (checkedId == R.id.radioButtonChange4) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (Settings.System.canWrite(ChangeModePhone.this)) {
                            Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, brightness);
                            WindowManager.LayoutParams layoutpars = getWindow().getAttributes();
                            layoutpars.screenBrightness = 1.0f;
                            getWindow().setAttributes(layoutpars);

                            android.provider.Settings.System.putInt(getContentResolver(),
                                    Settings.System.SCREEN_OFF_TIMEOUT, 60000);
                        }
                        else {
                            Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
                            intent.setData(Uri.parse("package:" + getPackageName()));
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }

                    WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                    wifiManager.setWifiEnabled(true);

                    AudioManager am;
                    am = (AudioManager) getBaseContext().getSystemService(Context.AUDIO_SERVICE);
                    am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);

                    Intent eintent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(eintent, 1);

                    myPrefs.edit().putInt("selectedChange", 4).commit();
                }
            }
        });

        int s = myPrefs.getInt("selectedChange", 0); //will return 0 when nothing is stored
        if (s == 1) {
            radioButton1.setChecked(true);
        } else if (s == 2) {
            radioButton2.setChecked(true);
        } else if (s == 3) {
            radioButton3.setChecked(true);
        } else if (s == 4) {
            radioButton4.setChecked(true);
        }
    }

    // Sets off the menu of activity_menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_menu_mode, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Options in the activity_menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.options:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
