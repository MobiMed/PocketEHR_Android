package com.cookiesmart.pocketehr_android;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.cookiesmart.pocketehr_android.HelperClasses.Patient;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by aditya841 on 4/16/2015.
 */
public class AddPatientActivity extends Activity implements AdapterView.OnItemSelectedListener {
    Context context = this;
    private static EditText dob_input;
    private Spinner spinner;
    private Patient patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpatient);
        dob_input = (EditText) findViewById(R.id.dob_input);

        spinner = (Spinner) findViewById(R.id.gender_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender, R.layout.spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        spinner.setSelection(0);

    }

    public void showDatePicker(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        patient.setGender((String) parent.getItemAtPosition(position));
        if (position == 1) {
            patient.setGender(getString(R.string.server_sex_male));
            view.setBackgroundColor(getResources().getColor(R.color.male_label_color));
        } else {
            patient.setGender(getString(R.string.server_sex_female));
            view.setBackgroundColor(getResources().getColor(R.color.female_label_color));
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //do nothing
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        private static final Calendar c = Calendar.getInstance();

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);


            // Create a new instance of DatePickerDialog and return it
            DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);
            dialog.getDatePicker().setMaxDate(new Date().getTime());
            return dialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            Calendar current = Calendar.getInstance();
            Calendar selected = Calendar.getInstance();
            selected.set(year, month, day);

            int age = current.get(Calendar.YEAR) - selected.get(Calendar.YEAR);
            c.set(year, month, day);

            month = month + 1;
            String monthString = month + "";
            String dayString = day + "";

            if (month < 10) {
                monthString = "0" + month;
            }
            if (day < 10) {
                dayString = "0" + day;
            }
            dob_input.setText(monthString + "-" + dayString + "-" + year);
        }
    }

    public void savePatient(View view) {

        final ParseObject patientParse = new ParseObject("patient");
        HashMap<String, Object> patient_details = new HashMap<>();

        String firstName = ((EditText) findViewById(R.id.first_name_input)).getText().toString();
        if (firstName.trim().equals("")) {
            Toast.makeText(context, getString(R.string.first_name_toast), Toast.LENGTH_SHORT).show();
            return;
        } else {
            patient_details.put("firstName", firstName);
            patientParse.put("firstName", firstName);
            patient.setFirstName(firstName);
        }

        String lastName = ((EditText) findViewById(R.id.last_name_input)).getText().toString();
        if (lastName.trim().equals("")) {
            Toast.makeText(context, getString(R.string.last_name_toast), Toast.LENGTH_SHORT).show();
            return;
        } else {
            patient_details.put("lastName", lastName);
            patientParse.put("lastName", lastName);
            patient.setLastName(lastName);
        }

        Date dob = null;
        String dob_string = ((EditText) findViewById(R.id.dob_input)).getText().toString();

        if (!dob_string.trim().equals("")) {
            SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault());
            try {
                dob = df.parse(dob_string);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
            if (dob != null) {
                patient_details.put("dob", dob);
                patientParse.put("dob", dob);
                patient.setDob(dob_string);
            } else {
                Toast.makeText(context, getString(R.string.dob_toast), Toast.LENGTH_SHORT).show();
                return;
            }
        } else {
            Toast.makeText(context, getString(R.string.dob_toast), Toast.LENGTH_SHORT).show();
            return;
        }

        patient_details.put("gender", patient.getGender().toLowerCase());
        patientParse.put("gender", patient.getGender().toLowerCase());


        String phoneNumber = ((EditText) findViewById(R.id.contact_number)).getText().toString();
        if (phoneNumber.trim().equals("")) {
            patient.setContactNo(null);
            patient_details.put("phoneNumber", JSONObject.NULL);
            patientParse.put("phoneNumber", JSONObject.NULL);
        } else {
            patient_details.put("phoneNumber", phoneNumber);
            patientParse.put("phoneNumber", phoneNumber);
            patient.setContactNo(phoneNumber);
        }

        String email = ((EditText) findViewById(R.id.email_input)).getText().toString();
        if (email.trim().equals("")) {
            patient.setEmail(null);
            patient_details.put("email", JSONObject.NULL);
            patientParse.put("email", JSONObject.NULL);
        } else {
            patient_details.put("email", email);
            patientParse.put("email", email);
            patient.setEmail(email);
        }

        String patientHistory = ((EditText) findViewById(R.id.email_input)).getText().toString();
        if (patientHistory.trim().equals("")) {
            patient.setPatientHistory(null);
            patient_details.put("patientHistory", JSONObject.NULL);
            patientParse.put("patientHistory", JSONObject.NULL);
        } else {
            patient_details.put("patientHistory", patientHistory);
            patientParse.put("patientHistory", patientHistory);
            patient.setPatientHistory(patientHistory);
        }

        patientParse.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                Intent intent = new Intent(context, VisitTypeActivity.class);
                intent.putExtra("patient_object_id", patientParse.getObjectId());
                startActivity(intent);
            }
        });
    }
}
