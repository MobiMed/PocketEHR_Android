package com.cookiesmart.pocketehr_android;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cookiesmart.pocketehr_android.HelperClasses.Patient;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by aditya841 on 12/1/2014.
 */
public class AddPatientHistoryActivity extends Activity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "AddPatientHistActivity";
    private static boolean flag = false;
    private static LinearLayout patientHistory;
    private static LinearLayout dobandage;
    private static String objectId;
    private final int SHOW_BODY = 1;
    Context context = this;
    private ArrayList<String> bodyParts;
    private ArrayList<String> backBodyParts;
    private Patient p = null;
    private String action = "";
    private Spinner spinner;

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
        action = intent.getStringExtra("action");

        spinner = (Spinner) findViewById(R.id.gender_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender, R.layout.spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        if (action.equals("view")) {
            RelativeLayout contact_view_layout = (RelativeLayout) findViewById(R.id.cancel_button_Layout);
            contact_view_layout.setVisibility(View.VISIBLE);
            setView();
            bodyParts = intent.getStringArrayListExtra("bodyParts");
            backBodyParts = intent.getStringArrayListExtra("backBodyParts");
            objectId = intent.getStringExtra("objectId");
        } else {
            spinner.setSelection(0);
        }
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

        if (action.equals("view")) {
            Intent intent = new Intent(this, AddPatientBodyActivity.class);
            p.setNotes("");
            intent.putExtra("objectId", objectId);
            intent.putExtra("Patient", p);
            intent.putExtra("action", action);
            intent.putStringArrayListExtra("bodyParts", bodyParts);
            intent.putStringArrayListExtra("backBodyParts", backBodyParts);
            startActivityForResult(intent, SHOW_BODY);
        } else {
            Intent intent = new Intent(this, AddPatientNotesActivity.class);
            intent.putExtra("Patient", p);
            startActivity(intent);
        }
    }

    public void closeProfileView(View v) {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            finish();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        p.setGender((String) parent.getItemAtPosition(position));
        if (p.getGender().equals("Male")) {
            view.setBackgroundColor(Color.parseColor("#00CCFF"));
        } else {
            view.setBackgroundColor(Color.parseColor("#FFC0CB"));
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void showDatePicker(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    private void setView() {
        Spinner spinner = (Spinner) findViewById(R.id.gender_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout

        Log.i(TAG, p.getGender());
        if (p.getGender().equals(getString(R.string.female_gender))) {
            spinner.setSelection(0);
        } else if (p.getGender().equals(getString(R.string.male_gender))) {
            spinner.setSelection(1);
        }

        EditText dobField = ((EditText) dobandage.findViewById(R.id.dob_input));
        dobField.setText(p.getDob());

        setAge(p.getAge());
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
