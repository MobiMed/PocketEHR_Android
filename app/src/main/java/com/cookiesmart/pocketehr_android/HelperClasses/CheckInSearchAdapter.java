package com.cookiesmart.pocketehr_android.HelperClasses;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cookiesmart.pocketehr_android.R;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by aditya841 on 11/19/2014.
 */
public class CheckInSearchAdapter extends ParseQueryAdapter<ParseObject> {
    private static String TAG = "CheckInSearchAdapter";
    private Context activity_context;

    public CheckInSearchAdapter(final Context context, final int type, final String searchTerm) {
        super(context, new ParseQueryAdapter.QueryFactory<ParseObject>() {
            public ParseQuery<ParseObject> create() {
                ParseQuery<ParseObject> query;
                List<ParseQuery<ParseObject>> queries = new ArrayList<ParseQuery<ParseObject>>();
                ParseQuery<ParseObject> query1 = new ParseQuery<ParseObject>("patient");
                ParseQuery<ParseObject> query2 = new ParseQuery<ParseObject>("patient");
                query1.whereMatches("patientLastName", searchTerm, "i");
                query2.whereMatches("patientFirstName", searchTerm, "i");
                queries.add(query1);
                queries.add(query2);
                query = ParseQuery.or(queries);
                return query;
            }
        });
        setObjectsPerPage(50);
        activity_context = context;
    }

    // Customize the layout by overriding getItemView
    @Override
    public View getItemView(ParseObject object, View v, ViewGroup parent) {
        if (v == null) {
            v = View.inflate(getContext(), R.layout.checkinrowlayout, null);
        }

        Log.i(TAG, "Inflating view");
        v.setTag(object.getObjectId());

        // Add the patient name
        TextView nameTextView = (TextView) v.findViewById(R.id.patient_name);
        nameTextView.setText(object.getString("patientLastName") + ", " + object.getString("patientFirstName"));

        TextView eventDate = (TextView) v.findViewById(R.id.patient_dob);
        Date date = object.getDate("patientDOB");
        if (date != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yy", Locale.getDefault());
            String dateString = simpleDateFormat.format(date);
            eventDate.setText(dateString);
        } else {
            eventDate.setText("Date not coming");
        }

        return v;
    }

    @Override
    public void loadNextPage() {
        super.loadNextPage();
    }
}
