package com.cookiesmart.pocketehr_android;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddPatientActivity extends FragmentActivity {
    private static final String ADDPATIENT = "AddPatientActivity";
    static int previousPage = 0;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide fragments representing
     * each object in a collection. We use a {@link android.support.v4.app.FragmentStatePagerAdapter}
     * derivative, which will destroy and re-create fragments as needed, saving and restoring their
     * state in the process. This is important to conserve memory and is a best practice when
     * allowing navigation between objects in a potentially large collection.
     */
    DemoCollectionPagerAdapter mDemoCollectionPagerAdapter;

    /**
     * The {@link android.support.v4.view.ViewPager} that will display the object collection.
     */
    ViewPager mViewPager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_object);
        mDemoCollectionPagerAdapter = new DemoCollectionPagerAdapter(getSupportFragmentManager());

        // Set up action bar.
        final ActionBar actionBar = getActionBar();

        // Specify that the Home button should show an "Up" caret, indicating that touching the
        // button will take the user one step up in the application's hierarchy.
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Set up the ViewPager, attaching the adapter.
    }


    private void saveState(int page, View v) {
        Log.i(ADDPATIENT, v.getId() + "");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // This is called when the Home (Up) button is pressed in the action bar.
                // Create a simple intent that starts the hierarchical parent activity and
                // use NavUtils in the Support Package to ensure proper handling of Up.
                Intent upIntent = new Intent(this, MainActivity.class);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    // This activity is not part of the application's task, so create a new task
                    // with a synthesized back stack.
                    TaskStackBuilder.from(this)
                            // If there are ancestor activities, they should be added here.
                            .addNextIntent(upIntent)
                            .startActivities();
                    finish();
                } else {
                    // This activity is part of the application's task, so simply
                    // navigate up to the hierarchical parent activity.
                    NavUtils.navigateUpTo(this, upIntent);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void storePatient(View view) {
        final ParseObject patient = new ParseObject("Patient");
        Log.i(ADDPATIENT, view.getId() + " view id");
        //Log.i(ADDPATIENT, R.id.main_add_patient_layout + " main layout id");

        LinearLayout main_add_patient_layout = (LinearLayout) view.getParent();
        ScrollView contactInformationScrollLayout = (ScrollView) main_add_patient_layout.findViewById(R.id.contact_information_scroll_layout);
        LinearLayout contactInformationLayout = (LinearLayout) contactInformationScrollLayout.findViewById(R.id.contact_information_layout);
        String lastName = ((EditText) contactInformationLayout.findViewById(R.id.last_name_input)).getText().toString();
        String firstName = ((EditText) contactInformationLayout.findViewById(R.id.first_name_input)).getText().toString();
        String hospital = ((EditText) contactInformationLayout.findViewById(R.id.hospital_input)).getText().toString();
        //String telepathologyID = ((EditText) contactInformationLayout.findViewById(R.id.patient_id_number_input)).getText().toString();
        String contactNo = ((EditText) contactInformationLayout.findViewById(R.id.contact_number_input)).getText().toString();
        //String address = ((EditText) contactInformationLayout.findViewById(R.id.address_input)).getText().toString();
        //String email = ((EditText) contactInformationLayout.findViewById(R.id.email_input)).getText().toString();

        LinearLayout patientHistory = (LinearLayout) main_add_patient_layout.findViewById(R.id.patient_history_layout);
        LinearLayout dobandage = (LinearLayout) patientHistory.findViewById(R.id.dobandage_layout);
        SimpleDateFormat df = new SimpleDateFormat("mm/dd/yyyy");
        Date dob = null;
        try {
            Log.i(ADDPATIENT, ((EditText) dobandage.findViewById(R.id.dob_input)).getText().toString() + " date field");
            dob = df.parse(((EditText) dobandage.findViewById(R.id.dob_input)).getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //int age = Integer.parseInt(((EditText) dobandage.findViewById(R.id.age_input)).getText().toString());
        //String occupation = ((EditText) patientHistory.findViewById(R.id.occupation_input)).getText().toString();
        LinearLayout genderLayout = (LinearLayout) patientHistory.findViewById(R.id.gender_layout);
        //String gender = ((EditText) genderLayout.findViewById(R.id.gender_input)).getText().toString();

        LinearLayout notesLayout = (LinearLayout) main_add_patient_layout.findViewById(R.id.notes_layout);
        final String notes = ((EditText) notesLayout.findViewById(R.id.patient_notes_input)).getText().toString();
        Log.i(ADDPATIENT, notes + " notes field");
        ParseObject userObject = ParseObject.createWithoutData("User", MainActivity.USEROBJECTID);

        patient.put("firstName", firstName);
        patient.put("author", userObject);
        patient.put("lastName", lastName);
        patient.put("hospital", hospital);
        patient.put("telepathologyID", "TPPID151");
        patient.put("contactNo", contactNo);
        patient.put("dob", dob);
        //patient.put("sex", "k" + gender);
        patient.put("age", 23);

        patient.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    String objectId = patient.getObjectId();
                    if (notes != null || !notes.trim().equals("")) {
                        saveNotes(objectId, notes);
                    } else {
                        finish();
                    }
                } else {
                    Log.d(ADDPATIENT, "Error: " + e.getMessage());
                }
            }
        });
    }

    public void saveNotes(String objectId, String notes) {
        Log.i(ADDPATIENT, "save notes called");
        ParseObject patientObject = ParseObject.createWithoutData("Patient", objectId);
        ParseObject userObject = ParseObject.createWithoutData("User", MainActivity.USEROBJECTID);
        ParseObject noteObject = new ParseObject("Activity");

        noteObject.put("author", userObject);
        noteObject.put("text", notes);
        noteObject.put("patient", patientObject);
        noteObject.saveInBackground();
        finish();
        return;
    }

    /**
     * A {@link android.support.v4.app.FragmentStatePagerAdapter} that returns a fragment
     * representing an object in the collection.
     */
    public static class DemoCollectionPagerAdapter extends FragmentPagerAdapter {

        public DemoCollectionPagerAdapter(FragmentManager fm) {

            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment = new DemoObjectFragment();
            Bundle args = new Bundle();
            args.putInt(DemoObjectFragment.ARG_OBJECT, i); // Our object is just an integer :-P
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            //Since we have four screen
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Contact Information";
                case 1:
                    return "Patient History";
                case 2:
                    return "Associated Symptoms";
                case 3:
                    return "Body Parts";
                default:
                    return "Dont Know";
            }
        }
    }

    /**
     * A dummy fragment representing a section of the app, but that simply displays dummy text.
     */
    public static class DemoObjectFragment extends Fragment {

        public static final String ARG_OBJECT = "object";

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            Bundle args = getArguments();
            View rootView;
            int page = args.getInt(ARG_OBJECT);
            switch (page) {
                case 0:
                    rootView = inflater.inflate(R.layout.contact_object, container, false);
                    break;
                case 1:
                    rootView = inflater.inflate(R.layout.patient_history_object, container, false);
                    break;
                case 2:
                    rootView = inflater.inflate(R.layout.note_object, container, false);
                    break;
                case 3:
                    rootView = inflater.inflate(R.layout.image_object, container, false);
                    break;
                default:
                    return null;
            }
            return rootView;
        }
    }
}
