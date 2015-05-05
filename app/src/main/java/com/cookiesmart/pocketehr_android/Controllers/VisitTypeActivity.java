package com.cookiesmart.pocketehr_android.Controllers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cookiesmart.pocketehr_android.HelperClasses.Visit;
import com.cookiesmart.pocketehr_android.R;

/**
 * Created by aditya841 on 4/14/2015.
 */
public class VisitTypeActivity extends Activity {
    private Intent orgIntent;
    private Visit visit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visittype);
        orgIntent = getIntent();
        visit = orgIntent.getParcelableExtra("visit");
    }

    public void addVisit(View view) {
        Intent intent = new Intent(this, PatientStatusActivity.class);
        visit.setClassType("visit");
        intent.putExtra("visit", visit);
        startActivityForResult(intent, 1);
    }

    public void addAdmit(View view) {
        Intent intent = new Intent(this, PatientStatusActivity.class);
        visit.setClassType("admit");
        intent.putExtra("visit", visit);
        startActivityForResult(intent, 1);
    }

    public void addEmergency(View view) {
        Intent intent = new Intent(this, PatientStatusActivity.class);
        visit.setClassType("emergency");
        intent.putExtra("visit", visit);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //do nothing with the result
        finish();
    }
}
