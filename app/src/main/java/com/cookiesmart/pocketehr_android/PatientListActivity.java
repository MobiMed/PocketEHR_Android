package com.cookiesmart.pocketehr_android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.ParseObject;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by aditya841 on 11/19/2014.
 */
public class PatientListActivity extends Activity {
    private static String TAG = "PatientListActivity";
    private final String DATE_ORDER = "createdAt";
    private final String ALPHA_ORDER = "lastName";
    private final int ALPHA_AZ = 3;
    private final int ALPHA_ZA = 1;
    private final int DATE = 2;
    final Context context = this;
    ArrayList<ParseObject> patients;
    private int preLast;
    private Menu menu = null;
    private int currentType = DATE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patientlist);
        loadView(currentType);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.patient_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up login_button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_alpha_za_order && currentType != R.id.action_alpha_za_order) {
            item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            MenuItem item1 = menu.findItem(R.id.action_date_order);
            MenuItem item2 = menu.findItem(R.id.action_alpha_az_order);
            item1.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
            item2.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
            loadView(ALPHA_ZA);
            currentType = R.id.action_alpha_za_order;
        } else if (id == R.id.action_date_order && currentType != R.id.action_date_order) {
            item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            MenuItem item1 = menu.findItem(R.id.action_alpha_az_order);
            MenuItem item2 = menu.findItem(R.id.action_alpha_za_order);
            item1.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
            item2.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
            loadView(DATE);
            currentType = R.id.action_date_order;
        } else if (id == R.id.action_alpha_az_order && currentType != R.id.action_alpha_az_order) {
            item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            MenuItem item1 = menu.findItem(R.id.action_alpha_za_order);
            MenuItem item2 = menu.findItem(R.id.action_date_order);
            item1.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
            item2.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
            loadView(ALPHA_AZ);
            currentType = R.id.action_alpha_az_order;
        }
        return super.onOptionsItemSelected(item);
    }

    public void loadView(int type) {
        final MyCustomAdapter patientViewAdapter = new MyCustomAdapter(this, type);
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
                intent.putExtra("view_tag", (String) view.getTag());

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
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String view_tag = data.getStringExtra("view_tag");
        String status = data.getStringExtra("status");
        final ListView listView = (ListView) findViewById(R.id.listView);
        getViewsByTag(listView, view_tag, status);
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
                TextView patient_status = (TextView) child.findViewById(R.id.patient_status);
                if (status.equalsIgnoreCase(getString(R.string.server_negative_status))) {
                    patient_status.setBackgroundColor(Color.GREEN);
                    //status_field.setTextColor(Color.WHITE);
                    patient_status.setText(getString(R.string.negative_status));
                } else if (status.equalsIgnoreCase(getString(R.string.server_positive_status))) {
                    patient_status.setBackgroundColor(Color.RED);
                    //status_field.setTextColor(Color.WHITE);
                    patient_status.setText(getString(R.string.positive_status));
                } else if (status.equalsIgnoreCase(getString(R.string.server_incomplete_status))) {
                    patient_status.setBackgroundColor(Color.BLUE);
                    //status_field.setTextColor(Color.WHITE);
                    patient_status.setText(getString(R.string.incomplete_status));
                } else if (status.equalsIgnoreCase(getString(R.string.server_deceased_status))) {
                    patient_status.setBackgroundColor(Color.BLACK);
                    //status_field.setTextColor(Color.WHITE);
                    patient_status.setText(getString(R.string.deceased_status));
                }
            }
        }
        return views;
    }
}
