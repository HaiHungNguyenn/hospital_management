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
import java.util.logging.Level;
import java.util.logging.Logger;
import mng.LogIn;
import mng.Menu;
import validation.MyTools;
import static validation.MyTools.inputDate;
import static validation.MyTools.readBool;
import static validation.MyTools.readNonBlank;
import static validation.MyTools.writeFile;

public class DepartmentList extends ArrayList<Department> {

    LogIn logObj = null;
    protected String file = "";
    protected boolean updated = false;
    public static final String DEPARTMENT_ID_FORMAT = "DEP\\d{4}";

    public DepartmentList(LogIn loginObj) {
        this.logObj = loginObj;
    }

    public void loadDepartmentFromFile() throws IOException {
        List<String> list = MyTools.readLinesFromFile(file);
        for (int i = 0; i < list.size(); i++) {
            this.add(new Department(list.get(i)));
        }
    }

    public void initWithFile() throws IOException {
        Config con = new Config();
        file = con.getDepartmentFile();
        loadDepartmentFromFile();
    }

    public int searchDepartmentByID(String ID) {
        for (Department x : this) {
            if (x.getId().equals(ID)) {
                return this.indexOf(x);
            }
        }
        return -1;
    }

    public void printAllDepartment() {
        if (this.isEmpty()) {
            System.out.println("Empty List");
        } else {
            System.out.println("-----------------------------------------------------------------");
            System.out.format("%7s %21s %18s %16s \n", "ID", "NAME", "CREATEDATE", "LASTUPDATEDATE");
            System.out.println("-----------------------------------------------------------------");
            for (Department x : this) {
                x.display();
            }
            System.out.println("-----------------------------------------------------------------");
        }
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

    public void addDepartment() throws ParseException {
        String id;
        String name;
        String createDate;
        String lastUpdateDate = null;
        int pos;
        boolean cond = false;
        do {
            id = MyTools.readPattern("ID of new Department", DEPARTMENT_ID_FORMAT);
            pos = searchDepartmentByID(id.toUpperCase());
            if (pos >= 0) {
                System.out.println("Doctor ID alreadu exist");
            }
        } while (pos >= 0);
        name = MyTools.readNonBlank("Name of new Department");
        createDate = MyTools.toString(MyTools.inputDate("Create date"));
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
        this.add(new Department(id, name, createDate, lastUpdateDate));
        System.out.println("New Department has just been added");
        updated = true;
    }

    public void updateInfomationDepartment() {
        boolean answ;
        boolean stay = false;
        String[] ops = {"id", "name", "create date", "last updated date"};
        do {
            String input = MyTools.readNonBlank("Enter the ID of Department will be updated");
            int pos = searchDepartmentByID(input.toUpperCase());
            if (pos < 0) {
                System.out.println("The Department does not exist!");
                answ = readBool("Do you want to view department list?");
                if (answ) {
                    printAllDepartment();
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
                    Department d = this.get(pos);
                    Menu menuUpdate = new Menu(ops);
                    do {
                        userChoice = menuUpdate.getChoice("Updating information");
                        switch (userChoice) {
                            case 1:
                                String newID;
                                do {
                                    newID = MyTools.readPattern("New ID of Department", DEPARTMENT_ID_FORMAT);
                                    pos = searchDepartmentByID(newID);
                                    if (pos >= 0) {
                                        System.out.println("This ID is already exist");
                                    }

                                } while (pos >= 0);
                                d.setId(newID);
                                break;
                            case 2:
                                String newName = MyTools.readNonBlank("New name of Department");
                                d.setName(newName);
                                break;
                            case 3:
                                String newCreateDate = MyTools.toString(inputDate("Create date"));
                                d.setCreateDate(newCreateDate);
                                break;
                            case 4:
                                String newLaUpDate = MyTools.toString(inputDate("Last update date"));
                                d.setLastUpdateDate(newLaUpDate);
                                break;
                            default:
                                System.out.println("No matching information");
                        }
                        if (!readBool("Do you want to continue updating this department")) {
                            break;
                        }
                    } while (userChoice > 0 && userChoice < menuUpdate.size());
                }
            }
        } while (stay);
    }

    public void removeDepartmentByID() {
        boolean answ;
        boolean cond = false;
        do {
            String input = readNonBlank("Enter the ID of department will be removed ");
            int pos = searchDepartmentByID(input.toUpperCase());
            if (pos < 0) {
                System.out.println("The Department does not exist!");
                answ = readBool("Do you want to view department list?");
                if (answ) {
                    this.printAllDepartment();
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
                    System.out.println("The department has been removed!");
                    updated = true;
                    break;
                }
            }
        } while (cond);

    }

    public void sortByID() {
        Collections.sort(this, new Comparator<Department>() {
            @Override
            public int compare(Department t, Department t1) {
                return t.getId().compareTo(t1.getId());
            }
        });
        System.out.println("-----------------------------------------------------------------");
        System.out.println("After sorting by ID");
        System.out.println("-----------------------------------------------------------------");
        System.out.format("%7s %21s %18s %16s \n", "ID", "NAME", "CREATEDATE", "LASTUPDATEDATE");
        System.out.println("-----------------------------------------------------------------");
        for (Department x : this) {
            x.display();
        }
        System.out.println("-----------------------------------------------------------------");
    }

    public void sortByLastUpdateDate() {
        Collections.sort(this, new Comparator<Department>() {
            @Override
            public int compare(Department t, Department t1) {
                try {
                    return MyTools.toDate(t.getLastUpdateDate()).compareTo(MyTools.toDate(t1.getLastUpdateDate()));
                } catch (ParseException ex) {
                    Logger.getLogger(DepartmentList.class.getName()).log(Level.SEVERE, null, ex);
                }
                return 0;
            }
        });
        System.out.println("-----------------------------------------------------------------");
        System.out.println("After sorting by LastUpdateDate");
        System.out.println("-----------------------------------------------------------------");
        System.out.format("%7s %21s %18s %16s \n", "ID", "NAME", "CREATEDATE", "LASTUPDATEDATE");
        System.out.println("-----------------------------------------------------------------");
        for (Department x : this) {
            x.display();
        }
        System.out.println("-----------------------------------------------------------------");
    }

}
