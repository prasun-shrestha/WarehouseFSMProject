import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Iterator;
import java.awt.event.ActionEvent;

public class ClientMenu extends JFrame {
    private static Warehouse warehouse;
    private static Client client;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public ClientMenu(Warehouse w, Client C) {
		warehouse=w;
		client=C;
		setTitle("Client Menu");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 724, 349);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		//----------------------------Display Client Detail----------------------------
		JButton ClientDetailButton = new JButton("Display Client Detail");
		ClientDetailButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayClientDetails(client);
			}
		});
		ClientDetailButton.setBounds(241, 23, 176, 23);
		contentPane.add(ClientDetailButton);
		//--------------------------------------------------------
		
		//----------------------------Show List OF Product
		JButton DisplayProductListButton = new JButton("Show List OF Product");
		DisplayProductListButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayProductList();		            
			}
		});
		//--------------------------------------------------------
		
		//----------------------------Show Client's Transaction----------------------------
		DisplayProductListButton.setBounds(241, 57, 176, 23);
		contentPane.add(DisplayProductListButton);
		
		JButton TransactionButton = new JButton("Show Client's Transaction");
		TransactionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayClientTransactions(client);
				
			}
		});
		TransactionButton.setBounds(193, 91, 284, 23);
		contentPane.add(TransactionButton);
		//--------------------------------------------------------
		
		//----------------------------Edit Client Shoping Cart (Opens Seperate Menu)----------------------------
		JButton ShoppingCartMenuButton = new JButton("Edit Client Shoping Cart (Opens Seperate Menu)");
		ShoppingCartMenuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
							ClientShoppingMenu frame = new ClientShoppingMenu(client, warehouse);
							frame.setVisible(true);
						
			}
		});
		ShoppingCartMenuButton.setBounds(139, 125, 403, 23);
		contentPane.add(ShoppingCartMenuButton);
		//--------------------------------------------------------
		
		//----------------------------Display Client's Wait List
		JButton ClientWaitListButton = new JButton("Display Client's Wait List");
		ClientWaitListButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayWaitList(client);
			}
		});
		ClientWaitListButton.setBounds(228, 159, 225, 23);
		contentPane.add(ClientWaitListButton);
		//--------------------------------------------------------
		
		//----------------------------Place An Oder----------------------------
		JButton OrderButton = new JButton("Place An Oder");
		OrderButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				placeOrder(client);
			}
		});
		OrderButton.setBounds(241, 193, 176, 23);
		contentPane.add(OrderButton);
		
		JButton MainMenuButton = new JButton("Main Menu");
		MainMenuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
				
				OpeningMenu frame = new OpeningMenu(warehouse);
				frame.setVisible(true);
			}
		});
		MainMenuButton.setBounds(494, 226, 109, 49);
		contentPane.add(MainMenuButton);
		//--------------------------------------------------------
	}





	//------displayClientDetails---------------
	private static void displayClientDetails(Client c){
		JOptionPane.showMessageDialog(null, warehouse.findClient(c.getId()).toString(),
				"Client Detail", JOptionPane.PLAIN_MESSAGE);
		}//end displayClients
	//----displayProductList----------------
	public static void displayProductList(){
		Iterator iterator = warehouse.getProducts();
		String ProductList= "";
		int Count=0;
		while(iterator.hasNext())
			ProductList= ProductList + " \n\t" +iterator.next().toString();
		JOptionPane.showMessageDialog(null,ProductList,"List of Product In Ware House", JOptionPane.PLAIN_MESSAGE);
		}//end display Products
	
	//-------displayClientTransactions-------------------
    private static void displayClientTransactions(Client c){
        int clientId = c.getId();
        Iterator paymentIt, invoiceIt;
        if(!warehouse.verifyClient(clientId)){
        	JOptionPane.showMessageDialog(null,"ERROR invalid client id ");
            return;
        }//end if
        invoiceIt = warehouse.getInvoice(clientId);
        paymentIt = warehouse.getPayment(clientId);
        String Message = "";
        Message= Message +" \n\t"+ "INVOICES\n" + "_____________________";
        while(invoiceIt.hasNext() )
        	Message= Message + " \n\t" + ((Invoice)(invoiceIt.next())).itemListString();
        Message= Message + " \n\t" + "\nPAYMENTS\n" + "_____________________";
        while(paymentIt.hasNext() )
        	Message= Message + " \n\t" +  ((Payment)(paymentIt.next())).toString();
    	JOptionPane.showMessageDialog(null,Message,"Tranjection List Of " + c.getName(), JOptionPane.PLAIN_MESSAGE);
        } //end displayTransaction
    
  //-------displayWaitList-------------------
    public static void displayWaitList(Client c) {
    		String Message = "Wait Listed Products Of " + c.getName();
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
                		 Message= Message + " \n\t" + curritem.toString();
    		 		}
             	}
    		 JOptionPane.showMessageDialog(null,Message,"WaitListed Product Of " + c.getName(), JOptionPane.PLAIN_MESSAGE);

    }
    //--------placeOrder---------------
    public static void placeOrder(Client c){
        int clientId = c.getId();
        if(!warehouse.verifyClient(clientId) ){
            System.out.println("ERROR invalid client id");
            return;
        }//end if
        warehouse.placeOrder(clientId);
        JOptionPane.showMessageDialog(null,"Order placed successfully");
    }//end placeOrder
  //--------Closeaframe---------------
	public void close() {
		WindowEvent closeWindow = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closeWindow);
		
	}//end clsoe()
}