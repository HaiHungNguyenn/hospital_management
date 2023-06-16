/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import static data.DoctorList.DEPARTMENT_ID_FORMAT;
import static data.Examination.DOCTOR_ID_FORMAT;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import mng.LogIn;
import mng.Menu;
import validation.MyTools;
import static validation.MyTools.readBool;
import static validation.MyTools.readNonBlank;
import static validation.MyTools.readPattern;
import static validation.MyTools.writeFile;

public class ExaminationList extends ArrayList<Examination> {

    LogIn loginObj = null;
    protected String file = "";
    protected boolean updated = false;
    public static final String DOCTOR_ID_FORMAT = "DOC\\d{4}";
    public static final String PATIENT_ID_FORMAT = "PAT\\d{4}";
    public static final String EXAMINATION_ID_FORMAT = "EXA\\d{4}";

    public ExaminationList(LogIn loginObj) {
        this.loginObj = loginObj;
    }

    public void loadExaminationFromFile() throws IOException {
        List<String> list = MyTools.readLinesFromFile(file);
        for (int i = 0; i < list.size(); i++) {
            this.add(new Examination(list.get(i)));
        }
    }

    public void initWithFile() throws IOException {
        Config con = new Config();
        file = con.getExaminationFile();
        loadExaminationFromFile();
    }

    public int searchExaminationByID(String ID) {
        int n = this.size();
        for (int i = 0; i < n; i++) {
            if (this.get(i).getExaminationID().equals(ID.toUpperCase())) {
                return i;
            }
        }
        return -1;
    }

    public String validDoctorID(String message) throws IOException {
        DoctorList ref = new DoctorList(this.loginObj);
        ref.initWithFile();
        String input;
        boolean answer;
        int pos;
        do {
            input = readPattern(message, DOCTOR_ID_FORMAT);
            pos = ref.searchDoctorByID(input);
            if (pos < 0) {
                System.out.println("No matching Doctor ID!");
                answer = readBool("Would you like to view full list of Doctor?");
                if (answer) {
                    ref.printAllDoctor();
                }
                System.out.println("Now try again!");
            }
        } while (pos < 0);
        return input;
    }

    public String validPatientID(String message) throws IOException {
        PatientList ref = new PatientList(this.loginObj);
        ref.initWithFile();
        String input;
        boolean answer;
        int pos;
        do {
            input = readPattern(message, PATIENT_ID_FORMAT);
            pos = ref.searchPatientByID(input);
            if (pos < 0) {
                System.out.println("No matching Patient ID!");
                answer = readBool("Would you like to view full list of Patient?");
                if (answer) {
                    ref.printAllPatient();
                }
                System.out.println("Now try again!");
            }
        } while (pos < 0);
        return input;
    }

    public void addExamination() throws IOException {
        String exaID;
        String docID;
        String patID;
        String result;
        String date;
        int pos = 0;
        do {
            exaID = MyTools.readPattern("ID of new Examination", EXAMINATION_ID_FORMAT);
            pos = searchExaminationByID(exaID.toUpperCase());
            if (pos >= 0) {
                System.out.println("Examination ID already exist!");
            }
        } while (pos >= 0);
        docID = validDoctorID("ID of Doctor");
        patID = validPatientID("ID of Patient");
        result = MyTools.readNonBlank("Result of examination");
        date = MyTools.toString(MyTools.inputDate("Date of examination"));
        this.add(new Examination(exaID, docID, patID, result, date));
        System.out.println("New examination has just been added");
        updated = true;
    }

    public void printAllExamination() {
        if (this.isEmpty()) {
            System.out.println("Empty List");
        } else {
            System.out.println("------------------------------------------------------------------------");
            System.out.format("%10s %10s %10s %15s %13s", "EXAM_ID", "DOC_ID", "PAT_ID", "RESULT", "DATE\n");
            System.out.println("------------------------------------------------------------------------");
            for (Examination x : this) {
                x.display();
            }
            System.out.println("------------------------------------------------------------------------");
        }
    }

    public void removeExaminationByID() {
        boolean answ;
        boolean cond = false;
        do {
            String input = readNonBlank("Enter the ID of examination will be removed ");
            int pos = searchExaminationByID(input.toUpperCase());
            if (pos < 0) {
                System.out.println("The examination is not found!");
                answ = readBool("Do you want to view examination list?");
                if (answ) {
                    this.printAllExamination();
                }
                answ = readBool("Do you want to remove again?");
                if (answ) {
                    cond = true;
                }
            } else {
                this.get(pos).toString();
                answ = readBool("Are you sure to remove?");
                if (answ) {
                    this.remove(pos);
                    System.out.println("The examination has been removed!");
                    updated = true;
                    break;
                }
            }
        } while (cond);
    }

    public void updateInfomationExamination() throws IOException {
        boolean answ;
        boolean stay = false;
        String[] ops = {"Examination ID", "Doctor ID", "Patient ID", "result", "date", "QUit"};
        do {
            String input = readNonBlank("Enter ID of examination will be updated");
            int pos = searchExaminationByID(input);
            if (pos < 0) {
                System.out.println("The examination is not found");
                answ = readBool("Do you want to view examination list");
                if (answ) {
                    printAllExamination();
                }
                answ = readBool("Do you want to update again?");
                if (answ) {
                    stay = true;
                }
            } else {
                this.get(pos).display();
                answ = readBool("Are you sure to update?");
                if (answ) {
                    int userChoice = 0;
                    boolean cond = false;
                    Examination e = this.get(pos);
                    Menu menuUpdate = new Menu(ops);
                    do {
                        userChoice = menuUpdate.getChoice("Updating information");
                        switch (userChoice) {
                            case 1:
                                String newExaID;
                                do {
                                    newExaID = readPattern("New ID of Examination", EXAMINATION_ID_FORMAT);
                                    pos = searchExaminationByID(newExaID);
                                    if (pos >= 0) {
                                        System.out.println("This ID is already exist");
                                    }

                                } while (pos >= 0);
                                e.setExaminationID(newExaID);
                                break;
                            case 2:
                                String newDocID = validDoctorID("new Doctor ID");
                                e.setDoctorID(newDocID);
                                break;
                            case 3:
                                String newPatID = validPatientID("new Patient ID");
                                e.setPatientID(newPatID);
                                break;
                            case 4:
                                String newResult = readNonBlank("new result of examination");
                                e.setExaminationID(newResult);
                                break;
                            case 5:
                                String newDate = MyTools.toString(MyTools.inputDate("New date of examination"));
                                e.setDate(newDate);
                                break;
                            default:
                                System.out.println("No matching information");
                        }
                        if (!readBool("Do you want to continue updating this examination")) {
                            break;
                        }
                    } while (userChoice > 0 && userChoice < menuUpdate.size());
                }
            }
            updated = true;
        } while (stay);
    }

    public boolean isUpdated() {
        return updated;
    }

    public void setUpdated(boolean updated) {
        this.updated = updated;
    }

    public void writeObjectToFile() throws IOException {
        if (updated) {
            writeFile(file, this);
            updated = false;
        }
    }

    public void filterByDoctorID() {
        String input = MyTools.readPattern("Enter ID of Doctor to be filter", DOCTOR_ID_FORMAT);
        System.out.println("------------------------------------------------------------------------");
        System.out.format("%10s %10s %10s %15s %13s", "EXAM_ID", "DOC_ID", "PAT_ID", "RESULT", "DATE\n");
        System.out.println("------------------------------------------------------------------------");
        for (Examination x : this) {
            if (x.getDoctorID().equals(input)) {
                x.display();
            }
        }
        System.out.println("------------------------------------------------------------------------");
    }

    public void sortByDate() {
        Collections.sort(this, new Comparator<Examination>() {
            @Override
            public int compare(Examination t, Examination t1) {
                try {
                    return MyTools.toDate(t.getDate()).compareTo(MyTools.toDate(t1.getDate()));
                } catch (ParseException ex) {
                    java.util.logging.Logger.getLogger(DepartmentList.class.getName()).log(Level.SEVERE, null, ex);
                }
                return 0;
            }
        });
        System.out.println("------------------------------------------------------------------------");
        System.out.format("%10s %10s %10s %15s %13s", "EXAM_ID", "DOC_ID", "PAT_ID", "RESULT", "DATE\n");
        System.out.println("------------------------------------------------------------------------");
        for (Examination x : this) {
            x.display();
        }
        System.out.println("------------------------------------------------------------------------");
    }

}
