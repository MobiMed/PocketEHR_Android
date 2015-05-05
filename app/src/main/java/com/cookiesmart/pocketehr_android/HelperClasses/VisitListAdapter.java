package com.cookiesmart.pocketehr_android.HelperClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cookiesmart.pocketehr_android.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by aditya841 on 4/22/2015.
 */
public class VisitListAdapter extends ArrayAdapter<HashMap<String, Object>> {
    private LayoutInflater mLayoutInflater;
    private ArrayList<HashMap<String, Object>> patientList = new ArrayList<>();
    private Context activity_context;


    public VisitListAdapter(Context context) {
        super(context, 0);
        activity_context = context;
        mLayoutInflater = (LayoutInflater) activity_context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return patientList.size();
    }

    @Override
    public HashMap<String, Object> getItem(int position) {
        return patientList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(
                    R.layout.visitrowlayout, parent, false);

        }
        HashMap<String, Object> patientListObject = patientList.get(position);

        String eventType = (String) patientListObject.get("eventType") + "";
        LinearLayout detailsLayout = (LinearLayout) convertView.findViewById(R.id.visit_rowlayout);

        if (eventType.equalsIgnoreCase(activity_context.getString(R.string.admit_server_string))) {
            detailsLayout.setBackgroundResource(R.drawable.details_background_admit);
        } else if (eventType.equalsIgnoreCase(activity_context.getString(R.string.visit_server_string))) {
            detailsLayout.setBackgroundResource(R.drawable.details_background_visit);
        } else if (eventType.equalsIgnoreCase(activity_context.getString(R.string.emergency_server_string))) {
            detailsLayout.setBackgroundResource(R.drawable.details_background_emergency);
        }

        ImageView testStatus = (ImageView) convertView.findViewById(R.id.test_status_placeholder);
//        String testStatusString = (String) patientList.get("patientTestStatus");
        String testStatusString = "Reported";
        if (testStatusString.equalsIgnoreCase(activity_context.getString(R.string.test_reported_string))) {
            testStatus.setImageResource(R.drawable.ic_test_report);
        } else {
            testStatus.setImageResource(R.drawable.ic_test_order);
        }

        TextView eventDate = (TextView) convertView.findViewById(R.id.date_placeholder);
        Date date = (Date) patientListObject.get("updatedAt");
        if (date != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yy", Locale.getDefault());
            String dateString = simpleDateFormat.format(date);
            eventDate.setText(dateString);
        } else {
            eventDate.setText("Date not coming");
        }

        ImageView eventStatus = (ImageView) convertView.findViewById(R.id.event_status_placeholder);
        String eventStatusString = (String) patientListObject.get("eventStatus") + "";
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
        String eventLocationString = (String) patientListObject.get("eventLocation") + "";

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
            eventLocation.setImageResource(R.drawable.ic_cardiology);
        } else if (eventLocationString.equalsIgnoreCase(activity_context.getString(R.string.infectious_location_string))) {
            eventLocation.setImageResource(R.drawable.ic_infectious);
        } else if (eventLocationString.equalsIgnoreCase(activity_context.getString(R.string.orthopedics_location_string))) {
            eventLocation.setImageResource(R.drawable.ic_orthopedics);
        } else if (eventLocationString.equalsIgnoreCase(activity_context.getString(R.string.urology_location_string))) {
            eventLocation.setImageResource(R.drawable.ic_urology);
        } else if (eventLocationString.equalsIgnoreCase(activity_context.getString(R.string.other_location_string))) {
            eventLocation.setImageResource(R.drawable.ic_other);
        }

        return convertView;
    }

    public void updateEntries(ArrayList<HashMap<String, Object>> newPatientList) {
        if (!newPatientList.isEmpty()) {
            patientList.addAll(newPatientList);
            notifyDataSetChanged();
        }
    }
}
