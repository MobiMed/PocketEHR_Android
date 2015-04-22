package com.cookiesmart.pocketehr_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.cookiesmart.pocketehr_android.HelperClasses.Visit;

import java.util.ArrayList;

/**
 * Created by aditya841 on 4/17/2015.
 */
public class DoctorSelectionActivity extends Activity implements AdapterView.OnItemSelectedListener {
    private Spinner spinner;
    private Visit visit;
    private Intent orgIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctorselection);

        orgIntent = getIntent();
        visit = orgIntent.getParcelableExtra("visit");

        spinner = (Spinner) findViewById(R.id.doctor_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.spinner_item, fetchDoctorList());
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    private ArrayList<String> fetchDoctorList() {
        ArrayList<String> doctor_list = new ArrayList<>();
        doctor_list.add("Aditya");
        doctor_list.add("Dhaval");
        return doctor_list;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        visit.setDoctorId((String) parent.getItemAtPosition(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void bodyMapSelection(View view) {
        String initialObservation = ((EditText) findViewById(R.id.initial_observation)).getText().toString();
        visit.setInitialObservation(initialObservation);
        Intent intent = new Intent(this, AddPatientBodyActivity.class);
        intent.putExtra("visit", visit);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        finish();
    }
}
