package com.cookiesmart.pocketehr_android.HelperClasses;

import java.util.Date;

/**
 * Created by aditya841 on 4/22/2015.
 */
public class PatientList {
    private String patientFirstName = "";
    private String patientLastName = "";
    private Date patientVisitDate = null;
    private String patientVisitStatus = "";
    private String patientVisitLocation = "";
    private String patientTestStatus = "";

    PatientList(String patientFirstName, String patientLastName, String patientTestStatus,
                String patientVisitLocation, String patientVisitStatus, Date patientVisitDate) {
        this.patientFirstName = patientFirstName;
        this.patientLastName = patientLastName;
        this.patientTestStatus = patientTestStatus;
        this.patientVisitDate = patientVisitDate;
        this.patientVisitLocation = patientVisitLocation;
        this.patientVisitStatus = patientVisitStatus;
    }

    public String getPatientFirstName() {
        return patientFirstName;
    }

    public void setPatientFirstName(String patientFirstName) {
        this.patientFirstName = patientFirstName;
    }

    public String getPatientLastName() {
        return patientLastName;
    }

    public void setPatientLastName(String patientLastName) {
        this.patientLastName = patientLastName;
    }

    public Date getPatientVisitDate() {
        return patientVisitDate;
    }

    public void setPatientVisitDate(Date patientVisitDate) {
        this.patientVisitDate = patientVisitDate;
    }

    public String getPatientVisitStatus() {
        return patientVisitStatus;
    }

    public void setPatientVisitStatus(String patientVisitStatus) {
        this.patientVisitStatus = patientVisitStatus;
    }

    public String getPatientVisitLocation() {
        return patientVisitLocation;
    }

    public void setPatientVisitLocation(String patientVisitLocation) {
        this.patientVisitLocation = patientVisitLocation;
    }

    public String getPatientTestStatus() {
        return patientTestStatus;
    }

    public void setPatientTestStatus(String patientTestStatus) {
        this.patientTestStatus = patientTestStatus;
    }
}
