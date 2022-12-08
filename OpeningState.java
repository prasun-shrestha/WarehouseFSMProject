/*
 OPENING STATE
 
 
 */


import java.util.*;
import java.io.*;
import java.lang.*;

public class OpeningState {

        final static String MAINMENU = "" + "Select option   \n\t" +
            "1. Client Menu\n\t" + "2. Clerk Menu\n\t" +
            "3. Manager Menu\n\t" + "Q. Quit the program\n\n";

        final static String FILENAME = "WareData";

//------------saveChanges---------------------
// Saves changes made to warehosue
//-------------------------------------------
    public static void saveChanges(String file, Warehouse warehouse) {
        if(warehouse.saveData(file))
            System.out.println("Saved Successfully");
        else
            System.out.println("Save Failed. ERROR");
    } //end saveChanges
    
//-----------openWarehouse-------------------
    private static Warehouse openWarehouse(String file){
        Warehouse w;
       
            w = Warehouse.retrieveData(file);
            if(w == null){
                System.out.println("Empty File. Creating New Warehouse");
                w = Warehouse.instance();
            } else
                System.out.println("Warehouse Successfully opened");

                return w;

    }//end openWarehouse

    
    
    
    
//-------------MAIN-----------------------------
    public static void main (String args[]){
    	Warehouse warehouse;
    	 Scanner input = new Scanner(System.in);
         System.out.println("Open saved warehouse?(Y|N)");       //opening warehouse
         String opt = input.next();
         if(opt.equals("Y"))
        	 warehouse = openWarehouse(FILENAME);
         else{
             warehouse = Warehouse.instance();
             System.out.println("New Warehouse created");
         }//end else
       
        Scanner s = new Scanner(System.in);
        boolean notDone = true;
        while (notDone) {
            System.out.println(MAINMENU);
            String choice = s.nextLine();
            switch(choice) {
                    case"1":
                    ClientMenuState.processInput(warehouse);
                    break;
                    case"2":
                    ClerkMenuState.processInput(warehouse);
                    break;
                    case"3":
                    ManagerMenuState.performMenu(warehouse);
                    break;
                    case "exit":
                    case"q":
                    case"Q":
                    notDone = false;
                    break;
                default:
                    System.out.println("ERROR");
            }//end switch
        }//end while
        saveChanges(FILENAME, warehouse);
    }//end main
    
    
    
    }
