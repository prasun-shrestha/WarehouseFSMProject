/*
CLIENT MENU STATE
*/


import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;


public class ClientMenuState{

	private static Warehouse warehouse;
    private static ClerkMenuState clerkMenuState;
    
    final static String MAINMENU = "" + "CLIENT MENU OPTIONS                \n\t"+
    "a. Show Client Detail\n\t" + "b.Show list of Products\n\t" +
    "c. Show Client Transactions\n\t" + "d. Edit client shopping cart(open seperate menu)\n\t" +
    "e. Display clients waitlist\n\t" + "f Place an order\n\t" +
    "g. Logout\n\n"; 
    
//-------getProductID----------------------
    public static int getProductId(){
        System.out.println("Enter Product ID: ");
        Scanner s = new Scanner(System.in);
        return s.nextInt();
    } //end getProductID
    
//------displayClientDetails---------------
    private static void displayClientDetails(Client c){
        try {
        	System.out.println(warehouse.findClient(c.getId()).toString());
        }
        catch (Exception e) { System.out.println("ERROR" + e); }
    }//end displayClients
    
//----displayProductList----------------
    private static void displayProductList() {
        try {
            Iterator iterator = warehouse.getProducts();
            while(iterator.hasNext())
                System.out.println(iterator.next().toString());
        }
        catch(Exception e ) { System.out.println("ERROR" + e);
        }
    } //end display Products
    
//-------displayClientTransactions-------------------
    private static void displayClientTransactions(Client c){
        int clientId = c.getId();
        Iterator paymentIt, invoiceIt;
        if(!warehouse.verifyClient(clientId)){
            System.out.println("ERROR invalid client id ");
            return;
        }//end if
        invoiceIt = warehouse.getInvoice(clientId);
        paymentIt = warehouse.getPayment(clientId);
        System.out.println("INVOICES\n" +
                           "_____________________");
        while(invoiceIt.hasNext() )
            System.out.println(((Invoice)(invoiceIt.next())).itemListString());
        System.out.println("\nPAYMENTS\n" +
                           "_____________________");
        while(paymentIt.hasNext() )
            System.out.println( ((Payment)(paymentIt.next())).toString() );       
        } //end displayTransaction
    
//-------ShowWaitList------------
    
    private static void showWaitList(Client c) {
        try {
        	int clientID = c.getId();
            Iterator itClient;
            Iterator itOrder;
            Iterator itProduct;
            Iterator itWait;
            itProduct = warehouse.getProducts();
            while(itProduct.hasNext() ) {
            Product currProduct = (Product)itProduct.next();
            itWait = warehouse.getWaitList(currProduct.getProductNumber());
            while(itWait.hasNext()) {
            WaitListProduct currWait = (WaitListProduct)itProduct.next();
             Order tempOrder = currWait.getOrder();
             Client tempClient = tempOrder.getClient();
            if(tempClient.getId()==c.getId())
            	System.out.println(currWait.toString());
            }
            }
            
        
        }//end try
        catch(Exception e) { System.out.println("ERROR " + e ); }
    }//end showWaitList
    

//------EditShoppingCart------------
    private static void Shoppingcart() {
        Iterator it = warehouse.getProducts();
        while(it.hasNext())
            System.out.println(it.next().toString());
    }
    
    private static void displayShoppingCart(Client C) {
        int id = C.getId();
        Iterator it;
        if(warehouse.verifyClient(id)){
            it = warehouse.getCart(id);
            while(it.hasNext())
                System.out.println( ((OrderedProduct)it.next()).toString() );
        }//end if
        else
            System.out.println("ERROR Invalid id given");
    }//end displaywishlist
            
    

    
    //--------placeOrder---------------
    public static void placeOrder(Client c){
        int clientId = c.getId();
        if(!warehouse.verifyClient(clientId) ){
            System.out.println("ERROR invalid client id");
            return;
        }//end if
        warehouse.placeOrder(clientId);
        System.out.println("Order placed successfully");
    }//end placeOrder
    
    //-------getClientID-------------------
    public static int getClientId(){
        System.out.print("Please enter a client id: ");
        Scanner s = new Scanner(System.in);
        return s.nextInt();
    }//end getClientId()
    //---open client shopping state-----
    private static void callClientShoppingState(Warehouse w,Client c) {
    	ClientShoppingState.processInput(w, c);
        System.out.println(MAINMENU);
    }
    public static void displayWaitList(Client c) {
    	try {
    		
    		 Iterator itProduct=warehouse.getProducts();
    		 Product currProduct=null;
    		 while(itProduct.hasNext()) 			 
    		 {
    			 currProduct= (Product) itProduct.next();
    			 int productID = currProduct.getProductNumber();
    		 Iterator it;
    		 it = warehouse.getWaitList(productID);
    		 while(it.hasNext()) {
    		 WaitListProduct curritem = (WaitListProduct) it.next();
                 
                	 if(c.getId()==curritem.getOrder().getClient().getId())
                		 System.out.println(curritem.toString());
    		 		}
             	} 
    		       
          
        }//end try
        catch(Exception e) { System.out.println("ERROR " + e ); }
    }
    
    public static void processInput(Warehouse w){
    	warehouse = w;
    	 Client c = warehouse.findClient(getClientId());
         if(c == null)
             System.out.println("ERROR No client found with given id");
         else {
        Scanner input = new Scanner(System.in);
        String inputStr = "";
        System.out.println(MAINMENU);
        while (!inputStr.equals("exit") && !inputStr.equals("g")&& !inputStr.equals("G")){
            inputStr = input.next();
            
            switch(inputStr.toUpperCase()){
                case"EXIT":
                    System.out.println("Exiting");
                    break;
                case "A":
                    displayClientDetails(c);
                    break;
                case "B":
                	displayProductList();
                    break;
                case"C":
                    displayClientTransactions(c);
                    break;
                case "D":
                	callClientShoppingState( w, c);
                    break;                    
                case"E":
                	displayWaitList(c);
                    break;
                case"F":
                	placeOrder(c);
                    break;
                case"G":
                    System.out.println("logging out");
                    break;    
                default:
                    System.out.println("ERROR" + MAINMENU);
                    break;
            } //end switch
        }//end while
      }//end else
    }//end processInput
} //end Client menu state



        
        
    

