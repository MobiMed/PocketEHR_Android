package com.cookiesmart.pocketehr_android.HelperClasses;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cookiesmart.pocketehr_android.R;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by aditya841 on 11/19/2014.
 */
public class MyCustomAdapter extends ParseQueryAdapter<ParseObject> {
    private static String TAG = "CustomAdapter";
    private Context activity_context;

    public MyCustomAdapter(final Context context, final int type, final String searchTerm) {
        super(context, new ParseQueryAdapter.QueryFactory<ParseObject>() {
            public ParseQuery<ParseObject> create() {
                ParseQuery<ParseObject> query = null;
                if (type == 1) {
                    ParseQuery<ParseObject> innerQuery = new ParseQuery<ParseObject>("patient");
                    query = new ParseQuery<>("medicalEvents");
                    query.whereMatchesKeyInQuery("patientId", "objectId", innerQuery);
                    query.orderByDescending("createdAt");
                    query.include("patientId");
                } else if (type == 2) {
                    query = new ParseQuery<>("patient");
                    query.orderByDescending("createdAt");
                } else if (type == 3) {
                    query = new ParseQuery<>("patient");
                    query.orderByAscending("lastName");
                } else if (type == 4) {
                    List<ParseQuery<ParseObject>> queries = new ArrayList<ParseQuery<ParseObject>>();
                    ParseQuery<ParseObject> query1 = new ParseQuery<ParseObject>("patient");
                    ParseQuery<ParseObject> query2 = new ParseQuery<ParseObject>("patient");
                    query1.whereMatches("lastName", searchTerm, "i");
                    query2.whereMatches("firstName", searchTerm, "i");
                    queries.add(query1);
                    queries.add(query2);
                    query = ParseQuery.or(queries);
                }
                return query;
            }
        });
        Log.i(TAG, searchTerm + type);
        activity_context = context;
    }

    // Customize the layout by overriding getItemView
    @Override
    public View getItemView(ParseObject object, View v, ViewGroup parent) {
        if (v == null) {
            v = View.inflate(getContext(), R.layout.rowlayout, null);
        }

        v.setTag(object.getObjectId());
//        System.out.println(object.getClassName());
//        return v;

        ParseObject patient_object = object.getParseObject("patientId");


        // Add the patient status
        String status = object.getString("eventType");

        if (status.equalsIgnoreCase(activity_context.getString(R.string.admit_server_string))) {
            v.setBackgroundColor(activity_context.getResources().getColor(R.color.admit_label_color));
        } else if (status.equalsIgnoreCase(activity_context.getString(R.string.visit_server_string))) {
            v.setBackgroundColor(activity_context.getResources().getColor(R.color.visit_label_color));
        } else if (status.equalsIgnoreCase(activity_context.getString(R.string.emergency_server_string))) {
            v.setBackgroundColor(activity_context.getResources().getColor(R.color.emergency_label_color));
        }

        // Add the patient name
        TextView nameTextView = (TextView) v.findViewById(R.id.patient_name_placeholder);
        nameTextView.setText(patient_object.getString("patientLastName") + ", " + patient_object.getString("patientFirstName"));

        ImageView testStatus = (ImageView) v.findViewById(R.id.test_status_placeholder);
//        String testStatusString = object.getString("testStatus");
        String testStatusString = "Reported";
        if (testStatusString.equalsIgnoreCase(activity_context.getString(R.string.test_reported_string))) {
            testStatus.setImageResource(R.drawable.ic_test_report);
        } else {
            testStatus.setImageResource(R.drawable.ic_test_order);
        }

        TextView eventDate = (TextView) v.findViewById(R.id.date_placeholder);
        Date date = object.getCreatedAt();
        if (date != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            month = month + 1;
            String monthString = month + "";
            String dayString = day + "";
            if (month < 10) {
                monthString = "0" + month;
            }
            if (day < 10) {
                dayString = "0" + day;
            }
            eventDate.setText(monthString + "/" + dayString + "/" + year);
        } else {
            eventDate.setText("Date not coming");
        }

        ImageView eventStatus = (ImageView) v.findViewById(R.id.event_status_placeholder);
        String eventStatusString = object.getString("eventStatus");
        if (eventStatusString.equalsIgnoreCase(activity_context.getString(R.string.routine_status_string))) {
            eventStatus.setImageResource(R.drawable.ic_routine);
        } else if (eventStatusString.equalsIgnoreCase(activity_context.getString(R.string.surgery_status_string))) {
            eventStatus.setImageResource(R.drawable.ic_surgery);
        } else if (eventStatusString.equalsIgnoreCase(activity_context.getString(R.string.newborn_status_string))) {
            eventStatus.setImageResource(R.drawable.ic_newborn);
        } else if (eventStatusString.equalsIgnoreCase(activity_context.getString(R.string.labor_status_string))) {
            eventStatus.setImageResource(R.drawable.ic_labor);
        } else if (eventStatusString.equalsIgnoreCase(activity_context.getString(R.string.accident_status_string))) {
            eventStatus.setImageResource(R.drawable.ic_accident);
        } else if (eventStatusString.equalsIgnoreCase(activity_context.getString(R.string.urgent_status_string))) {
            eventStatus.setImageResource(R.drawable.ic_urgent);
        }

        ImageView eventLocation = (ImageView) v.findViewById(R.id.event_location_placeholder);
        String eventLocationString = object.getString("eventLocation");

        if (eventLocationString.equalsIgnoreCase(activity_context.getString(R.string.general_location_string))) {
            eventLocation.setImageResource(R.drawable.ic_general);
        } else if (eventLocationString.equalsIgnoreCase(activity_context.getString(R.string.surgery_location_string))) {
            eventLocation.setImageResource(R.drawable.ic_or);
        } else if (eventLocationString.equalsIgnoreCase(activity_context.getString(R.string.pediatrics_location_string))) {
            eventLocation.setImageResource(R.drawable.ic_pediatrics);
        } else if (eventLocationString.equalsIgnoreCase(activity_context.getString(R.string.maternity_location_string))) {
            eventLocation.setImageResource(R.drawable.ic_maternity);
        } else if (eventLocationString.equalsIgnoreCase(activity_context.getString(R.string.er_location_string))) {
            eventLocation.setImageResource(R.drawable.ic_er);
        } else if (eventLocationString.equalsIgnoreCase(activity_context.getString(R.string.icu_location_string))) {
            eventLocation.setImageResource(R.drawable.ic_icu);
        } else if (eventLocationString.equalsIgnoreCase(activity_context.getString(R.string.dental_location_string))) {
            eventLocation.setImageResource(R.drawable.ic_dentistry);
        } else if (eventLocationString.equalsIgnoreCase(activity_context.getString(R.string.radiology_location_string))) {
            eventLocation.setImageResource(R.drawable.ic_radiology);
        } else if (eventLocationString.equalsIgnoreCase(activity_context.getString(R.string.cardiology_location_string))) {
            eventStatus.setImageResource(R.drawable.ic_cardiology);
        } else if (eventLocationString.equalsIgnoreCase(activity_context.getString(R.string.infectious_location_string))) {
            eventStatus.setImageResource(R.drawable.ic_infectious);
        } else if (eventLocationString.equalsIgnoreCase(activity_context.getString(R.string.orthopedics_location_string))) {
            eventStatus.setImageResource(R.drawable.ic_orthopedics);
        } else if (eventLocationString.equalsIgnoreCase(activity_context.getString(R.string.urology_location_string))) {
            eventStatus.setImageResource(R.drawable.ic_urology);
        } else if (eventLocationString.equalsIgnoreCase(activity_context.getString(R.string.other_location_string))) {
            eventStatus.setImageResource(R.drawable.ic_other);
        }

        return v;
    }
}
