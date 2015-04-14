package com.cookiesmart.pocketehr_android;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.parse.ParseCloud;
import com.parse.ParseException;

import java.util.HashMap;

/**
 * Created by aditya841 on 4/14/2015.
 */
public class ChangePasswordActivity extends Activity {
    Context context = this;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);
        intent = getIntent();
    }

    public void changePassword(View view) {
        String password = ((EditText) findViewById(R.id.new_password)).getText().toString();
        String retype_password = ((EditText) findViewById(R.id.retype_password)).getText().toString();

        if (password.equals(retype_password)) {
            HashMap<String, String> user_password = new HashMap<>();
            user_password.put("email", intent.getStringExtra("email"));
            user_password.put("password", password);
            try {
                ParseCloud.callFunction("changePassword", user_password);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            showPositiveAlert();
        } else {
            showNegativeAlert();
        }
    }

    private void showPositiveAlert() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set title
        alertDialogBuilder.setTitle(getString(R.string.password_changed_alert));

        // set dialog message
        alertDialogBuilder
                .setMessage(getString(R.string.password_changed_alert_string))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.ok_string), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ChangePasswordActivity.this.finish();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    private void showNegativeAlert() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set title
        alertDialogBuilder.setTitle(getString(R.string.password_not_same_alert));

        // set dialog message
        alertDialogBuilder
                .setMessage(getString(R.string.password_not_same_alert_string))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.ok_string), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
}
