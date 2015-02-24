package com.cookiesmart.pocketehr_android;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by aditya841 on 2/24/2015.
 */
public class ShowAlertActivity extends Activity {
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showAlert();
    }

    public void showAlert() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set title
        alertDialogBuilder.setTitle(getString(R.string.network_alert));

        // set dialog message
        alertDialogBuilder
                .setMessage(getString(R.string.network_alert_string))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.ok_string), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this login_button is clicked, close
                        // current activity
                        ShowAlertActivity.this.finish();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
}
