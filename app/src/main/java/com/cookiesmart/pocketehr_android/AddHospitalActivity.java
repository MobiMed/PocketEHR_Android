package com.cookiesmart.pocketehr_android;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cookiesmart.pocketehr_android.HelperClasses.ScaleAnimToHide;
import com.cookiesmart.pocketehr_android.HelperClasses.ScaleAnimToShow;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class AddHospitalActivity extends ListActivity implements OnClickListener {
    public static View openLayout;
    public OnLongClickListener longClickListner;
    LinearLayout panel1, panel2, panel3, panel4;
    TextView text1, text2, text3, text4, text5;
    private Context context = this;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addhospital);

        panel1 = (LinearLayout) findViewById(R.id.panel1);
        panel2 = (LinearLayout) findViewById(R.id.panel2);
        panel3 = (LinearLayout) findViewById(R.id.panel3);
        panel4 = (LinearLayout) findViewById(R.id.panel4);

        text1 = (TextView) findViewById(R.id.text1);
        text2 = (TextView) findViewById(R.id.text2);
        text3 = (TextView) findViewById(R.id.text3);
        text4 = (TextView) findViewById(R.id.text4);

        text1.setOnClickListener(this);
        text2.setOnClickListener(this);
        text3.setOnClickListener(this);
        text4.setOnClickListener(this);
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
            image.setImageResource(R.drawable.ic_plus);
            openLayout = null;
            panel1.startAnimation(new ScaleAnimToHide(1.0f, 1.0f, 1.0f, 0.0f, 500, panel1, true));
        }
        if (openLayout == panel2) {
            ImageView image = (ImageView) findViewById(R.id.image2);
            image.setImageResource(R.drawable.ic_plus);
            openLayout = null;
            panel2.startAnimation(new ScaleAnimToHide(1.0f, 1.0f, 1.0f, 0.0f, 500, panel2, true));
        }
        if (openLayout == panel3) {
            ImageView image = (ImageView) findViewById(R.id.image3);
            image.setImageResource(R.drawable.ic_plus);
            openLayout = null;
            panel3.startAnimation(new ScaleAnimToHide(1.0f, 1.0f, 1.0f, 0.0f, 500, panel3, true));
        }
        if (openLayout == panel4) {
            ImageView image = (ImageView) findViewById(R.id.image4);
            image.setImageResource(R.drawable.ic_plus);
            openLayout = null;
            panel4.startAnimation(new ScaleAnimToHide(1.0f, 1.0f, 1.0f, 0.0f, 500, panel4, true));
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
                image.setImageResource(R.drawable.ic_minus);
                openLayout = panel1;
                panel1.startAnimation(new ScaleAnimToShow(1.0f, 1.0f, 1.0f, 0.0f, 500, panel1, true));
            }
        } else if (layoutView.getId() == R.id.text2) {
            v = panel2.getVisibility();
            hideThemAll();
            if (v != View.VISIBLE) {
                ImageView image = (ImageView) findViewById(R.id.image2);
                image.setImageResource(R.drawable.ic_minus);
                openLayout = panel2;
                panel2.startAnimation(new ScaleAnimToShow(1.0f, 1.0f, 1.0f, 0.0f, 500, panel2, true));
            }
        } else if (layoutView.getId() == R.id.text3) {
            v = panel3.getVisibility();
            hideThemAll();
            if (v != View.VISIBLE) {
                ImageView image = (ImageView) findViewById(R.id.image3);
                image.setImageResource(R.drawable.ic_minus);
                openLayout = panel3;
                panel3.startAnimation(new ScaleAnimToShow(1.0f, 1.0f, 1.0f, 0.0f, 500, panel3, true));
            }
        } else if (layoutView.getId() == R.id.text4) {
            v = panel4.getVisibility();
            hideThemAll();
            if (v != View.VISIBLE) {
                ImageView image = (ImageView) findViewById(R.id.image4);
                image.setImageResource(R.drawable.ic_minus);
                openLayout = panel4;
                panel4.startAnimation(new ScaleAnimToShow(1.0f, 1.0f, 1.0f, 0.0f, 500, panel4, true));
            }
        }
    }

    public void saveHospital(View v) {
        final ParseObject hospital = new ParseObject("hospital");
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

        String admin_name = ((EditText) findViewById(R.id.admin_name_input)).getText().toString();
        if (admin_name.trim().equals("")) {
            Toast.makeText(this, getString(R.string.home_add_admin_text), Toast.LENGTH_SHORT).show();
            return;
        }
        user.add("name", admin_name);
        String admin_contact = ((EditText) findViewById(R.id.admin_name_input)).getText().toString();
        if (admin_contact.trim().equals("")) {
            Toast.makeText(this, getString(R.string.contact_number_input), Toast.LENGTH_SHORT).show();
            return;
        }
        user.add("contactNumber", admin_contact);

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

        String hospital_name = ((EditText) findViewById(R.id.hospital_name_input)).getText().toString();
        if (hospital_name.trim().equals("")) {
            Toast.makeText(this, getString(R.string.hospital_input), Toast.LENGTH_SHORT).show();
            return;
        }
        hospital.add("hospital_name", hospital_name);

        String hospital_address_line = ((EditText) findViewById(R.id.property_number_input)).getText().toString()
                + ((EditText) findViewById(R.id.address_input)).getText().toString()
                + ((EditText) findViewById(R.id.address_input1)).getText().toString();
        hospital.add("address", hospital_address_line);

        String hospital_city = ((EditText) findViewById(R.id.city_input)).getText().toString();
        hospital.add("city", hospital_city);

        String hospital_state = ((EditText) findViewById(R.id.state_input)).getText().toString();
        hospital.add("state", hospital_state);

        String hospital_country = ((EditText) findViewById(R.id.country_input)).getText().toString();
        hospital.add("country", hospital_country);

        String hospital_postal_code = ((EditText) findViewById(R.id.postal_code_input)).getText().toString();
        hospital.add("postal_code", hospital_postal_code);

        hospital.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                Intent intent = new Intent(context, GenerateQRCodeActivity.class);
                try {
                    hospital.fetch();
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
                intent.putExtra("QRData", (String) hospital.get("hospitalId"));
                startActivity(intent);
                finish();
            }
        });

        Intent intent = new Intent(context, GenerateQRCodeActivity.class);
        intent.putExtra("QRData", "pEHRH16675");
        startActivity(intent);
        finish();
    }
}
