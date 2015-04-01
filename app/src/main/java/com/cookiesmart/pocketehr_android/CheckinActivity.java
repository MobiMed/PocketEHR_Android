package com.cookiesmart.pocketehr_android;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

/**
 * Created by aditya841 on 4/1/2015.
 */
public class CheckinActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkin);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.checkin, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
