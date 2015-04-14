package com.cookiesmart.pocketehr_android;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
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
                    password_input.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
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
                    ((EditText) findViewById(R.id.username_input)).setText("");
                    ((EditText) findViewById(R.id.password_input)).setText("");
                    login_layout.removeView(progressBar);
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra("user_type", "User"/*user.getString("user_type")*/);
                    startActivity(intent);
                } else {
                    login_layout.removeView(progressBar);
                    Toast.makeText(context, e.getMessage() + getString(R.string.wrong_cred_toast),
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void createUser(View view) {
        Intent intent = new Intent(this, VerifyUser.class);
        startActivityForResult(intent, 1);
    }

    public void createHospital(View view) {
        Intent intent = new Intent(this, AddHospitalActivity.class);
        startActivity(intent);
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

    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public void forgotPassword(View view) {
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(intent);
    }
}
