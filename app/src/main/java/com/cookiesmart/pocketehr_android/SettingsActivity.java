package com.cookiesmart.pocketehr_android;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Locale;

/**
 * Created by aditya841 on 2/10/2015.
 */
public class SettingsActivity extends Activity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "SettingsActivity";
    Context context = this;

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
        showAlert();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //do nothing
    }

    public void showAlert() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set title
        alertDialogBuilder.setTitle(getString(R.string.restart_alert));
        final Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // set dialog message
        alertDialogBuilder
                .setMessage(getString(R.string.restart_alert_string))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.ok_string), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this login_button is clicked, close
                        // current activity
                        startActivity(i);
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    public void feedbackInput(View v) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle(getString(R.string.feedback_button));
        alert.setMessage("Message");

// Set an EditText view to get user input
        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String value = input.getText().toString();
                Intent Email = new Intent(Intent.ACTION_SEND);
                Email.setType("text/email");
                Email.putExtra(Intent.EXTRA_EMAIL, new String[]{"lou@teamlivelonger.com"});
                Email.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
                Email.putExtra(Intent.EXTRA_TEXT, value);
                startActivity(Intent.createChooser(Email, "Send Feedback:"));
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });

        alert.show();
    }
}
