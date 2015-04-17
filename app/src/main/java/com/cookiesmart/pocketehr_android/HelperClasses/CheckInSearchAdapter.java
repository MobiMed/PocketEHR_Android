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

/**
 * Created by aditya841 on 11/19/2014.
 */
public class CheckInSearchAdapter extends ParseQueryAdapter<ParseObject> {
    private static String TAG = "CustomAdapter";
    private Context activity_context;

    public CheckInSearchAdapter(final Context context, final int type, final String searchTerm) {
        super(context, new ParseQueryAdapter.QueryFactory<ParseObject>() {
            public ParseQuery<ParseObject> create() {
                ParseQuery<ParseObject> query = null;
                query = new ParseQuery<ParseObject>("patient");
                query.whereMatches("patientName", searchTerm, "i");
                query.orderByDescending("lastName");
                return query;
            }
        });
        setObjectsPerPage(50);
        Log.i(TAG, searchTerm + type);
        activity_context = context;
    }

    // Customize the layout by overriding getItemView
    @Override
    public View getItemView(ParseObject object, View v, ViewGroup parent) {
        if (v == null) {
            v = View.inflate(getContext(), R.layout.checkinrowlayout, null);
        }

//        Context context = getContext();
        super.getItemView(object, v, parent);

        v.setTag(object.getObjectId());

        // Add the patient name
        TextView nameTextView = (TextView) v.findViewById(R.id.patient_name);
        nameTextView.setText(object.getString("patientName"));

        return v;
    }

    @Override
    public void loadNextPage() {
        super.loadNextPage();
    }
}
