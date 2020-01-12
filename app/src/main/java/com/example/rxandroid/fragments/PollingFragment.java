package com.example.rxandroid.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.rxandroid.LogAdapter;
import com.example.rxandroid.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PollingFragment extends Fragment {

    private static final long INITIAL_DELAY = 0L;
    private static final long PERIOD = 3L;

    @BindView(R.id.lv_polling_log)
    ListView mLogView;

    Unbinder mUnbinder;

    private LogAdapter mLogAdapter;
    private List<String> mLogs;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_polling, container, false);
        mUnbinder = ButterKnife.bind(this, layout);
        setupLogger();
        return layout;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }

    @OnClick(R.id.btn_polling)
    void polling() { startPollingV1(); }

    @OnClick(R.id.btn_polling2)
    void polling2() { startPollingV2(); }


    private void startPollingV1() {

        Observable<String> ob = Observable.interval(INITIAL_DELAY, PERIOD, TimeUnit.SECONDS)
                .flatMap(o -> Observable.just("polling #1 " + o.toString()));

        ob.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::log);
    }

    private void startPollingV2() {

        Observable<String> ob2 = Observable.just("polling #2")
                .repeatWhen(o -> o.delay(3, TimeUnit.SECONDS));

        ob2.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::log);
    }

    private void log(String log) {
        mLogs.add(log);
        mLogAdapter.clear();
        mLogAdapter.addAll(mLogs);
    }

    private void setupLogger() {
        mLogs = new ArrayList<>();
        mLogAdapter = new LogAdapter(getActivity(), new ArrayList<>());
        mLogView.setAdapter(mLogAdapter);
    }
}
