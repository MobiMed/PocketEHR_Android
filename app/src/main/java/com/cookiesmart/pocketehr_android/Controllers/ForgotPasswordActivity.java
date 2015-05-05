package com.cookiesmart.pocketehr_android.Controllers;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.cookiesmart.pocketehr_android.R;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;

import java.util.HashMap;

/**
 * Created by aditya841 on 4/14/2015.
 */
public class ForgotPasswordActivity extends Activity {
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
    }

    public void checkUserEmail(View view) {
        final String email = ((EditText) view).getText().toString();
        HashMap<String, String> emailId = new HashMap<String, String>();
        emailId.put("email", email);
        ParseCloud.callFunctionInBackground("checkEmail", emailId, new FunctionCallback<Boolean>() {
            @Override
            public void done(Boolean result, ParseException e) {
                if (result) {
                    showPositiveAlert(email);
                } else {
                    showNegativeAlert();
                }
            }
        });
    }

    private void showNegativeAlert() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set title
        alertDialogBuilder.setTitle(getString(R.string.user_not_exist_alert));

        // set dialog message
        alertDialogBuilder
                .setMessage(getString(R.string.user_not_exist_alert_string))
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

    private void showPositiveAlert(String email) {
        Intent intent = new Intent(context, ChangePasswordActivity.class);
        intent.putExtra("email", email);
        startActivity(intent);
        finish();
    }
}
