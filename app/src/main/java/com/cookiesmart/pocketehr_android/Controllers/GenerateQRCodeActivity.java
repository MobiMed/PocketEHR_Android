package com.cookiesmart.pocketehr_android.Controllers;

/**
 * Created by aditya841 on 4/7/2015.
 */

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.cookiesmart.pocketehr_android.HelperClasses.QRCodeEncoder;
import com.cookiesmart.pocketehr_android.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;

public class GenerateQRCodeActivity extends Activity {

    private String LOG_TAG = "GenerateQRCode";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generateqrcode);
        Intent intent = getIntent();
        String QRData = intent.getStringExtra("QRData");
        generateQRCode(QRData);
    }

    private void generateQRCode(String data) {
        //Find screen size
        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        int smallerDimension = width < height ? width : height;
        smallerDimension = smallerDimension * 3 / 4;

        //Encode with a QR Code image
        QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(data,
                null,
                Contents.Type.TEXT,
                BarcodeFormat.QR_CODE.toString(),
                smallerDimension);
        try {
            Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();
            ImageView myImage = (ImageView) findViewById(R.id.imageView1);
            myImage.setImageBitmap(bitmap);

            //store image in database in hospital QR code column
            //send email with the QR code to admin hospital email
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    public void closeActivity(View v) {
        finish();
    }
}