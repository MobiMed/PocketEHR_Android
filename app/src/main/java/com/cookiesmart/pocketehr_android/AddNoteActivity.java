package com.cookiesmart.pocketehr_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.parse.ParseObject;

/**
 * Created by aditya841 on 12/5/2014.
 */
public class AddNoteActivity extends Activity {
    private String objectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnote);

        Intent intent = getIntent();
        objectId = intent.getStringExtra("objectId");
    }

    public void sendNote(View view) {
        LinearLayout main_layout = (LinearLayout) view.getParent();
        String notes = ((EditText) main_layout.findViewById(R.id.patient_notes_input)).getText().toString();
        if (!notes.trim().equalsIgnoreCase("")) {
            saveNotes(notes);
        }
        Intent intent = new Intent();
        intent.putExtra("notes", notes);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void saveNotes(String notes) {
        ParseObject patientObject = ParseObject.createWithoutData("Patient", objectId);
        ParseObject userObject = ParseObject.createWithoutData("User", MainActivity.USEROBJECTID);
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
