package com.cookiesmart.pocketehr_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by aditya841 on 12/1/2014.
 */
public class AddPatientBodyActivity extends Activity {
    private static final String ADDPATIENT = "AddPatientBodyActivity";
    Patient p = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_object);

        Intent intent = getIntent();
        p = intent.getParcelableExtra("Patient");

    }

    public void saveAndFinish(View v) {
        final ParseObject patient = new ParseObject("Patient");
        patient.put("firstName", p.getFirstName());
        patient.put("lastName", p.getLastName());

        if (p.getHospital().trim().equals("")) {
            patient.put("hospital", JSONObject.NULL);
        } else {
            patient.put("hospital", p.getHospital());
        }
        if (p.getContactNo().trim().equals("")) {
            patient.put("contactNo", JSONObject.NULL);
        } else {
            patient.put("contactNo", p.getContactNo());
        }

        Date d = null;
        if (!p.getDob().trim().equals("")) {
            SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
            try {
                d = df.parse(p.getDob());
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
            if (d != null) {
                patient.put("dob", d);
            } else {
                patient.put("dob", JSONObject.NULL);
            }
        } else {
            patient.put("dob", JSONObject.NULL);
        }
        patient.put("sex", "k" + p.getGender());
        patient.put("age", p.getAge());
        patient.put("status", p.getStatus());

        patient.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    String objectId = patient.getObjectId();
                    saveNotes(objectId, p.getNotes());
                } else {
                    Log.d(ADDPATIENT, "Error: " + e.getMessage());
                }
            }
        });
    }

    public void saveNotes(String objectId, String notes) {
        Log.i(ADDPATIENT, "save notes called");
        ParseObject patientObject = ParseObject.createWithoutData("Patient", objectId);
        ParseObject noteObject = new ParseObject("Activity");
        if (notes.trim().equals("")) {
            noteObject.put("text", JSONObject.NULL);
        } else {
            noteObject.put("text", notes);
        }
        noteObject.put("patient", patientObject);
        noteObject.put("type", "kPatientCreated");
        noteObject.saveInBackground();


        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
