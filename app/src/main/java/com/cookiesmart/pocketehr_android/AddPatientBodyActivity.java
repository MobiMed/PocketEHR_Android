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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Created by aditya841 on 12/1/2014.
 */
public class AddPatientBodyActivity extends Activity {
    private static final String ADDPATIENT = "AddPatientBodyActivity";
    private static HashSet<String> bodyParts = new HashSet<String>();
    private static String GENDER;
    private ArrayList<String> bodyPartsList = new ArrayList<String>();
    private Patient p = null;
    private String action = "";
    private ImageView bodyImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_object);
        Intent intent = getIntent();
        p = intent.getParcelableExtra("Patient");
        action = intent.getStringExtra("action");

        GENDER = p.getGender();
        bodyImage = (ImageView) findViewById(R.id.body_part_image);

        if (action.equals("view")) {
            bodyPartsList = intent.getStringArrayListExtra("bodyParts");
            loadPatientBodyMap();
        } else {
            Resources r = getResources();
            Drawable[] layers = new Drawable[1];
            if (GENDER.equals(getString(R.string.male_gender))) {
//          bodyImage.setImageResource(R.drawable.male_body_parts);
                layers[0] = r.getDrawable(R.drawable.male_body_parts);
                LayerDrawable layerDrawable = new LayerDrawable(layers);
                bodyImage.setImageDrawable(layerDrawable);
            } else if (GENDER.equals(getString(R.string.female_gender))) {
                layers[0] = r.getDrawable(R.drawable.female_body_parts);
                LayerDrawable layerDrawable = new LayerDrawable(layers);
                bodyImage.setImageDrawable(layerDrawable);
            }
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
                        if (GENDER.equals(getString(R.string.male_gender)))
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

    private void loadPatientBodyMap() {
        Drawable[] layers = new Drawable[bodyPartsList.size() + 1];
        Resources r = getResources();
        if (GENDER.equals(getString(R.string.female_gender))) {
            layers[0] = r.getDrawable(R.drawable.female_body_parts);
            if (bodyPartsList.size() == 0) {
                //do nothing
            } else {
                int i = 1;
                while (i <= bodyPartsList.size()) {
                    String bodyPart = bodyPartsList.get(i - 1);
                    if (bodyPart.equals("head")) {
                        layers[i] = r.getDrawable(R.drawable.female_head);
                        bodyParts.add("head");
                    } else if (bodyPart.equals("throat")) {
                        bodyParts.add("throat");
                        layers[i] = r.getDrawable(R.drawable.female_throat);
                    } else if (bodyPart.equals("upperArmLeft")) {
                        bodyParts.add("upperArmLeft");
                        layers[i] = r.getDrawable(R.drawable.female_upper_arm_left);
                    } else if (bodyPart.equals("chestLeft")) {
                        bodyParts.add("chestLeft");
                        layers[i] = r.getDrawable(R.drawable.female_chest_left);
                    } else if (bodyPart.equals("chestRight")) {
                        bodyParts.add("chestRight");
                        layers[i] = r.getDrawable(R.drawable.female_chest_right);
                    } else if (bodyPart.equals("upperArmRight")) {
                        bodyParts.add("upperArmRight");
                        layers[i] = r.getDrawable(R.drawable.female_upper_arm_right);
                    } else if (bodyPart.equals("abdomen")) {
                        bodyParts.add("abdomen");
                        layers[i] = r.getDrawable(R.drawable.female_abdomen);
                    } else if (bodyPart.equals("groin")) {
                        bodyParts.add("groin");
                        layers[i] = r.getDrawable(R.drawable.female_groin);
                    } else if (bodyPart.equals("upperLegLeft")) {
                        bodyParts.add("upperLegLeft");
                        layers[i] = r.getDrawable(R.drawable.female_upper_leg_left);
                    } else if (bodyPart.equals("upperLegRight")) {
                        bodyParts.add("upperLegRight");
                        layers[i] = r.getDrawable(R.drawable.female_upper_leg_right);
                    } else if (bodyPart.equals("lowerLegLeft")) {
                        bodyParts.add("lowerLegLeft");
                        layers[i] = r.getDrawable(R.drawable.female_lower_leg_left);
                    } else if (bodyPart.equals("lowerLegRight")) {
                        bodyParts.add("lowerLegRight");
                        layers[i] = r.getDrawable(R.drawable.female_lower_leg_right);
                    } else if (bodyPart.equals("lowerArmLeft")) {
                        bodyParts.add("lowerArmLeft");
                        layers[i] = r.getDrawable(R.drawable.female_lower_arm_left);
                    } else if (bodyPart.equals("lowerArmRight")) {
                        bodyParts.add("lowerArmRight");
                        layers[i] = r.getDrawable(R.drawable.female_lower_arm_right);
                    }
                    i++;
                }
            }
            LayerDrawable layerDrawable = new LayerDrawable(layers);
            bodyImage.setImageDrawable(layerDrawable);
        } else if (GENDER.equals(getString(R.string.male_gender))) {
            layers[0] = r.getDrawable(R.drawable.male_body_parts);
            if (bodyPartsList.size() == 0) {
                //do nothing
            } else {
                int i = 1;
                while (i <= bodyPartsList.size()) {
                    String bodyPart = bodyPartsList.get(i - 1);
                    if (bodyPart.equals("head")) {
                        bodyParts.add("head");
                        layers[i] = r.getDrawable(R.drawable.male_head);
                    } else if (bodyPart.equals("throat")) {
                        bodyParts.add("throat");
                        layers[i] = r.getDrawable(R.drawable.male_throat);
                    } else if (bodyPart.equals("upperArmLeft")) {
                        bodyParts.add("upperArmLeft");
                        layers[i] = r.getDrawable(R.drawable.male_upper_arm_left);
                    } else if (bodyPart.equals("chestLeft")) {
                        bodyParts.add("chestLeft");
                        layers[i] = r.getDrawable(R.drawable.male_chest_left);
                    } else if (bodyPart.equals("chestRight")) {
                        bodyParts.add("chestRight");
                        layers[i] = r.getDrawable(R.drawable.male_chest_right);
                    } else if (bodyPart.equals("upperArmRight")) {
                        bodyParts.add("upperArmRight");
                        layers[i] = r.getDrawable(R.drawable.male_upper_arm_right);
                    } else if (bodyPart.equals("abdomen")) {
                        bodyParts.add("abdomen");
                        layers[i] = r.getDrawable(R.drawable.male_abdomen);
                    } else if (bodyPart.equals("groin")) {
                        bodyParts.add("groin");
                        layers[i] = r.getDrawable(R.drawable.male_groin);
                    } else if (bodyPart.equals("upperLegLeft")) {
                        bodyParts.add("upperLegLeft");
                        layers[i] = r.getDrawable(R.drawable.male_upper_leg_left);
                    } else if (bodyPart.equals("upperLegRight")) {
                        bodyParts.add("upperLegRight");
                        layers[i] = r.getDrawable(R.drawable.male_upper_leg_right);
                    } else if (bodyPart.equals("lowerLegLeft")) {
                        bodyParts.add("lowerLegLeft");
                        layers[i] = r.getDrawable(R.drawable.male_lower_leg_left);
                    } else if (bodyPart.equals("lowerLegRight")) {
                        bodyParts.add("lowerLegRight");
                        layers[i] = r.getDrawable(R.drawable.male_lower_leg_right);
                    } else if (bodyPart.equals("lowerArmLeft")) {
                        bodyParts.add("lowerArmLeft");
                        layers[i] = r.getDrawable(R.drawable.male_lower_arm_left);
                    } else if (bodyPart.equals("lowerArmRight")) {
                        bodyParts.add("lowerArmRight");
                        layers[i] = r.getDrawable(R.drawable.male_lower_arm_right);
                    }
                    i++;
                }
            }
            LayerDrawable layerDrawable = new LayerDrawable(layers);
            bodyImage.setImageDrawable(layerDrawable);
        }
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
        if (touchPoint[0] >= 746 && touchPoint[0] <= 1208 && touchPoint[1] >= 83 && touchPoint[1] <= 348) {
            if (bodyParts.contains("head")) {
                bodyParts.remove("head");
            } else {
                bodyParts.add("head");
            }
        } else if (touchPoint[0] >= 813 && touchPoint[0] <= 1112 && touchPoint[1] >= 371 && touchPoint[1] <= 422) {
            if (bodyParts.contains("throat")) {
                bodyParts.remove("throat");
            } else {
                bodyParts.add("throat");
            }
        } else if (touchPoint[0] >= 558 && touchPoint[0] <= 700 && touchPoint[1] >= 527 && touchPoint[1] <= 930) {
            if (bodyParts.contains("upperArmLeft")) {
                bodyParts.remove("upperArmLeft");
            } else {
                bodyParts.add("upperArmLeft");
            }
        } else if (touchPoint[0] >= 746 && touchPoint[0] <= 986 && touchPoint[1] >= 467 && touchPoint[1] <= 946) {
            if (bodyParts.contains("chestLeft")) {
                bodyParts.remove("chestLeft");
            } else {
                bodyParts.add("chestLeft");
            }
        } else if (touchPoint[0] >= 986 && touchPoint[0] <= 1177 && touchPoint[1] >= 506 && touchPoint[1] <= 919) {
            if (bodyParts.contains("chestRight")) {
                bodyParts.remove("chestRight");
            } else {
                bodyParts.add("chestRight");
            }
        } else if (touchPoint[0] >= 1223 && touchPoint[0] <= 1381 && touchPoint[1] >= 552 && touchPoint[1] <= 976) {
            if (bodyParts.contains("upperArmRight")) {
                bodyParts.remove("upperArmRight");
            } else {
                bodyParts.add("upperArmRight");
            }
        } else if (touchPoint[0] >= 790 && touchPoint[0] <= 1207 && touchPoint[1] >= 981 && touchPoint[1] <= 1152) {
            if (bodyParts.contains("abdomen")) {
                bodyParts.remove("abdomen");
            } else {
                bodyParts.add("abdomen");
            }
        } else if (touchPoint[0] >= 767 && touchPoint[0] <= 1237 && touchPoint[1] >= 1161 && touchPoint[1] <= 1328) {
            if (bodyParts.contains("groin")) {
                bodyParts.remove("groin");
            } else {
                bodyParts.add("groin");
            }
        } else if (touchPoint[0] >= 713 && touchPoint[0] <= 961 && touchPoint[1] >= 1385 && touchPoint[1] <= 1902) {
            if (bodyParts.contains("upperLegLeft")) {
                bodyParts.remove("upperLegLeft");
            } else {
                bodyParts.add("upperLegLeft");
            }
        } else if (touchPoint[0] >= 1012 && touchPoint[0] <= 1248 && touchPoint[1] >= 1329 && touchPoint[1] <= 1935) {
            if (bodyParts.contains("upperLegRight")) {
                bodyParts.remove("upperLegRight");
            } else {
                bodyParts.add("upperLegRight");
            }
        } else if (touchPoint[0] >= 732 && touchPoint[0] <= 961 && touchPoint[1] >= 1953 && touchPoint[1] <= 2517) {
            if (bodyParts.contains("lowerLegLeft")) {
                bodyParts.remove("lowerLegLeft");
            } else {
                bodyParts.add("lowerLegLeft");
            }
        } else if (touchPoint[0] >= 983 && touchPoint[0] <= 1195 && touchPoint[1] >= 1984 && touchPoint[1] <= 2517) {
            if (bodyParts.contains("lowerLegRight")) {
                bodyParts.remove("lowerLegRight");
            } else {
                bodyParts.add("lowerLegRight");
            }
        } else if (touchPoint[0] >= 1213 && touchPoint[0] <= 1640 && touchPoint[1] >= 1045 && touchPoint[1] <= 1529) {
            if (bodyParts.contains("lowerArmRight")) {
                bodyParts.remove("lowerArmRight");
            } else {
                bodyParts.add("lowerArmRight");
            }
        } else if (touchPoint[0] >= 291 && touchPoint[0] <= 681 && touchPoint[1] >= 1018 && touchPoint[1] <= 1460) {
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
        if (touchPoint[0] >= 740 && touchPoint[0] <= 1092 && touchPoint[1] >= 96 && touchPoint[1] <= 285) {
            if (bodyParts.contains("head")) {
                bodyParts.remove("head");
            } else {
                bodyParts.add("head");
            }
        } else if (touchPoint[0] >= 806 && touchPoint[0] <= 1062 && touchPoint[1] >= 291 && touchPoint[1] <= 387) {
            if (bodyParts.contains("throat")) {
                bodyParts.remove("throat");
            } else {
                bodyParts.add("throat");
            }
        } else if (touchPoint[0] >= 587 && touchPoint[0] <= 705 && touchPoint[1] >= 451 && touchPoint[1] <= 819) {
            if (bodyParts.contains("upperArmLeft")) {
                bodyParts.remove("upperArmLeft");
            } else {
                bodyParts.add("upperArmLeft");
            }
        } else if (touchPoint[0] >= 779 && touchPoint[0] <= 934 && touchPoint[1] >= 433 && touchPoint[1] <= 729) {
            if (bodyParts.contains("chestLeft")) {
                bodyParts.remove("chestLeft");
            } else {
                bodyParts.add("chestLeft");
            }
        } else if (touchPoint[0] >= 941 && touchPoint[0] <= 1069 && touchPoint[1] >= 494 && touchPoint[1] <= 711) {
            if (bodyParts.contains("chestRight")) {
                bodyParts.remove("chestRight");
            } else {
                bodyParts.add("chestRight");
            }
        } else if (touchPoint[0] >= 1100 && touchPoint[0] <= 1217 && touchPoint[1] >= 499 && touchPoint[1] <= 837) {
            if (bodyParts.contains("upperArmRight")) {
                bodyParts.remove("upperArmRight");
            } else {
                bodyParts.add("upperArmRight");
            }
        } else if (touchPoint[0] >= 804 && touchPoint[0] <= 1061 && touchPoint[1] >= 828 && touchPoint[1] <= 997) {
            if (bodyParts.contains("abdomen")) {
                bodyParts.remove("abdomen");
            } else {
                bodyParts.add("abdomen");
            }
        } else if (touchPoint[0] >= 774 && touchPoint[0] <= 1119 && touchPoint[1] >= 1041 && touchPoint[1] <= 1155) {
            if (bodyParts.contains("groin")) {
                bodyParts.remove("groin");
            } else {
                bodyParts.add("groin");
            }
        } else if (touchPoint[0] >= 696 && touchPoint[0] <= 849 && touchPoint[1] >= 1168 && touchPoint[1] <= 1706) {
            if (bodyParts.contains("upperLegLeft")) {
                bodyParts.remove("upperLegLeft");
            } else {
                bodyParts.add("upperLegLeft");
            }
        } else if (touchPoint[0] >= 942 && touchPoint[0] <= 1146 && touchPoint[1] >= 1204 && touchPoint[1] <= 1702) {
            if (bodyParts.contains("upperLegRight")) {
                bodyParts.remove("upperLegRight");
            } else {
                bodyParts.add("upperLegRight");
            }
        } else if (touchPoint[0] >= 720 && touchPoint[0] <= 882 && touchPoint[1] >= 1774 && touchPoint[1] <= 2343) {
            if (bodyParts.contains("lowerLegLeft")) {
                bodyParts.remove("lowerLegLeft");
            } else {
                bodyParts.add("lowerLegLeft");
            }
        } else if (touchPoint[0] >= 935 && touchPoint[0] <= 1119 && touchPoint[1] >= 1808 && touchPoint[1] <= 2278) {
            if (bodyParts.contains("lowerLegRight")) {
                bodyParts.remove("lowerLegRight");
            } else {
                bodyParts.add("lowerLegRight");
            }
        } else if (touchPoint[0] >= 1225 && touchPoint[0] <= 1400 && touchPoint[1] >= 906 && touchPoint[1] <= 1368) {
            if (bodyParts.contains("lowerArmRight")) {
                bodyParts.remove("lowerArmRight");
            } else {
                bodyParts.add("lowerArmRight");
            }
        } else if (touchPoint[0] >= 484 && touchPoint[0] <= 679 && touchPoint[1] >= 895 && touchPoint[1] <= 1457) {
            if (bodyParts.contains("lowerArmLeft")) {
                bodyParts.remove("lowerArmLeft");
            } else {
                bodyParts.add("lowerArmLeft");
            }
        }
        drawFemaleBodyPart(bodyParts);
    }

    public void saveAndFinish(View v) throws JSONException {
        final ParseObject patient = new ParseObject("Patient");
        ParseUser userObject = ParseUser.getCurrentUser();

        patient.put("author", userObject);
        patient.put("firstName", p.getFirstName());
        patient.put("lastName", p.getLastName());

        if (bodyParts.size() == 0) {
            patient.put("locations", JSONObject.NULL);
        } else {
            JSONArray bodyPartsArray = new JSONArray(bodyParts.toArray());
            patient.put("locations", bodyPartsArray);
        }

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
        if (p.getGender().equals(getString(R.string.male_gender))) {
            patient.put("sex", getString(R.string.server_sex_male));
        } else if (p.getGender().equals(getString(R.string.female_gender))) {
            patient.put("sex", getString(R.string.server_sex_female));
        }
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
