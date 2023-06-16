/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import mng.LogIn;
import mng.Menu;
import validation.MyTools;
import static validation.MyTools.inputDate;
import static validation.MyTools.readBool;
import static validation.MyTools.readNonBlank;
import static validation.MyTools.readPattern;
import static validation.MyTools.writeFile;

/**
 *
 * @author admin
 */
public class DoctorList extends ArrayList<Doctor> {

    LogIn logObj = null;
    protected String file = "";
    protected boolean updated = false;
    public static final String DOCTOR_ID_FORMAT = "DOC\\d{4}";
    public static final String DEPARTMENT_ID_FORMAT = "DEP\\d{4}";

    public DoctorList(LogIn loginObj) {
        this.logObj = loginObj;
    }

    public void loadDoctorFromFile() throws IOException {
        List<String> list = MyTools.readLinesFromFile(file);
        for (int i = 0; i < list.size(); i++) {
            this.add(new Doctor(list.get(i)));
        }
    }

    public void initWithFile() throws IOException {
        Config con = new Config();
        file = con.getDoctorFile();
        loadDoctorFromFile();
    }

    public int searchDoctorByID(String ID) {
        for (Doctor x : this) {
            if (x.getID().equals(ID)) {
                return this.indexOf(x);
            }
        }
        return -1;
    }

    public String validDepartmentID(String message) throws IOException {
        DepartmentList ref = new DepartmentList(this.logObj);
        ref.initWithFile();
        String input;
        boolean answer;
        int pos;
        do {
            input = readPattern(message, DEPARTMENT_ID_FORMAT);
            pos = ref.searchDepartmentByID(input);
            if (pos < 0) {
                System.out.println("No matching Department ID!");
                answer = readBool("Would you like to view full list of Departments?");
                if (answer) {
                    ref.printAllDepartment();
                }
                System.out.println("Now try again!");
            }
        } while (pos < 0);
        return input;
    }

    public void addDoctor() throws ParseException, IOException {
        String doctorID;
        String name;
        boolean sex, cond;
        String departmentID;
        String address;
        String createDate;
        String lastUpdateDate = null;
        int pos;
        do {
            doctorID = MyTools.readPattern("ID of new Doctor", DOCTOR_ID_FORMAT);
            pos = searchDoctorByID(doctorID.toUpperCase());
            if (pos >= 0) {
                System.out.println("Doctor ID already exists!");
            }
        } while (pos >= 0);
        name = MyTools.readNonBlank("Name of new Doctor");
        sex = MyTools.validSex("Gender of new Doctor [male/femal] -> ");
        departmentID = validDepartmentID("Department of new Doctor");
        address = MyTools.readNonBlank("Address of new Doctor");
        createDate = MyTools.toString(inputDate("Create date"));
        do {
            Date date = inputDate("Lastupdate Date");

            if (date.after(MyTools.toDate(createDate))) {
                lastUpdateDate = MyTools.toString(date);
                cond = false;
            } else {
                System.out.println("Lastupdate Date can not be before create Date(" + createDate + ")");
                cond = true;
            }
        } while (cond);
        this.add(new Doctor(doctorID, name, sex, address, departmentID, createDate, lastUpdateDate));
        System.out.println("New Doctor has just been added!");
        updated = true;
    }

    public void printAllDoctor() {
        if (this.isEmpty()) {
            System.out.print("Empty List");
        } else {
            System.out.println("-----------------------------------------------------------------------------------------------------------");
            System.out.format("%5s %22s %11s %16s %15s %13s %20s", "ID", "NAME", "SEX", "ADDRESS", "DEPARTMENTId", "CREATEDATE", "LASTUPDATEDATE \n");
            System.out.println("-----------------------------------------------------------------------------------------------------------");
            for (Doctor x : this) {
                x.display();
            }
            System.out.println("-----------------------------------------------------------------------------------------------------------");
        }
    }

    public void removeDoctorByID() {
        boolean answ;
        boolean cond = false;
        do {
            String input = MyTools.readNonBlank("Enter ID of Doctor will be removed ");
            int pos = searchDoctorByID(input.toUpperCase());
            if (pos < 0) {
                System.out.println("The patient does not exist!");
                answ = readBool("Do you want to view doctor list");
                if (answ) {
                    printAllDoctor();
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
                    System.out.println("The patient has been removed!");
                    updated = true;
                    break;
                }
            }
        } while (cond);

    }

    public void updateInfomationDoctor() throws IOException {
        boolean answ;
        boolean stay = false;
        String[] ops = {"ID", "name", "sex", "address", "departmentID", "created date", "last updated date"};
        do {
            String input = readNonBlank("Enter the ID of Doctor will be updated");
            int pos = searchDoctorByID(input.toUpperCase());
            if (pos < 0) {
                System.out.println("The Doctor does not exist!");
                answ = readBool("Do you want to view doctor list?");
                if (answ) {
                    printAllDoctor();
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
                    Doctor d = this.get(pos);
                    Menu menuUpdate = new Menu(ops);
                    do {
                        userChoice = menuUpdate.getChoice("Updating information");
                        switch (userChoice) {
                            case 1:
                                String newID;
                                do {
                                    newID = readPattern("New ID of Doctor", DOCTOR_ID_FORMAT);
                                    pos = searchDoctorByID(newID);
                                    if (pos >= 0) {
                                        System.out.println("This ID is already exist");
                                    }

                                } while (pos >= 0);
                                d.setID(newID);
                                break;
                            case 2:
                                String newName = readNonBlank("New name of Doctor");
                                d.setName(newName);
                                break;
                            case 3:
                                boolean newSex = readBool("New gender of Doctor [male/femal] -> ");
                                d.setSex(newSex);
                                break;
                            case 4:
                                String newAddress = readNonBlank("New address of Doctor");
                                d.setAddress(newAddress);
                                break;
                            case 5:
                                String newDepID = validDepartmentID("New Department ID of Doctor");
                                d.setDepartmentId(newDepID);
                                break;
                            case 6:
                                String newCreateDate = MyTools.toString(inputDate("Create date"));
                                d.setCreateDate(newCreateDate);
                                break;
                            case 7:
                                String newLaUpDate = MyTools.toString(inputDate("Last update date"));
                                d.setLastUpdateDate(newLaUpDate);
                                break;
                            default:
                                System.out.println("No matching information");
                        }

                        if (!readBool("Do you want to continue updating this doctor")) {
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

    public void sortByName() {
        Collections.sort(this, new Comparator<Doctor>() {
            @Override
            public int compare(Doctor t, Doctor t1) {
                return t.getName().compareTo(t1.getName());
            }
        });

        System.out.println("----------------------------------------------------------------------------------------------------------");
        System.out.println("After sorting by name:");
        System.out.println("----------------------------------------------------------------------------------------------------------");
        System.out.format("%5s %22s %11s %16s %15s %13s %20s", "ID", "NAME", "SEX", "ADDRESS", "DEPARTMENTId", "CREATEDATE", "LASTUPDATEDATE \n");
        for (Doctor x : this) {
            x.display();
        }
        System.out.println("----------------------------------------------------------------------------------------------------------");
    }

    public void filterDoctorByDepartmentID() throws IOException {
        String input = validDepartmentID("Enter Department ID to be sorted");
        System.out.println("----------------------------------------------------------------------------------------------------------");
        System.out.format("%5s %22s %11s %16s %15s %13s %20s", "id", "name", "sex", "address", "departmentId", "createDate", "lastUpdateDate \n");
        System.out.println("----------------------------------------------------------------------------------------------------------");
        for (Doctor x : this) {
            if (x.getDepartmentId().equals(input)) {
                x.display();
            }
        }
        System.out.println("----------------------------------------------------------------------------------------------------------");
    }
}
