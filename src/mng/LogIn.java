/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mng;

import data.Account;
import data.AccountChecker;
import data.Config;
import data.DepartmentList;
import data.Doctor;
import data.DoctorList;
import data.ExaminationList;
import data.Logger;
import static data.Logger.inputAccount;
import data.PatientList;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Scanner;
import javax.xml.transform.ErrorListener;
import sun.security.tools.PathList;
import validation.MyTools;

/**
 *
 * @author admin
 */
public class LogIn {

    private Account acc = null;
    private Logger user = null;

    public LogIn(Account acc) {
        this.acc = acc;
    }

    public Account getAcc() {
        return acc;
    }

    public static void main(String[] args) throws IOException, ParseException {
        Account acc = null;
        Logger user = null;
        boolean cont = true;
        boolean valid = false;
        String accFile;
        String role = null;
        Config cR = new Config();
        accFile = cR.getAccountFile();

        do {
            System.out.println("---------------  Input your account  ---------------");
            user = inputAccount();
            List<String> lines = MyTools.readLinesFromFile(accFile);
            System.out.println("----------------------------------------------------");
            for (String item : lines) {
                String[] info = item.split(",");
                if (user.getAccName().equals(info[0]) && user.getPwd().equals(info[1])) {
                    role = info[2];
                    cont = false;
                    break;
                }
            }
        } while (cont);

        LogIn loginObj = new LogIn(acc);

        PatientList pList = new PatientList(loginObj);
        DoctorList dList = new DoctorList(loginObj);
        DepartmentList deList = new DepartmentList(loginObj);
        ExaminationList eList = new ExaminationList(loginObj);
        pList.initWithFile();
        dList.initWithFile();
        deList.initWithFile();
        eList.initWithFile();

        if (role.equalsIgnoreCase("User")) {
            String[] ops = {"View patient list", "View doctor list", "View department list", "View examination list", "QUIT"};
            Menu menu = new Menu(ops);

            System.out.println("------------------  Login success  -----------------");
            System.out.println("----------------------------------------------------");

            int choice = 0;
            do {
                choice = menu.getChoice("------------------ Menu for user -------------------");
                switch (choice) {
                    case 1:
                        pList.printAllPatient();
                        break;
                    case 2:
                        dList.printAllDoctor();
                        break;
                    case 3:
                        deList.printAllDepartment();
                        break;
                    case 4:
                        eList.printAllExamination();
                        break;
                    default:
                        if (pList.isUpdated()) {
                            if (MyTools.readBool("Do you want to save to file? ")) {
                                pList.writeObjectToFile();
                            }
                        }
                }
            } while (choice > 0 && choice < menu.size());

        } else if (role.equalsIgnoreCase("Doctor")) {
            String[] Ops = {"Patient", "Doctor", "Department", "Examination", "End Program"};
            Menu menu = new Menu(Ops);

            System.out.println("------------------  Login success  -----------------");
            System.out.println("----------------------------------------------------");
            int choice = 0;
            int userChoice = 0;
            do {
                choice = menu.getChoice("<<<<<<<<<<<<<<<<< Menu for doctor >>>>>>>>>>>>>>>>>");
                switch (choice) {
                    case 1:
                        String[] pOps = {"View patient list", "Add new patient", "Remove patient", "Update information", "Sort by age", "Sort by name", "Exit to Main Menu"};
                        Menu menuP = new Menu(pOps);
                        do {
                            System.out.println("******************");
                            userChoice = menuP.getChoice("Patient Management");
                            switch (userChoice) {
                                case 1:
                                    pList.printAllPatient();
                                    break;
                                case 2:
                                    pList.addPatient();
                                    break;
                                case 3:
                                    pList.removePatientByID();
                                    break;
                                case 4:
                                    pList.updateInfomationPatient();
                                    break;
                                case 5:
                                    pList.sortByAge();
                                    break;
                                case 6:
                                    pList.sortByName();
                                    break;
                                default:
                                    if (pList.isUpdated()) {
                                        if (MyTools.readBool("Do you want to save to file? ")) {
                                            pList.writeObjectToFile();
                                        }
                                    }
                            }
                        } while (userChoice > 0 && userChoice < menuP.size());
                        break;
                    case 2:
                        String[] dOps = {"View doctor list", "Sort by name", "Filter by Department", "Exit to Main Menu"};
                        Menu menuD = new Menu(dOps);
                        do {
                            userChoice = menuD.getChoice("Doctor Management");
                            switch (userChoice) {
                                case 1:
                                    dList.printAllDoctor();
                                    break;
                                case 2:
                                    dList.sortByName();
                                    break;
                                case 3:
                                    dList.filterDoctorByDepartmentID();
                                    break;
                                default:
                                    if (pList.isUpdated()) {
                                        if (MyTools.readBool("Do you want to save to file? ")) {
                                            pList.writeObjectToFile();
                                        }
                                    }
                            }
                        } while (userChoice > 0 && userChoice < menuD.size());
                        break;
                    case 3:
                        String[] deOps = {"View department list", "Sort by Id", "Sort By LastUpdateDate", "Exit to Main Menu"};
                        Menu menuDe = new Menu(deOps);
                        do {
                            userChoice = menuDe.getChoice("Managing Department");
                            switch (userChoice) {
                                case 1:
                                    deList.printAllDepartment();
                                    break;
                                case 2:
                                    deList.sortByID();
                                    break;
                                case 3:
                                    deList.sortByLastUpdateDate();
                                    break;
                                default:
                                    if (deList.isUpdated()) {
                                        if (MyTools.readBool("DO you want to save to file? ")) {
                                            deList.writeObjectToFile();
                                        }
                                    }
                            }
                        } while (userChoice > 0 && userChoice < menuDe.size());
                        break;
                    case 4:
                        String[] eOps = {"View examination list", "Add new examination", "Remove examination", "Update information", "Filter by Doctor Id", "Sort by date", "Exit to Main Menu"};
                        Menu menuE = new Menu(eOps);
                        do {
                            userChoice = menuE.getChoice("Managing Examination");
                            switch (userChoice) {
                                case 1:
                                    eList.printAllExamination();
                                    break;
                                case 2:
                                    eList.addExamination();
                                    break;
                                case 3:
                                    eList.removeExaminationByID();
                                    break;
                                case 4:
                                    eList.updateInfomationExamination();
                                    break;
                                case 5:
                                    eList.filterByDoctorID();
                                    break;
                                case 6:
                                    eList.sortByDate();
                                    break;
                                default:
                                    if (eList.isUpdated()) {
                                        if (MyTools.readBool("DO you want to save to file? ")) {
                                            eList.writeObjectToFile();
                                        }
                                    }
                            }
                        } while (userChoice > 0 && userChoice < menuE.size());
                        break;
                    default:
                        if (dList.isUpdated()) {
                            boolean res = MyTools.readBool("Do you want to save to file? ");
                            if (res) {
                                dList.writeObjectToFile();
                            }
                        }
                }
            } while (choice > 0 && choice < menu.size());
        } else if (role.equalsIgnoreCase("Boss")) {
            String[] Ops = {"Patient", "Doctor", "Department", "Examination", "End Program"};
            Menu menu = new Menu(Ops);
       
            System.out.println("------------------  Login success  -----------------");
            System.out.println("----------------------------------------------------");
            int choice = 0;
            int userChoice = 0; 
            do {
                choice = menu.getChoice("<<<<<<<<<<<<<<<<<< Menu for ADMIN >>>>>>>>>>>>>>>>>>");
                switch (choice) {
                    case 1:
                        String[] pOps = {"View patient list", "Add new patient", "Remove patient", "Update information", "Sort by age", "Sort by name", "Exit to Main Menu"};
                        Menu menuP = new Menu(pOps);
                        do {
                            userChoice = menuP.getChoice("Patient Management");
                            switch (userChoice) {
                                case 1:
                                    pList.printAllPatient();
                                    break;
                                case 2:
                                    pList.addPatient();
                                    break;
                                case 3:
                                    pList.removePatientByID();
                                    break;
                                case 4:
                                    pList.updateInfomationPatient();
                                    break;
                                case 5:
                                    pList.sortByAge();
                                    break;
                                case 6:
                                    pList.sortByName();
                                    break;
                                default:
                                    if (pList.isUpdated()) {
                                        if (MyTools.readBool("Do you want to save to file? ")) {
                                            pList.writeObjectToFile();
                                        }
                                    }
                            }
                        } while (userChoice > 0 && userChoice < menuP.size());
                        break;
                    case 2:
                        String[] dOps = {"View doctor list", "Add new doctor", "Remove doctor", "Update information", "Sort by name", "Filter by Department", "Exit to Main Menu"};
                        Menu menuD = new Menu(dOps);
                        do {
                            userChoice = menuD.getChoice("Doctor Management");
                            switch (userChoice) {
                                case 1:
                                    dList.printAllDoctor();
                                    break;
                                case 2:
                                    dList.addDoctor();
                                    break;
                                case 3:
                                    dList.removeDoctorByID();
                                    break;
                                case 4:
                                    dList.updateInfomationDoctor();
                                    break;
                                case 5:
                                    dList.sortByName();
                                    break;
                                case 6:
                                    dList.filterDoctorByDepartmentID();
                                    break;
                                default:
                                    if (pList.isUpdated()) {
                                        if (MyTools.readBool("Do you want to save to file? ")) {
                                            pList.writeObjectToFile();
                                        }
                                    }
                            }
                        } while (userChoice > 0 && userChoice < menuD.size());
                        break;
                    case 3:
                        String[] deOps = {"View department list", "Add new department", "Remove department", "Update information", "Sort by Id", "Sort By LastUpdateDate", "Exit to Main Menu"};
                        Menu menuDe = new Menu(deOps);
                        do {
                            userChoice = menuDe.getChoice("Managing Department");
                            switch (userChoice) {
                                case 1:
                                    deList.printAllDepartment();
                                    break;
                                case 2:
                                    deList.addDepartment();
                                    break;
                                case 3:
                                    deList.removeDepartmentByID();
                                    break;
                                case 4:
                                    deList.updateInfomationDepartment();
                                    break;
                                case 5:
                                    deList.sortByID();
                                    break;
                                case 6:
                                    deList.sortByLastUpdateDate();
                                    break;
                                default:
                                    if (deList.isUpdated()) {
                                        if (MyTools.readBool("DO you want to save to file? ")) {
                                            deList.writeObjectToFile();
                                        }
                                    }
                            }
                        } while (userChoice > 0 && userChoice < menuDe.size());
                        break;
                    case 4:
                        String[] eOps = {"View examination list", "Add new examination", "Remove examination", "Update information", "Filter by Doctor Id", "Sort by date", "Exit to Main Menu"};
                        Menu menuE = new Menu(eOps);
                        do {
                            userChoice = menuE.getChoice("Managing Examination");
                            switch (userChoice) {
                                case 1:
                                    eList.printAllExamination();
                                    break;
                                case 2:
                                    eList.addExamination();
                                    break;
                                case 3:
                                    eList.removeExaminationByID();
                                    break;
                                case 4:
                                    eList.updateInfomationExamination();
                                    break;
                                case 5:
                                    eList.filterByDoctorID();
                                    break;
                                case 6:
                                    eList.sortByDate();
                                    break;
                                default:
                                    if (eList.isUpdated()) {
                                        if (MyTools.readBool("DO you want to save to file? ")) {
                                            eList.writeObjectToFile();
                                        }
                                    }
                            }
                        } while (userChoice > 0 && userChoice < menuE.size());
                        break;
                    default:
                        if (dList.isUpdated()) {
                            boolean res = MyTools.readBool("Do you want to save to file? ");
                            if (res) {
                                dList.writeObjectToFile();
                            }
                        }
                }
            } while (choice > 0 && choice < menu.size());

        }

    }

}
