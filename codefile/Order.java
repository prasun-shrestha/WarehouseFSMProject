/******************************************************************************
	Order Class
        Individual Order made by Client
*******************************************************************************/

import java.util.*;
import java.io.*;
import java.lang.*;

public class Order implements Serializable{
	private static final long serialVersionUID = 1L;
    
	private int orderNumber;
    private Client clientAccount;
	private ItemList itemsOrdered;

    
 //-------Constructor-----------------------
	public Order(Client c){
		clientAccount = c;
		orderNumber = (OrderIdServer.instance() ).getId();
		itemsOrdered = new ItemList();
		fillOrder();
		processOrder();
	}//end constructor
 
    
 //----------Accessors--------------
    public int getId(){
        return orderNumber;  }
    
    public Client getClient(){
        return clientAccount;  }
    
 //-----------getItemList-----------------
 // Get list of items Ordered
 //-------------------------------------
    public Iterator getItemList(){
        return itemsOrdered.getIterator();  }

//-------------getItemQuantity-----------------
// Get Quantity of an Ordered product
//--------------------------------------------
    public int getItemQuantity(Product p){
        Iterator<OrderedProduct> it = itemsOrdered.getIterator();
        OrderedProduct curr;
        boolean done = false;
        while(it.hasNext() && !done){
            curr = it.next();
            if(curr.getProduct() == p)
                return curr.getQuantity();
        }//end while
        return 0;	//If not found quantity is 0
    }//end getItemQuantity
    
//--------------Add Item-----------------
// Add product to itemOrdered
//------------------------------------
	public void addItem(Product p, int quantity){
		itemsOrdered.addItem(p, quantity);  }//end addItem

//-----------Remove Item-----------------
// Remove product from itemOrdered
//----------------------------------------
	public void removeItem(Product p){
		itemsOrdered.removeItem(p);
	}//end removeItem

//-----------Modify Item Quantity-------------
	public void modifyItemQuantity(Product p, int quantity){
		itemsOrdered.changeQuantity(p, quantity);
	}//end modifyItemQuantity

//----------Fill Order-----------------
//Add all items from client's wishlist to order
//------------------------------------
	private void fillOrder(){
		Iterator it = clientAccount.getCart();
		while(it.hasNext()){
			itemsOrdered.addItem((OrderedProduct)it.next());
		}//end while
	}//end fillOrder

//----------toString()----------------
	public String toString(){
		return "Order Number: " + orderNumber + " Client Number: " + clientAccount.getId()
					+ "\n" + itemsOrdered.toString();
	}//end toString


//----------------Process Order-----------------------------
// Goes through every item in the Order. Items that are
// in stock will be added to the Invoice. Items that are
// not in stock will be added to the Waitlist. The invoice
// is calculated and assigned to a Client.
// Clients wishlist is cleared.
//--------------------------------------------------------
	public void processOrder(){
		clientAccount.clearCart(); //Clear client's cart
		Iterator orderIt = itemsOrdered.getIterator(); //Get iterator for all items
		Invoice invoice = new Invoice(clientAccount); //Create the invoice
		OrderedProduct currItem;
        
		while(orderIt.hasNext() ){
			currItem = (OrderedProduct) orderIt.next(); //Get next item in the list
			System.out.println(currItem.toString());
	        System.out.println("If you wish to place the order for the item type |Y| ");
	        System.out.println("If you dont want to place an order for the item type |N| ");
	        Scanner input = new Scanner(System.in);
	        String opt = input.next();
			if(opt.equals("Y")) {
			
			//Check if we can fulfill it with current stock
			if(currItem.getProduct().getStock() >= currItem.getQuantity() ){
				//Decrement stock, add it to the invoice
				currItem.getProduct().removeStock(currItem.getQuantity() );
				invoice.addItem(currItem);
			} else{	//Not enough in stock, add it to the wait list
				invoice.addItem(currItem);
				currItem.getProduct().waitListProduct(this, currItem.getQuantity()-currItem.getProduct().getStock());
				System.out.println("Waitlisting item \"" + currItem.getProduct().getData() + "\" due to insufficient inventory.");
				System.out.println(currItem.getProduct().getStock() + " Quantity of " + currItem.getProduct().getName() + " is shipped ");
				System.out.println((currItem.getQuantity()-currItem.getProduct().getStock()) + " Quantity of " + currItem.getProduct().getName() + "is added to wait list");
				currItem.getProduct().removeStock(currItem.getProduct().getStock() );
				}//end else
			}else{
				 System.out.println("Oder wont be placed for the item.");
	        }
			
		}//end while		
		//Apply the invoice with the items that are currently being fulfilled
		invoice.applyInvoice();
	}//end processOrder

}//end Order Class
