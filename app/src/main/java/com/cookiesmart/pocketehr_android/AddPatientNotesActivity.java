package com.cookiesmart.pocketehr_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * Created by aditya841 on 12/1/2014.
 */
public class AddPatientNotesActivity extends Activity {
    Patient p = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_object);

        Intent intent = getIntent();
        p = intent.getParcelableExtra("Patient");
    }

    public void saveAndNext(View v) {

        LinearLayout notesLayout = (LinearLayout) v.getParent().getParent();
        p.setNotes(((EditText) notesLayout.findViewById(R.id.patient_notes_input)).getText().toString());

        Intent intent = new Intent(this, AddPatientBodyActivity.class);
        intent.putExtra("Patient", p);
        intent.putExtra("action", "add");
        startActivity(intent);
    }
}
