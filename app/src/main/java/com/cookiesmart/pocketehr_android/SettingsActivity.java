package com.cookiesmart.pocketehr_android;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.Locale;

/**
 * Created by aditya841 on 2/10/2015.
 */
public class SettingsActivity extends Activity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "SettingsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Spinner spinner = (Spinner) findViewById(R.id.language_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.language, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        Resources res = getResources();
        Configuration conf = res.getConfiguration();
        System.out.println(conf.locale.getDisplayLanguage());
        if (conf.locale.getDisplayLanguage().equals(getString(R.string.english_language)))
            spinner.setSelection(0);
        else if (conf.locale.getDisplayLanguage().equals(getString(R.string.french_language)))
            spinner.setSelection(1);
        else if (conf.locale.getDisplayLanguage().equals(getString(R.string.hindi_language)))
            spinner.setSelection(2);
        else
            System.out.println("Invalid Language");

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                setLocale(getString(R.string.english_locale));
                break;
            case 1:
                setLocale(getString(R.string.french_locale));
                break;
            case 2:
                setLocale(getString(R.string.hindi_locale));
                break;
            default:
                System.exit(1);
        }
    }

    private void setLocale(String language) {
        Locale myLocale = new Locale(language);
        Resources res = getResources();
        Configuration conf = res.getConfiguration();
        Log.i(TAG, "Checking current and selected locale");
        if (!conf.locale.getDisplayLanguage().equals(myLocale.getDisplayLanguage()))
            MyApplication.updateLanguage(getApplicationContext(), language);
        else
            return;
        Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());

        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(i);
    }

//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        System.out.println(newConfig.locale);
//        Intent intent = new Intent(this, MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//        finish();
//        startActivity(intent);
//    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //do nothing
    }
}
