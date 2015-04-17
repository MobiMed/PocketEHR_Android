package com.cookiesmart.pocketehr_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by aditya841 on 4/14/2015.
 */
public class VisitTypeActivity extends Activity {
    private Intent orgIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visittype);
        orgIntent = getIntent();
    }

    public void addVisit(View view) {
        Intent intent = new Intent(this, PatientStatusActivity.class);
        intent.putExtra("patient_object_id", orgIntent.getStringExtra("patient_object_id"));
        intent.putExtra("visitType", "visit");
        startActivity(intent);
    }

    public void addAdmit(View view) {
        Intent intent = new Intent(this, PatientStatusActivity.class);
        intent.putExtra("patient_object_id", orgIntent.getStringExtra("patient_object_id"));
        intent.putExtra("visitType", "admit");
        startActivity(intent);
    }

    public void addEmergency(View view) {
        Intent intent = new Intent(this, PatientStatusActivity.class);
        intent.putExtra("patient_object_id", orgIntent.getStringExtra("patient_object_id"));
        intent.putExtra("visitType", "emergency");
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //do nothing with the result
    }
}
