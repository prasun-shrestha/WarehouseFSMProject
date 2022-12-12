import java.util.*;
import java.io.*;
import java.lang.*;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;


import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;

import java.awt.Button;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Component;
import javax.swing.Box;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFormattedTextField;

public class OpeningMenu extends JFrame {

	private static JPanel contentPane;
    private static Warehouse warehouse;
    final static String FILENAME = "WareData";
	/**
	 * Create the frame.
	 */
	public OpeningMenu(Warehouse w) {
		warehouse = w;
		setTitle("Main Menu");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 463, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		//---------------------------/Clark Menu/---------------------------		
		JButton ClarkMenuButton = new JButton("Clark Menu");
		ClarkMenuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
				
				ClarkMenu frame = new ClarkMenu(warehouse);
				frame.setVisible(true);
					
			}
		});
		//---------------------------//---------------------------
		
		//---------------------------/Manager Menu/---------------------------
		ClarkMenuButton.setBounds(58, 55, 292, 32);
		contentPane.add(ClarkMenuButton);
		
		JButton ManagerMenuButton = new JButton("Manager Menu");
		ManagerMenuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
				
				ManagerMenu frame = new ManagerMenu(warehouse);
				frame.setVisible(true);
						
			}
		});
		ManagerMenuButton.setBounds(58, 98, 292, 32);
		contentPane.add(ManagerMenuButton);
		//---------------------------//---------------------------
		
		
		//---------------------------/Save And Quit Program/---------------------------
		JButton SaveandQuitButton = new JButton("Save And Quit Program");
		SaveandQuitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
				LoadFileMenu.saveChanges(FILENAME, warehouse);
			}
		});
		SaveandQuitButton.setBounds(58, 186, 292, 32);
		contentPane.add(SaveandQuitButton);
		//---------------------------//---------------------------
		
		
		//---------------------------/CLientMenu/---------------------------
		JButton CLientMenuButton = new JButton("CLientMenu");
		CLientMenuButton.addActionListener(new ActionListener() {
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
		//---------------------------/Save Changes/---------------------------
		CLientMenuButton.setBounds(58, 11, 292, 33);
		contentPane.add(CLientMenuButton);
		
		JButton SaveButton = new JButton("Save Changes");
		SaveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoadFileMenu.saveChanges(FILENAME, warehouse);
				JOptionPane.showMessageDialog(null,"Data of warehouse system was saved sucessfully","Saveed", JOptionPane.PLAIN_MESSAGE);
			}
		});
		SaveButton.setBounds(58, 141, 292, 34);
		contentPane.add(SaveButton);
		//---------------------------//---------------------------
		
		
		
		
		
		
	}

	public static boolean CLientIDLogin(int ID) {
		Client c = warehouse.findClient(ID);
		 if(c == null) {
            System.out.println("ERROR No client found with given id");
		 return false;
			 	}
        else {
				ClientMenu frame = new ClientMenu(warehouse,c);
				frame.setVisible(true);
				return true;
			} 	
        
		
	}
		
	
			
			
		
	

	//-----------------/Close Frame/-----------------
	public void close() {
		WindowEvent closeWindow = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closeWindow);
		//-----------------//-----------------	
	}

	
	public static int YesOrNo(String Title,String Message) {
		 int YesOrNo=JOptionPane.showConfirmDialog(null, Message,Title,JOptionPane.YES_NO_OPTION);
		 return YesOrNo;
	}
}
