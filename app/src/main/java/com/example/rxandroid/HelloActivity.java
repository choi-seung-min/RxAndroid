package com.example.rxandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class HelloActivity extends AppCompatActivity {

    public static final String TAG = HelloActivity.class.getSimpleName();

    @BindView(R.id.textView)
    TextView textView;

    private Disposable mDisposable;
    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUnbinder = ButterKnife.bind(this);

//        mDisposable = Observable.create((ObservableOnSubscribe<String>) emitter -> {
////            emitter.onNext("Hello world");
////            emitter.onComplete();
////        }).subscribe(o -> textView.setText(o));
        Observable.just("what? does it works?").subscribe(textView::setText);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!mDisposable.isDisposed()){
            mDisposable.dispose();
        }
        if (mUnbinder != null){
            mUnbinder.unbind();
        }
    }
}
