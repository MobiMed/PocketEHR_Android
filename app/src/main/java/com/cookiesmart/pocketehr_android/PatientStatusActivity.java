package com.cookiesmart.pocketehr_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cookiesmart.pocketehr_android.HelperClasses.Visit;

/**
 * Created by aditya841 on 4/14/2015.
 */
public class PatientStatusActivity extends Activity {
    private Intent orgIntent;
    private Visit visit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patientstatus);
        orgIntent = getIntent();
        visit = orgIntent.getParcelableExtra("visit");
    }

    public void surgerySelected(View view) {
        Intent intent = new Intent(this, PatientLocationActivity.class);
        visit.setStatusType("surgery");
        intent.putExtra("visit", visit);
        startActivityForResult(intent, 1);
    }

    public void routineSelected(View view) {
        Intent intent = new Intent(this, PatientLocationActivity.class);
        visit.setStatusType("routine");
        intent.putExtra("visit", visit);
        startActivityForResult(intent, 1);
    }

    public void newbornSelected(View view) {
        Intent intent = new Intent(this, PatientLocationActivity.class);
        visit.setStatusType("newborn");
        intent.putExtra("visit", visit);
        startActivityForResult(intent, 1);
    }

    public void accidentSelected(View view) {
        Intent intent = new Intent(this, PatientLocationActivity.class);
        visit.setStatusType("accident");
        intent.putExtra("visit", visit);
        startActivityForResult(intent, 1);
    }

    public void laborSelected(View view) {
        Intent intent = new Intent(this, PatientLocationActivity.class);
        visit.setStatusType("labor");
        intent.putExtra("visit", visit);
        startActivityForResult(intent, 1);
    }

    public void urgentSelected(View view) {
        Intent intent = new Intent(this, PatientLocationActivity.class);
        visit.setStatusType("urgent");
        intent.putExtra("visit", visit);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        finish();
    }
}
