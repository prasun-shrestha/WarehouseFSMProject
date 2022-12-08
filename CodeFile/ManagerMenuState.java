package CodeFile;
/*
MANAGER MENU STATE
*/

import java.util.*;
import java.io.*;
import java.lang.*;


public class ManagerMenuState{

private final static String MENUOPTIONS = " MANAGER MENU  \n" + "-------------------\n" +
            "a. Add a product\n" + "b. receive shipment\n" + "c. become salesclerk(Opens Clark menu)\n" +
            "D. Log Out)";

    private static Warehouse warehouse;
    private static ManagerMenuState ManagerMenuState;

    public static void performMenu(Warehouse w) {
        boolean getNextOption = true;
        Scanner s = new Scanner(System.in);
        warehouse = w;
        char choice;
        while(getNextOption) {
            System.out.println(MENUOPTIONS);
            choice = s.nextLine().charAt(0);
            getNextOption = processUserChoice(choice);
        }
    }


    private static boolean processUserChoice (char choice) {
        boolean iterateAgain = true;
        switch(Character.toLowerCase(choice)){
            case'a':
            addProduct();
            break;
            
            case'b':
            ProcessShipment();
            break;
            
            case'c':
            loginAsClerk();
            break;
            case 'd':
            iterateAgain = false;
            break;
        }
        return iterateAgain;
    }

    private static void addProduct() {
        boolean adding = true;
        Scanner input;
        while(adding) {
            input = new Scanner(System.in);
            System.out.print("Enter name: ");
            String name = input.nextLine();
            System.out.print("Enter price: ");
            String salePrice = input.nextLine();
            System.out.println("Enter stock: ");
            int stock = input.nextInt();
            warehouse.addProduct(name,Double.valueOf(salePrice), stock);
            System.out.println("Product added.");
            System.out.print("Add another product? Y/N : ");
            input = new Scanner(System.in);
            adding = (input.next().charAt(0) == 'Y');
        }
    }
    

    private static void loginAsClerk(){
        ClerkMenuState.processInput(warehouse);
    }
    

    public static ManagerMenuState instance() {
        if(ManagerMenuState == null)
            return ManagerMenuState = new ManagerMenuState();
        else
            return ManagerMenuState;
    }
    //-----------ProcessShipment()--------------------------
    public static void ProcessShipment() {
            System.out.print("Enter Product id: ");
            Scanner s = new Scanner(System.in);
            Product p = warehouse.findProduct(s.nextInt() );
            if(p == null)
                System.out.println("No Product found with given id");
            else {
            	System.out.print("Enter Product QUANTITY: ");
                s = new Scanner(System.in);
            	warehouse.ProcessShipment(p, s.nextInt() );
            }
    }

}










