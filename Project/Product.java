/**************************************************************************
Product Class 
 Create product object. Each product has a description, id, Purchase Price,
 Sale Price, Supplier, and amount in stock.
***************************************************************************/

import java.util.*;

import javax.swing.JOptionPane;

import java.lang.*;
import java.io.*;

public class Product implements Serializable {
  private static final long serialVersionUID = 1L;
  
  private String name;
  private int id;
  private int inStock;
  private double Price;
  private List<WaitListProduct> waitList = new ArrayList<WaitListProduct>();

//----------Contructor-------------------
  public Product(){
    id = ProductIdServer.instance().getId();
    name = "";
    Price = 0;
    inStock = 0;
  }

//---------accessors------------------
  public int getProductNumber(){
    return id;   }

  public String getName(){
    return name;  }
  public double getPrice(){
    return Price;     }

  public int getStock(){
	return inStock;  }
  
  public String getData(){
    String data = "";
    data += name;
    data += "\n\tId: " + id;
    data += "\n\t Price:     ";
    data += Price;
    data += "\n\tStock:          ";
    data += inStock;
    data += "\n\tNumber of waitlisted orders: ";
    data += waitList.size();
    return data;
    }
    
    
//-----------mutators----------------
  public void setName(String input){
    name = input;  }
    
  public void setPrice(double input){
    Price = input;     }

    
  public void setData(String n, double p, int st){
    name            =    n;
    Price   =   p;
    inStock 		=   st;
    }
    
    
//-------------removeStock------------
// Remove specified amount of stock
//------------------------------------
  public void removeStock(int r){
	inStock -= r;
  }//end removeStock
  
//-----------AddItem-----------------
// Add Item to Stock from Shipment
//-----------------------------------
  public void addStock(int quantity){
	  inStock += quantity;
  }//end addShippedItem
  
  
//--------WaitListItem--------------
  public void waitListProduct(Order o, int quantity){
	waitList.add(new WaitListProduct(o, quantity, this));
  }//end waitListItem

    
//------WaitList Iterator-----------------
  public Iterator getWaitList(){
	  return waitList.iterator();	  
  }//end getWaitList
  
//-----------FulfillWaitlist---------------------
// Process item from waitlist then remove it
//-------------------------------------------------
  public void finishWaitListProduct(WaitListProduct item){
	  waitList.remove(item);
  }//end finishWaitlist
    
//-------------toString()----------------
  public String toString(){
     return getData();  }
  //------------------ProcessShipment-----------
  public void ProcessShipment( int Quantity) {
	  Iterator it = waitList.iterator();
	  WaitListProduct currIt= null;
	  int Count = -1;
	  int RemoveCount = -1;
	  String message = "";
	  while (it.hasNext()) {
		  currIt=(WaitListProduct) it.next();
		  Count++;
		if (Quantity >= currIt.getQuantity()) {
			Quantity-=currIt.getQuantity();
			message= message + "\n\t" + " Shipment for " + currIt.getOrder().getClient().getName();
			message= message + "\n\t" +" item name: " + name + " Quantity " + currIt.getQuantity() + " Was Shipped to " + currIt.getOrder().getClient().getName();
			message= message + "\n\t" +"-------------------------------------------------------------------";
    		it.remove();
		}
		else if (Quantity < currIt.getQuantity() && Quantity!=0){
			currIt.setQuantity(currIt.getQuantity()- Quantity) ;
			message= message + "\n\t" + " Shipment for " + currIt.getOrder().getClient().getName()+ " Was Partialy filled";
			message= message + "\n\t" + " item name " + name + " Quantity " + Quantity +" Was Shipped to " + currIt.getOrder().getClient().getName();
			message= message + "\n\t" + " item name " + name + " Quantity " + currIt.getQuantity() + " is still Wait listed";
			message= message + "\n\t" +"-------------------------------------------------------------------";
    		Quantity=0;
		}
		else {
			message= message + "\n\t" + " Shipment for " + currIt.getOrder().getClient().getName()+" Was not Filled";
			message= message + "\n\t" +  " item name " + name + " Quantity " + currIt.getQuantity()+" is still Wait listed";
			message= message + "\n\t" +"-------------------------------------------------------------------";
		}
	}
	
	  inStock=inStock+ Quantity;
	  message= message + "\n\t" +"item name: " + name + " ID: " + id + " has been updated Current Quantity in Stock " + inStock ;
	  JOptionPane.showMessageDialog(null,message,"Product added", JOptionPane.PLAIN_MESSAGE);
  }
	  
  }
	  
//end Product Class





