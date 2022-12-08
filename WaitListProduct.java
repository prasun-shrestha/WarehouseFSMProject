/******************************************************************************
WaitListProduct Class
******************************************************************************/

import java.util.*;
import java.io.*;
import java.lang.*;

public class WaitListProduct implements Serializable{
	private static final long serialVersionUID = 1L;
    
	private Order order;
	private Product product;
	private int quantity;



	//----------Constructor---------------------
	public WaitListProduct(Order o, int q, Product p){
		order = o;
		this.quantity = q;
		product = p;
	}
	//----------Modifior---------------------
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
    
//---------accessors----------------
	public Order getOrder(){
        return order;	}

	public int getQuantity(){
		return quantity; 	}
	
	public Product getProduct(){
		return product; }
	public WaitListProduct getWaitListProduct() {
		return this;
	}
	

//-------toString()-----------------
	public String toString(){
		return "Order Id: " + order.getId() + " Product: " + product.getName() + " ProductID: " + product.getProductNumber() + " Quantity : " + quantity;
	}//end toString
	
    
//----------Process()-------------------
// Process waitList Item
//--------------------------------------
    public void process(){
		Invoice i = new Invoice(order.getClient());
		i.addItem(new OrderedProduct(product, quantity));
		i.applyInvoice();
	}//end process

}//end Waitlist Item class
