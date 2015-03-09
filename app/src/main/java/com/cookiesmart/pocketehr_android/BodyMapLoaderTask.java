package com.cookiesmart.pocketehr_android;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by aditya841 on 3/7/2015.
 */
public class BodyMapLoaderTask extends AsyncTask<String, Void, LayerDrawable> {

    private WeakReference<ImageView> body_parts;
    private ArrayList<String> bodyParts;
    private Context activity_context;
    private String gender;
    private String sex = "";
    private TextView gender_view;
    private ProgressDialog progressDialog;

    public BodyMapLoaderTask(ImageView body_parts, Context context, ArrayList<String> bodyParts, String gender, TextView gender_view, ProgressDialog progressDialog) {
        this.body_parts = new WeakReference<ImageView>(body_parts);
        this.bodyParts = bodyParts;
        activity_context = context;
        this.gender_view = gender_view;
        this.gender = gender;
        this.progressDialog = progressDialog;
    }

    @Override
    protected LayerDrawable doInBackground(String... params) {
        if (params[0].equals("front")) {
            return showFrontBody();
        } else {
            return showBackBody();
        }
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    private LayerDrawable showFrontBody() {
        Resources r = activity_context.getResources();
        Drawable[] layers = new Drawable[bodyParts.size() + 1];
        if (gender.contains(activity_context.getString(R.string.server_sex_female))) {
            layers[0] = new BitmapDrawable(r, decodeSampledBitmapFromResource(r, R.drawable.female_body_parts, 325, 325));
            if (bodyParts.size() != 0) {
                int i = 1;
                while (i <= bodyParts.size()) {
                    String bodyPart = bodyParts.get(i - 1);
                    if (bodyPart.equals("head")) {
                        layers[i] = new BitmapDrawable(r, decodeSampledBitmapFromResource(r, R.drawable.female_head, 325, 325));
                    } else if (bodyPart.equals("throat")) {
                        layers[i] = new BitmapDrawable(r, decodeSampledBitmapFromResource(r, R.drawable.female_throat, 325, 325));
                    } else if (bodyPart.equals("upperArmLeft")) {
                        layers[i] = new BitmapDrawable(r, decodeSampledBitmapFromResource(r, R.drawable.female_upper_arm_left, 325, 325));
                    } else if (bodyPart.equals("chestLeft")) {
                        layers[i] = new BitmapDrawable(r, decodeSampledBitmapFromResource(r, R.drawable.female_chest_left, 325, 325));
                    } else if (bodyPart.equals("chestRight")) {
                        layers[i] = new BitmapDrawable(r, decodeSampledBitmapFromResource(r, R.drawable.female_chest_right, 325, 325));
                    } else if (bodyPart.equals("upperArmRight")) {
                        layers[i] = new BitmapDrawable(r, decodeSampledBitmapFromResource(r, R.drawable.female_upper_arm_right, 325, 325));
                    } else if (bodyPart.equals("abdomen")) {
                        layers[i] = new BitmapDrawable(r, decodeSampledBitmapFromResource(r, R.drawable.female_abdomen, 325, 325));
                    } else if (bodyPart.equals("groin")) {
                        layers[i] = new BitmapDrawable(r, decodeSampledBitmapFromResource(r, R.drawable.female_groin, 325, 325));
                    } else if (bodyPart.equals("upperLegLeft")) {
                        layers[i] = new BitmapDrawable(r, decodeSampledBitmapFromResource(r, R.drawable.female_upper_leg_left, 325, 325));
                    } else if (bodyPart.equals("upperLegRight")) {
                        layers[i] = new BitmapDrawable(r, decodeSampledBitmapFromResource(r, R.drawable.female_upper_leg_right, 325, 325));
                    } else if (bodyPart.equals("lowerLegLeft")) {
                        layers[i] = new BitmapDrawable(r, decodeSampledBitmapFromResource(r, R.drawable.female_lower_leg_left, 325, 325));
                    } else if (bodyPart.equals("lowerLegRight")) {
                        layers[i] = new BitmapDrawable(r, decodeSampledBitmapFromResource(r, R.drawable.female_lower_leg_right, 325, 325));
                    } else if (bodyPart.equals("lowerArmLeft")) {
                        layers[i] = new BitmapDrawable(r, decodeSampledBitmapFromResource(r, R.drawable.female_lower_arm_left, 325, 325));
                    } else if (bodyPart.equals("lowerArmRight")) {
                        layers[i] = new BitmapDrawable(r, decodeSampledBitmapFromResource(r, R.drawable.female_lower_arm_right, 325, 325));
                    }
                    i++;
                }
            }
            sex = activity_context.getString(R.string.female_gender);
            LayerDrawable layerDrawable = new LayerDrawable(layers);
            layers = null;
            return layerDrawable;
        } else {
            layers[0] = r.getDrawable(R.drawable.male_body_parts);
            if (bodyParts.size() != 0) {
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
            sex = activity_context.getString(R.string.male_gender);
            LayerDrawable layerDrawable = new LayerDrawable(layers);
            layers = null;
            return layerDrawable;
        }
    }

    private LayerDrawable showBackBody() {
        Drawable[] layers = new Drawable[bodyParts.size() + 1];
        Resources r = activity_context.getResources();
        if (gender.contains(activity_context.getString(R.string.server_sex_female))) {
            layers[0] = r.getDrawable(R.drawable.female_body_parts_b);
            if (bodyParts.size() != 0) {
                int i = 1;
                while (i <= bodyParts.size()) {
                    String bodyPart = bodyParts.get(i - 1);
                    if (bodyPart.equals("head")) {
                        layers[i] = r.getDrawable(R.drawable.female_head_b);
                    } else if (bodyPart.equals("throat")) {
                        layers[i] = r.getDrawable(R.drawable.female_throat_b);
                    } else if (bodyPart.equals("upperArmLeft")) {
                        layers[i] = r.getDrawable(R.drawable.female_upper_arm_left_b);
                    } else if (bodyPart.equals("back")) {
                        layers[i] = r.getDrawable(R.drawable.female_back_b);
                    } else if (bodyPart.equals("upperArmRight")) {
                        layers[i] = r.getDrawable(R.drawable.female_upper_arm_right_b);
                    } else if (bodyPart.equals("butt")) {
                        layers[i] = r.getDrawable(R.drawable.female_butt_b);
                    } else if (bodyPart.equals("upperLegLeft")) {
                        layers[i] = r.getDrawable(R.drawable.female_upper_leg_left_b);
                    } else if (bodyPart.equals("upperLegLeft")) {
                        layers[i] = r.getDrawable(R.drawable.female_upper_leg_left_b);
                    } else if (bodyPart.equals("upperLegRight")) {
                        layers[i] = r.getDrawable(R.drawable.female_upper_leg_right_b);
                    } else if (bodyPart.equals("lowerLegLeft")) {
                        layers[i] = r.getDrawable(R.drawable.female_lower_leg_left_b);
                    } else if (bodyPart.equals("lowerLegRight")) {
                        layers[i] = r.getDrawable(R.drawable.female_lower_leg_right_b);
                    } else if (bodyPart.equals("lowerArmLeft")) {
                        layers[i] = r.getDrawable(R.drawable.female_lower_arm_left_b);
                    } else if (bodyPart.equals("lowerArmRight")) {
                        layers[i] = r.getDrawable(R.drawable.female_lower_arm_right_b);
                    }
                    i++;
                }
            }
            sex = activity_context.getString(R.string.female_gender);
            LayerDrawable layerDrawable = new LayerDrawable(layers);
            layers = null;
            return layerDrawable;
        } else {
            layers[0] = r.getDrawable(R.drawable.male_body_parts_b);
            if (bodyParts.size() != 0) {
                int i = 1;
                while (i <= bodyParts.size()) {
                    String bodyPart = bodyParts.get(i - 1);
                    System.out.println(bodyPart);
                    if (bodyPart.equals("head")) {
                        layers[i] = r.getDrawable(R.drawable.male_head_b);
                    } else if (bodyPart.equals("throat")) {
                        layers[i] = r.getDrawable(R.drawable.male_throat_b);
                    } else if (bodyPart.equals("upperArmLeft")) {
                        layers[i] = r.getDrawable(R.drawable.male_upper_arm_left_b);
                    } else if (bodyPart.equals("back")) {
                        layers[i] = r.getDrawable(R.drawable.male_back_b);
                    } else if (bodyPart.equals("upperArmRight")) {
                        layers[i] = r.getDrawable(R.drawable.male_upper_arm_right_b);
                    } else if (bodyPart.equals("butt")) {
                        layers[i] = r.getDrawable(R.drawable.male_butt_b);
                    } else if (bodyPart.equals("upperLegLeft")) {
                        layers[i] = r.getDrawable(R.drawable.male_upper_leg_left_b);
                    } else if (bodyPart.equals("upperLegRight")) {
                        layers[i] = r.getDrawable(R.drawable.male_upper_leg_right_b);
                    } else if (bodyPart.equals("lowerLegLeft")) {
                        layers[i] = r.getDrawable(R.drawable.male_lower_leg_left_b);
                    } else if (bodyPart.equals("lowerLegRight")) {
                        layers[i] = r.getDrawable(R.drawable.male_lower_leg_right_b);
                    } else if (bodyPart.equals("lowerArmLeft")) {
                        layers[i] = r.getDrawable(R.drawable.male_lower_arm_left_b);
                    } else if (bodyPart.equals("lowerArmRight")) {
                        layers[i] = r.getDrawable(R.drawable.male_lower_arm_right_b);
                    }
                    i++;
                }
            }
            sex = activity_context.getString(R.string.male_gender);
            LayerDrawable layerDrawable = new LayerDrawable(layers);
            layers = null;
            return layerDrawable;
        }
    }

    @Override
    protected void onPostExecute(LayerDrawable layerDrawable) {
        if (layerDrawable != null && body_parts != null) {
            final ImageView imageView = body_parts.get();
            imageView.setImageDrawable(layerDrawable);
            if (gender.contains(activity_context.getString(R.string.server_sex_female))) {
                gender_view.setText(activity_context.getString(R.string.female_gender));
            } else {
                gender_view.setText(activity_context.getString(R.string.male_gender));
            }

        }
        body_parts = null;
        bodyParts = null;
        layerDrawable = null;
    }
}

