package com.cookiesmart.pocketehr_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by aditya841 on 12/1/2014.
 */
public class AddPatientContactActivity extends Activity {

    private static String objectId;
    private Patient p = null;
    private String action = "";
    private ArrayList<String> bodyParts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_object);
        Intent intent = getIntent();
        action = intent.getStringExtra("action");

        if (action.equals("view")) {
            p = intent.getParcelableExtra("Patient");
            objectId = intent.getStringExtra("objectId");
            bodyParts = intent.getStringArrayListExtra("bodyParts");
            setView();
        } else {
            p = new Patient();
        }
    }

    public void saveAndNext(View v) {
        LinearLayout contactInformationLayout = (LinearLayout) v.getParent().getParent();
        if (((EditText) contactInformationLayout.findViewById(R.id.last_name_input)).getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.last_name_toast), Toast.LENGTH_LONG).show();
            return;
        }
        if (((EditText) contactInformationLayout.findViewById(R.id.first_name_input)).getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.first_name_toast), Toast.LENGTH_LONG).show();
            return;
        }
        p.setLastName(((EditText) contactInformationLayout.findViewById(R.id.last_name_input)).getText().toString());
        p.setFirstName(((EditText) contactInformationLayout.findViewById(R.id.first_name_input)).getText().toString());
        p.setHospital(((EditText) contactInformationLayout.findViewById(R.id.hospital_input)).getText().toString());
        p.setPatientIDNumber(((EditText) contactInformationLayout.findViewById(R.id.patient_id_number_input)).getText().toString());
        p.setContactNo(((EditText) contactInformationLayout.findViewById(R.id.contact_number_input)).getText().toString());
        p.setEmail(((EditText) contactInformationLayout.findViewById(R.id.email_input)).getText().toString());

        Intent intent = new Intent(this, AddPatientHistoryActivity.class);
        intent.putExtra("Patient", p);
        intent.putExtra("action", action);
        intent.putExtra("objectId", objectId);
        if (action.equals("view")) {
            intent.putStringArrayListExtra("bodyParts", bodyParts);
        }
        startActivity(intent);
    }

    private void setView() {
        LinearLayout contactInformationLayout = (LinearLayout) findViewById(R.id.contact_information_layout);

        EditText lastName = (EditText) contactInformationLayout.findViewById(R.id.last_name_input);
        lastName.setText(p.getLastName());

        EditText firstName = (EditText) contactInformationLayout.findViewById(R.id.first_name_input);
        firstName.setText(p.getFirstName());

        if (p.getHospital() != null) {
            EditText hospital = (EditText) contactInformationLayout.findViewById(R.id.hospital_input);
            hospital.setText(p.getHospital());
        }

        if (p.getContactNo() != null) {
            EditText contactNo = (EditText) contactInformationLayout.findViewById(R.id.contact_number_input);
            contactNo.setText(p.getContactNo());
        }

        if (p.getPatientIDNumber() != null) {
            EditText patientIDNumber = (EditText) contactInformationLayout.findViewById(R.id.patient_id_number_input);
            patientIDNumber.setText(p.getPatientIDNumber());
        }
    }
}
