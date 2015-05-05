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

import com.cookiesmart.pocketehr_android.HelperClasses.MyCustomAdapter;
import com.parse.ParseObject;

import java.util.ArrayList;

/**
 * Created by aditya841 on 11/19/2014.
 */
public class PatientListActivity extends Activity {
//    private static String TAG = "PatientListActivity";
//    final Context context = this;
//    private final String DATE_ORDER = "createdAt";
//    private final String ALPHA_ORDER = "lastName";
//    private final int ALPHA_AZ = 3;
//    private final int ALPHA_ZA = 1;
//    private final int DATE = 2;
//    private int currentType = DATE;
//    //    private ArrayList<ParseObject> patients;
//    private int preLast;
//    private Menu menu = null;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_patientlist);
//        Intent intent = getIntent();
////        int type = intent.getIntExtra("type", 1);
//        int type = 1;
//        switch (type) {
//            case ALPHA_AZ:
//                currentType = R.id.action_alpha_az_order;
//                break;
//            case ALPHA_ZA:
//                currentType = R.id.action_alpha_za_order;
//                break;
//            case DATE:
//                currentType = R.id.action_date_order;
//                break;
//            default:
//                Log.i(TAG, "Wrong choices");
//        }
//        loadView(type);
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        this.menu = menu;
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.patient_list, menu);
//
//        MenuItem date = menu.findItem(R.id.action_date_order);
//        MenuItem az = menu.findItem(R.id.action_alpha_az_order);
//        MenuItem za = menu.findItem(R.id.action_alpha_za_order);
//
//        switch (currentType) {
//            case R.id.action_alpha_za_order:
//                date.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
//                az.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
//                za.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
//                break;
//            case R.id.action_alpha_az_order:
//                date.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
//                za.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
//                az.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
//                break;
//            case R.id.action_date_order:
//                az.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
//                za.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
//                date.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
//                break;
//            default:
//                Log.i(TAG, "Wrong current type");
//        }
//
//        // Associate searchable configuration with the SearchView
//        SearchManager searchManager =
//                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        SearchView searchView =
//                (SearchView) menu.findItem(R.id.action_search).getActionView();
//        searchView.setSearchableInfo(
//                searchManager.getSearchableInfo(getComponentName()));
//
//        return true;
//    }
//
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up login_button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.action_alpha_za_order && currentType != R.id.action_alpha_za_order) {
//            Intent intent = new Intent(this, PatientListActivity.class);
//            intent.putExtra("type", ALPHA_ZA);
//            finish();
//            startActivity(intent);
//        } else if (id == R.id.action_date_order && currentType != R.id.action_date_order) {
//            Intent intent = new Intent(this, PatientListActivity.class);
//            intent.putExtra("type", DATE);
//            finish();
//            startActivity(intent);
//        } else if (id == R.id.action_alpha_az_order && currentType != R.id.action_alpha_az_order) {
//            Intent intent = new Intent(this, PatientListActivity.class);
//            intent.putExtra("type", ALPHA_AZ);
//            finish();
//            startActivity(intent);
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    public void loadView(int type) {
//        final MyCustomAdapter patientViewAdapter = new MyCustomAdapter(this, type, "");
//        final ListView listView = (ListView) findViewById(R.id.listView);
//        listView.setAdapter(patientViewAdapter);
//
//        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                final int lastItem = firstVisibleItem + visibleItemCount;
//                if (lastItem == totalItemCount) {
//                    if (preLast != lastItem) { //to avoid multiple calls for last item
//                        preLast = lastItem;
//                        patientViewAdapter.loadNextPage();
//                    }
//                }
//            }
//        });
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Object o = listView.getItemAtPosition(position);
//                ParseObject patient = (ParseObject) o;
//                Intent intent = new Intent(context, PatientActivity.class);
//                intent.putExtra("objectId", patient.getObjectId());
//                intent.putExtra("view_tag", (String) view.getTag());
//                startActivityForResult(intent, 1);
//            }
//        });
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (data.hasExtra("view_tag") && resultCode == RESULT_OK) {
//            String view_tag = data.getStringExtra("view_tag");
//            String status = data.getStringExtra("status");
//            final ListView listView = (ListView) findViewById(R.id.listView);
//            getViewsByTag(listView, view_tag, status);
//        }
//    }
//
//    private ArrayList<View> getViewsByTag(ViewGroup root, String tag, String status) {
//        ArrayList<View> views = new ArrayList<View>();
//        final int childCount = root.getChildCount();
//        for (int i = 0; i < childCount; i++) {
//            final View child = root.getChildAt(i);
//            if (child instanceof ViewGroup) {
//                views.addAll(getViewsByTag((ViewGroup) child, tag, status));
//            }
//
//            final Object tagObj = child.getTag();
//            if (tagObj != null && tagObj.equals(tag)) {
//                Log.i(TAG, "Found view. Updating now." + status);
////                TextView patient_status = (TextView) child.findViewById(R.id.patient_status);
////                if (status.equalsIgnoreCase(getString(R.string.server_negative_status))) {
////                    patient_status.setBackgroundColor(Color.GREEN);
////                    //status_field.setTextColor(Color.WHITE);
////                    patient_status.setText(getString(R.string.negative_status));
////                } else if (status.equalsIgnoreCase(getString(R.string.server_positive_status))) {
////                    patient_status.setBackgroundColor(Color.RED);
////                    //status_field.setTextColor(Color.WHITE);
////                    patient_status.setText(getString(R.string.positive_status));
////                } else if (status.equalsIgnoreCase(getString(R.string.server_incomplete_status))) {
////                    patient_status.setBackgroundColor(Color.BLUE);
////                    //status_field.setTextColor(Color.WHITE);
////                    patient_status.setText(getString(R.string.incomplete_status));
////                } else if (status.equalsIgnoreCase(getString(R.string.server_deceased_status))) {
////                    patient_status.setBackgroundColor(Color.BLACK);
////                    //status_field.setTextColor(Color.WHITE);
////                    patient_status.setText(getString(R.string.deceased_status));
////                }
//            }
//        }
//        return views;
//    }
//
//    @Override
//    public void onBackPressed() {
//        finish();
//        super.onBackPressed();
//    }
}
