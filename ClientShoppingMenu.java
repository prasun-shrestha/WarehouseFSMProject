import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Component;

import javax.print.DocFlavor.STRING;
import javax.swing.Box;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Iterator;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class ClientShoppingMenu extends JFrame {

	private JPanel contentPane;
    private static Warehouse warehouse;
    private static Client client;


	/**
	 * Create the frame.
	 */
	public ClientShoppingMenu(Client C, Warehouse w) {
		warehouse=w;
		client=C;
		setTitle("Client Shopping Menu");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 623, 365);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		//----------------------------Add Product to Shopping List----------------------------
		JButton AddProductButton = new JButton("Add Product to Shopping List");
		AddProductButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addToWishList(client);
			}
		});
		AddProductButton.setBounds(44, 23, 496, 23);
		contentPane.add(AddProductButton);
		//--------------------------------------------------------
		
		
		//----------------------------Show List Of All Product In Shopping List----------------------------
		
		JButton DisplayListButton = new JButton("Show List Of All Product In Shopping List");
		DisplayListButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayWishList(client);
			}
		});
		DisplayListButton.setBounds(44, 57, 496, 23);
		contentPane.add(DisplayListButton);
		
		//--------------------------------------------------------
		
		//----------------------------"Remove Product In Shoping List"----------------------------
		JButton RemoveProductButton = new JButton("Remove Product In Shoping List");
		RemoveProductButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeFromWishList(client);
				
			}
		});
		RemoveProductButton.setBounds(44, 91, 496, 23);
		contentPane.add(RemoveProductButton);
		//--------------------------------------------------------
		
		//----------------------------Edit Shoping Cart----------------------------
		JButton EditListButton = new JButton("Edit Shoping Cart");
		EditListButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modifyWishList(client);
			}
		});
		EditListButton.setBounds(44, 144, 496, 23);
		contentPane.add(EditListButton);
		
		JLabel lblNewLabel = new JLabel("Edit Shoping Cart Option Allows You Change Quantity Of Products In Your Shoping Cart");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(44, 178, 496, 14);
		contentPane.add(lblNewLabel);
		//--------------------------------------------------------
		
		//----------------------------Back to Client Menu----------------------------
		JButton ClientMenuButton = new JButton("Back to Client Menu");
		ClientMenuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
				ClientMenu frame = new ClientMenu(warehouse,client);
				frame.setVisible(true);
				
			}
		});
		ClientMenuButton.setBounds(220, 260, 155, 55);
		contentPane.add(ClientMenuButton);
		
		JLabel lblNewLabel_1 = new JLabel("----------------------------------------------------------------------------------------------------------------------");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(44, 125, 496, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("----------------------------------------------------------------------------------------------------------------------");
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1.setBounds(44, 203, 496, 14);
		contentPane.add(lblNewLabel_1_1);
		//--------------------------------------------------------
	}
	
	//-------addToWishList--------------
    public static void addToWishList(Client C){
    	 int clientid;
         clientid = C.getId();
         while(JOptionPane.YES_OPTION==OpeningMenu.YesOrNo("Add Prduct to Cart", "Do you wish to Add a Product to Shoping Cart")) {
         
        	 if( warehouse.getProductsList().size()==0)
        		 JOptionPane.showMessageDialog(null, "No Products in the System", "Error", JOptionPane.ERROR_MESSAGE);
        	 else {
        		 String[] options= new String[ warehouse.getProductsList().size()+1];
        		 Iterator iter = warehouse.getProducts();
        		 options[0]="Choose a Product";
        		 int count=1;
        		 while (iter.hasNext()) {
        			Product Current =(Product)iter.next() ;
        			options[count]="ID:" + " " + Current.getProductNumber() +  " " + "Name:"+  " " + Current.getName() + " " + "Price" +" " + Current.getPrice() + " Quantity in Stock: " + Current.getStock();
        			count++;
        		 }
        		 String n = (String)JOptionPane.showInputDialog(null, "Choose a Product to Add to Shoping Cart", 
 		                "Product Selection", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        		 if(n=="Choose a Product")
        		 
 		        	JOptionPane.showMessageDialog(null, "Please Choose a Product and Click OK", "Error", JOptionPane.ERROR_MESSAGE);
 		        
        		 else {
        			 String[] parts = n.split(" ");
        			 int ID = Integer.valueOf(parts[1]);
        			 String Name = parts[3];
        			 double Price = Double.valueOf(parts[5]);
        			 int CartQuantity = 0;
        			 boolean check= true;
        	            do
        	            {
        	                check = true;
        	                try
        	                {
        	                	CartQuantity =  Integer.parseInt(JOptionPane.showInputDialog("Enter quantity for the product to add to cart"));
        	                }
        	            
        	            catch(NumberFormatException nfe)
        	            {
        	                nfe.printStackTrace();
        	                check = false;
        	            }
        		 }while(!check);
        	            warehouse.addToCart(clientid, ID, CartQuantity);
        	            JOptionPane.showMessageDialog(null,"Item: " + Name
        	           		 + "\n\tID: " + ID + "\n\t Quantity: " + CartQuantity + 
        	           		 "\n\t Was added Sucessfully","Item Added"
        	           		 , JOptionPane.PLAIN_MESSAGE);
        	            System.out.println("Item added to cart.");
        		 	}// end else product selection
        		 }// end else no product in system
         }//end while yes no
    }//end addToWishList
    
  //-----------displayWishList-------------
    public static void displayWishList(Client C){
        
       
        int id = C.getId();
        Iterator it;
        String message = "";
        	if(C.getShoppingCart().isEmpty()) {
        		System.out.println(" Shoping Cart is empty " );
        		message = " Shoping Cart is empty ";
        	}else {
            it = warehouse.getCart(id);
            while(it.hasNext())
                message= message+ "\n\t"+((OrderedProduct)it.next()).toString() ;}
        	JOptionPane.showMessageDialog(null,message,"Shoping Cart of "  + C.getName(), JOptionPane.PLAIN_MESSAGE);
    }//end displaywishlist  
    
    //------------RemoveFromCart--------------------------
		public static void removeFromWishList(Client c){
			while(JOptionPane.YES_OPTION==OpeningMenu.YesOrNo("remove Prduct from Cart", "Do you wish to remove a Product from Shoping Cart"))
			{if( c.getShoppingCart().size()==0)
				JOptionPane.showMessageDialog(null, "No Products in the shoping cart", "Error", JOptionPane.ERROR_MESSAGE);
			else {
				 String[] options= new String[ c.getShoppingCart().size()+1];
        		 Iterator iter = c.getCart();
        		 options[0]="Choose a Product in the shoping cart to remove";
        		 int count=1;
        		 while (iter.hasNext()) {
        			OrderedProduct Current =(OrderedProduct)iter.next() ;
        			options[count]="ID:" + " " + Current.getProduct().getProductNumber() +  " " + "Name:"+  " " + Current.getProduct().getName() + " " + "Price" +" " + Current.getProduct().getPrice()+ " Quantity in cart " + Current.getQuantity();
        			count++;
        		 }//end while
        		 String n = (String)JOptionPane.showInputDialog(null, "Choose a Product to Remove from Shoping Cart", 
  		                "Product Selection", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        		 if(n=="Choose a Product")
            		 
  		        	JOptionPane.showMessageDialog(null, "Please Choose a Product and Click OK", "Error", JOptionPane.ERROR_MESSAGE);
        		 else {
        			 String[] parts = n.split(" ");
        			 int ID = Integer.valueOf(parts[1]);
        			 String Name = parts[3];
        			 int Quantity = Integer.valueOf(parts[9]);
        			 warehouse.RemoveFromCart(c.getId(), ID);	
        			 JOptionPane.showMessageDialog(null,"Item: " + Name
        	           		 + " ID: " + ID + " Quantity: " + Quantity + 
        	           		 " Was Removed Sucessfully","Item Removed"
        	           		 , JOptionPane.PLAIN_MESSAGE);
        			 System.out.println("Item: " + Name + " was removed from cart.");
        		 }//end else
				}// end else
			
			}//end while Yes and no
			 
		}//end RemoveFromCart
		
		
		 //-----modifyWishList----------------
	    public static void modifyWishList(Client c){
	    	while(JOptionPane.YES_OPTION==OpeningMenu.YesOrNo("Edit Prduct in Cart", "Do you wish to Change quantity of a Product in Shoping Cart"))
			{if( c.getShoppingCart().size()==0)
				JOptionPane.showMessageDialog(null, "No Products in the shoping cart", "Error", JOptionPane.ERROR_MESSAGE);
			else {
				 String[] options= new String[ c.getShoppingCart().size()+1];
        		 Iterator iter = c.getCart();
        		 options[0]="Choose a Product in the shoping cart to changr the quantity";
        		 int count=1;
        		 while (iter.hasNext()) {
        			OrderedProduct Current =(OrderedProduct)iter.next() ;
        			options[count]="ID:" + " " + Current.getProduct().getProductNumber() +  " " + "Name:"+  " " + Current.getProduct().getName() + " " + "Price" +" " + Current.getProduct().getPrice()+ " Quantity in cart " + Current.getQuantity();
        			count++;
        		 }//end while
        		 String n = (String)JOptionPane.showInputDialog(null, "Choose a Product to change quantity in Shoping Cart", 
  		                "Product Selection", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        		 if(n=="Choose a Product")
            		 
  		        	JOptionPane.showMessageDialog(null, "Please Choose a Product and Click OK", "Error", JOptionPane.ERROR_MESSAGE);
        		 else {
        			 String[] parts = n.split(" ");
        			 int ID = Integer.valueOf(parts[1]);
        			 String Name = parts[3];
        			 double Price = Double.valueOf(parts[5]);
        			 int CartQuantity = 0;
        			 boolean check= true;
        	            do
        	            {
        	                check = true;
        	                try
        	                {
        	                	CartQuantity =  Integer.parseInt(JOptionPane.showInputDialog("Enter quantity for the product you want to change to"));
        	                }
        	            
        	            catch(NumberFormatException nfe)
        	            {
        	                nfe.printStackTrace();
        	                check = false;
        	            }
        		 }while(!check);
        	            warehouse.modifyCart(c.getId(), ID, CartQuantity);	
        			 JOptionPane.showMessageDialog(null,"Quantity of Item: " + Name
        	           		 + " ID: " + ID + " Was Changed to " + CartQuantity ,"Item Quantity Changed"
        	           		 , JOptionPane.PLAIN_MESSAGE);
        			 System.out.println("Quantity of Item: " + Name + " was Changed.");
        		 }//end else
				}// end else
			
			}//end while Yes and no
	    }//end modifyCart()
	//-----------------/Close Frame/-----------------
	public void close() {
		WindowEvent closeWindow = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closeWindow);
		//-----------------//-----------------	
	}
}
