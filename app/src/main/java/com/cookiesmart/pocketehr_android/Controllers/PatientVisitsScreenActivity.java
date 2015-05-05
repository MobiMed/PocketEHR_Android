package com.cookiesmart.pocketehr_android.Controllers;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cookiesmart.pocketehr_android.HelperClasses.Patient;
import com.cookiesmart.pocketehr_android.HelperClasses.Visit;
import com.cookiesmart.pocketehr_android.HelperClasses.VisitListAdapter;
import com.cookiesmart.pocketehr_android.R;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by aditya841 on 4/22/2015.
 */
public class PatientVisitsScreenActivity extends Activity {

    private static String TAG = "PatientVisitsScreenActivity";
    final Context context = this;
    private int preLast;
    private int iteration = 0;
    private boolean more = true;
    private Patient patient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patientvisitsscreen);
        findViewById(R.id.patient_first_name).requestFocus();
        Intent intent = getIntent();
        patient = intent.getParcelableExtra("patient");
        loadView();
    }

    public void loadView() {
        final VisitListAdapter visitViewAdapter = new VisitListAdapter(this);
        final ListView listView = (ListView) findViewById(R.id.listView);
        listView.setEmptyView(findViewById(R.id.emptyList));
        listView.setAdapter(visitViewAdapter);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                final int lastItem = firstVisibleItem + visibleItemCount;
                if (lastItem == totalItemCount) {
                    if (preLast != lastItem && more) { //to avoid multiple calls for last item
                        preLast = lastItem;
                        iteration++;
                        loadNextPage(visitViewAdapter);
                    }
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, Object> listObject = (HashMap<String, Object>) listView.getItemAtPosition(position);
                Intent intent = new Intent(context, NewPatientActivity.class);
                intent.putExtra("patient", patient);

                Visit visit = new Visit();
                visit.setEventObjectId((String) listObject.get("eventObjectId"));
                visit.setStatusType((String) listObject.get("eventStatus"));
                visit.setLocationType((String) listObject.get("eventLocation"));
                visit.setClassType((String) listObject.get("eventType"));
                Date date = (Date) listObject.get("updatedAt");
                if (date != null) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yy", Locale.getDefault());
                    String dateString = simpleDateFormat.format(date);
                    visit.setVisitDate(dateString);
                } else {
                    visit.setVisitDate("Date not coming");
                }
                intent.putExtra("visit", visit);
                intent.putExtra("view_tag", (String) view.getTag());
                startActivityForResult(intent, 1);
            }
        });


        TextView firstNameView = (TextView) findViewById(R.id.patient_first_name);
        firstNameView.setText(patient.getFirstName() + patient.getFirstName() + patient.getFirstName() + patient.getFirstName() + patient.getFirstName() + patient.getFirstName());

        TextView lastNameView = (TextView) findViewById(R.id.patient_last_name);
        lastNameView.setText(patient.getLastName());

        TextView dobgender = (TextView) findViewById(R.id.patient_dobgender);
        try {
            String dateString = new SimpleDateFormat("dd MMM, yyyy", Locale.getDefault()).
                    format(new SimpleDateFormat().parse(patient.getDob()));
            dobgender.setText(patient.getGender() + "  " + dateString);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        TextView patientIdNumber = (TextView) findViewById(R.id.patient_id_number);
        patientIdNumber.setText(patient.getPatientIDNumber());

        iteration++;
        loadNextPage(visitViewAdapter);
    }

    private void loadNextPage(final VisitListAdapter adapter) {
        HashMap<String, Object> request = new HashMap<>();
        request.put("iteration", iteration);
        request.put("patientObjectId", "xVGLITfdKD");
        ParseCloud.callFunctionInBackground("patientEventList", request, new FunctionCallback<ArrayList<HashMap<String, Object>>>() {
            @Override
            public void done(ArrayList<HashMap<String, Object>> response, ParseException e) {
                if (response.size() < 10) {
                    more = false;
                }
                System.out.println(response.size());
                adapter.updateEntries(response);
            }
        });
    }

    public void changeProfilePicture(View view) {
        // activity for taking photo
    }

    public void showPatientHistory(View view) {
        HashMap<String, Object> request = new HashMap<>();
        request.put("patientObjectId", patient.getPatientObjectId());
        ParseCloud.callFunctionInBackground("retrievePatientHistory", request, new FunctionCallback<String>() {
            @Override
            public void done(String history, ParseException e) {
                showPatientHistory(history);
            }
        });
    }

    private void showPatientHistory(String patientHistory) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle(getString(R.string.feedback_button));

        // Set an EditText view to get user input
        final EditText input = new EditText(this);
        input.setText(patientHistory);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String value = input.getText().toString();
                storePatientHistory(value);
                dialog.dismiss();
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });

        alert.show();
    }

    private void storePatientHistory(String history) {
        HashMap<String, Object> request = new HashMap<>();
        request.put("patientObjectId", patient.getPatientObjectId());
        request.put("patientHistory", history);
        ParseCloud.callFunctionInBackground("savePatientHistory", request);
    }

    public void showPatientContactInfo(View view) {
        HashMap<String, Object> request = new HashMap<>();
        request.put("patientObjectId", patient.getPatientObjectId());
        ParseCloud.callFunctionInBackground("retrievePatientContactInfo", request, new FunctionCallback<HashMap<String, Object>>() {
            @Override
            public void done(HashMap<String, Object> response, ParseException e) {
                showPatientContactInfo(response);
            }
        });
    }

    private void showPatientContactInfo(HashMap<String, Object> contactInfo) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle(getString(R.string.feedback_button));

        LinearLayout contactInfoLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.patient_contact_info_layout, null);

        // Set an EditText view to get user input
        final TextView phoneNumber = (TextView) contactInfoLayout.findViewById(R.id.phoneNumber_placeholder);
        phoneNumber.setText((String) contactInfo.get("phoneNumber"));
        alert.setView(phoneNumber);

        final TextView input = (TextView) contactInfoLayout.findViewById(R.id.email_placeholder);
        input.setText((String) contactInfo.get("email"));
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String value = input.getText().toString();
                storePatientHistory(value);
                dialog.dismiss();
            }
        });

        alert.show();
    }

    public void startCheckin(View view) {
        Intent intent = new Intent(context, VisitTypeActivity.class);
        Visit visit = new Visit();
        visit.setPatient_object_id(patient.getPatientObjectId());
        intent.putExtra("visit", visit);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        finish();
        Intent intent = new Intent(this, PatientVisitsScreenActivity.class);

    }
}
