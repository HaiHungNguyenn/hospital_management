/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import sun.management.resources.agent;


public class MyTools {
    public static final String DATE_FORMAT ="dd/MM/yyyy";
    public static final String PATIENT_FORMAT="PAT\\d{4}";
    public static final Scanner sc = new Scanner(System.in);
    // check chuoi theo cu phap
    public static boolean validStr(String str, String regEx) {
        return str.matches(regEx);
    }
    // check chuoi ngay theo format
    public static Date parseDate(String dateStr, String dateFormat) {
        SimpleDateFormat dF = (SimpleDateFormat) SimpleDateFormat.getInstance();
        dF.applyPattern(dateFormat);
        try {
            long t = dF.parse(dateStr).getTime();
            return new Date(t);
        } catch (ParseException e) {
            System.out.println(e);
        }
        return null;
    }
    // ep kieu tu string sang bool
    public static boolean parseBool(String boolStr) {
        char c = boolStr.trim().toUpperCase().charAt(0);
        return (c == '1' || c == 'Y' || c == 'T');
    }
    // nhap chuoi sau do bo di khoang trang
    public static String readNonBlank(String message) {
        String input = "";
        do {
            System.out.print(message + ": ");
            input = sc.nextLine().trim();
        } while (input.isEmpty());
        return input;
    }
    // nhap string theo cu phap (pattern)
    public static String readPattern(String message, String pattern) {
        String input = "";
        boolean valid;
        do {
            System.out.print(message + ": ");
            input = sc.nextLine().trim().toUpperCase();
            valid = validStr(input, pattern);
            if (!valid) {
                System.out.println("ID must be in a format "+pattern.substring(0,3)+"xxxx");
                
            }
        } while (!valid);
        return input;
    }
    // nhap gtri boolean
    public static boolean readBool(String message) {
        String input;
        System.out.print(message + "[1/0-Y/N-T/F]: ");
        input = sc.nextLine().trim();
        if (input.isEmpty()) {
            return false;
        }
        char c = Character.toUpperCase(input.charAt(0));
        return (c == '1' || c == 'Y' || c == 'T');
    }
    // doc file "fName"
    public static List<String> readLinesFromFile(String fName) throws FileNotFoundException, IOException {
        List<String> list;
        list = new ArrayList<>();
        File f = new File(fName);
        try {
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            while (true) 
            {            
            String line =br.readLine();
            if (line == null) break;
            list.add(line.trim());  
            }
        br.close();
        fr.close();
        } catch (IOException e) {
            System.out.println(e);
        }
        return list;
    }
    // ghi vao file "fName"
    public static void writeFile(String fName, List list) throws IOException{
        File f = new File(fName);
        try (FileWriter fw = new FileWriter(f)) {
            BufferedWriter bw = new BufferedWriter(fw);
            for (Object object : list) {
                bw.write(object.toString());
            }
            bw.close();
            fw.close();
        }catch(FileNotFoundException e){
            System.out.println(e);
        }
    }
    // nhap tuoi

    public static int validAge(String msg){
       
        
       int age = 0;
       boolean cond = false;
       Scanner sc = new Scanner(System.in);
       do {
            try {
                System.out.print(msg+": ");
                age = Integer.parseInt(sc.nextLine());
                if (age < 0 || age > 120) {System.out.println("please enter age in range [0..120]");cond =true; }
                else{break;}  
            } catch (NumberFormatException e) {
                System.out.println("Age must be a number");
                cond = true;
            }
                  
        } while (cond);
        return age;
        
    }
    // check gender
    public static boolean validSex(String msg){       
        return readBool(msg);         
    }
    // check date
    public static Date toDate(String strDate) throws ParseException {
        if (strDate == null) {
            return null;
        }
        SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);
        df.setLenient(false);
        return df.parse(strDate);
    }
    
    public static Date inputDate(String message) {
        Scanner sc = new Scanner(System.in);
        Date date = null;
        Date now = new Date();
        do {
            System.out.print(message + "(" + DATE_FORMAT + "): ");
            try {
                date = toDate(sc.nextLine());
                if (date.after(now)) {
                    System.out.println("Can not after today ("+toString(now)+")");                    
                }
            } catch (ParseException ex) {
                System.out.println("Invalid date! Try again");
            } 
            
        } while (date == null || date.after(now));
        return date;
    }
    
     public static String toString(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);
        return df.format(date);
    }
    
    public static void getToday() throws ParseException{
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        System.out.println(formatter.format(date));
    } 

}

