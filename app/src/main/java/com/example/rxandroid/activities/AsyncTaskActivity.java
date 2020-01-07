package com.example.rxandroid.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rxandroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class AsyncTaskActivity extends AppCompatActivity {

    public static final String TAG = AsyncTaskActivity.class.getSimpleName();
    private MyAsyncTask myAsyncTask;

    Unbinder mUnbinder;
    @BindView(R.id.textView)
    TextView mAndroidTextView;
    @BindView(R.id.textView2)
    TextView mRxTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUnbinder = ButterKnife.bind(this);
        initAndroidAsync();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    private void initAndroidAsync(){
        myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute("Hello", "async", "world");
    }

    public class MyAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            StringBuilder word = new StringBuilder();
            for (String s : params) {
                word.append(s).append(" ");
            }
            return word.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            mAndroidTextView.setText(result);
        }
    }
}
