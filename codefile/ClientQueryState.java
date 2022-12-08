import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Scanner;

public class ClientQueryState {
    private static Warehouse warehouse;
    private static ClientQueryState clientQueryState;
    
    final static String MAINMENU = "" + " CIENT QUERY MENU OPTIONS            \n\t" +
    "a. display all clients \n\t" + "b. list of clients with outstanding balance \n\t" + "c. see list of clients with no transactions in the last six months\n\t" + "d. logout\n\n";
	
  //-----------------displayAllClients()------------------------
    private static void displayAllClients() {
        Iterator it = warehouse.getClients();
        while(it.hasNext())
            System.out.println(it.next().toString()) ;
    }
    //--------displayOutstandingBalances-------------------
    public static void displayOutstandingBalances(){
    	double outstandingBalance = 0.00;
        Iterator it = warehouse.getClients();
        Client currClient = null;
        boolean found = false;
        while(it.hasNext()){
            currClient = (Client) it.next();
            if(currClient.getClientBalance() > outstandingBalance){
                System.out.println(currClient.toString());
                found = true;
            }//end if
        }//end while()
        if(!found)
            System.out.println("No clients with an outstanding balance found");
        if(currClient == null)
            System.out.println("No Outstanding Balances currently");
    }//end displayOutstandingBalances
    
  //--------noTransactionsClients()-------------------
    public static void noTransactionsClients() {
        boolean foundInvoice = false;
        boolean foundPayment = false;
        LocalDateTime CompareDate=LocalDateTime.now().minusMonths(6);

     		
        //--------ClientIterator---------
        Iterator itClient = warehouse.getClients();
        Client currClient = null;
        while(itClient.hasNext()){
        	currClient = (Client) itClient.next();
        	
        	if( currClient.getInvoiceArrayList().isEmpty() && currClient.getPaymentsArrayList().isEmpty()) {
          		System.out.println( "ClientID: " + currClient.getId()+ " ClientName: " + currClient.getName() + " doesnt have any orders");
          		System.out.println( "ClientID: " + currClient.getId()+ " ClientName: " + currClient.getName() + " doesnt have any Payments");
         		foundPayment = true;
          		foundInvoice = true;
          	}else {
        	 
        		//--------InvoiceIterator---------
            	Iterator itInvoice = currClient.getInvoices();
            	Invoice currInvoice = null;
            	while(itInvoice.hasNext() ){
            		currInvoice = (Invoice) itInvoice.next();
            		if(currInvoice.getDate().isBefore(CompareDate)) {
            			System.out.println(" ClientID: " + currClient.getId()+ " ClientName: " + currClient.getName() + " doesnt have orders in last 6 months");
            			foundInvoice = true;
            			}// End if statement
            		}// End InvoiceIterator While Loop
        	 
            	//--------PaymentIterator---------
            	Iterator itPayment = currClient.getPayments();
            	Payment currPayment = null;
            	while(itPayment.hasNext() ){
            		currPayment = (Payment) itPayment.next();
            		if(currPayment.getDate().isBefore(CompareDate)) {
            			System.out.println(" ClientID: " + currClient.getId()+ " ClientName: " + currClient.getName() + " doesnt have Paymants in last 6 months");
            			foundPayment = true;
            				}// End if statement
            			}// End PaymentIterator While Loop
                }// End ClientIterator While Loop
        }
        if(!foundInvoice)
            System.out.println("there are No clients that havent ordered in last 6 months");
        if(!foundInvoice )
            System.out.println("there ane no  clients that havent paid in last 6 months");
        if(currClient == null)
            System.out.println("No clients Found");
            }
        
      
    	
    public static void processInput(Warehouse w){
        warehouse = w;
        Scanner input = new Scanner(System.in);
        String inputStr = "";
        System.out.println(MAINMENU);
        while(!inputStr.equals("exit") && !inputStr.equals("D")&& !inputStr.equals("d") && !inputStr.equals("logout")){
            inputStr = input.next();
            switch(inputStr.toUpperCase()){
            case"D":
            case"EXIT":
                System.out.println("exiting clerk operations\n");
                break;
            
            case"A":
            	displayAllClients();
                break;
                
            case"B":
            	displayOutstandingBalances(); 
            	break;
            
            case"C":
            	noTransactionsClients();//see list of clients with no transactions in the last six months
                break;
               
            default: System.out.print("ERROR" + MAINMENU);
                break;
            }
        }
		
		
	}
}
