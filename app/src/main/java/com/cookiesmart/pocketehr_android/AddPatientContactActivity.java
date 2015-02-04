package com.cookiesmart.pocketehr_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by aditya841 on 12/1/2014.
 */
public class AddPatientContactActivity extends Activity {

    Patient p = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_object);
        p = new Patient();
    }

    public void saveAndNext(View v) {
        LinearLayout contactInformationLayout = (LinearLayout) v.getParent();
        if (((EditText) contactInformationLayout.findViewById(R.id.last_name_input)).getText().toString().trim().equals("")) {
            Toast.makeText(this, "Last name cannot be blank.", Toast.LENGTH_LONG).show();
            return;
        }
        if (((EditText) contactInformationLayout.findViewById(R.id.first_name_input)).getText().toString().trim().equals("")) {
            Toast.makeText(this, "First name cannot be blank.", Toast.LENGTH_LONG).show();
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
        startActivity(intent);
    }
}
