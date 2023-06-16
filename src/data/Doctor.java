/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.util.Date;
import validation.MyTools;


public class Doctor implements Comparable<Doctor>{
    private String ID;
    private String name;
    private boolean sex;
    private String address;
    private String departmentId;
    private String createDate;
    private String lastUpdateDate;
    public static final String SEPARATOR = ",";
    public Doctor() {
    }

    public Doctor(String ID, String name, boolean sex, String address, String departmentId, String createDate, String lastUpdateDate) {
        this.ID = ID;
        this.name = name;
        this.sex = sex;
        this.address = address;
        this.departmentId = departmentId;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
    }

    public Doctor (String line){
        String [] info = line.split(this.SEPARATOR);
        ID = info[0].trim();
        name = info[1].trim();
        sex = MyTools.parseBool(info[2]);
        address = info[3].trim();
        departmentId = info[4].trim();
        createDate =info[5].trim() ;
        lastUpdateDate=info[6].trim() ;
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

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
    public void display(){
        System.out.format("%7s %25s %7s %15s %12s %16s %16s \n",getID(),getName(),isSex(),getAddress(),getDepartmentId(),getCreateDate(),getLastUpdateDate());
    }

    @Override
    public String toString() {
        return getID()+SEPARATOR+getName()+SEPARATOR+isSex()+SEPARATOR+getAddress()+SEPARATOR+getDepartmentId()+SEPARATOR+getCreateDate()+SEPARATOR+getLastUpdateDate()+"\n";
    }

    @Override
    public int compareTo(Doctor o) {
        return this.getID().compareTo(o.getID());
    }
    
}
