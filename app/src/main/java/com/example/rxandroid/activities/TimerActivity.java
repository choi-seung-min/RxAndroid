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
        countDownTimerStop();
    }

    //CountDownTimer
    private static final int MILLISINFUTURE = 11 * 1000;
    private static final int COUNT_DOWN_INTERVAL = 1000;

    CountDownTimer mCountDownTimer;

    private void initCountDownTask() {
        mCountDownTimer = new CountDownTimer(MILLISINFUTURE, COUNT_DOWN_INTERVAL) {
            @Override
            public void onTick(long l) {
                mTextView.setText(String.valueOf(count--));
            }

            @Override
            public void onFinish() {
                mTextView.setText("Finish .");
            }
        };
    }

    public void countDownTimerStart() {
        count = 10;
        mCountDownTimer.start();
    }

    public void countDownTimerStop() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }

    @OnClick(R.id.button)
    void timerTask() {
        stop();
        timerStart();
    }

    @OnClick(R.id.button2)
    void countDownTask() {
        stop();
        countDownTimerStart();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mUnbinder = ButterKnife.bind(this);
        initCountDownTask();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        stop();
    }
}
