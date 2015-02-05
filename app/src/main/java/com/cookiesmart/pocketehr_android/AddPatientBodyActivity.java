package com.cookiesmart.pocketehr_android;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Created by aditya841 on 12/1/2014.
 */
public class AddPatientBodyActivity extends Activity {
    private static final String ADDPATIENT = "AddPatientBodyActivity";
    private static final int HEAD = 1;
    private static final int THROAT = 2;
    private static final int UPPERARMLEFT = 3;
    private static final int CHESTLEFT = 4;
    private static final int CHESTRIGHT = 5;
    private static final int UPPERARMRIGHT = 6;
    private static final int LOWERARMLEFT = 7;
    private static final int ABDOMEN = 8;
    private static final int LOWERARMRIGHT = 9;
    private static final int GROIN = 10;
    private static final int UPPERLEGLEFT = 11;
    private static final int UPPERLEGRIGHT = 12;
    private static final int LOWERLEGLEFT = 13;
    private static final int LOWERLEGRIGHT = 14;
    Patient p = null;
    private ImageView bodyImage;
    private static HashSet<String> bodyParts = new HashSet<String>();
    private static String GENDER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_object);
        Intent intent = getIntent();
        p = intent.getParcelableExtra("Patient");
        GENDER = p.getGender();
        bodyImage = (ImageView) findViewById(R.id.body_part_image);
        if (GENDER.equals("Male")) {
//          bodyImage.setImageResource(R.drawable.male_body_parts);
            Resources r = getResources();
            Drawable[] layers = new Drawable[1];
            layers[0] = r.getDrawable(R.drawable.male_body_parts);
            LayerDrawable layerDrawable = new LayerDrawable(layers);
            bodyImage.setImageDrawable(layerDrawable);
        } else {
            Resources r = getResources();
            Drawable[] layers = new Drawable[1];
            layers[0] = r.getDrawable(R.drawable.female_body_parts);
            LayerDrawable layerDrawable = new LayerDrawable(layers);
            bodyImage.setImageDrawable(layerDrawable);
        }

        bodyImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Matrix inverse = new Matrix();
                        bodyImage.getImageMatrix().invert(inverse);

                        // map touch point from ImageView to image
                        float[] touchPoint = new float[]{event.getX(), event.getY()};
                        inverse.mapPoints(touchPoint);
                        Log.i("Coordinates", touchPoint[0] + ", " + touchPoint[1]);
                        if (GENDER.equals("Male"))
                            getMaleBodyPart(touchPoint);
                        else
                            getFemaleBodyPart(touchPoint);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    public void drawMaleBodyPart(HashSet<String> bodyParts) {
        Drawable[] layers = new Drawable[bodyParts.size() + 1];
        Resources r = getResources();
        if (bodyParts.size() < 1) {
            layers[0] = r.getDrawable(R.drawable.male_body_parts);
        } else {
            layers[0] = r.getDrawable(R.drawable.male_body_parts);
            int i = 1;
            Iterator<String> part = bodyParts.iterator();
            while (part.hasNext()) {
                String bodyPart = part.next();
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
        bodyImage.setImageDrawable(layerDrawable);
    }

    public void drawFemaleBodyPart(HashSet<String> bodyParts) {
        Drawable[] layers = new Drawable[bodyParts.size() + 1];
        Resources r = getResources();
        if (bodyParts.size() < 1) {
            layers[0] = r.getDrawable(R.drawable.female_body_parts);
        } else {
            layers[0] = r.getDrawable(R.drawable.female_body_parts);
            int i = 1;
            Iterator<String> part = bodyParts.iterator();
            while (part.hasNext()) {
                String bodyPart = part.next();
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
        bodyImage.setImageDrawable(layerDrawable);
    }

    private void getMaleBodyPart(float[] touchPoint) {
        //checking for head
        if (touchPoint[0] >= 555 && touchPoint[0] <= 688 && touchPoint[1] >= 310 && touchPoint[1] <= 451) {
            if (bodyParts.contains("head")) {
                bodyParts.remove("head");
            } else {
                bodyParts.add("head");
            }
        } else if (touchPoint[0] >= 566 && touchPoint[0] <= 681 && touchPoint[1] >= 499 && touchPoint[1] <= 513) {
            if (bodyParts.contains("throat")) {
                bodyParts.remove("throat");
            } else {
                bodyParts.add("throat");
            }
        } else if (touchPoint[0] >= 389 && touchPoint[0] <= 438 && touchPoint[1] >= 581 && touchPoint[1] <= 819) {
            if (bodyParts.contains("upperArmLeft")) {
                bodyParts.remove("upperArmLeft");
            } else {
                bodyParts.add("upperArmLeft");
            }
        } else if (touchPoint[0] >= 528 && touchPoint[0] <= 563 && touchPoint[1] >= 546 && touchPoint[1] <= 795) {
            if (bodyParts.contains("chestLeft")) {
                bodyParts.remove("chestLeft");
            } else {
                bodyParts.add("chestLeft");
            }
        } else if (touchPoint[0] >= 641 && touchPoint[0] <= 731 && touchPoint[1] >= 546 && touchPoint[1] <= 795) {
            if (bodyParts.contains("chestRight")) {
                bodyParts.remove("chestRight");
            } else {
                bodyParts.add("chestRight");
            }
        } else if (touchPoint[0] >= 779 && touchPoint[0] <= 843 && touchPoint[1] >= 581 && touchPoint[1] <= 819) {
            if (bodyParts.contains("upperArmRight")) {
                bodyParts.remove("upperArmRight");
            } else {
                bodyParts.add("upperArmRight");
            }
        } else if (touchPoint[0] >= 551 && touchPoint[0] <= 738 && touchPoint[1] >= 856 && touchPoint[1] <= 951) {
            if (bodyParts.contains("abdomen")) {
                bodyParts.remove("abdomen");
            } else {
                bodyParts.add("abdomen");
            }
        } else if (touchPoint[0] >= 464 && touchPoint[0] <= 737 && touchPoint[1] >= 1007 && touchPoint[1] <= 1045) {
            if (bodyParts.contains("groin")) {
                bodyParts.remove("groin");
            } else {
                bodyParts.add("groin");
            }
        } else if (touchPoint[0] >= 488 && touchPoint[0] <= 592 && touchPoint[1] >= 1085 && touchPoint[1] <= 1453) {
            if (bodyParts.contains("upperLegLeft")) {
                bodyParts.remove("upperLegLeft");
            } else {
                bodyParts.add("upperLegLeft");
            }
        } else if (touchPoint[0] >= 644 && touchPoint[0] <= 715 && touchPoint[1] >= 1085 && touchPoint[1] <= 1453) {
            if (bodyParts.contains("upperLegRight")) {
                bodyParts.remove("upperLegRight");
            } else {
                bodyParts.add("upperLegRight");
            }
        } else if (touchPoint[0] >= 506 && touchPoint[0] <= 567 && touchPoint[1] >= 1502 && touchPoint[1] <= 1857) {
            if (bodyParts.contains("lowerLegLeft")) {
                bodyParts.remove("lowerLegLeft");
            } else {
                bodyParts.add("lowerLegLeft");
            }
        } else if (touchPoint[0] >= 668 && touchPoint[0] <= 716 && touchPoint[1] >= 1504 && touchPoint[1] <= 1857) {
            if (bodyParts.contains("lowerLegRight")) {
                bodyParts.remove("lowerLegRight");
            } else {
                bodyParts.add("lowerLegRight");
            }
        } else if (touchPoint[0] >= 824 && touchPoint[0] <= 1010 && touchPoint[1] >= 916 && touchPoint[1] <= 1199) {
            if (bodyParts.contains("lowerArmRight")) {
                bodyParts.remove("lowerArmRight");
            } else {
                bodyParts.add("lowerArmRight");
            }
        } else if (touchPoint[0] >= 332 && touchPoint[0] <= 422 && touchPoint[1] >= 916 && touchPoint[1] <= 1199) {
            if (bodyParts.contains("lowerArmLeft")) {
                bodyParts.remove("lowerArmLeft");
            } else {
                bodyParts.add("lowerArmLeft");
            }
        }
        drawMaleBodyPart(bodyParts);
    }

    private void getFemaleBodyPart(float[] touchPoint) {
        //checking for head
        if (touchPoint[0] >= 555 && touchPoint[0] <= 688 && touchPoint[1] >= 310 && touchPoint[1] <= 451) {
            if (bodyParts.contains("head")) {
                bodyParts.remove("head");
            } else {
                bodyParts.add("head");
            }
        } else if (touchPoint[0] >= 581 && touchPoint[0] <= 660 && touchPoint[1] >= 492 && touchPoint[1] <= 530) {
            if (bodyParts.contains("throat")) {
                bodyParts.remove("throat");
            } else {
                bodyParts.add("throat");
            }
        } else if (touchPoint[0] >= 435 && touchPoint[0] <= 482 && touchPoint[1] >= 581 && touchPoint[1] <= 824) {
            if (bodyParts.contains("upperArmLeft")) {
                bodyParts.remove("upperArmLeft");
            } else {
                bodyParts.add("upperArmLeft");
            }
        } else if (touchPoint[0] >= 519 && touchPoint[0] <= 607 && touchPoint[1] >= 570 && touchPoint[1] <= 775) {
            if (bodyParts.contains("chestLeft")) {
                bodyParts.remove("chestLeft");
            } else {
                bodyParts.add("chestLeft");
            }
        } else if (touchPoint[0] >= 641 && touchPoint[0] <= 731 && touchPoint[1] >= 570 && touchPoint[1] <= 775) {
            if (bodyParts.contains("chestRight")) {
                bodyParts.remove("chestRight");
            } else {
                bodyParts.add("chestRight");
            }
        } else if (touchPoint[0] >= 779 && touchPoint[0] <= 843 && touchPoint[1] >= 581 && touchPoint[1] <= 824) {
            if (bodyParts.contains("upperArmRight")) {
                bodyParts.remove("upperArmRight");
            } else {
                bodyParts.add("upperArmRight");
            }
        } else if (touchPoint[0] >= 551 && touchPoint[0] <= 738 && touchPoint[1] >= 856 && touchPoint[1] <= 951) {
            if (bodyParts.contains("abdomen")) {
                bodyParts.remove("abdomen");
            } else {
                bodyParts.add("abdomen");
            }
        } else if (touchPoint[0] >= 464 && touchPoint[0] <= 737 && touchPoint[1] >= 1007 && touchPoint[1] <= 1045) {
            if (bodyParts.contains("groin")) {
                bodyParts.remove("groin");
            } else {
                bodyParts.add("groin");
            }
        } else if (touchPoint[0] >= 488 && touchPoint[0] <= 592 && touchPoint[1] >= 1085 && touchPoint[1] <= 1453) {
            if (bodyParts.contains("upperLegLeft")) {
                bodyParts.remove("upperLegLeft");
            } else {
                bodyParts.add("upperLegLeft");
            }
        } else if (touchPoint[0] >= 644 && touchPoint[0] <= 715 && touchPoint[1] >= 1085 && touchPoint[1] <= 1453) {
            if (bodyParts.contains("upperLegRight")) {
                bodyParts.remove("upperLegRight");
            } else {
                bodyParts.add("upperLegRight");
            }
        } else if (touchPoint[0] >= 506 && touchPoint[0] <= 567 && touchPoint[1] >= 1502 && touchPoint[1] <= 1857) {
            if (bodyParts.contains("lowerLegLeft")) {
                bodyParts.remove("lowerLegLeft");
            } else {
                bodyParts.add("lowerLegLeft");
            }
        } else if (touchPoint[0] >= 668 && touchPoint[0] <= 716 && touchPoint[1] >= 1504 && touchPoint[1] <= 1857) {
            if (bodyParts.contains("lowerLegRight")) {
                bodyParts.remove("lowerLegRight");
            } else {
                bodyParts.add("lowerLegRight");
            }
        } else if (touchPoint[0] >= 824 && touchPoint[0] <= 1010 && touchPoint[1] >= 916 && touchPoint[1] <= 1199) {
            if (bodyParts.contains("lowerArmRight")) {
                bodyParts.remove("lowerArmRight");
            } else {
                bodyParts.add("lowerArmRight");
            }
        } else if (touchPoint[0] >= 332 && touchPoint[0] <= 422 && touchPoint[1] >= 916 && touchPoint[1] <= 1199) {
            if (bodyParts.contains("lowerArmLeft")) {
                bodyParts.remove("lowerArmLeft");
            } else {
                bodyParts.add("lowerArmLeft");
            }
        }
        drawFemaleBodyPart(bodyParts);
    }

    public void saveAndFinish(View v) {
        final ParseObject patient = new ParseObject("Patient");
        ParseUser userObject = ParseUser.getCurrentUser();

        patient.put("author", userObject);
        patient.put("firstName", p.getFirstName());
        patient.put("lastName", p.getLastName());

        if (p.getHospital().trim().equals("")) {
            patient.put("hospital", JSONObject.NULL);
        } else {
            patient.put("hospital", p.getHospital());
        }
        if (p.getContactNo().trim().equals("")) {
            patient.put("contactNo", JSONObject.NULL);
        } else {
            patient.put("contactNo", p.getContactNo());
        }
        if (p.getPatientIDNumber().trim().equals("")) {
            patient.put("patientIDNumber", JSONObject.NULL);
        } else {
            patient.put("patientIDNumber", p.getPatientIDNumber());
        }

        Date d = null;
        if (!p.getDob().trim().equals("")) {
            SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
            try {
                d = df.parse(p.getDob());
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
            if (d != null) {
                patient.put("dob", d);
            } else {
                patient.put("dob", JSONObject.NULL);
            }
        } else {
            patient.put("dob", JSONObject.NULL);
        }
        patient.put("sex", "k" + p.getGender());
        patient.put("age", p.getAge());
        patient.put("status", p.getStatus());

        patient.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    String objectId = patient.getObjectId();
                    saveNotes(objectId, p.getNotes());
                } else {
                    Log.d(ADDPATIENT, "Error: " + e.getMessage());
                }
            }
        });
    }

    public void saveNotes(String objectId, String notes) {
        Log.i(ADDPATIENT, "save notes called");
        ParseObject patientObject = ParseObject.createWithoutData("Patient", objectId);
        ParseUser userObject = ParseUser.getCurrentUser();
        ParseObject noteObject = new ParseObject("Activity");
        if (notes.trim().equals("")) {
            noteObject.put("text", JSONObject.NULL);
        } else {
            noteObject.put("text", notes);
        }
        noteObject.put("author", userObject);
        noteObject.put("patient", patientObject);
        noteObject.put("type", "kPatientCreated");
        noteObject.saveInBackground();


        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
