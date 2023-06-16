/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

public class Department implements Comparable<Department> {

    public static final String SEPARATOR = ",";
    private String id;
    private String name;
    private String createDate;
    private String lastUpdateDate;

    public Department() {
    }

    public Department(String id, String name, String createDate, String lastUpdateDate) {
        this.id = id;
        this.name = name;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
    }

    public Department(String lines) {
        String[] info = lines.split(SEPARATOR);
        id = info[0].trim();
        name = info[1].trim();
        createDate = info[2].trim();
        lastUpdateDate = info[3].trim();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return getId() + SEPARATOR + getName() + SEPARATOR + getCreateDate() + SEPARATOR + getLastUpdateDate() + "\n";
    }

    public void display() {
        System.out.format("%9s %25s %12s %14s \n", getId(), getName(), getCreateDate(), getLastUpdateDate());
    }

    @Override
    public int compareTo(Department o) {
        return this.getId().compareTo(o.getId());
    }
}
