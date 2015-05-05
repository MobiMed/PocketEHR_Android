package com.cookiesmart.pocketehr_android.Controllers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.cookiesmart.pocketehr_android.R;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

/**
 * Created by aditya841 on 12/3/2014.
 */
public class ChangeStatusActivity extends Activity implements AdapterView.OnItemSelectedListener {

    private String statusSelected;
    private String originalStatus;
    private String objectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changestatus);

        Spinner spinner = (Spinner) findViewById(R.id.status_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.status, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        Intent intent = getIntent();
        objectId = intent.getStringExtra("objectId");

        originalStatus = intent.getStringExtra("status");
        statusSelected = intent.getStringExtra("status");

        if (originalStatus.equalsIgnoreCase(getString(R.string.incomplete_status))) {
            spinner.setSelection(0);
        } else if (originalStatus.equalsIgnoreCase(getString(R.string.negative_status))) {
            spinner.setSelection(1);
        } else if (originalStatus.equalsIgnoreCase(getString(R.string.positive_status))) {
            spinner.setSelection(2);
        } else if (originalStatus.equalsIgnoreCase(getString(R.string.deceased_status))) {
            spinner.setSelection(3);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        statusSelected = (String) parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void sendStatus(View view) {
        LinearLayout main_layout = (LinearLayout) view.getParent().getParent();
        String notes = ((EditText) main_layout.findViewById(R.id.patient_notes_input)).getText().toString();
        if (!notes.trim().equalsIgnoreCase("")) {
            saveNotes(notes);
        }
        if (!originalStatus.equalsIgnoreCase(statusSelected)) {
            saveStatus();
        }
        Intent intent = new Intent();
        intent.putExtra("status", statusSelected);
        intent.putExtra("notes", notes);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void saveStatus() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Patient");

        // Retrieve the object by id
        query.getInBackground(objectId, new GetCallback<ParseObject>() {
            public void done(ParseObject gameScore, ParseException e) {
                if (e == null) {
//                    System.out.println("Updating status to parse");
//                    System.out.println(statusSelected);
                    // Now let's update it with some new data. In this case, only cheatMode and score
                    // will get sent to the Parse Cloud. playerName hasn't changed.
                    if (statusSelected.equalsIgnoreCase(getString(R.string.positive_status))) {
                        gameScore.put("status", getString(R.string.server_positive_status));
                    } else if (statusSelected.equalsIgnoreCase(getString(R.string.negative_status))) {
                        gameScore.put("status", getString(R.string.server_negative_status));
                    } else if (statusSelected.equalsIgnoreCase(getString(R.string.incomplete_status))) {
                        gameScore.put("status", getString(R.string.server_incomplete_status));
                    } else if (statusSelected.equalsIgnoreCase(getString(R.string.deceased_status))) {
                        gameScore.put("status", getString(R.string.server_deceased_status));
                    }
                    gameScore.saveInBackground();
                }
            }
        });
    }

    private void saveNotes(String notes) {
        ParseObject patientObject = ParseObject.createWithoutData("Patient", objectId);
        ParseUser userObject = ParseUser.getCurrentUser();
        ParseObject noteObject = new ParseObject("Activity");

        noteObject.put("author", userObject);
        noteObject.put("text", notes);
        noteObject.put("patient", patientObject);
        noteObject.put("type", "kTextAdded");
        noteObject.saveInBackground();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_CANCELED);
    }
}
