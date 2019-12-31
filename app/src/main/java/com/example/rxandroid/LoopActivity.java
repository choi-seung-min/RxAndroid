package com.example.rxandroid;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;

public class LoopActivity extends AppCompatActivity {
    public static final String TAG = LoopActivity.class.getSimpleName();

    @BindView(R.id.lv_log)
    ListView mLogView;

    @BindView(R.id.tv_title)
    TextView mTitle;

    private Unbinder mUnbinder;
    private LogAdapter mLogAdapter;
    private List<String> mLogs;

    Iterable<String> samples  = Arrays.asList("banana", "orange", "apple", "apple mango", "melon", "watermelon");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loop);
        mUnbinder = ButterKnife.bind(this);

        setupLogger();
        setSampleTitle();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        mUnbinder = null;
    }

    @OnClick(R.id.btn_loop)
    void loop() {
        log(">>>>> get an apple :: java");
        for (String s : samples) {
            if (s.contains("apple")) {
                log(s);
                break;
            }
        }
    }

    @OnClick(R.id.btn_loop2)
    void loop2() {
        log(">>>>> get an apple :: rx 1.x");

        //rxJava 1.x
//        rx.Observable.from(samples)
//                .filter(s -> s.contains("apple"))
//                .firstOrDefault("Not found")
//                .subscribe(this::log);
    }


    @OnClick(R.id.btn_loop3)
    void loop3() {
        log(">>>>> get an apple :: rx 2.x");

        // rxJava 2.x
        Observable.fromIterable(samples)
                .skipWhile(s -> !s.contains("apple"))
//                .filter(s -> s.contains("apple"))
                .first("Not found")
                .subscribe(this::log);

    }


    private void setSampleTitle() {
        mTitle.append(Observable.fromIterable(samples).reduce((r, s) -> (r + "\n") + s).blockingGet());
    }

    private void log(String log) {
        mLogs.add(log);
        mLogAdapter.clear();
        mLogAdapter.addAll(mLogs);
    }

    private void setupLogger() {
        mLogs = new ArrayList<>();
        mLogAdapter = new LogAdapter(this, new ArrayList<>());
        mLogView.setAdapter(mLogAdapter);
    }
}
