package com.cookiesmart.pocketehr_android;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aditya841 on 11/21/2014.
 */
public class PatientActivity extends Activity {
    private static String objectId;
    private String mCurrentPhotoPath;
    private static final int CHANGE_STATUS = 1;
    private static final int ADD_NOTE = 2;
    private static final int SELECT_PICTURE = 3;
    private static final int TAKE_PICTURE = 4;
    private static String PATIENT = "PatientActivity";
    Context context = this;
    final int THUMBNAIL_SIZE = 250;
    private String status = "";
    private String view_tag = "";
    private LinearLayout main_view = null;
    ProgressDialog progressDialog;
    Bitmap thumbnail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);
        Intent intent = getIntent();
        objectId = intent.getStringExtra("objectId");
        view_tag = intent.getStringExtra("view_tag");
        main_view = (LinearLayout) findViewById(R.id.patient_main_layout);
        progressDialog = ProgressDialog.show(this, getString(R.string.loading_text), "", true);
        getPatientDetails();
    }


    public void changeStatusActivity(View view) {
        Intent intent = new Intent(this, ChangeStatusActivity.class);
        intent.putExtra("objectId", objectId);
        intent.putExtra("status", ((TextView) view).getText().toString());
        startActivityForResult(intent, CHANGE_STATUS);
    }

    public void addNote(View view) {
        Intent intent = new Intent(this, AddNoteActivity.class);
        intent.putExtra("objectId", objectId);
        startActivityForResult(intent, ADD_NOTE);
    }

    public void addPicture(View view) {
        final CharSequence[] items = {getString(R.string.image_dialog_box_camera), getString(R.string.image_dialog_box_gallery),
                getString(R.string.image_dialog_box_cancel)};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.image_dialog_box));
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals(getString(R.string.image_dialog_box_camera))) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = null;
                    try {
                        f = createImageFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (f != null) {
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                        startActivityForResult(intent, TAKE_PICTURE);
                    }
                } else if (items[item].equals(getString(R.string.image_dialog_box_gallery))) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            SELECT_PICTURE);
                } else if (items[item].equals(getString(R.string.image_dialog_box_cancel))) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        } else if (requestCode == CHANGE_STATUS) {
            TextView status_field = (TextView) findViewById(R.id.status_patient_screen);
            status = data.getStringExtra("status");
            System.out.println(status);
            if (status.equalsIgnoreCase(getString(R.string.negative_status))) {
                status = getString(R.string.server_negative_status);
                status_field.setBackgroundColor(Color.GREEN);
                //status_field.setTextColor(Color.WHITE);
                status_field.setText(getString(R.string.negative_status));
            } else if (status.equalsIgnoreCase(getString(R.string.positive_status))) {
                status = getString(R.string.server_positive_status);
                status_field.setBackgroundColor(Color.RED);
                //status_field.setTextColor(Color.WHITE);
                status_field.setText(getString(R.string.positive_status));
            } else if (status.equalsIgnoreCase(getString(R.string.incomplete_status))) {
                status = getString(R.string.server_incomplete_status);
                status_field.setBackgroundColor(Color.BLUE);
                //status_field.setTextColor(Color.WHITE);
                status_field.setText(R.string.incomplete_status);
            } else if (status.equalsIgnoreCase(getString(R.string.deceased_status))) {
                status = getString(R.string.server_deceased_status);
                status_field.setBackgroundColor(Color.BLACK);
                //status_field.setTextColor(Color.WHITE);
                status_field.setText(getString(R.string.deceased_status));
            }

            String notes = data.getStringExtra("notes");
            LinearLayout activity_section = (LinearLayout) findViewById(R.id.activity_view);
            if (!notes.trim().equalsIgnoreCase("")) {
                TextView newTextView = new TextView(context);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0, 10, 0, 0);
                newTextView.setBackgroundColor(Color.WHITE);
                newTextView.setMinHeight(150);
                newTextView.setPadding(20, 10, 0, 0);
                newTextView.setText(notes);
                activity_section.addView(newTextView, 0, layoutParams);
            }
        } else if (requestCode == ADD_NOTE) {
            String notes = data.getStringExtra("notes");
            LinearLayout main_section = (LinearLayout) findViewById(R.id.activity_view);
            if (!notes.trim().equalsIgnoreCase("")) {
                TextView newTextView = new TextView(context);
                newTextView.setBackgroundColor(Color.WHITE);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0, 10, 0, 0);
                newTextView.setMinHeight(150);
                newTextView.setPadding(20, 10, 0, 0);
                newTextView.setText(notes);
                main_section.addView(newTextView, 0, layoutParams);
            }

        } else if (requestCode == TAKE_PICTURE) {
            try {

                BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
                Bitmap bm;
                bm = BitmapFactory.decodeFile(mCurrentPhotoPath,
                        btmapOptions);
                thumbnail = Bitmap.createScaledBitmap(bm, THUMBNAIL_SIZE, THUMBNAIL_SIZE, false);
                ByteArrayOutputStream image_stream = new ByteArrayOutputStream();
                ByteArrayOutputStream thumbnail_stream = new ByteArrayOutputStream();
                byte[] image_data = null;
                byte[] thumbnail_data = null;
                try {
                    bm.compress(Bitmap.CompressFormat.JPEG, 100, image_stream);
                    thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, thumbnail_stream);
                    image_data = image_stream.toByteArray();
                    thumbnail_data = thumbnail_stream.toByteArray();
                    thumbnail_stream.flush();
                    thumbnail_stream.close();
                    image_stream.flush();
                    image_stream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                ParseFile image_file = new ParseFile("image_file", image_data);
                ParseFile thumbnail_file = new ParseFile("thumbnail_file", thumbnail_data);
                if (uploadPhoto(image_file, thumbnail_file)) {
                    //do nothing
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == SELECT_PICTURE) {
            InputStream image_input_stream = null;
            try {
                image_input_stream = context.getContentResolver().openInputStream(data.getData());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            Bitmap bm;
            bm = BitmapFactory.decodeStream(image_input_stream);
            thumbnail = Bitmap.createScaledBitmap(bm, THUMBNAIL_SIZE, THUMBNAIL_SIZE, false);
            ByteArrayOutputStream image_stream = new ByteArrayOutputStream();
            ByteArrayOutputStream thumbnail_stream = new ByteArrayOutputStream();
            byte[] image_data = null;
            byte[] thumbnail_data = null;
            try {
                bm.compress(Bitmap.CompressFormat.JPEG, 100, image_stream);
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, thumbnail_stream);
                image_data = image_stream.toByteArray();
                thumbnail_data = thumbnail_stream.toByteArray();
                thumbnail_stream.flush();
                thumbnail_stream.close();
                image_stream.flush();
                image_stream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            ParseFile image_file = new ParseFile("image_file", image_data);
            ParseFile thumbnail_file = new ParseFile("thumbnail_file", thumbnail_data);
            if (uploadPhoto(image_file, thumbnail_file)) {
                //do nothing
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String imageFileName = "temp";
        File storageDir = getExternalFilesDir(null);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void addActivity(final String photo_objectId) {
        ParseObject activity_object = new ParseObject("Activity");
        ParseObject patient = ParseObject.createWithoutData("Patient", objectId);
        ParseObject photo = ParseObject.createWithoutData("Photo", photo_objectId);
        activity_object.put("patient", patient);
        activity_object.put("photo", photo);
        activity_object.put("type", getString(R.string.server_activity_imageAddedType));
        activity_object.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                refreshActivityLayoutAfterPictureAdded(photo_objectId);
            }
        });
    }

    private void refreshActivityLayoutAfterPictureAdded(String photo_objectId) {
        final LinearLayout activity_layout = (LinearLayout) findViewById(R.id.activity_view);
        final ImageView imageView = new ImageView(context);
        imageView.setTag(photo_objectId);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 10, 0, 0);
        imageView.setMinimumHeight(150);
        imageView.setPadding(20, 10, 0, 0);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShowPhotoActivity.class);
                intent.putExtra("Photo_objectId", (String) v.getTag());
                startActivity(intent);
            }
        });
        activity_layout.addView(imageView, 0, layoutParams);
        imageView.setImageBitmap(thumbnail);
    }

    private void getPatientDetails() {
        ParseQuery<ParseObject> user_query = ParseQuery.getQuery("Patient");
        user_query.whereEqualTo("objectId", objectId);
        user_query.setLimit(1);
        user_query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                loadView(parseObjects.get(0));
            }
        });
    }

    private void loadView(ParseObject patient_object) {
        TextView firstName = (TextView) findViewById(R.id.first_name);
        firstName.setText(patient_object.getString("firstName"));

        TextView lastName = (TextView) findViewById(R.id.last_name);
        lastName.setText(patient_object.getString("lastName"));

        TextView status_field = (TextView) findViewById(R.id.status_patient_screen);
        status = patient_object.getString("status");

        if (status.equalsIgnoreCase(getString(R.string.server_negative_status))) {
            status_field.setBackgroundColor(Color.GREEN);
            //status_field.setTextColor(Color.WHITE);
            status_field.setText(getString(R.string.negative_status));
        } else if (status.equalsIgnoreCase(getString(R.string.server_positive_status))) {
            status_field.setBackgroundColor(Color.RED);
            //status_field.setTextColor(Color.WHITE);
            status_field.setText(getString(R.string.positive_status));
        } else if (status.equalsIgnoreCase(getString(R.string.server_incomplete_status))) {
            status_field.setBackgroundColor(Color.BLUE);
            //status_field.setTextColor(Color.WHITE);
            status_field.setText(getString(R.string.incomplete_status));
        } else if (status.equalsIgnoreCase(getString(R.string.server_deceased_status))) {
            status_field.setBackgroundColor(Color.BLACK);
            //status_field.setTextColor(Color.WHITE);
            status_field.setText(getString(R.string.deceased_status));
        }

        TextView telepathologyID = (TextView) findViewById(R.id.telepathologyID);
        telepathologyID.setText(patient_object.getString("telepathologyID"));

        TextView sex = (TextView) findViewById(R.id.patient_gender);
        String gender = patient_object.getString("sex");

        loadPatientBodyMap(patient_object, sex, gender);

        fetchActivities();
    }

    private void loadPatientBodyMap(ParseObject patient_object, TextView sex, String gender) {
        ImageView body_parts = (ImageView) findViewById(R.id.body_part_image);
        JSONArray bodyPartsArray = patient_object.getJSONArray("locations");
        ArrayList<String> bodyParts = new ArrayList<String>();
        if (bodyPartsArray != null) {
            for (int i = 0; i < bodyPartsArray.length(); i++) {
                try {
                    bodyParts.add(bodyPartsArray.get(i).toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        Drawable[] layers = new Drawable[bodyParts.size() + 1];
        Resources r = getResources();
        if (gender.contains(getString(R.string.server_sex_female))) {
            layers[0] = r.getDrawable(R.drawable.female_body_parts);
            if (bodyParts.size() == 0) {
                //do nothing
            } else {
                int i = 1;
                while (i <= bodyParts.size()) {
                    String bodyPart = bodyParts.get(i - 1);
                    if (bodyPart.equals("head")) {
                        layers[i] = r.getDrawable(R.drawable.female_head);
                    } else if (bodyPart.equals("throat")) {
                        layers[i] = r.getDrawable(R.drawable.female_throat);
                    } else if (bodyPart.equals("upperArmLeft")) {
                        layers[i] = r.getDrawable(R.drawable.female_upper_arm_left);
                    } else if (bodyPart.equals("chestLeft")) {
                        layers[i] = r.getDrawable(R.drawable.female_chest_left);
                    } else if (bodyPart.equals("chestRight")) {
                        layers[i] = r.getDrawable(R.drawable.female_chest_right);
                    } else if (bodyPart.equals("upperArmRight")) {
                        layers[i] = r.getDrawable(R.drawable.female_upper_arm_right);
                    } else if (bodyPart.equals("abdomen")) {
                        layers[i] = r.getDrawable(R.drawable.female_abdomen);
                    } else if (bodyPart.equals("groin")) {
                        layers[i] = r.getDrawable(R.drawable.female_groin);
                    } else if (bodyPart.equals("upperLegLeft")) {
                        layers[i] = r.getDrawable(R.drawable.female_upper_leg_left);
                    } else if (bodyPart.equals("upperLegLeft")) {
                        layers[i] = r.getDrawable(R.drawable.female_upper_leg_left);
                    } else if (bodyPart.equals("upperLegRight")) {
                        layers[i] = r.getDrawable(R.drawable.female_upper_leg_right);
                    } else if (bodyPart.equals("lowerLegLeft")) {
                        layers[i] = r.getDrawable(R.drawable.female_lower_leg_left);
                    } else if (bodyPart.equals("lowerLegRight")) {
                        layers[i] = r.getDrawable(R.drawable.female_lower_leg_right);
                    } else if (bodyPart.equals("lowerArmLeft")) {
                        layers[i] = r.getDrawable(R.drawable.female_lower_arm_left);
                    } else if (bodyPart.equals("lowerArmRight")) {
                        layers[i] = r.getDrawable(R.drawable.female_lower_arm_right);
                    }
                    i++;
                }
            }
            LayerDrawable layerDrawable = new LayerDrawable(layers);
            body_parts.setImageDrawable(layerDrawable);
            sex.setText(getString(R.string.female_gender));
        } else {
            layers[0] = r.getDrawable(R.drawable.male_body_parts);
            if (bodyParts.size() == 0) {
                //do nothing
            } else {
                int i = 1;
                while (i <= bodyParts.size()) {
                    String bodyPart = bodyParts.get(i - 1);
                    System.out.println(bodyPart);
                    if (bodyPart.equals("head")) {
                        layers[i] = r.getDrawable(R.drawable.male_head);
                    } else if (bodyPart.equals("throat")) {
                        layers[i] = r.getDrawable(R.drawable.male_throat);
                    } else if (bodyPart.equals("upperArmLeft")) {
                        layers[i] = r.getDrawable(R.drawable.male_upper_arm_left);
                    } else if (bodyPart.equals("chestLeft")) {
                        layers[i] = r.getDrawable(R.drawable.male_chest_left);
                    } else if (bodyPart.equals("chestRight")) {
                        layers[i] = r.getDrawable(R.drawable.male_chest_right);
                    } else if (bodyPart.equals("upperArmRight")) {
                        layers[i] = r.getDrawable(R.drawable.male_upper_arm_right);
                    } else if (bodyPart.equals("abdomen")) {
                        layers[i] = r.getDrawable(R.drawable.male_abdomen);
                    } else if (bodyPart.equals("groin")) {
                        layers[i] = r.getDrawable(R.drawable.male_groin);
                    } else if (bodyPart.equals("upperLegLeft")) {
                        layers[i] = r.getDrawable(R.drawable.male_upper_leg_left);
                    } else if (bodyPart.equals("upperLegLeft")) {
                        layers[i] = r.getDrawable(R.drawable.male_upper_leg_left);
                    } else if (bodyPart.equals("upperLegRight")) {
                        layers[i] = r.getDrawable(R.drawable.male_upper_leg_right);
                    } else if (bodyPart.equals("lowerLegLeft")) {
                        layers[i] = r.getDrawable(R.drawable.male_lower_leg_left);
                    } else if (bodyPart.equals("lowerLegRight")) {
                        layers[i] = r.getDrawable(R.drawable.male_lower_leg_right);
                    } else if (bodyPart.equals("lowerArmLeft")) {
                        layers[i] = r.getDrawable(R.drawable.male_lower_arm_left);
                    } else if (bodyPart.equals("lowerArmRight")) {
                        layers[i] = r.getDrawable(R.drawable.male_lower_arm_right);
                    }
                    i++;
                }
            }
            LayerDrawable layerDrawable = new LayerDrawable(layers);
            body_parts.setImageDrawable(layerDrawable);
            sex.setText(getString(R.string.male_gender));
        }
    }

    private boolean uploadPhoto(ParseFile image_file, ParseFile thumbnail_file) {
        image_file.saveInBackground();
        thumbnail_file.saveInBackground();
        final ParseObject photo_table = new ParseObject("Photo");
        ParseObject patient = ParseObject.createWithoutData("Patient", objectId);
        photo_table.put("patient", patient);
        photo_table.put("image", image_file);
        photo_table.put("thumbnail", thumbnail_file);
        photo_table.put("type", getString(R.string.server_file_type));
        photo_table.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    System.out.println("Uploaded image");
                    addActivity(photo_table.getObjectId());
                }
            }
        });
        return true;
    }

    private void fetchActivities() {
        //Query to get data
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Activity");
        ParseObject o = ParseObject.createWithoutData("Patient", objectId);
        query.whereEqualTo("patient", o);
        query.orderByDescending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> notesList, ParseException e) {
                if (e == null) {
                    parseActivities(notesList);
                } else {
                    Log.d(PATIENT, "Error: " + e.getMessage());
                    parseActivities(new ArrayList<ParseObject>());
                }
            }
        });
        return;
    }

    private void showThumbnail(ParseObject activity) throws ParseException {
        final LinearLayout activity_layout = (LinearLayout) findViewById(R.id.activity_view);
        final ImageView imageView = new ImageView(context);
        imageView.setTag(activity.getParseObject("photo").getObjectId());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 10, 0, 0);
        imageView.setMinimumHeight(150);
        imageView.setPadding(20, 10, 0, 0);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShowPhotoActivity.class);
                intent.putExtra("photo_objectId", (String) v.getTag());
                startActivity(intent);
            }
        });
        activity_layout.addView(imageView, layoutParams);
        ParseObject photo_object = activity.getParseObject("photo").fetchIfNeeded();
        ParseFile thumbnail_file = photo_object.getParseFile("thumbnail");
        thumbnail_file.getDataInBackground(new GetDataCallback() {
            @Override
            public void done(byte[] bytes, ParseException e) {
                Bitmap thumbnail = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imageView.setImageBitmap(thumbnail);
            }
        });
    }

    private void parseActivities(List<ParseObject> activity_list) {
        final LinearLayout main_section = (LinearLayout) findViewById(R.id.activity_view);
        for (ParseObject activity : activity_list) {
            String activity_type = activity.getString("type");
            System.out.println(activity_type);
            if (activity_type.equalsIgnoreCase(getString(R.string.server_activity_imageAddedType))) {
                try {
                    showThumbnail(activity);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else {
                if (null == activity.getString("text")) {
                    continue;
                }
                TextView newTextView = new TextView(context);
                newTextView.setBackgroundColor(Color.WHITE);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0, 10, 0, 0);
                newTextView.setMinHeight(150);
                newTextView.setPadding(20, 10, 0, 0);
                String note = activity.getString("text");
                newTextView.setText(note);
                main_section.addView(newTextView, layoutParams);
            }
        }
        progressDialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("status", status);
        intent.putExtra("view_tag", view_tag);
        setResult(RESULT_OK, intent);
        finish();
        super.onBackPressed();
    }
}
