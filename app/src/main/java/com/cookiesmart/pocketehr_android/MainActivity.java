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
    public static String UserObjectId = "";
    private final int DATE = 2;
    private final String admin_user_type = "Admin";
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String user_type = intent.getStringExtra("user_type");
        if (user_type.equals(admin_user_type)) {
            setContentView(R.layout.activity_main_admin);
        } else {
            setContentView(R.layout.activity_main_user);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            UserObjectId = data.getStringExtra("userObjectId");
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
        Intent intent = new Intent(this, PatientListActivity_copy.class);
        intent.putExtra("type", DATE);
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

    public void addUser(View v) {
        Intent intent = new Intent(this, CreateUserActivity.class);
        startActivity(intent);
    }

    public void showMyPatients(View v) {
        Intent intent = new Intent(this, CreateUserActivity.class);
        startActivity(intent);
    }

    public void checkInPatient(View v) {
        Intent intent = new Intent(this, CheckinActivity.class);
        startActivity(intent);
    }

    public void dischargePatient(View v) {
        Intent intent = new Intent(this, CreateUserActivity.class);
        startActivity(intent);
    }

    public void orderedTests(View v) {
        Intent intent = new Intent(this, CreateUserActivity.class);
        startActivity(intent);
    }

    public void editHospital(View v) {
        Intent intent = new Intent(this, EditHospitalActivity.class);
        startActivity(intent);
    }
}
