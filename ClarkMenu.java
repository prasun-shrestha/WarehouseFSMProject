import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Iterator;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Component;
import javax.swing.Box;

public class ClarkMenu extends JFrame {

	private JPanel contentPane;
	private static Warehouse warehouse;


	/**
	 * Create the frame.
	 */
	public ClarkMenu(Warehouse w) {
		warehouse=w;
		setTitle("ClarkMenu");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 598, 377);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		//-----------------/Add Client/-----------------
		JButton ADDCLientButton = new JButton("Add Client");
		ADDCLientButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addClient();
			}
		});
		ADDCLientButton.setBounds(30, 49, 488, 23);
		contentPane.add(ADDCLientButton);
		//-----------------//-----------------
		
		//-----------------/Show List Of Product/-----------------
		JButton ShowProductListButton = new JButton("Show List Of Product");
		ShowProductListButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayProductList();
			}
		});
		ShowProductListButton.setBounds(30, 83, 488, 23);
		contentPane.add(ShowProductListButton);
		//-----------------//-----------------
		
		//-----------------/Client Query Menu/-----------------
		JButton ClientQuiryMenuButton = new JButton("Client Query Menu");
		ClientQuiryMenuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

							close();
							ClientQueryMenu frame = new ClientQueryMenu(warehouse);
							frame.setVisible(true);
						
				
			}
		});
		ClientQuiryMenuButton.setBounds(30, 117, 488, 23);
		contentPane.add(ClientQuiryMenuButton);
		//-----------------//-----------------
		
		//-----------------/Accept Payment/-----------------
		JButton PaymantButton = new JButton("Accept Payment");
		PaymantButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				makePayment();
			}
		});
		PaymantButton.setBounds(40, 151, 478, 21);
		contentPane.add(PaymantButton);
		//-----------------//-----------------
		
		//-----------------/Become A Client (Opens Client Menu)/-----------------
		JButton ClientMenuButton = new JButton("Become A Client (Opens Client Menu)");
		ClientMenuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if( warehouse.getClientsArraylist().size()==0)
					JOptionPane.showMessageDialog(null, "No Client in the System", "Error", JOptionPane.ERROR_MESSAGE);
				else {
				String[] options= new String[warehouse.getClientsArraylist().size()+1];
			      Iterator iter = warehouse.getClientsArraylist().iterator();
			      options[0]="Choose a Client";
			      int count=1;
			      while (iter.hasNext()) {
			    	  Client Current =(Client)iter.next() ;
			    	  options[count]="ID:" + " " + Current.getId() +  " " + "Name:"+  " " + Current.getName();
			    	  count++;
			      }	        
		        String n = (String)JOptionPane.showInputDialog(null, "Choose a Client to log in as", 
		                "Client Log IN", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		        if(n=="Choose a Client") {
		        	JOptionPane.showMessageDialog(null, "Please Choose a CLient and Click OK", "Error", JOptionPane.ERROR_MESSAGE);
		        }else {
		        String[] parts = n.split(" ");
		        int ID = Integer.valueOf(parts[1]); 
		        String Name = parts[3];
		        Client C=warehouse.findClient(ID);
		        close();
		        ClientMenu frame = new ClientMenu(warehouse,C);
				frame.setVisible(true);}
				
				}
			}
		});
		ClientMenuButton.setBounds(30, 183, 491, 23);
		contentPane.add(ClientMenuButton);
		//-----------------//-----------------
		
		//-----------------/Display Wait List For A Product/-----------------
		JButton ProductWaitListButton = new JButton("Display Wait List For A Product");
		ProductWaitListButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showWaitList();
			}
		});
		ProductWaitListButton.setBounds(30, 217, 488, 23);
		contentPane.add(ProductWaitListButton);
		//-----------------//-----------------
		
		//-----------------//-----------------
		JButton MainMenuButton = new JButton("Main Menu");
		MainMenuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
				
				OpeningMenu frame = new OpeningMenu(warehouse);
				frame.setVisible(true);
			}
		});
		MainMenuButton.setBounds(407, 268, 111, 46);
		contentPane.add(MainMenuButton);
		//-----------------//-----------------
	}
	//---------------/addClient/--------------------------
    private static void addClient() {
    	 while(JOptionPane.YES_OPTION==OpeningMenu.YesOrNo("Add Client in warehouse", "Do you wish to Add a client to the wareHouse system")) {
    	String name = JOptionPane.showInputDialog("Please enter client's name");
    	String address = JOptionPane.showInputDialog("Please enter client's address"); 
        Client c = warehouse.addClient(name, address);
        String message =  c.toString();
        JOptionPane.showMessageDialog(null,message,"Client added", JOptionPane.PLAIN_MESSAGE);
        System.out.println("Client added successfully");
    	}
    }
//-----------------------------------------
    
	//----displayProductList----------------
	public static void displayProductList(){
		Iterator iterator = warehouse.getProducts();
		String ProductList= "";
		int Count=0;
		while(iterator.hasNext())
			ProductList= ProductList + " \n\t" +iterator.next().toString();
		JOptionPane.showMessageDialog(null,ProductList,"List of Product In Ware House", JOptionPane.PLAIN_MESSAGE);
		}//end display Products
	  //----------makePayment------------------------ 
	 // User provides client ID and payment amount
	 // Payment gets made to clients account
	 //---------------------------------------------
	     public static void makePayment(){
	    	 if( warehouse.getClientsArraylist().size()==0)
					JOptionPane.showMessageDialog(null, "No Client in the System", "Error", JOptionPane.ERROR_MESSAGE);
				else {
				String[] options= new String[warehouse.getClientsArraylist().size()+1];
			      Iterator iter = warehouse.getClientsArraylist().iterator();
			      options[0]="Choose a Client";
			      int count=1;
			      while (iter.hasNext()) {
			    	  Client Current =(Client)iter.next() ;
			    	  options[count]="ID:" + " " + Current.getId() +  " " + "Name:"+  " " + Current.getName();
			    	  count++;
			      }	        
		        String n = (String)JOptionPane.showInputDialog(null, "Choose a Client to make a payment for", 
		                "Client Log IN", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		        if(n=="Choose a Client") {
		        	JOptionPane.showMessageDialog(null, "Please Choose a CLient and Click OK", "Error", JOptionPane.ERROR_MESSAGE);
		        }else {
		        String[] parts = n.split(" ");
		        int ID = Integer.valueOf(parts[1]); 
		        String Name = parts[3];
		        Client C=warehouse.findClient(ID);
		        
		        
		        Double amount = 0.0;
	            boolean check= true;
	            do
	            {
	                check = true;
	            try
	            {
	            	amount = Double.valueOf( Integer.parseInt(JOptionPane.showInputDialog("Enter payment amount")));
	            }
	            catch(NumberFormatException nfe)
	            {
	                nfe.printStackTrace();
	                check = false;
	            }
	        }while(!check);
	            if(amount <= 0.0){
	            	JOptionPane.showMessageDialog(null,"ERROR, payment must be a positive value. ", "Error", JOptionPane.ERROR_MESSAGE);
		             return;
	            		}//end if
	            String description = JOptionPane.showInputDialog("Please enter a description for this transaction (ie payment method)");
	            warehouse.makePayment(ID, amount, description);
	            JOptionPane.showMessageDialog(null,"Payment Received From "+ Name +"\n\t Amount: " + amount + "\n\t Payment Discription: " + description,"Payment Sycessful", JOptionPane.PLAIN_MESSAGE);
	            System.out.println("Payment received successfully");
					}
				
				}

	         
	     }//end makePayment
	   //------------------------------
	     private static void showWaitList() {
	     		String Message="";
	     		if( warehouse.getProductsList().size()==0)
	        		 JOptionPane.showMessageDialog(null, "No Products in the System", "Error", JOptionPane.ERROR_MESSAGE);
	        	 else {
	        		 String[] options= new String[ warehouse.getProductsList().size()+1];
	        		 Iterator iter = warehouse.getProducts();
	        		 options[0]="Choose a Product";
	        		 int count=1;
	        		 while (iter.hasNext()) {
	        			Product Current =(Product)iter.next() ;
	        			options[count]="ID:" + " " + Current.getProductNumber() +  " " + "Name:"+  " " + Current.getName() + " " + "Price" +" " + Current.getPrice();
	        			count++;
	        		 }
	        		 String n = (String)JOptionPane.showInputDialog(null, "Choose a Product to Add to Shoping Cart", 
	 		                "Product Selection", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
	        		 if(n=="Choose a Product")
	        		 
	 		        	JOptionPane.showMessageDialog(null, "Please Choose a Product and Click OK", "Error", JOptionPane.ERROR_MESSAGE);
	 		        
	        		 else {
	        			 String[] parts = n.split(" ");
	        			 int productID = Integer.valueOf(parts[1]);
	        			 String Name = parts[3];
	        			 double Price = Double.valueOf(parts[5]);
	        			 Iterator it;
	             it = warehouse.getWaitList(productID);
	             Message= Message+ "\n\t" +"Product: " + warehouse.findProduct(productID).toString();
	             if(!it.hasNext())
	            	 Message= Message+ "\n\t" + "Product has no wait list currently";
	             else {
	            	 Message= Message+ "\n\t" + "Wait list: \n" + "------------------";
	                 while(it.hasNext())
	                	 Message= Message+ "\n\t" +((WaitListProduct)it.next()).toString();
	             } //end else
	             JOptionPane.showMessageDialog(null, Message,"Wait list of "+ Name, JOptionPane.PLAIN_MESSAGE);
	             
	        		 }
	        	 }
	     }
    
    
	//-----------------/Close Frame/-----------------
		public void close() {
			WindowEvent closeWindow = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
			Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closeWindow);
			//-----------------//-----------------	
		}
	
}
