package CodeFile;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Scanner;




public class ClientShoppingState {
    private static Warehouse warehouse;
    private static ClientShoppingState ClientShoppingState;
    private static ClientMenuState clientMenuState;
    final static String MAINMENU = "" + "CLIENT SHOPPING MENU OPTIONS                \n\t"+
    "a. Add Product To Shopping List\n\t" + "b.Show list of Products ShopingList\n\t" +
    "c. Remove Product in Shopping List\n\t" + "d. Edit shopping cart\n\t" +
    "e. Logout\n\n";
    
    //-------addToWishList--------------
    public static void addToWishList(Client C){
        int productid, clientid, quantity;
        clientid = C.getId();
        boolean adding = true;
        Scanner input;
       
        
        productid = clientMenuState.getProductId();
        if(!warehouse.verifyProduct(productid) ){
            System.out.println("ERROR invalid product id");
        }//end if
        input = new Scanner(System.in);
        System.out.print("Enter a quantity: ");
        Scanner s = new Scanner(System.in);
        quantity = s.nextInt();
        warehouse.addToCart(clientid, productid, quantity);
        System.out.println("Item added to cart.");
        return;
   
    }//end addToWishList
    
  //------------RemoveFromCart--------------------------
 		public static void removeFromWishList(Client c){
 			int productid, clientid;
 			 boolean removing = true;
 	        Scanner input;
 	        productid = clientMenuState.getProductId();
 	        if(!warehouse.verifyProduct(productid) ){
 	            System.out.println("ERROR invalid product id");
 	        }//end if 	      
 	        warehouse.RemoveFromCart(c.getId(), productid);
 	        System.out.println("Item removed from cart.");
 		}//end RemoveFromCart    
    

    //-----modifyWishList----------------
    public static void modifyWishList(Client c){
        Iterator it;
        boolean done, continueModifying = true;
        Scanner s;
        int productId, quantity;
        OrderedProduct currProd;
        
        while(continueModifying){
            it = warehouse.getCart(c.getId());
            displayWishList(c);
            if(!c.getShoppingCart().isEmpty()) {
            System.out.print("Modify cart? (Y|N) ");
            s = new Scanner(System.in);
            if(s.next().charAt(0) == 'Y'){
                System.out.print("Enter the id for the product to modify: ");
                s = new Scanner(System.in); //clear buffer
                productId = s.nextInt();
                it = warehouse.getCart(c.getId());
                done = false;
                while(it.hasNext() && !done){
                    currProd = (OrderedProduct)it.next();
                    if(currProd.getProduct().getProductNumber() == productId){
                    	quantity = -1 ;
                    	while(quantity<=-1) {
                        System.out.print("Enter a new quantity for this product (0 to remove it): ");
                        s = new Scanner(System.in);//clear buffer
                        quantity = s.nextInt();
                        if(quantity > -1){
                            warehouse.modifyCart(c.getId(), productId, quantity);
                            System.out.println("Changes made successfully");
                            done = true;
                        } else
                            System.out.println("Invalid quantity entered; cannot be negative, enter number again");
                    	}
                    }//end if
                }//end while
                if(!done)
                    System.out.println("Given product id not found in cart.");
            } else
                continueModifying = false;
            }else
                continueModifying = false;
            
        }//end while
    }//end modifyCart()
    //-----------displayWishList-------------
    public static void displayWishList(Client C){
        
       
        int id = C.getId();
        Iterator it;
        	if(C.getShoppingCart().isEmpty()) {
        		System.out.println(" Shoping Cart is empty " );
        	}else {
            it = warehouse.getCart(id);
            while(it.hasNext())
                System.out.println( ((OrderedProduct)it.next()).toString() );}
    }//end displaywishlist  
	public static void processInput(Warehouse w, Client c){
        warehouse = w;
        Scanner input = new Scanner(System.in);
        String inputStr = "";
        System.out.println(MAINMENU);
        while(!inputStr.equals("exit") && !inputStr.equals("E")&& !inputStr.equals("e") && !inputStr.equals("logout")){
            inputStr = input.next();
            switch(inputStr.toUpperCase()){
            case"E":
            case"EXIT":
                System.out.println("exiting clerk operations\n");
                break;
            
            case"A":
            	addToWishList(c);
                break;
            case"B":
            	displayWishList(c); 
            	
            	break;    
            case"C":
            	removeFromWishList(c); 
            	break;
            
            case"D":
            	modifyWishList(c);
                break;
               
            default: System.out.print("ERROR" + MAINMENU);
                break;
            }
        }
		
		
	}
}
