package com.cookiesmart.pocketehr_android;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

/**
 * Created by aditya841 on 12/5/2014.
 */

public class LoginActivity extends Activity {
    private static final String LOGIN = "LoginActivity";
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Intent intent = getIntent();
        if (intent.hasExtra("Alert")) {
            showAlert();
        }

        final EditText password_input = (EditText) findViewById(R.id.password_input);

        ImageView register_button = (ImageView) findViewById(R.id.create_hospital_button);
        register_button.setTag("1");

        CheckBox checkbox = (CheckBox) findViewById(R.id.visible_check);
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    password_input.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else {
                    password_input.setInputType(129);
                }
            }
        });
    }

    public void login(View view) {

        String username = ((EditText) findViewById(R.id.username_input)).getText().toString();
        String password = ((EditText) findViewById(R.id.password_input)).getText().toString();
        Log.i("Username", username);
        Log.i("Password", password);

        final RelativeLayout login_layout = (RelativeLayout) view.getParent();
        final ProgressBar progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleLarge);
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(300, 300);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        login_layout.addView(progressBar, params);

        ParseUser.logInInBackground(username, password, new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    login_layout.removeView(progressBar);
                    Intent intent = new Intent(context, MainActivity.class);
                    startActivity(intent);
                } else {
                    login_layout.removeView(progressBar);
                    Toast.makeText(context, e.getMessage() + getString(R.string.wrong_cred_toast),
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void register(View view) {
        Button register_button = (Button) view;

        final EditText email_input = (EditText) findViewById(R.id.email_input);

        if (register_button.getTag() == "1") {
            email_input.setVisibility(View.VISIBLE);
            register_button.setTag("2");
        } else {
            Log.i(LOGIN, "onclickset");
            final String username = ((EditText) findViewById(R.id.username_input)).getText().toString();
            final String password = ((EditText) findViewById(R.id.password_input)).getText().toString();
            String email = email_input.getText().toString();
            if (username.trim().equals("") || password.trim().equals("") || email.trim().equals("")) {
                Toast.makeText(context, getString(R.string.all_fields_toast), Toast.LENGTH_LONG).show();
                return;
            }
            ParseUser user = new ParseUser();
            user.setUsername(username);
            user.setPassword(password);
            user.setEmail(email);

            user.signUpInBackground(new SignUpCallback() {
                public void done(ParseException e) {
                    if (e == null) {
                        loginAfterRegister(username, password);
                    } else {
                        Toast.makeText(context, e.getMessage() + "Try again.", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
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
                        LoginActivity.this.finish();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    public void loginAfterRegister(String username, String password) {
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    Intent intent = new Intent(context, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(context, e.getMessage() + getString(R.string.wrong_cred_toast),
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_CANCELED);
    }
}
