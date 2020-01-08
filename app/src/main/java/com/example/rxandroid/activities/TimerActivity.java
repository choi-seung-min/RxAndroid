package com.example.rxandroid.activities;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rxandroid.R;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class TimerActivity extends AppCompatActivity {

    private final static String TAG = TimerActivity.class.getSimpleName();

    Unbinder mUnbinder;
    @BindView(R.id.textView)
    TextView mTextView;

    private int DELAY = 0;
    private int PERIOD = 1000;
    private int count;

    private Timer mTimer = new Timer();

    public void timerStart() {
        count = 0;
        mTimer = new Timer();
        mTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTextView.setText(String.valueOf(count++));
                    }
                });
            }
        }, DELAY, PERIOD);
    }

    public void timerStop() {
        if (mTimer != null) {
            mTimer.cancel();
        }
    }

    public void stop() {
        timerStop();
    }

    @OnClick(R.id.button)
    void timerTask() {
        stop();
        timerStart();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mUnbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        stop();
    }
}
