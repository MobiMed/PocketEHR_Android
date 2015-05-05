package com.cookiesmart.pocketehr_android.Controllers;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cookiesmart.pocketehr_android.HelperClasses.CheckInSearchAdapter;
import com.cookiesmart.pocketehr_android.HelperClasses.Visit;
import com.cookiesmart.pocketehr_android.R;
import com.parse.ParseObject;

/**
 * Created by aditya841 on 3/2/2015.
 */
public class CheckInSearchResultActivity extends Activity {
    private static String TAG = "SearchResultActivity";
    private final int SEARCH = 4;
    //    private ArrayList<ParseObject> patients;
    Context context = this;
    private int preLast = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkinsearch);
        handleIntent(getIntent());
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            loadView(query);
        }
    }

    public void loadView(String query) {
        final CheckInSearchAdapter patientViewAdapter = new CheckInSearchAdapter(this, SEARCH, query);
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
                if (lastItem == totalItemCount && totalItemCount > 1) {
                    if (preLast != lastItem) { //to avoid multiple calls for last item
                        preLast = lastItem;
                        Log.i(TAG, "Loading Next Page");
                        patientViewAdapter.loadNextPage();
                    }
                }
            }
        });

//        patients = new ArrayList<ParseObject>();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = listView.getItemAtPosition(position);
                ParseObject patient = (ParseObject) o;
                Intent intent = new Intent(context, VisitTypeActivity.class);
                Visit visit = new Visit();
                visit.setPatient_object_id(patient.getObjectId());
                intent.putExtra("visit", visit);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //do nothing with the result
        finish();
    }
}
