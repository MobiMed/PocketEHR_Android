package com.cookiesmart.pocketehr_android.HelperClasses;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cookiesmart.pocketehr_android.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by aditya841 on 4/28/2015.
 */
public class EventActivityListAdapter extends ArrayAdapter<HashMap<String, Object>> {

    private LayoutInflater mLayoutInflater;
    private ArrayList<HashMap<String, Object>> activityList = new ArrayList<>();
    private Context activity_context;

    public EventActivityListAdapter(Context context) {
        super(context, 0);
        activity_context = context;
        mLayoutInflater = (LayoutInflater) activity_context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return activityList.size();
    }

    @Override
    public HashMap<String, Object> getItem(int position) {
        return activityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HashMap<String, Object> patientListObject = activityList.get(position);

        if (patientListObject.get("activityType") == activity_context.getString(R.string.event_picture_activity)) {
            if (convertView == null) {
                convertView = mLayoutInflater.inflate(
                        R.layout.event_picture_rowlayout, parent, false);

            }

            return pictureLayoutView(convertView, patientListObject);
        } else if (patientListObject.get("activityType") == activity_context.getString(R.string.event_note_activity)) {
            if (convertView == null) {
                convertView = mLayoutInflater.inflate(
                        R.layout.event_note_rowlayout, parent, false);

            }

            return noteLayoutView(convertView, patientListObject);
        } else if (patientListObject.get("activityType") == activity_context.getString(R.string.event_test_activity)) {
            if (convertView == null) {
                convertView = mLayoutInflater.inflate(
                        R.layout.event_test_rowlayout, parent, false);
            }

            return testLayoutView(convertView, patientListObject);
        }

        return convertView;
    }

    private View pictureLayoutView(View convertView, HashMap<String, Object> patientListObject) {
        byte[] imageData = (byte[]) patientListObject.get("eventPicture");
        Bitmap thumbnail = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
        thumbnail = Bitmap.createScaledBitmap(thumbnail, 250, 250, true);
        ImageView eventPicturePlaceholder = (ImageView) convertView.findViewById(R.id.picture_image_placeholder);
        eventPicturePlaceholder.setImageBitmap(thumbnail);
        return convertView;
    }

    private View noteLayoutView(View convertView, HashMap<String, Object> patientListObject) {
        TextView noteText = (TextView) convertView.findViewById(R.id.note_text_placeholder);
        noteText.setText((String) patientListObject.get("eventNote"));
        return convertView;
    }

    private View testLayoutView(View convertView, HashMap<String, Object> patientListObject) {
        TextView testText = (TextView) convertView.findViewById(R.id.test_text_placeholder);
        testText.setText((String) patientListObject.get("testNote"));

        ImageView testPicture = (ImageView) convertView.findViewById(R.id.test_image_placeholder);
        byte[] imageData = (byte[]) patientListObject.get("eventPicture");
        Bitmap thumbnail = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
        thumbnail = Bitmap.createScaledBitmap(thumbnail, 250, 250, true);
        testPicture.setImageBitmap(thumbnail);
        return convertView;
    }

    public void updateEntries(ArrayList<HashMap<String, Object>> newPatientList) {
        if (!newPatientList.isEmpty()) {
            activityList.addAll(newPatientList);
            notifyDataSetChanged();
        }
    }
}
