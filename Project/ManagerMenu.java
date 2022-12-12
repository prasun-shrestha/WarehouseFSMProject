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
import javax.swing.SwingConstants;
import java.awt.Component;
import javax.swing.Box;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Iterator;
import java.util.Scanner;
import java.awt.event.ActionEvent;

public class ManagerMenu extends JFrame {

	private JPanel contentPane;
	private static Warehouse warehouse;


	public ManagerMenu(Warehouse w) {
		warehouse=w;
		setTitle("ManagerMenu");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 639, 242);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton AddProductButton = new JButton("Add Product");
		AddProductButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addProduct();
			}
		});
		AddProductButton.setBounds(48, 28, 531, 23);
		contentPane.add(AddProductButton);
		
		JButton ShipmentButton = new JButton("Recieve Shipment");
		ShipmentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			ProcessShipment();
			}
		});
		ShipmentButton.setBounds(48, 62, 531, 23);
		contentPane.add(ShipmentButton);
		
		JButton ClarkMenuButton = new JButton("Become A Salse Clark (Opens Clark Menu)");
		ClarkMenuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();			
				ClarkMenu frame = new ClarkMenu(warehouse);
				frame.setVisible(true);
			}
		});
		ClarkMenuButton.setBounds(48, 96, 531, 23);
		contentPane.add(ClarkMenuButton);
		
		JButton MainMenuButton = new JButton("Main Menu");
		MainMenuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
				
				OpeningMenu frame = new OpeningMenu(warehouse);
				frame.setVisible(true);
			}
		});
		MainMenuButton.setBounds(416, 130, 146, 51);
		contentPane.add(MainMenuButton);
	}
	//-----------------/Add product/-----------------
	private static void addProduct() {
        boolean adding = true;
        Scanner input;
        while(JOptionPane.YES_OPTION==OpeningMenu.YesOrNo("Add Prduct to Inventory", "Do you wish to Add a Product to the inventore of wareHouse")) {
            String name = JOptionPane.showInputDialog("Please enter Products name");
            Double saleprice = 0.0;
            boolean check= true;
            do
            {
                check = true;
            try
            {
            	saleprice = Double.valueOf( Integer.parseInt(JOptionPane.showInputDialog("Enter price for the product")));
            }
            catch(NumberFormatException nfe)
            {
                nfe.printStackTrace();
                check = false;
            }
        }while(!check);
            int stock = 0;
            boolean checkstock= true;
            do
            {
                checkstock = true;
            try
            {
            	stock =  Integer.parseInt(JOptionPane.showInputDialog("Enter Amount in stock for the product"));
            }
            catch(NumberFormatException nfe)
            {
                nfe.printStackTrace();
                checkstock = false;
            }
        }while(!checkstock);
            
            warehouse.addProduct(name,saleprice, stock);
            String message = warehouse.findProduct(name).toString();
            JOptionPane.showMessageDialog(null,message,"Product added", JOptionPane.PLAIN_MESSAGE);
            System.out.println("Product added.");
            
        }
    }
	//-----------------//-----------------
	
	
    //-----------ProcessShipment()--------------------------
    public static void ProcessShipment() {
    	
        while(JOptionPane.YES_OPTION==OpeningMenu.YesOrNo("Recievement Shipment", "Do you wish Add shipment for a Product")) {
        
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
       			int Quantity = 0;
   			 boolean check= true;
   	            do
   	            {
   	                check = true;
   	                try
   	                {
   	                	Quantity =  Integer.parseInt(JOptionPane.showInputDialog("Enter quantity for the Shipment of produst that was received"));
   	                }
   	            
   	            catch(NumberFormatException nfe)
   	            {
   	                nfe.printStackTrace();
   	                check = false;
   	            }
   		 }while(!check);
   	         warehouse.ProcessShipment(warehouse.findProduct(ID), Quantity );
       		 }
       	 }
        }
           
    }
  //-----------------//-----------------
	
	//-----------------/Close Frame/-----------------
	public void close() {
		WindowEvent closeWindow = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closeWindow);
		//-----------------//-----------------	
	}
}
