package com.cookiesmart.pocketehr_android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

/**
 * Created by aditya841 on 12/5/2014.
 */

public class LoginActivity extends Activity {
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(View view) {
        String username = ((EditText) view.findViewById(R.id.username_input)).getText().toString();
        String password = ((EditText) view.findViewById(R.id.username_input)).getText().toString();

        ParseUser.logInInBackground(username, password, new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra("userObjectId", user.getObjectId());
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Toast.makeText(context, e.getMessage() + "Username or Password incorrect. Please try again.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void showPassword(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        if (checked) {
            EditText password_input = (EditText) findViewById(R.id.password_input);
            password_input.setInputType(InputType.TYPE_CLASS_TEXT);
        } else {
            EditText password_input = (EditText) findViewById(R.id.password_input);
            password_input.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }

    public void register(View view) {
        final EditText email_input = (EditText) findViewById(R.id.email_input);
        email_input.setVisibility(View.VISIBLE);

        final View v = view;
        email_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = ((EditText) v.findViewById(R.id.username_input)).getText().toString();
                final String password = ((EditText) v.findViewById(R.id.username_input)).getText().toString();
                String email = email_input.getText().toString();
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
        });
    }

    public void loginAfterRegister(String username, String password) {
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra("userId", user.getUsername());
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Toast.makeText(context, e.getMessage() + "Username or Password incorrect. Please try again.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
