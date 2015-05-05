package com.cookiesmart.pocketehr_android.Controllers;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.cookiesmart.pocketehr_android.HelperClasses.Patient;
import com.cookiesmart.pocketehr_android.HelperClasses.PatientListAdapter;
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
 * Created by aditya841 on 11/19/2014.
 */
public class NewPatientListActivity extends Activity {
    private static String TAG = "PatientListActivity";
    final Context context = this;
    private int preLast;
    private int iteration = 0;
    private boolean more = true;
    private String orderType = "DD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patientlist);
        Intent intent = getIntent();
        loadView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.patient_list, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up login_button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
    }

    public void loadView() {
        final PatientListAdapter patientViewAdapter = new PatientListAdapter(this);
        final ListView listView = (ListView) findViewById(R.id.listView);
        listView.setEmptyView(findViewById(R.id.emptyList));
        listView.setAdapter(patientViewAdapter);

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
                        loadNextPage(patientViewAdapter);
                    }
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, Object> listObject = (HashMap<String, Object>) parent.getItemAtPosition(position);
                Patient patient = new Patient();
                Intent intent = new Intent(context, PatientVisitsScreenActivity.class);
                patient.setPatientIDNumber((String) listObject.get("patientId"));
                patient.setFirstName((String) listObject.get("patientFirstName"));
                patient.setLastName((String) listObject.get("patientLastName"));
//                Add patient pEHRP Id to cloud code
                patient.setPatientObjectId("patientObjectId");
//                Add patientDOB to cloud code
                Date date = (Date) listObject.get("patientDOB");
                if (date != null) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yy", Locale.getDefault());
                    String dateString = simpleDateFormat.format(date);
                    patient.setDob(dateString);
                } else {
                    patient.setDob("Date not coming");
                }
//                Add patientGender to cloud code
                patient.setGender((String) listObject.get("patientGender"));
                intent.putExtra("patient", patient);
                intent.putExtra("view_tag", (String) view.getTag());
                startActivityForResult(intent, 1);
            }
        });

        iteration++;
        loadNextPage(patientViewAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (data.hasExtra("view_tag") && resultCode == RESULT_OK) {
//            String view_tag = data.getStringExtra("view_tag");
//            String status = data.getStringExtra("status");
//            final ListView listView = (ListView) findViewById(R.id.listView);
//            getViewsByTag(listView, view_tag, status);
//        }
    }

    private ArrayList<View> getViewsByTag(ViewGroup root, String tag, String status) {
        ArrayList<View> views = new ArrayList<View>();
        final int childCount = root.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = root.getChildAt(i);
            if (child instanceof ViewGroup) {
                views.addAll(getViewsByTag((ViewGroup) child, tag, status));
            }

            final Object tagObj = child.getTag();
            if (tagObj != null && tagObj.equals(tag)) {
                Log.i(TAG, "Found view. Updating now." + status);
//                TextView patient_status = (TextView) child.findViewById(R.id.patient_status);
//                if (status.equalsIgnoreCase(getString(R.string.server_negative_status))) {
//                    patient_status.setBackgroundColor(Color.GREEN);
//                    //status_field.setTextColor(Color.WHITE);
//                    patient_status.setText(getString(R.string.negative_status));
//                } else if (status.equalsIgnoreCase(getString(R.string.server_positive_status))) {
//                    patient_status.setBackgroundColor(Color.RED);
//                    //status_field.setTextColor(Color.WHITE);
//                    patient_status.setText(getString(R.string.positive_status));
//                } else if (status.equalsIgnoreCase(getString(R.string.server_incomplete_status))) {
//                    patient_status.setBackgroundColor(Color.BLUE);
//                    //status_field.setTextColor(Color.WHITE);
//                    patient_status.setText(getString(R.string.incomplete_status));
//                } else if (status.equalsIgnoreCase(getString(R.string.server_deceased_status))) {
//                    patient_status.setBackgroundColor(Color.BLACK);
//                    //status_field.setTextColor(Color.WHITE);
//                    patient_status.setText(getString(R.string.deceased_status));
//                }
            }
        }
        return views;
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    private void loadNextPage(final PatientListAdapter adapter) {
        HashMap<String, Object> request = new HashMap<>();
        request.put("iteration", iteration);
        request.put("orderType", orderType);
        ParseCloud.callFunctionInBackground("patientList", request, new FunctionCallback<ArrayList<HashMap<String, Object>>>() {
            @Override
            public void done(ArrayList<HashMap<String, Object>> response, ParseException e) {
                if (response.size() < 50) {
                    more = false;
                }
                System.out.println(response);
                adapter.updateEntries(response);
            }
        });
    }
}
