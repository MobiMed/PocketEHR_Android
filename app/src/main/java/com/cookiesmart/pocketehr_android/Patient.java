package com.cookiesmart.pocketehr_android;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by aditya841 on 12/1/2014.
 */
public class Patient implements Parcelable {
    private String firstName = "";
    private String lastName = "";
    private String contactNo = "";
    private String gender = "";
    private String email = "";
    private String dob = "";
    private String notes = "";
    private String hospital = "";
    private String patientIDNumber = "";
    private int age = -1;

    private String status = "kIncomplete";
    public static final Parcelable.Creator<Patient> CREATOR
            = new Parcelable.Creator<Patient>() {
        public Patient createFromParcel(Parcel in) {
            Patient p = new Patient();
            p.firstName = in.readString();
            p.lastName = in.readString();
            p.contactNo = in.readString();
            p.gender = in.readString();
            p.email = in.readString();
            p.dob = in.readString();
            p.notes = in.readString();
            p.hospital = in.readString();
            p.age = in.readInt();
            p.status = in.readString();
            p.patientIDNumber = in.readString();
            return p;
        }

        public Patient[] newArray(int size) {
            return new Patient[size];
        }
    };

    public String getStatus() {
        return status;
    }

    public int describeContents() {
        return 0;
    }

//    Patient() {
//        firstName = "";
//        lastName = "";
//        contactNo = "";
//        gender = "";
//        email = "";
//        dob = "";
//        notes = "";
//        hospital = "";
//        patientIDNumber = "";
//        age = -1;
//        status = "kIncomplete";
//    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(contactNo);
        dest.writeString(gender);
        dest.writeString(email);
        dest.writeString(dob);
        dest.writeString(notes);
        dest.writeString(hospital);
        dest.writeInt(age);
        dest.writeString(status);
        dest.writeString(patientIDNumber);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPatientIDNumber() {
        return patientIDNumber;
    }

    public void setPatientIDNumber(String patientIDNumber) {
        this.patientIDNumber = patientIDNumber;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
