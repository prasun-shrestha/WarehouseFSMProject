/*
CLERK MENU STATE
*/



import java.util.*;
import java.io.*;
import java.lang.*;

public class ClerkMenuState{

    private static Warehouse warehouse;
    private static ClerkMenuState ClerkMenuState;
    final static String FILENAME = "WareData";
    
    final static String MAINMENU = "" + 
    " CLERK MENU OPTIONS            \n\t" +
    "a. Add a Client \n\t" + 
    "b. Show List of Products \n\t" + 
    "c. Client Query Menu\n\t" +
    "d. Accept payment from a client\n\t" + 
    "e. Become a client (Open client menu) \n\t " +
    "F. Display Waitlist for product\n\t" + 
    "h. logout\n\n";
    

  //----------makePayment------------------------ 
 // User provides client ID and payment amount
 // Payment gets made to clients account
 //---------------------------------------------
     public static void makePayment(){
         //Get client id
         int clientId = getClientId();
         if(!warehouse.verifyClient(clientId)){
             System.out.println("Error, invalid client id.");
             return;
         }//end if
        
         System.out.print("Enter a payment amount: ");
         double amount = Double.valueOf(new Scanner(System.in).nextLine());
         if(amount <= 0.0){
             System.out.println("ERROR, payment must be a positive value. ");
             return;
         }//end if
         System.out.println("Please enter a description for this transaction (ie payment method)");
         String description = (new Scanner(System.in).nextLine());
         warehouse.makePayment(clientId, amount, description);
         System.out.println("Payment received successfully");
     }//end makePayment

//-----getClientId-----------------
    public static int getClientId(){
        System.out.print("Enter Client id: ");
        Scanner s = new Scanner(System.in);
        return s.nextInt();
    } //end getClientId()

//-----getProductId--------------
    public static int getProductId() {
        System.out.print("Enter Product Id: ");
        Scanner s = new Scanner(System.in);
        return s.nextInt();
    } //end getProuctId
    
//----openWarehouse---------------
    public static void openWarehouse() {
        Warehouse w = Warehouse.retrieveData(FILENAME);
        if(w==null){
            System.out.println("Creating new warehouse");
            warehouse = Warehouse.instance();
        } else {
            System.out.println("Warehouse read from file");
            warehouse = w;
        } //end else
    } //end open warehouse
    
//-----------------------------------------
    public static void saveChanges() {
        if(warehouse.saveData(FILENAME))
            System.out.println("Saved");
        else
            System.out.println("Failed");
    }
//-----------------------------------------
    public static void logout() {
        saveChanges();
    }
//-----------------------------------------
    public static ClerkMenuState instance() {
        if(ClerkMenuState == null)
            return ClerkMenuState = new ClerkMenuState();
        else
            return ClerkMenuState;
    }
    
//-----------------------------------------
    private static void addClient() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter name for client:");
        String name = input.nextLine();
        System.out.print("Enter address for client:");
        String address = input.nextLine();
        warehouse.addClient(name, address);
        System.out.println("Client added successfully");
    }
//-----------------------------------------
    private static void displayAllProducts() {
        Iterator it = warehouse.getProducts();
        while(it.hasNext())
            System.out.println(it.next().toString());
    }

    
//-----------------------------------------
    private static void displayInvoices() {
        int clientId = getClientId();
        Iterator invoiceIt;
        boolean choice;
        if(!warehouse.verifyClient(clientId)){
            System.out.println("ERROR");
            return;
        } //end if
        System.out.print("Display detailed transaction? Y/N");
        choice = new Scanner(System.in).next().equals("Y");
        invoiceIt = warehouse.getInvoice(clientId);
        if(choice)
            while(invoiceIt.hasNext())
                System.out.println(((Invoice)(invoiceIt.next())).itemListString());
        else
            while(invoiceIt.hasNext())
                System.out.println(((Invoice)(invoiceIt.next())).toString());
        System.out.println("Display invoice in clerk menu testing incomplete" );
    }
    
    
//------------------------------
    private static void showWaitList() {
    	try {
            int productID = getProductId();
            Iterator it;
            if(!warehouse.verifyProduct(productID)) {
                System.out.println("ERROR");
                return;
            } //end if
            
            it = warehouse.getWaitList(productID);
            System.out.println("Product: \n" + warehouse.findProduct(productID).toString());
            if(!it.hasNext())
                System.out.println("Product has no wait list currently");
            else {
                System.out.println("Wait list: \n" + "------------------");
                while(it.hasNext())
                    System.out.println(((WaitListProduct)it.next()).toString());
            } //end else
        }//end try
        catch(Exception e) { System.out.println("ERROR " + e ); }
    }



    private static void callClient() {
        ClientMenuState.processInput(warehouse);
        System.out.println(MAINMENU);
    }
    private static void callClientQuirie() {
    	ClientQueryState.processInput(warehouse);
        System.out.println(MAINMENU);
    }
    public static void processInput(Warehouse w){
        warehouse = w;
        Scanner input = new Scanner(System.in);
        String inputStr = "";
        System.out.println(MAINMENU);
        while(!inputStr.equals("exit") && !inputStr.equals("H")&& !inputStr.equals("h") && !inputStr.equals("logout")){
            inputStr = input.next();
            switch(inputStr.toUpperCase()){
            case "H":
            case"EXIT":
                System.out.println("exiting clerk operations\n");
                break;
            
            case"A":
                addClient();
                break;
                
            case"B":
                displayAllProducts();
                break;
            
            case"C":
            	callClientQuirie();
                break;
            
            case"D":
            	makePayment();
                break;
                
            case"E":
            	callClient();
                break;
            
            case"F":
            	showWaitList();
                break;
                
                
            default: System.out.print("ERROR" + MAINMENU);
                break;
            }
        }
    }
}




