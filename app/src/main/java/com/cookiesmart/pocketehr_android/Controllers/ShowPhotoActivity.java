package com.cookiesmart.pocketehr_android.Controllers;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.cookiesmart.pocketehr_android.R;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;

/**
 * Created by aditya841 on 2/18/2015.
 */
public class ShowPhotoActivity extends Activity {
    private static final String TAG = "ShowPhotoActivity";
    RelativeLayout main_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showphoto);

        Intent intent = getIntent();
        main_layout = (RelativeLayout) findViewById(R.id.showPhoto_main_layout);
        final ProgressBar progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleLarge);
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(300, 300);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        main_layout.addView(progressBar, params);
        String photo_objectId = intent.getStringExtra("photo_objectId");
        try {
            fetchPhoto(photo_objectId);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void fetchPhoto(String photo_objectId) throws ParseException {
        final ImageView imageView = new ImageView(this);
        ParseObject photo_object = new ParseObject("Photo");
        System.out.println(photo_objectId);
        photo_object.setObjectId(photo_objectId);
        photo_object.fetch();
        ParseFile image_file = photo_object.getParseFile("image");
        image_file.getDataInBackground(new GetDataCallback() {
            @Override
            public void done(byte[] bytes, ParseException e) {
                Bitmap thumbnail = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imageView.setImageBitmap(thumbnail);
                imageView.setVisibility(View.VISIBLE);
                main_layout.addView(imageView);
            }
        });
    }
}
