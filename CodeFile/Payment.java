package CodeFile;
/******************************************************************************
	Payment Class
*******************************************************************************/

import java.util.*;
import java.lang.*;
import java.io.*;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;    
public class Payment implements Serializable{
	private static final long serialVersionUID = 1L;
	private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy MM dd");
    
	private double clientPayment;
	private Client clientAccount;
    private String description;
    private LocalDateTime date;



	//-----------Constructor-------------------------
	public Payment(double p, Client c, String d){
		clientPayment = p;
		this.clientAccount = c;
        this.description = d;
        date=LocalDateTime.now();
		sendToAccount();
	}//end Constructor
    
//-------------Accessor--------------------
    public double getPayment(){
        return clientPayment; }
    
    public LocalDateTime getDate() {
		return date;
	}
    
//-------------SendToAccount---------------------------
// Sends payment to Clients account updating balance
//-----------------------------------------------------
	private void sendToAccount(){
			clientAccount.addPayment(this);  }
	
//-------------toString()-------------------
	public String toString(){
		return  " Amount: " + clientPayment + " Date: " + date.format(dateFormat) + 
               "\n" + description;
	}//end toString

}//end Payment Class





