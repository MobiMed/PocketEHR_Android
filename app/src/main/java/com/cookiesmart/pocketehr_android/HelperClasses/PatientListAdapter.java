package com.cookiesmart.pocketehr_android.HelperClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cookiesmart.pocketehr_android.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by aditya841 on 4/22/2015.
 */
public class PatientListAdapter extends ArrayAdapter<PatientList> {
    private LayoutInflater mLayoutInflater;
    private ArrayList<PatientList> patientList = new ArrayList<>();
    private Context activity_context;


    public PatientListAdapter(Context context) {
        super(context, 0);
        activity_context = context;
        mLayoutInflater = (LayoutInflater) activity_context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(
                    R.layout.rowlayout, parent, false);

        }
        PatientList patientList = this.patientList.get(position);

        String status = patientList.getPatientVisitStatus();

        if (status.equalsIgnoreCase(activity_context.getString(R.string.admit_server_string))) {
            convertView.setBackgroundColor(activity_context.getResources().getColor(R.color.admit_label_color));
        } else if (status.equalsIgnoreCase(activity_context.getString(R.string.visit_server_string))) {
            convertView.setBackgroundColor(activity_context.getResources().getColor(R.color.visit_label_color));
        } else if (status.equalsIgnoreCase(activity_context.getString(R.string.emergency_server_string))) {
            convertView.setBackgroundColor(activity_context.getResources().getColor(R.color.emergency_label_color));
        }

        // Add the patient name
        TextView nameTextView = (TextView) convertView.findViewById(R.id.patient_name_placeholder);
        nameTextView.setText(patientList.getPatientLastName() + ", " + patientList.getPatientFirstName());

        ImageView testStatus = (ImageView) convertView.findViewById(R.id.test_status_placeholder);
//        String testStatusString = patientList.getPatientTestStatus();
        String testStatusString = "Reported";
        if (testStatusString.equalsIgnoreCase(activity_context.getString(R.string.test_reported_string))) {
            testStatus.setImageResource(R.drawable.ic_test_report);
        } else {
            testStatus.setImageResource(R.drawable.ic_test_order);
        }

        TextView eventDate = (TextView) convertView.findViewById(R.id.date_placeholder);
        Date date = patientList.getPatientVisitDate();
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

        ImageView eventStatus = (ImageView) convertView.findViewById(R.id.event_status_placeholder);
        String eventStatusString = patientList.getPatientVisitStatus();
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

        ImageView eventLocation = (ImageView) convertView.findViewById(R.id.event_location_placeholder);
        String eventLocationString = patientList.getPatientVisitLocation();

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

        return convertView;
    }

    public void updateEntries(ArrayList<PatientList> newPatientList) {
        patientList = newPatientList;
        notifyDataSetChanged();
    }
}
