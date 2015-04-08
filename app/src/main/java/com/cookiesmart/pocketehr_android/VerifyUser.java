package com.cookiesmart.pocketehr_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

/**
 * Created by aditya841 on 4/2/2015.
 */
public class VerifyUser extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifyuser);
    }

    public void startQRReader(View v) {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.initiateScan();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null) {
            System.out.println(scanResult.getContents());
        }
    }

    public void checkUser(View view){
        String username = ((EditText) findViewById(R.id.username_input)).getText().toString();
        //check username exists in database. If activated or not?

        //If exists and not activated send to make profile activity
        Intent intent = new Intent(this, CreateUserActivity.class);
        startActivity(intent);
        //else if user exists and activated show error
        //else day that user does not exist
    }
}
