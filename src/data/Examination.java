/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import validation.MyTools;

public class Examination implements Comparable<Examination> {

    public static final String SEPARATOR = ",";
    public static final String DOCTOR_ID_FORMAT = "DOC\\d{4}";
    public static final String PATIENT_ID_FORMAT = "PAT\\d{4}";
    public static final String EXAMINATION_ID_FORMAT = "EXA\\d{4}";

    private String examinationID;
    private String doctorID;
    private String patientID;
    private String result;
    private String date;

    public Examination() {
    }

    public Examination(String examinationID, String doctorID, String patientID, String result, String date) {
        this.examinationID = examinationID;
        this.doctorID = doctorID;
        this.patientID = patientID;
        this.result = result;
        this.date = date;
    }

    public Examination(String lines) {
        String[] info = lines.split(SEPARATOR);
        examinationID = info[0].trim();
        doctorID = info[1].trim();
        patientID = info[2].trim();
        result = info[3].trim();
        date = info[4].trim();
    }

    public String getExaminationID() {
        return examinationID;
    }

    public void setExaminationID(String examinationID) {
        this.examinationID = examinationID;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return getExaminationID() + SEPARATOR + getDoctorID() + SEPARATOR + getPatientID() + SEPARATOR + getResult() + SEPARATOR + getDate() + " \n";
    }

    public void display() {
        System.out.format("%10s %10s %10s %15s %15s \n", getExaminationID(), getDoctorID(), getPatientID(), getResult(), getDate());
    }

    @Override
    public int compareTo(Examination o) {
        return this.getExaminationID().compareTo(o.getExaminationID());
    }

}
