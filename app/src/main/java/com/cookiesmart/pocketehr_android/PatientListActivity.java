package com.cookiesmart.pocketehr_android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.parse.ParseObject;

import org.json.JSONArray;
import org.json.JSONException;

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

                //getting locations array of patient
                JSONArray bodyPartsArray = patient.getJSONArray("locations");
                ArrayList<String> bodyParts = new ArrayList<String>();
                if (bodyPartsArray != null) {
                    for (int i = 0; i < bodyPartsArray.length(); i++) {
                        try {
                            bodyParts.add(bodyPartsArray.get(i).toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                intent.putStringArrayListExtra("locations", bodyParts);
                startActivity(intent);
            }
        });
    }
}
