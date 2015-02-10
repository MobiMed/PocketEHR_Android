package com.cookiesmart.pocketehr_android;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

/**
 * Created by aditya841 on 11/19/2014.
 */
public class MyCustomAdapter extends ParseQueryAdapter<ParseObject> {
    private static String ADAPTER = "CustomAdapter";

    public MyCustomAdapter(Context context) {
        super(context, new ParseQueryAdapter.QueryFactory<ParseObject>() {
            public ParseQuery create() {
                ParseQuery query = new ParseQuery("Patient");
                query.orderByDescending("createdAt");
                return query;
            }
        });
        setObjectsPerPage(50);
    }

    // Customize the layout by overriding getItemView
    @Override
    public View getItemView(ParseObject object, View v, ViewGroup parent) {
        if (v == null) {
            v = View.inflate(getContext(), R.layout.rowlayout, null);
        }

        Context context = getContext();
        super.getItemView(object, v, parent);

        // Add the patient name
        String status = object.getString("status");
        TextView statusTextView = (TextView) v.findViewById(R.id.patient_status);

        if (status.contains("Negative")) {
            statusTextView.setBackgroundColor(Color.parseColor("#00BB00"));
            //status_field.setTextColor(Color.WHITE);
            statusTextView.setText(context.getString(R.string.negative_status));
        } else if (status.contains("Positive")) {
            statusTextView.setBackgroundColor(Color.RED);
            //status_field.setTextColor(Color.WHITE);
            statusTextView.setText(context.getString(R.string.positive_status));
        } else if (status.contains("Incomplete")) {
            statusTextView.setBackgroundColor(Color.BLUE);
            //status_field.setTextColor(Color.WHITE);
            statusTextView.setText(context.getString(R.string.incomplete_status));
        } else if (status.contains("Deceased")) {
            statusTextView.setBackgroundColor(Color.BLACK);
            //status_field.setTextColor(Color.WHITE);
            statusTextView.setText(context.getString(R.string.deceased_status));
        }


        // Add the patient name
        TextView nameTextView = (TextView) v.findViewById(R.id.patient_name);
        nameTextView.setText(object.getString("lastName") + ", " + object.getString("firstName"));

        return v;
    }
}
