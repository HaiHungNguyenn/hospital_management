/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.util.Scanner;

public class Logger {

    private String accName;
    private String pwd;

    public Logger() {
    }

    public Logger(String accName, String pwd) {
        this.accName = accName;
        this.pwd = pwd;

    }

    public String getAccName() {
        return accName;
    }

    public String getPwd() {
        return pwd;
    }

    public Logger(String lines) {
        String[] info = lines.split(",");
        accName = info[0].trim();
        pwd = info[1].trim();

    }

    public static Logger inputAccount() {

        Scanner sc = new Scanner(System.in);
        System.out.print("input acc name: ");
        String accName = sc.nextLine();
        System.out.print("input password: ");
        String pwd = sc.nextLine();
        return new Logger(accName, pwd);
    }

}
