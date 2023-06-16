/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mng;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author admin
 */
public class Menu extends ArrayList<String>{

    public Menu() {
        super();
    }
    public Menu(String[] items){
        super();
        for (String item: items) this.add(item);
    }

    public int getChoice(String title){
        System.out.println(title);
        boolean cond = false;
        int choice = 0;
        Scanner sc = new Scanner(System.in);
        do {            
            try {
                for (int i=0;i<this.size();i++) {        
                System.out.println(i+1+"- "+this.get(i));
                }
                System.out.println("____________________________________________________");
                System.out.printf("Select 1..%d: ",this.size());
                choice = Integer.parseInt(sc.nextLine());
                break;
            } catch (Exception e) {
                cond = true;
            }
        } while (cond);
        return choice;
        
        
    }
    
}
