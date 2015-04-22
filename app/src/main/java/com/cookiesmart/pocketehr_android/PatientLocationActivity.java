package com.cookiesmart.pocketehr_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cookiesmart.pocketehr_android.HelperClasses.Visit;

/**
 * Created by aditya841 on 4/17/2015.
 */
public class PatientLocationActivity extends Activity {
    private Intent orgIntent;
    private Visit visit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patientlocation);
        orgIntent = getIntent();
        visit = orgIntent.getParcelableExtra("visit");
    }

    public void generalSelected(View view) {
        Intent intent = new Intent(this, DoctorSelectionActivity.class);
        visit.setLocationType("general");
        intent.putExtra("visit", visit);
        startActivityForResult(intent, 1);
    }

    public void surgerySelected(View view) {
        Intent intent = new Intent(this, DoctorSelectionActivity.class);
        visit.setLocationType("surgery");
        intent.putExtra("visit", visit);
        startActivityForResult(intent, 1);
    }

    public void pediatricsSelected(View view) {
        Intent intent = new Intent(this, DoctorSelectionActivity.class);
        visit.setLocationType("pediatrics");
        intent.putExtra("visit", visit);
        startActivityForResult(intent, 1);
    }

    public void maternitySelected(View view) {
        Intent intent = new Intent(this, DoctorSelectionActivity.class);
        visit.setLocationType("maternity");
        intent.putExtra("visit", visit);
        startActivityForResult(intent, 1);
    }

    public void erSelected(View view) {
        Intent intent = new Intent(this, DoctorSelectionActivity.class);
        visit.setLocationType("er");
        intent.putExtra("visit", visit);
        startActivityForResult(intent, 1);
    }

    public void icuSelected(View view) {
        Intent intent = new Intent(this, DoctorSelectionActivity.class);
        visit.setLocationType("icu");
        intent.putExtra("visit", visit);
        startActivityForResult(intent, 1);
    }

    public void dentalSelected(View view) {
        Intent intent = new Intent(this, DoctorSelectionActivity.class);
        visit.setLocationType("dental");
        intent.putExtra("visit", visit);
        startActivityForResult(intent, 1);
    }

    public void radiologySelected(View view) {
        Intent intent = new Intent(this, DoctorSelectionActivity.class);
        visit.setLocationType("radiology");
        intent.putExtra("visit", visit);
        startActivityForResult(intent, 1);
    }

    public void cardiologySelected(View view) {
        Intent intent = new Intent(this, DoctorSelectionActivity.class);
        visit.setLocationType("cardiology");
        intent.putExtra("visit", visit);
        startActivityForResult(intent, 1);
    }

    public void infectiousSelected(View view) {
        Intent intent = new Intent(this, DoctorSelectionActivity.class);
        visit.setLocationType("infectious");
        intent.putExtra("visit", visit);
        startActivityForResult(intent, 1);
    }

    public void orthopedicsSelected(View view) {
        Intent intent = new Intent(this, DoctorSelectionActivity.class);
        visit.setLocationType("orthopedics");
        intent.putExtra("visit", visit);
        startActivityForResult(intent, 1);
    }

    public void urologySelected(View view) {
        Intent intent = new Intent(this, DoctorSelectionActivity.class);
        visit.setLocationType("urology");
        intent.putExtra("visit", visit);
        startActivityForResult(intent, 1);
    }

    public void otherSelected(View view) {
        Intent intent = new Intent(this, DoctorSelectionActivity.class);
        visit.setLocationType("other");
        intent.putExtra("visit", visit);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        finish();
    }
}
