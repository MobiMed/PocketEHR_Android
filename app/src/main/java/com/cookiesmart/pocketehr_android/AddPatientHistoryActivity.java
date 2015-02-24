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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by aditya841 on 12/1/2014.
 */
public class AddPatientHistoryActivity extends Activity implements AdapterView.OnItemSelectedListener {
    private static final String ADDPATIENT = "AddPatientHistoryActivity";
    private static boolean flag = false;
    private static LinearLayout patientHistory;
    private static LinearLayout dobandage;
    Patient p = null;
    Context context = this;

    public static void setAge(int age) {
        TextView ageField = (TextView) dobandage.findViewById(R.id.age_input);
        ageField.setText(age + "");
        ageField.setFocusable(false);
        flag = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_history_object);
        patientHistory = (LinearLayout) findViewById(R.id.patient_history_layout);
        dobandage = (LinearLayout) patientHistory.findViewById(R.id.dobandage_layout);

        Intent intent = getIntent();
        p = intent.getParcelableExtra("Patient");

        Spinner spinner = (Spinner) findViewById(R.id.gender_spinner);
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

    public void saveAndNext(View v) {
        String ageString = ((TextView) dobandage.findViewById(R.id.age_input)).getText().toString();
        if (((EditText) dobandage.findViewById(R.id.dob_input)).getText().toString().trim().equals("") && ageString.trim().equals("")) {
            Toast.makeText(this, getString(R.string.dob_toast), Toast.LENGTH_LONG).show();
            return;
        }
        if (flag) {
            p.setDob(((EditText) dobandage.findViewById(R.id.dob_input)).getText().toString());
            p.setAge(Integer.parseInt(ageString));
        } else {
            p.setAge(Integer.parseInt(ageString));
        }

        Intent intent = new Intent(this, AddPatientNotesActivity.class);
        intent.putExtra("Patient", p);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        p.setGender((String) parent.getItemAtPosition(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void showDatePicker(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
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
            return new DatePickerDialog(getActivity(), this, year, month, day);
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

            EditText dobField = ((EditText) dobandage.findViewById(R.id.dob_input));
            if (month < 10) {
                monthString = "0" + month;
            }
            if (day < 10) {
                dayString = "0" + day;
            }
            dobField.setText(monthString + "-" + dayString + "-" + year);

            setAge(age);
        }
    }
}
