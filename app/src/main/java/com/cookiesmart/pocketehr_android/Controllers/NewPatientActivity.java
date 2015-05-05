package com.cookiesmart.pocketehr_android.Controllers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cookiesmart.pocketehr_android.HelperClasses.EventActivityListAdapter;
import com.cookiesmart.pocketehr_android.HelperClasses.Patient;
import com.cookiesmart.pocketehr_android.HelperClasses.Visit;
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
 * Created by aditya841 on 4/28/2015.
 */
public class NewPatientActivity extends Activity {

    private Patient patient;
    private Visit visit;
    private Context context = this;
    private int preLast;
    private int iteration = 0;
    private boolean more = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);
        Intent intent = getIntent();
        patient = intent.getParcelableExtra("patient");
        visit = intent.getParcelableExtra("visit");
        loadView();
    }

    private void loadView() {
        TextView nameView = (TextView) findViewById(R.id.patient_name_placeholder);
        nameView.setText(patient.getFirstName() + " " + patient.getLastName());

        TextView dateView = (TextView) findViewById(R.id.date_placeholder);
        dateView.setText(visit.getVisitDate());

        ImageView statusView = (ImageView) findViewById(R.id.event_status_placeholder);
        String eventStatusString = visit.getStatusType() + "";
        if (eventStatusString.equalsIgnoreCase(getString(R.string.routine_status_string))) {
            statusView.setImageResource(R.drawable.ic_routine);
        } else if (eventStatusString.equalsIgnoreCase(getString(R.string.surgery_status_string))) {
            statusView.setImageResource(R.drawable.ic_surgery);
        } else if (eventStatusString.equalsIgnoreCase(getString(R.string.newborn_status_string))) {
            statusView.setImageResource(R.drawable.ic_newborn);
        } else if (eventStatusString.equalsIgnoreCase(getString(R.string.labor_status_string))) {
            statusView.setImageResource(R.drawable.ic_labor);
        } else if (eventStatusString.equalsIgnoreCase(getString(R.string.accident_status_string))) {
            statusView.setImageResource(R.drawable.ic_accident);
        } else if (eventStatusString.equalsIgnoreCase(getString(R.string.urgent_status_string))) {
            statusView.setImageResource(R.drawable.ic_urgent);
        }

        ImageView eventLocation = (ImageView) findViewById(R.id.event_location_placeholder);
        String eventLocationString = visit.getLocationType() + "";

        if (eventLocationString.equalsIgnoreCase(getString(R.string.general_location_string))) {
            eventLocation.setImageResource(R.drawable.ic_general);
        } else if (eventLocationString.equalsIgnoreCase(getString(R.string.surgery_location_string))) {
            eventLocation.setImageResource(R.drawable.ic_or);
        } else if (eventLocationString.equalsIgnoreCase(getString(R.string.pediatrics_location_string))) {
            eventLocation.setImageResource(R.drawable.ic_pediatrics);
        } else if (eventLocationString.equalsIgnoreCase(getString(R.string.maternity_location_string))) {
            eventLocation.setImageResource(R.drawable.ic_maternity);
        } else if (eventLocationString.equalsIgnoreCase(getString(R.string.er_location_string))) {
            eventLocation.setImageResource(R.drawable.ic_er);
        } else if (eventLocationString.equalsIgnoreCase(getString(R.string.icu_location_string))) {
            eventLocation.setImageResource(R.drawable.ic_icu);
        } else if (eventLocationString.equalsIgnoreCase(getString(R.string.dental_location_string))) {
            eventLocation.setImageResource(R.drawable.ic_dentistry);
        } else if (eventLocationString.equalsIgnoreCase(getString(R.string.radiology_location_string))) {
            eventLocation.setImageResource(R.drawable.ic_radiology);
        } else if (eventLocationString.equalsIgnoreCase(getString(R.string.cardiology_location_string))) {
            eventLocation.setImageResource(R.drawable.ic_cardiology);
        } else if (eventLocationString.equalsIgnoreCase(getString(R.string.infectious_location_string))) {
            eventLocation.setImageResource(R.drawable.ic_infectious);
        } else if (eventLocationString.equalsIgnoreCase(getString(R.string.orthopedics_location_string))) {
            eventLocation.setImageResource(R.drawable.ic_orthopedics);
        } else if (eventLocationString.equalsIgnoreCase(getString(R.string.urology_location_string))) {
            eventLocation.setImageResource(R.drawable.ic_urology);
        } else if (eventLocationString.equalsIgnoreCase(getString(R.string.other_location_string))) {
            eventLocation.setImageResource(R.drawable.ic_other);
        }

        final EventActivityListAdapter visitViewAdapter = new EventActivityListAdapter(this);
        final ListView listView = (ListView) findViewById(R.id.listView);
        listView.setEmptyView(findViewById(R.id.emptyList));

        View header = getLayoutInflater().inflate(R.layout.listview_header_layout, null);
        listView.addHeaderView(header);
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
                Intent intent = new Intent(context, PatientActivity.class);
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

        loadNextPage(visitViewAdapter);

    }

    private void loadNextPage(final EventActivityListAdapter eventActivityListAdapter) {
        HashMap<String, Integer> request = new HashMap<>();
        request.put("iteration", iteration);
        ParseCloud.callFunctionInBackground("eventActivityList", request, new FunctionCallback<ArrayList<HashMap<String, Object>>>() {
            @Override
            public void done(ArrayList<HashMap<String, Object>> response, ParseException e) {
                if (!response.isEmpty()) {
                    if (response.size() < 5)
                        more = false;
                    eventActivityListAdapter.updateEntries(response);
                }
//                System.out.println(o);
            }
        });
    }

}
