/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

/**
 *
 * @author admin
 */
public class Patient implements Comparable<Patient> {

    private static String SEPARATOR = ",";
    private static String ID_PREFIX = "PAT";
    private static String ID_PATTERN = "PAT\\d{3}";
    private static final int AGE_MIN = 0;
    private static final int AGE_MAX = 120;
    private String ID;
    private String name;
    private int age;
    private String address;

    public Patient() {
    }

    public Patient(String ID, String name, int age, String address) {
        this.ID = ID;
        this.name = name;
        this.age = age;
        this.address = address;
    }

    public Patient(String line) {
        String[] info = line.split(this.SEPARATOR);
        ID = info[0].trim();
        name = info[1].trim();
        age = Integer.parseInt(info[2]);
        address = info[3].trim();
    }

    public String getID() {
        return ID;
    } 

    public void setID(String ID) {
        this.ID = ID;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return ID + SEPARATOR + name + SEPARATOR + age + SEPARATOR + address + "\n";
    }

    public void display() {
        System.out.format("%9s %25s %7s %20s \n", getID(), getName(), getAge(), getAddress());
    }

    @Override
    public int compareTo(Patient o) {
        return this.getID().compareTo(o.getID());
    }

}
