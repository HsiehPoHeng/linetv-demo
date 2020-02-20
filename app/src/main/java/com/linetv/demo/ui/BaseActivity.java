package com.linetv.demo.ui;

import android.app.Activity;
import android.os.Bundle;

import com.linetv.demo.DramasSampleApplication;

public class BaseActivity extends Activity {
    protected DramasSampleApplication App;
    private BaseActivity pthis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         pthis = this;
         App = ((DramasSampleApplication) pthis.getApplicationContext());
    }
}
