package com.cookiesmart.pocketehr_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by aditya841 on 4/14/2015.
 */
public class PatientStatusActivity extends Activity {
    private Intent orgIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patientstatus);
        orgIntent = getIntent();
    }

    public void surgerySelected(View view) {
        Intent intent = new Intent(this, PatientStatusActivity.class);
        intent.putExtra("patient_object_id", orgIntent.getStringExtra("patient_object_id"));
        intent.putExtra("visitType", orgIntent.getStringExtra("visitType"));
        intent.putExtra("status", "surgery");
        startActivity(intent);
    }

    public void routineSelected(View view) {
        Intent intent = new Intent(this, PatientStatusActivity.class);
        intent.putExtra("patient_object_id", orgIntent.getStringExtra("patient_object_id"));
        intent.putExtra("visitType", orgIntent.getStringExtra("visitType"));
        intent.putExtra("status", "routine");
        startActivity(intent);
    }

    public void newbornSelected(View view) {
        Intent intent = new Intent(this, PatientStatusActivity.class);
        intent.putExtra("patient_object_id", orgIntent.getStringExtra("patient_object_id"));
        intent.putExtra("visitType", orgIntent.getStringExtra("visitType"));
        intent.putExtra("status", "newborn");
        startActivity(intent);
    }

    public void accidentSelected(View view) {
        Intent intent = new Intent(this, PatientStatusActivity.class);
        intent.putExtra("patient_object_id", orgIntent.getStringExtra("patient_object_id"));
        intent.putExtra("visitType", orgIntent.getStringExtra("visitType"));
        intent.putExtra("status", "accident");
        startActivity(intent);
    }

    public void laborSelected(View view) {
        Intent intent = new Intent(this, PatientStatusActivity.class);
        intent.putExtra("patient_object_id", orgIntent.getStringExtra("patient_object_id"));
        intent.putExtra("visitType", orgIntent.getStringExtra("visitType"));
        intent.putExtra("status", "labor");
        startActivity(intent);
    }

    public void urgentSelected(View view) {
        Intent intent = new Intent(this, PatientStatusActivity.class);
        intent.putExtra("patient_object_id", orgIntent.getStringExtra("patient_object_id"));
        intent.putExtra("visitType", orgIntent.getStringExtra("visitType"));
        intent.putExtra("status", "urgent");
        startActivity(intent);
    }
}
