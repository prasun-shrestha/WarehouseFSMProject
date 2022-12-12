import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class LoadFileMenu extends JFrame {

	private JPanel contentPane;
	final static String FILENAME = "WareData";
	public static Warehouse warehouse;
	//------------saveChanges---------------------
	// Saves changes made to warehosue
	//-------------------------------------------
	    public static void saveChanges(String file, Warehouse warehouse) {
	        if(warehouse.saveData(file))
	            System.out.println("Saved Successfully");
	        else
	            System.out.println("Save Failed. ERROR");
	    } //end saveChanges
	    
	//-----------openWarehouse-------------------
	    private static Warehouse openWarehouse(String file){
	        Warehouse w;
	       
	            w = Warehouse.retrieveData(file);
	            if(w == null){
	                System.out.println("Empty File. Creating New Warehouse");
	                w = Warehouse.instance();
	            } else
	                System.out.println("Warehouse Successfully opened");

	                return w;

	    }//end openWarehouse
	    /**
		 * Create the frame.
		 */
		public LoadFileMenu() {
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setBounds(100, 100, 450, 300);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(null);
			
			JButton btnNewButton = new JButton("Yes");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					warehouse = openWarehouse(FILENAME);
					close();
					OpenOpeningMenu();
					
				}
			});
			btnNewButton.setBounds(174, 71, 89, 23);
			contentPane.add(btnNewButton);
			
			JButton btnNewButton_1 = new JButton("No");
			btnNewButton_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					warehouse = Warehouse.instance();
		             System.out.println("New Warehouse created");
		         	close();
					OpenOpeningMenu();
				}
			});
			btnNewButton_1.setBounds(174, 105, 89, 23);
			contentPane.add(btnNewButton_1);
			
			JLabel LoadPreviouslySavedFileLabel = new JLabel("Load Previously Saved File");
			LoadPreviouslySavedFileLabel.setHorizontalAlignment(SwingConstants.CENTER);
			LoadPreviouslySavedFileLabel.setBounds(57, 11, 307, 39);
			contentPane.add(LoadPreviouslySavedFileLabel);
		}
		public void close() {
			WindowEvent closeWindow = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
			Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closeWindow);
			
		}
		///Opens Opening Menu
		public static void OpenOpeningMenu() {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						OpeningMenu frame = new OpeningMenu(warehouse);
						frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoadFileMenu frame = new LoadFileMenu();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
