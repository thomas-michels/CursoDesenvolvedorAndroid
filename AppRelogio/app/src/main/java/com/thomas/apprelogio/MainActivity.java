package com.thomas.apprelogio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ViewHolder mViewHolder = new ViewHolder();
    private Handler mHandler = new Handler();
    private Runnable mRunnable;
    private boolean mTicker = false;
    private boolean mLandscape = false;

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            mViewHolder.textBattery.setText(String.format("%s%%", level));
        }
    };

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        this.mViewHolder.textHourMinute = findViewById(R.id.text_hour_minute);
        this.mViewHolder.textSeconds = findViewById(R.id.text_seconds);
        this.mViewHolder.textBattery = findViewById(R.id.text_battery);
        this.mViewHolder.textNight = findViewById(R.id.text_night);
    }

    @Override
    protected void onResume() {
        this.mTicker = true;
        this.registerReceiver(this.mReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        this.startClock();

        this.mLandscape = (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE);

        super.onResume();
    }

    @Override
    protected void onPause() {
        this.mTicker = false;
        this.unregisterReceiver(this.mReceiver);
        super.onPause();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_IMMERSIVE |
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |

                            // Esconde nav bar e status bar
                            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_FULLSCREEN
            );
        }
    }

    private void startClock() {

        Calendar calendar = Calendar.getInstance();

        this.mRunnable = new Runnable() {
            @Override
            public void run() {

                if (!mTicker) {
                    return;
                }
                calendar.setTimeInMillis(System.currentTimeMillis());
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minutes = calendar.get(Calendar.MINUTE);
                int seconds = calendar.get(Calendar.SECOND);

                // %d = converter inteiro em string - 02 = casas decimais
                mViewHolder.textHourMinute.setText(String.format(Locale.getDefault(),"%02d:%02d", hour, minutes));
                mViewHolder.textSeconds.setText(String.format(Locale.getDefault(),"%02d", seconds));

                if (mLandscape) {
                    if (hour >= 18) {
                        mViewHolder.textNight.setVisibility(View.VISIBLE);
                    } else {
                        mViewHolder.textNight.setVisibility(View.GONE);
                    }
                }

                long now = SystemClock.elapsedRealtime();
                long next = now + (1000 - (now % 1000));

                mHandler.postAtTime(mRunnable, next);
            }
        };
        mRunnable.run();
    }

    private static class ViewHolder {
        TextView textHourMinute;
        TextView textSeconds;
        TextView textBattery;
        TextView textNight;
    }
}