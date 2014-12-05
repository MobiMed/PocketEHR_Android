package com.cookiesmart.pocketehr_android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aditya841 on 11/21/2014.
 */
public class PatientActivity extends Activity {
    private static String objectId;
    private static String PATIENT = "PatientActivity";
    private static String NEGATIVE = "NEGATIVE";
    private static String POSITIVE = "POSITIVE";
    private static String INCOMPLETE = "INCOMPLETE";
    private static String DECEASED = "DECEASED";
    private static String FEMALE = "Female";
    private static String MALE = "Male";
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);
        Intent intent = getIntent();
        objectId = intent.getStringExtra("objectId");
        populateView(intent);
    }


    public void changeStatusActivity(View view) {
        Intent intent = new Intent(this, ChangeStatusActivity.class);
        intent.putExtra("objectId", objectId);
        intent.putExtra("status", ((TextView) view).getText().toString());
        startActivityForResult(intent, 1);
    }

    public void addNote(View view) {
        Intent intent = new Intent(this, AddNoteActivity.class);
        intent.putExtra("objectId", objectId);
        startActivityForResult(intent, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        } else if (requestCode == 1) {
            TextView status_field = (TextView) findViewById(R.id.status_patient_screen);
            String status = data.getStringExtra("status");
            if (status.contains("Negative")) {
                status_field.setBackgroundColor(Color.GREEN);
                //status_field.setTextColor(Color.WHITE);
                status_field.setText(NEGATIVE);
            } else if (status.contains("Positive")) {
                status_field.setBackgroundColor(Color.RED);
                //status_field.setTextColor(Color.WHITE);
                status_field.setText(POSITIVE);
            } else if (status.contains("Incomplete")) {
                status_field.setBackgroundColor(Color.BLUE);
                //status_field.setTextColor(Color.WHITE);
                status_field.setText(INCOMPLETE);
            } else if (status.contains("Deceased")) {
                status_field.setBackgroundColor(Color.BLACK);
                //status_field.setTextColor(Color.WHITE);
                status_field.setText(DECEASED);
            }

            String notes = data.getStringExtra("notes");
            LinearLayout main_section = (LinearLayout) findViewById(R.id.main_section);
            if (!notes.trim().equalsIgnoreCase("")) {
                TextView newTextView = new TextView(context);
                newTextView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                newTextView.setMinHeight(150);
                newTextView.setPadding(20, 10, 0, 0);
                newTextView.setText(notes);
                main_section.addView(newTextView);
            }
        } else {
            String notes = data.getStringExtra("notes");
            LinearLayout main_section = (LinearLayout) findViewById(R.id.main_section);
            if (!notes.trim().equalsIgnoreCase("")) {
                TextView newTextView = new TextView(context);
                newTextView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                newTextView.setMinHeight(150);
                newTextView.setPadding(20, 10, 0, 0);
                newTextView.setText(notes);
                main_section.addView(newTextView);
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up login_button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void populateView(Intent intent) {
        TextView firstName = (TextView) findViewById(R.id.first_name);
        firstName.setText(intent.getStringExtra("firstName"));

        TextView lastName = (TextView) findViewById(R.id.last_name);
        lastName.setText(intent.getStringExtra("lastName"));

        TextView status_field = (TextView) findViewById(R.id.status_patient_screen);
        String status = intent.getStringExtra("status");
        if (status.contains("Negative")) {
            status_field.setBackgroundColor(Color.GREEN);
            //status_field.setTextColor(Color.WHITE);
            status_field.setText(NEGATIVE);
        } else if (status.contains("Positive")) {
            status_field.setBackgroundColor(Color.RED);
            //status_field.setTextColor(Color.WHITE);
            status_field.setText(POSITIVE);
        } else if (status.contains("Incomplete")) {
            status_field.setBackgroundColor(Color.BLUE);
            //status_field.setTextColor(Color.WHITE);
            status_field.setText(INCOMPLETE);
        } else if (status.contains("Deceased")) {
            status_field.setBackgroundColor(Color.BLACK);
            //status_field.setTextColor(Color.WHITE);
            status_field.setText(DECEASED);
        }

        TextView telepathologyID = (TextView) findViewById(R.id.telepathologyID);
        telepathologyID.setText(intent.getStringExtra("telepathologyID"));

        TextView sex = (TextView) findViewById(R.id.patient_gender);
        String gender = intent.getStringExtra("sex");

        ImageView body_parts = (ImageView) findViewById(R.id.body_part_image);
        if (gender.contains("Female")) {
            body_parts.setImageResource(R.drawable.female_body_parts);
            sex.setText(FEMALE);
        } else {
            body_parts.setImageResource(R.drawable.male_body_parts);
            sex.setText(MALE);
        }

        fetchNotes();
    }

    private void fetchNotes() {
        //Query to get data
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Activity");
        ParseObject o = ParseObject.createWithoutData("Patient", objectId);
        query.whereEqualTo("patient", o);
        query.whereExists("text");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> notesList, ParseException e) {
                if (e == null) {
                    setNotes(notesList);
                } else {
                    Log.d(PATIENT, "Error: " + e.getMessage());
                    setNotes(new ArrayList<ParseObject>());
                }
            }
        });
        return;
    }

    private void setNotes(List<ParseObject> notesList) {
        LinearLayout main_section = (LinearLayout) findViewById(R.id.main_section);
        for (ParseObject notes : notesList) {
            TextView newTextView = new TextView(context);
            newTextView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            newTextView.setMinHeight(150);
            newTextView.setPadding(20, 10, 0, 0);
            String note = notes.getString("text");
            newTextView.setText(note);
            main_section.addView(newTextView);
        }
        return;
    }
}
