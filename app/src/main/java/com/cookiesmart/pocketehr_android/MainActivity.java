package com.cookiesmart.pocketehr_android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";
    public static String USEROBJECTID = "";
    private final int DATE = 2;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            USEROBJECTID = data.getStringExtra("userObjectId");
        } else {
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up login_button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startSettingsActivity();
        } else if (id == R.id.action_help) {
            startAboutActivity();
        }
        return super.onOptionsItemSelected(item);
    }

    public void showPatientList(View v) {
        Intent intent = new Intent(this, PatientListActivity.class);
        intent.putExtra("type", DATE);
        startActivity(intent);
    }

    public void addPatient(View v) {
        Intent intent = new Intent(this, AddPatientContactActivity.class);
        intent.putExtra("action", "add");
        startActivity(intent);
    }

    private void startSettingsActivity() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    private void startAboutActivity() {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    public void startAddHospitalActivity(View v) {
        Intent intent = new Intent(this, AddHospitalActivity.class);
        startActivity(intent);
    }
}
