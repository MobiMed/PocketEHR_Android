package com.cookiesmart.pocketehr_android;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;

/**
 * Created by aditya841 on 4/7/2015.
 */
public class CreateUserActivity extends ListActivity implements View.OnClickListener {
    public View.OnLongClickListener longClickListner;
    LinearLayout panel1, panel2, panel3;
    TextView text1, text2, text3;
    public static View openLayout;
    private Context context = this;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createuser);

        panel1 = (LinearLayout) findViewById(R.id.panel1);
        panel2 = (LinearLayout) findViewById(R.id.panel2);
        panel3 = (LinearLayout) findViewById(R.id.panel3);

        text1 = (TextView) findViewById(R.id.text1);
        text2 = (TextView) findViewById(R.id.text2);
        text3 = (TextView) findViewById(R.id.text3);

        text1.setOnClickListener(this);
        text2.setOnClickListener(this);
        text3.setOnClickListener(this);
        text1.performClick();

    }

    @Override
    public void onClick(View v) {
        hideOthers(v);
    }

    private void hideThemAll() {
        if (openLayout == null) return;
        System.out.println(openLayout.toString());
        if (openLayout == panel1) {
            ImageView image = (ImageView) findViewById(R.id.image1);
            image.setImageResource(R.drawable.ic_plus_32);
            openLayout = null;
            panel1.startAnimation(new ScaleAnimToHide(1.0f, 1.0f, 1.0f, 0.0f, 500, panel1, true));
        }
        if (openLayout == panel2) {
            ImageView image = (ImageView) findViewById(R.id.image2);
            image.setImageResource(R.drawable.ic_plus_32);
            openLayout = null;
            panel2.startAnimation(new ScaleAnimToHide(1.0f, 1.0f, 1.0f, 0.0f, 500, panel2, true));
        }
        if (openLayout == panel3) {
            ImageView image = (ImageView) findViewById(R.id.image3);
            image.setImageResource(R.drawable.ic_plus_32);
            openLayout = null;
            panel3.startAnimation(new ScaleAnimToHide(1.0f, 1.0f, 1.0f, 0.0f, 500, panel3, true));
        }
    }

    private void hideOthers(View layoutView) {
        int v;
        if (layoutView.getId() == R.id.text1) {
            v = panel1.getVisibility();
            if (v != View.VISIBLE) {
                panel1.setVisibility(View.VISIBLE);
                Log.v("CZ", "height..." + panel1.getHeight());
            }

            //panel1.setVisibility(View.GONE);
            //Log.v("CZ","again height..." + panel1.getHeight());
            hideThemAll();
            if (v != View.VISIBLE) {
                ImageView image = (ImageView) findViewById(R.id.image1);
                image.setImageResource(R.drawable.ic_minus_32);
                openLayout = panel1;
                panel1.startAnimation(new ScaleAnimToShow(1.0f, 1.0f, 1.0f, 0.0f, 500, panel1, true));
            }
        } else if (layoutView.getId() == R.id.text2) {
            v = panel2.getVisibility();
            hideThemAll();
            if (v != View.VISIBLE) {
                ImageView image = (ImageView) findViewById(R.id.image2);
                image.setImageResource(R.drawable.ic_minus_32);
                openLayout = panel2;
                panel2.startAnimation(new ScaleAnimToShow(1.0f, 1.0f, 1.0f, 0.0f, 500, panel2, true));
            }
        } else if (layoutView.getId() == R.id.text3) {
            v = panel3.getVisibility();
            hideThemAll();
            if (v != View.VISIBLE) {
                ImageView image = (ImageView) findViewById(R.id.image3);
                image.setImageResource(R.drawable.ic_minus_32);
                openLayout = panel3;
                panel3.startAnimation(new ScaleAnimToShow(1.0f, 1.0f, 1.0f, 0.0f, 500, panel3, true));
            }
        }
    }

    public void saveUser(View v) {
        ParseUser user = new ParseUser();

        String username = ((EditText) findViewById(R.id.username_input)).getText().toString();
        if (username.trim().equals("")) {
            Toast.makeText(this, getString(R.string.username_toast), Toast.LENGTH_SHORT).show();
            return;
        } else if (username.trim().length() < 5) {
            Toast.makeText(this, getString(R.string.username_toast), Toast.LENGTH_SHORT).show();
            return;
        }
        user.setUsername(username);
        String password = ((EditText) findViewById(R.id.password_input)).getText().toString();
        if (password.trim().equals("")) {
            Toast.makeText(this, getString(R.string.password_toast), Toast.LENGTH_SHORT).show();
            return;
        } else if (password.trim().length() < 5) {
            Toast.makeText(this, getString(R.string.password_toast), Toast.LENGTH_SHORT).show();
            return;
        }
        user.setPassword(password);

        String user_name = ((EditText) findViewById(R.id.user_name_input)).getText().toString();
        if (user_name.trim().equals("")) {
            Toast.makeText(this, getString(R.string.home_add_admin_text), Toast.LENGTH_SHORT).show();
            return;
        }
        user.add("name", user_name);
        String user_contact_number = ((EditText) findViewById(R.id.user_contact_input)).getText().toString();
        if (user_contact_number.trim().equals("")) {
            Toast.makeText(this, getString(R.string.contact_number_input), Toast.LENGTH_SHORT).show();
            return;
        }
        user.add("contactNumber", user_contact_number);

        String user_email = ((EditText) findViewById(R.id.user_email_input)).getText().toString();
        if (user_email.trim().equals("")) {
            Toast.makeText(this, getString(R.string.contact_number_input), Toast.LENGTH_SHORT).show();
            return;
        }
        user.add("email", user_email);

        try {
            user.signUp();
        } catch (ParseException e) {
            if (e.getCode() == ParseException.USERNAME_TAKEN) {
                Toast.makeText(this, getString(R.string.username_input), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.server_positive_status), Toast.LENGTH_SHORT).show();
            }
            return;
        }
        //send email to user with the user and password
        finish();
    }
}
