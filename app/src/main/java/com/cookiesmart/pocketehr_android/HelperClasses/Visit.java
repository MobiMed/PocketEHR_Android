package com.cookiesmart.pocketehr_android.HelperClasses;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by aditya841 on 4/17/2015.
 */
public class Visit implements Parcelable {
    private String classType = "";
    private String statusType = "";
    private String gender = "Male";
    private String locationType = "";
    private String initialObservation = "";
    private String patient_object_id = "";
    private String doctorId = "";
    private String additionalInfo = "";


    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getStatusType() {
        return statusType;
    }

    public void setStatusType(String statusType) {
        this.statusType = statusType;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public String getInitialObservation() {
        return initialObservation;
    }

    public void setInitialObservation(String initialObservation) {
        this.initialObservation = initialObservation;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(patient_object_id);
        dest.writeString(classType);
        dest.writeString(statusType);
        dest.writeString(locationType);
        dest.writeString(initialObservation);
        dest.writeString(doctorId);
        dest.writeString(additionalInfo);
        dest.writeString(gender);
    }

    public static final Parcelable.Creator<Visit> CREATOR
            = new Parcelable.Creator<Visit>() {
        @Override
        public Visit createFromParcel(Parcel source) {
            Visit v = new Visit();
            v.patient_object_id = source.readString();
            v.classType = source.readString();
            v.statusType = source.readString();
            v.locationType = source.readString();
            v.initialObservation = source.readString();
            v.doctorId = source.readString();
            v.additionalInfo = source.readString();
            v.gender = source.readString();
            return v;
        }

        public Visit[] newArray(int size) {
            return new Visit[size];
        }
    };

    public String getPatient_object_id() {
        return patient_object_id;
    }

    public void setPatient_object_id(String patient_object_id) {
        this.patient_object_id = patient_object_id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
