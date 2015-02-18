package com.cookiesmart.pocketehr_android;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;

/**
 * Created by aditya841 on 2/18/2015.
 */
public class ShowPhotoActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showphoto);
        Intent intent = getIntent();
        String photo_objectId = intent.getStringExtra("photo_objectId");
        try {
            fetchPhoto(photo_objectId);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void fetchPhoto(String photo_objectId) throws ParseException {
        final ImageView imageView = (ImageView) findViewById(R.id.full_photo);
        ParseObject photo_object = new ParseObject("Photo");
        photo_object.setObjectId(photo_objectId);
        photo_object.fetchIfNeeded();
        ParseFile image_file = photo_object.getParseFile("image");
        image_file.getDataInBackground(new GetDataCallback() {
            @Override
            public void done(byte[] bytes, ParseException e) {
                Bitmap thumbnail = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imageView.setImageBitmap(thumbnail);
            }
        });
    }
}
