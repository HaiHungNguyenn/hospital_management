/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import mng.LogIn;
import mng.Menu;
import validation.MyTools;
import static validation.MyTools.readBool;
import static validation.MyTools.readNonBlank;
import static validation.MyTools.readPattern;
import static validation.MyTools.validAge;
import static validation.MyTools.writeFile;

public class PatientList extends ArrayList<Patient> {

    LogIn loginObj = null;
    protected String file = "";
    protected boolean updated = false;
    public static final String PATIENT_ID_FORMAT = "PAT\\d{4}";

    public PatientList(LogIn loginObj) {
        this.loginObj = loginObj;
    }

    public void loadPatientFromFile() throws IOException {
        List<String> list = MyTools.readLinesFromFile(file);
        for (int i = 0; i < list.size(); i++) {
            this.add(new Patient(list.get(i)));
        }
    }

    public void initWithFile() throws IOException {
        Config con = new Config();
        file = con.getPatientFile();
        loadPatientFromFile();
    }

    public int searchPatientByID(String ID) {
        int n = this.size();
        for (int i = 0; i < n; i++) {
            if (this.get(i).getID().equals(ID.toUpperCase())) {
                return i;
            }
        }
        return -1;
    }

    public int searchPatientByName(String name) {
        for (Patient x : this) {
            if (x.getName().equals(name)) {
                return this.indexOf(x);
            }
        }
        return -1;
    }

    public void addPatient() {
        String PaID;
        String name;
        int age;
        String address;
        int pos = 0;
        do {
            PaID = MyTools.readPattern("ID of new Patient", PATIENT_ID_FORMAT);
            pos = searchPatientByID(PaID.toUpperCase());
            if (pos >= 0) {
                System.out.println("Patient ID already exist!");
            }
        } while (pos >= 0);
        name = MyTools.readNonBlank("Name of new Patient");
        age = validAge("Age of new Patient");
        address = readNonBlank("Address of new Patient");
        this.add(new Patient(PaID, name, age, address));
        System.out.println("New Patient has just been added");
        updated = true;
    }

    public void printAllPatient() {
        if (this.isEmpty()) {
            System.out.println("Empty List!");
        } else {

            System.out.println("-------------------------------------------------------------------");
            System.out.format("%7s %22s %13s %17s \n", "ID", "NAME", "AGE", "ADDRESS");
            System.out.println("-------------------------------------------------------------------");
            for (Patient x : this) {
                x.display();
            }
            System.out.println("-------------------------------------------------------------------");
        }
    }

    public void removePatientByID() {
        boolean answ;
        boolean cond = false;
        do {
            String input = readNonBlank("Enter the ID of patient will be removed ");
            int pos = searchPatientByID(input.toUpperCase());
            if (pos < 0) {
                System.out.println("The Patient does not exist!");
                answ = readBool("Do you want to view patient list?");
                if (answ) {
                    this.printAllPatient();
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

    public void updateInfomationPatient() {
        boolean answ;
        boolean stay = false;
        String[] ops = {"ID", "name", "age", "address"};
        do {
            String input = readNonBlank("Enter the ID of patient will be updated");
            int pos = searchPatientByID(input.toUpperCase());
            if (pos < 0) {
                System.out.println("The Patient does not exist!");
                answ = readBool("Do you want to view patient list?");
                if (answ) {
                    this.printAllPatient();
                }
                answ = readBool("Do you want to update again?");
                if (answ) {
                    stay = true;
                }
            } else {
                System.out.print(this.get(pos).toString());
                answ = readBool("Are you sure to update?");
                if (answ) {
                    Patient p = this.get(pos);
                    boolean cond = false;
                    int choice = 0;
                    Menu menu = new Menu(ops);
                    do {
                        choice = menu.getChoice("Information update");
                        switch (choice) {
                            case 1:
                                String newPaID;
                                do {
                                    newPaID = readPattern("New ID of patient", PATIENT_ID_FORMAT);
                                    pos = searchPatientByID(newPaID);
                                    if (pos >= 0) {
                                        System.out.println("This ID is already exist");
                                    }
                                } while (pos >= 0);
                                p.setID(newPaID);
                                break;
                            case 2:
                                p.setName(readNonBlank("New name of patient"));
                                break;
                            case 3:
                                p.setAge(validAge("New age of patient"));
                                break;
                            case 4:
                                p.setAddress(readNonBlank("New address of patient"));
                                break;
                            default:
                                System.out.println("No matching information");
                        }
                        if (!readBool("Do you want to continue updating this patient")) {
                            break;
                        }
                    } while (choice > 0 && choice < menu.size());

                }
                answ = readBool("Do you want to continue updating other patient");
                if (answ) {
                    stay = true;
                } else {
                    break;
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

    public void sortByAge() {
        Collections.sort(this, new Comparator<Patient>() {
            @Override
            public int compare(Patient t, Patient t1) {
                return t.getAge() - t1.getAge();
            }
        });
        System.out.println("-------------------------------------------------------------------");
        System.out.println("After sorting by age:");
        System.out.println("-------------------------------------------------------------------");
        System.out.format("%7s %22s %13s %17s \n", "ID", "NAME", "AGE", "ADDRESS");

        for (Patient x : this) {
            x.display();
        }
        System.out.println("-------------------------------------------------------------------");
    }

    public void sortByName() {
        Collections.sort(this, new Comparator<Patient>() {
            @Override
            public int compare(Patient t, Patient t1) {
                return t.getName().compareTo(t1.getName());
            }
        });
        System.out.println("-------------------------------------------------------------------");
        System.out.println("After sorting by name:");
        System.out.println("-------------------------------------------------------------------");
        System.out.format("%7s %22s %13s %17s \n", "ID", "NAME", "AGE", "ADDRESS");
        for (Patient x : this) {
            x.display();
        }
        System.out.println("-------------------------------------------------------------------");
    }
    
}
