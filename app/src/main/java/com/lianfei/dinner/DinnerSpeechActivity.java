package com.lianfei.dinner;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by lianfei1314 on 2016/9/22.
 */
public class DinnerSpeechActivity extends AppCompatActivity {
    private final static String TAG = DinnerSpeechActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dinner_speech_layout);
    }
}
