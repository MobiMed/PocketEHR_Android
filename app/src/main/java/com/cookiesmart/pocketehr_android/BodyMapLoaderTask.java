package com.cookiesmart.pocketehr_android;

import android.content.Context;
import android.content.res.Resources;
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

    public BodyMapLoaderTask(ImageView body_parts, Context context, ArrayList<String> bodyParts,
                             String gender, TextView gender_view) {
        this.body_parts = new WeakReference<ImageView>(body_parts);
        this.bodyParts = bodyParts;
        activity_context = context;
        this.gender_view = gender_view;
        this.gender = gender;
    }

    @Override
    protected LayerDrawable doInBackground(String... params) {
        if (params[0].equals("front")) {
            return showFrontBody();
        } else {
            return showBackBody();
        }
    }

    private LayerDrawable showFrontBody() {
        Drawable[] layers = new Drawable[bodyParts.size() + 1];
        Resources r = activity_context.getResources();
        if (gender.contains(activity_context.getString(R.string.server_sex_female))) {
            layers[0] = r.getDrawable(R.drawable.smaller_female_body_parts);
            if (bodyParts.size() == 0) {
                //do nothing
            } else {
                int i = 1;
                while (i <= bodyParts.size()) {
                    String bodyPart = bodyParts.get(i - 1);
                    if (bodyPart.equals("head")) {
                        layers[i] = r.getDrawable(R.drawable.smaller_female_head);
                    } else if (bodyPart.equals("throat")) {
                        layers[i] = r.getDrawable(R.drawable.smaller_female_throat);
                    } else if (bodyPart.equals("upperArmLeft")) {
                        layers[i] = r.getDrawable(R.drawable.smaller_female_upper_arm_left);
                    } else if (bodyPart.equals("chestLeft")) {
                        layers[i] = r.getDrawable(R.drawable.smaller_female_chest_left);
                    } else if (bodyPart.equals("chestRight")) {
                        layers[i] = r.getDrawable(R.drawable.smaller_female_chest_right);
                    } else if (bodyPart.equals("upperArmRight")) {
                        layers[i] = r.getDrawable(R.drawable.smaller_female_upper_arm_right);
                    } else if (bodyPart.equals("abdomen")) {
                        layers[i] = r.getDrawable(R.drawable.smaller_female_abdomen);
                    } else if (bodyPart.equals("groin")) {
                        layers[i] = r.getDrawable(R.drawable.smaller_female_groin);
                    } else if (bodyPart.equals("upperLegLeft")) {
                        layers[i] = r.getDrawable(R.drawable.smaller_female_upper_leg_left);
                    } else if (bodyPart.equals("upperLegRight")) {
                        layers[i] = r.getDrawable(R.drawable.smaller_female_upper_leg_right);
                    } else if (bodyPart.equals("lowerLegLeft")) {
                        layers[i] = r.getDrawable(R.drawable.smaller_female_lower_leg_left);
                    } else if (bodyPart.equals("lowerLegRight")) {
                        layers[i] = r.getDrawable(R.drawable.smaller_female_lower_leg_right);
                    } else if (bodyPart.equals("lowerArmLeft")) {
                        layers[i] = r.getDrawable(R.drawable.smaller_female_lower_arm_left);
                    } else if (bodyPart.equals("lowerArmRight")) {
                        layers[i] = r.getDrawable(R.drawable.smaller_female_lower_arm_right);
                    }
                    i++;
                }
            }
            sex = activity_context.getString(R.string.female_gender);
            return new LayerDrawable(layers);
        } else {
            layers[0] = r.getDrawable(R.drawable.smaller_male_body_parts);
            if (bodyParts.size() == 0) {
                //do nothing
            } else {
                int i = 1;
                while (i <= bodyParts.size()) {
                    String bodyPart = bodyParts.get(i - 1);
                    System.out.println(bodyPart);
                    if (bodyPart.equals("head")) {
                        layers[i] = r.getDrawable(R.drawable.smaller_male_head);
                    } else if (bodyPart.equals("throat")) {
                        layers[i] = r.getDrawable(R.drawable.smaller_male_throat);
                    } else if (bodyPart.equals("upperArmLeft")) {
                        layers[i] = r.getDrawable(R.drawable.smaller_male_upper_arm_left);
                    } else if (bodyPart.equals("chestLeft")) {
                        layers[i] = r.getDrawable(R.drawable.smaller_male_chest_left);
                    } else if (bodyPart.equals("chestRight")) {
                        layers[i] = r.getDrawable(R.drawable.smaller_male_chest_right);
                    } else if (bodyPart.equals("upperArmRight")) {
                        layers[i] = r.getDrawable(R.drawable.smaller_male_upper_arm_right);
                    } else if (bodyPart.equals("abdomen")) {
                        layers[i] = r.getDrawable(R.drawable.smaller_male_abdomen);
                    } else if (bodyPart.equals("groin")) {
                        layers[i] = r.getDrawable(R.drawable.smaller_male_groin);
                    } else if (bodyPart.equals("upperLegLeft")) {
                        layers[i] = r.getDrawable(R.drawable.smaller_male_upper_leg_left);
                    } else if (bodyPart.equals("upperLegLeft")) {
                        layers[i] = r.getDrawable(R.drawable.smaller_male_upper_leg_left);
                    } else if (bodyPart.equals("upperLegRight")) {
                        layers[i] = r.getDrawable(R.drawable.smaller_male_upper_leg_right);
                    } else if (bodyPart.equals("lowerLegLeft")) {
                        layers[i] = r.getDrawable(R.drawable.smaller_male_lower_leg_left);
                    } else if (bodyPart.equals("lowerLegRight")) {
                        layers[i] = r.getDrawable(R.drawable.smaller_male_lower_leg_right);
                    } else if (bodyPart.equals("lowerArmLeft")) {
                        layers[i] = r.getDrawable(R.drawable.smaller_male_lower_arm_left);
                    } else if (bodyPart.equals("lowerArmRight")) {
                        layers[i] = r.getDrawable(R.drawable.smaller_male_lower_arm_right);
                    }
                    i++;
                }
            }
            sex = activity_context.getString(R.string.male_gender);
            return new LayerDrawable(layers);
        }
    }

    private LayerDrawable showBackBody() {
        Drawable[] layers = new Drawable[bodyParts.size() + 1];
        Resources r = activity_context.getResources();
        if (gender.contains(activity_context.getString(R.string.server_sex_female))) {
            layers[0] = r.getDrawable(R.drawable.smaller_female_body_parts_b);
            if (bodyParts.size() == 0) {
                //do nothing
            } else {
                int i = 1;
                while (i <= bodyParts.size()) {
                    String bodyPart = bodyParts.get(i - 1);
                    if (bodyPart.equals("head_b")) {
                        layers[i] = r.getDrawable(R.drawable.smaller_female_head_b);
                    } else if (bodyPart.equals("throat_b")) {
                        layers[i] = r.getDrawable(R.drawable.smaller_female_throat_b);
                    } else if (bodyPart.equals("upperArmLeft_b")) {
                        layers[i] = r.getDrawable(R.drawable.smaller_female_upper_arm_left_b);
                    } else if (bodyPart.equals("back")) {
                        layers[i] = r.getDrawable(R.drawable.smaller_female_back_b);
                    } else if (bodyPart.equals("upperArmRight_b")) {
                        layers[i] = r.getDrawable(R.drawable.smaller_female_upper_arm_right_b);
                    } else if (bodyPart.equals("butt")) {
                        layers[i] = r.getDrawable(R.drawable.smaller_female_butt_b);
                    } else if (bodyPart.equals("upperLegLeft_b")) {
                        layers[i] = r.getDrawable(R.drawable.smaller_female_upper_leg_left_b);
                    } else if (bodyPart.equals("upperLegRight_b")) {
                        layers[i] = r.getDrawable(R.drawable.smaller_female_upper_leg_right_b);
                    } else if (bodyPart.equals("lowerLegLeft_b")) {
                        layers[i] = r.getDrawable(R.drawable.smaller_female_lower_leg_left_b);
                    } else if (bodyPart.equals("lowerLegRight_b")) {
                        layers[i] = r.getDrawable(R.drawable.smaller_female_lower_leg_right_b);
                    } else if (bodyPart.equals("lowerArmLeft_b")) {
                        layers[i] = r.getDrawable(R.drawable.smaller_female_lower_arm_left_b);
                    } else if (bodyPart.equals("lowerArmRight_b")) {
                        layers[i] = r.getDrawable(R.drawable.smaller_female_lower_arm_right_b);
                    }
                    i++;
                }
            }
            sex = activity_context.getString(R.string.female_gender);
            return new LayerDrawable(layers);
        } else {
            layers[0] = r.getDrawable(R.drawable.smaller_male_body_parts_b);
            if (bodyParts.size() == 0) {
                //do nothing
            } else {
                int i = 1;
                while (i <= bodyParts.size()) {
                    String bodyPart = bodyParts.get(i - 1);
                    if (bodyPart.equals("head_b")) {
                        layers[i] = r.getDrawable(R.drawable.smaller_male_head_b);
                    } else if (bodyPart.equals("throat_b")) {
                        layers[i] = r.getDrawable(R.drawable.smaller_male_throat_b);
                    } else if (bodyPart.equals("upperArmLeft_b")) {
                        layers[i] = r.getDrawable(R.drawable.smaller_male_upper_arm_left_b);
                    } else if (bodyPart.equals("back")) {
                        layers[i] = r.getDrawable(R.drawable.smaller_male_back_b);
                    } else if (bodyPart.equals("upperArmRight_b")) {
                        layers[i] = r.getDrawable(R.drawable.smaller_male_upper_arm_right_b);
                    } else if (bodyPart.equals("butt")) {
                        layers[i] = r.getDrawable(R.drawable.smaller_male_butt_b);
                    } else if (bodyPart.equals("upperLegLeft_b")) {
                        layers[i] = r.getDrawable(R.drawable.smaller_male_upper_leg_left_b);
                    } else if (bodyPart.equals("upperLegRight_b")) {
                        layers[i] = r.getDrawable(R.drawable.smaller_male_upper_leg_right_b);
                    } else if (bodyPart.equals("lowerLegLeft_b")) {
                        layers[i] = r.getDrawable(R.drawable.smaller_male_lower_leg_left_b);
                    } else if (bodyPart.equals("lowerLegRight_b")) {
                        layers[i] = r.getDrawable(R.drawable.smaller_male_lower_leg_right_b);
                    } else if (bodyPart.equals("lowerArmLeft_b")) {
                        layers[i] = r.getDrawable(R.drawable.smaller_male_lower_arm_left_b);
                    } else if (bodyPart.equals("lowerArmRight_b")) {
                        layers[i] = r.getDrawable(R.drawable.smaller_male_lower_arm_right_b);
                    }
                    i++;
                }
            }
            sex = activity_context.getString(R.string.female_gender);
            return new LayerDrawable(layers);
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
    }
}

