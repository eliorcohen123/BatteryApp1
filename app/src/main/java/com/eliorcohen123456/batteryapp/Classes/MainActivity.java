package com.eliorcohen123456.batteryapp.Classes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.BatteryManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.eliorcohen123456.batteryapp.BroadcastReceiverAndService.BatteryReceiver;
import com.eliorcohen123456.batteryapp.R;

import guy4444.smartrate.SmartRate;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private TextView mTextViewInfo, mTextViewPercentage, mInfo_amper, mInfo_time, mTextViewInfo2, mTextViewInfo3, mTextFull;
    private ProgressBar mProgressBar1, mProgressBar2, mProgressBar3;
    private int mProgressStatus = 0;
    private RadioGroup rg1;
    private RadioButton rb1, rb2;

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            mTextViewInfo.setText("Battery scale: " + scale);
            double level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            double percentage = level / scale;
            mProgressStatus = (int) ((percentage) * 100);
            double temp = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1);
            double voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, -1);
            double myTemp = temp / 10;
            double myVoltage = voltage / 1000;
            mTextViewPercentage.setText("" + mProgressStatus + "%");
            mTextViewInfo.setText(mTextViewInfo.getText() +
                    "\nPercentage: " + mProgressStatus + "%" + "\ntemp: " + myTemp + "C" + "\nvoltage: " + myVoltage + "V");
            if (mProgressStatus >= 80) {
                mProgressBar1.setVisibility(View.VISIBLE);
                mProgressBar1.setProgress(mProgressStatus);
            } else if (mProgressStatus < 80 && mProgressStatus > 20) {
                mProgressBar2.setVisibility(View.VISIBLE);
                mProgressBar2.setProgress(mProgressStatus);
            } else if (mProgressStatus <= 20) {
                mProgressBar3.setVisibility(View.VISIBLE);
                mProgressBar3.setProgress(mProgressStatus);
            }

            int health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, 0);
            String technology = intent.getExtras().getString(BatteryManager.EXTRA_TECHNOLOGY);

            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            if (status == BatteryManager.BATTERY_STATUS_CHARGING) {
                int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
                if (plugged == BatteryManager.BATTERY_PLUGGED_USB) {
                    mTextViewInfo2.setText("\n\nCharger:\n" + "Battery plugged usb");
                    mTextViewInfo2.setText("Battery health:\n" + healthMode(health) + "\n\nTechnology:\n" + technology + mTextViewInfo2.getText());
                } else if (plugged == BatteryManager.BATTERY_PLUGGED_AC) {
                    mTextViewInfo2.setText("\n\nCharger:\n" + "Battery plugged ac");
                    mTextViewInfo2.setText("Battery health:\n" + healthMode(health) + "\n\nTechnology:\n" + technology + mTextViewInfo2.getText());
                } else if (plugged == BatteryManager.BATTERY_PLUGGED_WIRELESS) {
                    mTextViewInfo2.setText("\n\nCharger:\n" + "Battery plugged wireless");
                    mTextViewInfo2.setText("Battery health:\n" + healthMode(health) + "\n\nTechnology:\n" + technology + mTextViewInfo2.getText());
                }
            } else {
                mTextViewInfo2.setText("Battery health:\n" + healthMode(health) + "\n\nTechnology:\n" + technology);
            }
            getBatteryCapacity();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        initAppRater();
        myBroadcastReceiver();
    }

    private void initUI() {
        mProgressBar1 = findViewById(R.id.pb1);
        mProgressBar2 = findViewById(R.id.pb2);
        mProgressBar3 = findViewById(R.id.pb3);

        mTextViewInfo = findViewById(R.id.tv_info);
        mTextViewPercentage = findViewById(R.id.tv_percentage);
        mInfo_amper = findViewById(R.id.info_amper);
        mInfo_time = findViewById(R.id.tv_time);
        mTextViewInfo2 = findViewById(R.id.tv_info2);
        mTextViewInfo3 = findViewById(R.id.tv_time_charge);
        mTextFull = findViewById(R.id.tv_full);

        rg1 = findViewById(R.id.radioGroupFull1);
        rb1 = findViewById(R.id.radioButtonFull1);
        rb2 = findViewById(R.id.radioButtonFull2);
    }

    private void initAppRater() {
        SmartRate.Rate(MainActivity.this
                , "Rate Us"
                , "Tell others what you think about this app"
                , "Continue"
                , "Please take a moment and rate us on Google Play"
                , "click here"
                , "Ask me later"
                , "Never ask again"
                , "Cancel"
                , "Thanks for the feedback"
                , Color.parseColor("#2196F3")
                , 5
                , 1
                , 1
        );
    }

    private void myBroadcastReceiver() {
        mContext = getApplicationContext();
        IntentFilter iFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        mContext.registerReceiver(mBroadcastReceiver, iFilter);
        mProgressBar1.setVisibility(View.INVISIBLE);
        mProgressBar2.setVisibility(View.INVISIBLE);
        mProgressBar3.setVisibility(View.INVISIBLE);

        mTextFull.setText("Alert when battery\n is full?\n(Keep the app open)");

        final SharedPreferences myPrefs = getSharedPreferences("general", MODE_PRIVATE);

        final IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        final BatteryReceiver receiver = new BatteryReceiver();

        rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButtonFull1) {
                    myPrefs.edit().putInt("selected", 1).apply();
                    registerReceiver(receiver, filter);
                } else if (checkedId == R.id.radioButtonFull2) {
                    myPrefs.edit().putInt("selected", 2).apply();
                    unregisterReceiver(receiver);
                }
            }
        });

        int s2 = myPrefs.getInt("selected", 0);
        if (s2 == 1) {
            rb1.setChecked(true);
        } else if (s2 == 2) {
            rb2.setChecked(true);
        }
    }

    private void getBatteryCapacity() {
        Object mPowerProfile_ = null;
        final String POWER_PROFILE_CLASS = "com.android.internal.os.PowerProfile";

        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = this.registerReceiver(null, ifilter);
        double level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        double temp = batteryStatus.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1);
        double voltage = batteryStatus.getIntExtra(BatteryManager.EXTRA_VOLTAGE, -1);

        try {
            mPowerProfile_ = Class.forName(POWER_PROFILE_CLASS)
                    .getConstructor(Context.class).newInstance(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            double batteryCapacity = (Double) Class
                    .forName(POWER_PROFILE_CLASS)
                    .getMethod("getAveragePower", java.lang.String.class)
                    .invoke(mPowerProfile_, "battery.capacity");
            double percentageBatteryCapacity = level * batteryCapacity / 100;
            mInfo_amper.setText("Battery capacity:\n" + percentageBatteryCapacity + "mAh" + " / " + batteryCapacity + "mAh");
            int timeTotal = (int) (percentageBatteryCapacity / ((voltage / 1000) - (0.016 * (temp / 10))));
            int timeHr = timeTotal / 60;
            int timeMin = timeTotal % 60;
            mInfo_time.setText("Battery time left:\n" + timeHr + "hr " + timeMin + "min");

            double batteryCapacityMe = batteryCapacity - percentageBatteryCapacity;

            int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            if (status == BatteryManager.BATTERY_STATUS_CHARGING) {
                int plugged = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
                if (plugged == BatteryManager.BATTERY_PLUGGED_USB) {
                    int batteryCapacityMeTime = (int) (batteryCapacityMe / 11.2);
                    int timeHrMeTime = batteryCapacityMeTime / 60;
                    int timeMinMeTime = batteryCapacityMeTime % 60;
                    mTextViewInfo3.setText("Charge time remaining:\n" + timeHrMeTime + "hr " + timeMinMeTime + "min");
                    mInfo_time.setVisibility(View.INVISIBLE);
                    mTextViewInfo3.setVisibility(View.VISIBLE);
                } else if (plugged == BatteryManager.BATTERY_PLUGGED_AC) {
                    int batteryCapacityMeTime = (int) (batteryCapacityMe / 11.8);
                    int timeHrMeTime = batteryCapacityMeTime / 60;
                    int timeMinMeTime = batteryCapacityMeTime % 60;
                    mTextViewInfo3.setText("Charge time remaining:\n" + timeHrMeTime + "hr " + timeMinMeTime + "min");
                    mInfo_time.setVisibility(View.INVISIBLE);
                    mTextViewInfo3.setVisibility(View.VISIBLE);
                } else if (plugged == BatteryManager.BATTERY_PLUGGED_WIRELESS) {
                    int batteryCapacityMeTime = (int) batteryCapacityMe / 12;
                    int timeHrMeTime = batteryCapacityMeTime / 60;
                    int timeMinMeTime = batteryCapacityMeTime % 60;
                    mTextViewInfo3.setText("Charge time remaining:\n" + timeHrMeTime + "hr " + timeMinMeTime + "min");
                    mInfo_time.setVisibility(View.INVISIBLE);
                    mTextViewInfo3.setVisibility(View.VISIBLE);
                }
            } else {
                mInfo_time.setVisibility(View.VISIBLE);
                mTextViewInfo3.setVisibility(View.INVISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String healthMode(int health) {
        String result;
        switch (health) {
            case BatteryManager.BATTERY_HEALTH_COLD:
                result = "Cold";
                break;
            case BatteryManager.BATTERY_HEALTH_DEAD:
                result = "Dead";
                break;
            case BatteryManager.BATTERY_HEALTH_GOOD:
                result = "Good";
                break;
            case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                result = "Overheat";
                break;
            case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                result = "Over voltage";
                break;
            case BatteryManager.BATTERY_HEALTH_UNKNOWN:
                result = "Unknown";
                break;
            case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                result = "Unspecified failure";
                break;
            default:
                result = "unknown";
        }
        return result;
    }

    // Sets off the menu of activity_menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Options in the activity_menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mode:
                Intent intentChangeModePhone = new Intent(MainActivity.this, ChangeModePhone.class);
                startActivity(intentChangeModePhone);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
