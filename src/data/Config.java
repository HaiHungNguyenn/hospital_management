/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.IOException;
import java.util.List;
import validation.MyTools;

public class Config {

    private static final String CONFIG_FILE = "config.txt";
    private String accountFile;
    private String doctorFile;
    private String patientFile;
    private String departmentFile;
    private String examinationFile;

    public Config() throws IOException {
        readData();
    }

    private void readData() throws IOException {
        List<String> lines = MyTools.readLinesFromFile("Config.txt");
        for (String line : lines) {
            line = line.toUpperCase();
            String[] parts = line.split(":");
            if (line.indexOf("ACCOUN") >= 0) {
                accountFile = parts[1].trim();
            } else if (line.indexOf("PATIEN") >= 0) {
                patientFile = parts[1].trim();
            } else if (line.indexOf("DOCTO") >= 0) {
                doctorFile = parts[1].trim();
            } else if (line.indexOf("DEPARTMEN") >= 0) {
                departmentFile = parts[1].trim();
            } else if (line.indexOf("EXAMINATIO") >= 0) {
                examinationFile = parts[1].trim();
            }
        }
    }

    public String getAccountFile() {
        return accountFile;
    }

    public String getDoctorFile() {
        return doctorFile;
    }

    public String getPatientFile() {
        return patientFile;
    }

    public String getDepartmentFile() {
        return departmentFile;
    }

    public String getExaminationFile() {
        return examinationFile;
    }

}
