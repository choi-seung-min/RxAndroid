package com.example.rxandroid.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.rxandroid.LogAdapter;
import com.example.rxandroid.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.observers.DisposableObserver;

public class OnClickFragment extends Fragment {
    public static final String TAG = OnClickFragment.class.getSimpleName();
    public static final int SEVEN = 7;

    @BindView(R.id.lv_log)
    ListView mLogView;
    @BindView(R.id.btn_click_observer)
    Button mButton;
    @BindView(R.id.btn_click_observer_lambda)
    Button mButtonLambda;
    @BindView(R.id.btn_click_observer_binding)
    Button mButtonBinding;
    @BindView(R.id.btn_click_observer_extra)
    Button mButtonExtra;

    private Unbinder mUnbinder;
    private LogAdapter mLogAdapter;
    private List<String> mLogs;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_onclick, container, false);
        mUnbinder = ButterKnife.bind(this, layout);
        setupLogger();
        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getClickEventObservable()
                .map(s -> "Clicked")
                .subscribe(getObserver());
    }

    private Observable<View> getClickEventObservable() {
        return Observable.create(new ObservableOnSubscribe<View>() {
            @Override
            public void subscribe(ObservableEmitter<View> emitter) throws Exception {
                mButton.setOnClickListener(emitter::onNext);
            }
        });
    }

    private DisposableObserver<? super String> getObserver() {
        return new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
                log(s);
            }

            @Override
            public void onError(Throwable e) {
                log(e.getMessage());
            }

            @Override
            public void onComplete() {
                log("complete");
            }
        };
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
