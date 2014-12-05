package com.cookiesmart.pocketehr_android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.parse.ParseObject;

import java.util.ArrayList;

/**
 * Created by aditya841 on 11/19/2014.
 */
public class PatientListActivity extends Activity {
    private static String PATIENT = "PatientListActivity";
    final Context context = this;
    ArrayList<ParseObject> patients;
    private int preLast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patientlist);
        //System.out.print(patients.get(0).getString("firstName"));

        final MyCustomAdapter patientViewAdapter = new MyCustomAdapter(this);
        final ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(patientViewAdapter);
        patientViewAdapter.loadObjects();

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                final int lastItem = firstVisibleItem + visibleItemCount;
                if (lastItem == totalItemCount) {
                    if (preLast != lastItem) { //to avoid multiple calls for last item
                        preLast = lastItem;
                        patientViewAdapter.loadNextPage();
                    }
                }
            }
        });

        patients = new ArrayList<ParseObject>();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = listView.getItemAtPosition(position);
                ParseObject patient = (ParseObject) o;
                Intent intent = new Intent(context, PatientActivity.class);
                intent.putExtra("firstName", patient.getString("firstName"));
                intent.putExtra("lastName", patient.getString("lastName"));
                intent.putExtra("telepathologyID", patient.getString("telepathologyID"));
                intent.putExtra("status", patient.getString("status"));
                intent.putExtra("sex", patient.getString("sex"));
                intent.putExtra("objectId", patient.getObjectId());
                startActivity(intent);
            }
        });
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
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
    }

//    private void getPatients() {
//        //Query to get data
//        ParseQuery<ParseObject> query = ParseQuery.getQuery("Patient");
//        query.addDescendingOrder("createdAt");
//        query.setLimit(15);
//        query.findInBackground(new FindCallback<ParseObject>() {
//            public void done(List<ParseObject> patientList, ParseException e) {
//                if (e == null) {
//                    Log.d(PATIENT, "Writing List.");
//                    for (ParseObject patient : patientList) {
//                        patients.add(patient);
//                    }
//                    setListAdapter(new MyCustomAdapter((Activity) context, R.layout.rowlayout, patients));
//                } else {
//                    Log.d(PATIENT, "Error: " + e.getMessage());
//                }
//            }
//        });
//        return;
//    }
}
