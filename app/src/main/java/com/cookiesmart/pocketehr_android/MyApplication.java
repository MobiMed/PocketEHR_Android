package com.cookiesmart.pocketehr_android;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.parse.Parse;
import com.parse.ParseCrashReporting;

import java.util.Locale;

/**
 * Created by aditya841 on 2/24/2015.
 */
public class MyApplication extends Application {
    public static final String FORCE_LOCAL = "";

    @Override
    public void onCreate() {
        updateLanguage(this, null);
        super.onCreate();
        if (!isOnline()) {
            showAlert();
        } else {
            if (!ParseCrashReporting.isCrashReportingEnabled()) {
                ParseCrashReporting.enable(this);
            }
            Parse.initialize(this, "CguKOD63X4OsgUyUPVy7jxS2b2DWap7My8J3QjI6", "OJZgRlZlpAoN3zc3XacaQCNOaH9i4VGi7i22TfWS");
        }
    }

    public static void updateLanguage(Context ctx, String lang) {
        Configuration cfg = new Configuration();
        SharedPreferences force_pref = PreferenceManager.getDefaultSharedPreferences(ctx);
        String language = force_pref.getString(FORCE_LOCAL, "");

        if (TextUtils.isEmpty(language) && lang == null) {
            cfg.locale = Locale.getDefault();

            SharedPreferences.Editor edit = force_pref.edit();
            String tmp = "";
            tmp = Locale.getDefault().toString().substring(0, 2);

            edit.putString(FORCE_LOCAL, tmp);
            edit.commit();
        } else if (lang != null) {
            cfg.locale = new Locale(lang);
            SharedPreferences.Editor edit = force_pref.edit();
            edit.putString(FORCE_LOCAL, lang);
            edit.commit();

        } else if (!TextUtils.isEmpty(language)) {
            cfg.locale = new Locale(language);
        }

        ctx.getResources().updateConfiguration(cfg, null);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        SharedPreferences force_pref = PreferenceManager.getDefaultSharedPreferences(getBaseContext().getApplicationContext());

        String language = force_pref.getString(FORCE_LOCAL, "");
        super.onConfigurationChanged(newConfig);
        updateLanguage(this, language);
    }

    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private void showAlert() {
        Intent intent = new Intent(this, ShowAlertActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
