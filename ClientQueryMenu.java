import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.awt.event.ActionEvent;

public class ClientQueryMenu extends JFrame {

	private JPanel contentPane;
	private static Warehouse warehouse;



	/**
	 * Create the frame.
	 */
	public ClientQueryMenu(Warehouse w) {
		warehouse=w;
		setTitle("ClientQueryMenu");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 691, 245);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		//-----------------/Display All Client/-----------------
		JButton DisplayAllClientButton = new JButton("Display All Client");
		DisplayAllClientButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayAllClients();
			}
		});
		DisplayAllClientButton.setBounds(48, 11, 617, 23);
		contentPane.add(DisplayAllClientButton);
		//-----------------//-----------------
		
		//-----------------/Display List Of CLient WIth OutStanding Balance/-----------------
		JButton OutstandingBalanceButton = new JButton("Display List Of CLient WIth OutStanding Balance");
		OutstandingBalanceButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayOutstandingBalances();
			}
		});
		OutstandingBalanceButton.setBounds(48, 45, 617, 23);
		contentPane.add(OutstandingBalanceButton);
		//-----------------//-----------------
		
		//-----------------/Display List Of CLient With No Transaction In Last 6 Month/-----------------
		JButton TransactionButton = new JButton("Display List Of CLient With No Transaction In Last 6 Month");
		TransactionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				noTransactionsClients();
			}
		});
		TransactionButton.setBounds(48, 79, 617, 23);
		contentPane.add(TransactionButton);
		//-----------------//-----------------
		
		//-----------------/Back To Clark Menu/-----------------
		JButton ClarkMenuButton = new JButton("Back To Clark Menu");
		ClarkMenuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
				
				ClarkMenu frame = new ClarkMenu(warehouse);
				frame.setVisible(true);
				
			}
		});
		ClarkMenuButton.setBounds(235, 131, 244, 56);
		contentPane.add(ClarkMenuButton);
		//-----------------//-----------------
	}
	//-----------------displayAllClients()------------------------
    private static void displayAllClients() {
        Iterator it = warehouse.getClients();
        String message= "";
        while(it.hasNext())
            
        message = message +"\n\t"+ it.next().toString();
        
        JOptionPane.showMessageDialog(null,message,"Client List", JOptionPane.PLAIN_MESSAGE);
    }
    
    //--------displayOutstandingBalances-------------------
    public static void displayOutstandingBalances(){
    	double outstandingBalance = 0.00;
        Iterator it = warehouse.getClients();
        Client currClient = null;
        String message= "";
        boolean found = false;
        while(it.hasNext()){
            currClient = (Client) it.next();
            if(currClient.getClientBalance() > outstandingBalance){
                message = message +"\n\t"+ currClient.toString();
                found = true;
            }//end if
        }//end while()
        if(!found)
        	JOptionPane.showMessageDialog(null,"No clients with an outstanding balance found");
        else
        	JOptionPane.showMessageDialog(null,message,"Clients with outsranding balance", JOptionPane.PLAIN_MESSAGE);
        if(currClient == null)
        	JOptionPane.showMessageDialog(null,"No Outstanding Balances currently");
    }//end displayOutstandingBalances
    
    
    //--------noTransactionsClients()-------------------
    public static void noTransactionsClients() {
        boolean foundInvoice = false;
        boolean foundPayment = false;
        LocalDateTime CompareDate=LocalDateTime.now().minusMonths(6);

     		
        //--------ClientIterator---------
        Iterator itClient = warehouse.getClients();
        Client currClient = null;
        String Message="";
        String MessageNOT="";
        while(itClient.hasNext()){
        	currClient = (Client) itClient.next();
        	
        	if( currClient.getInvoiceArrayList().isEmpty() && currClient.getPaymentsArrayList().isEmpty()) {
        		Message= Message +"\n\t" + "ClientID: " + currClient.getId()+ " ClientName: " + currClient.getName() + " doesn't have any orders";
        		Message= Message +"\n\t" + "ClientID: " + currClient.getId()+ " ClientName: " + currClient.getName() + " doesn't have any Payments";
         		foundPayment = true;
          		foundInvoice = true;
          	}else {
        	 
        		//--------InvoiceIterator---------
            	Iterator itInvoice = currClient.getInvoices();
            	Invoice currInvoice = null;
            	while(itInvoice.hasNext() ){
            		currInvoice = (Invoice) itInvoice.next();
            		if(currInvoice.getDate().isBefore(CompareDate)) {
            			Message= Message +"\n\t" +" ClientID: " + currClient.getId()+ " ClientName: " + currClient.getName() + " doesn't have orders in last 6 months";
            			foundInvoice = true;
            			}// End if statement
            		}// End InvoiceIterator While Loop
        	 
            	//--------PaymentIterator---------
            	Iterator itPayment = currClient.getPayments();
            	Payment currPayment = null;
            	while(itPayment.hasNext() ){
            		currPayment = (Payment) itPayment.next();
            		if(currPayment.getDate().isBefore(CompareDate)) {
            			Message= Message +"\n\t" +" ClientID: " + currClient.getId()+ " ClientName: " + currClient.getName() + " doesn't have Paymants in last 6 months";
            			foundPayment = true;
            				}// End if statement
            			}// End PaymentIterator While Loop
                }// End ClientIterator While Loop
        }
        if(foundInvoice || foundInvoice)
        	JOptionPane.showMessageDialog(null,Message,"Clients with no Transaction for past 6 month ", JOptionPane.PLAIN_MESSAGE);
        if(!foundInvoice)
        	MessageNOT= MessageNOT +"there are No clients that haven't ordered in last 6 months";
        if(!foundInvoice )
        	MessageNOT= MessageNOT +"there ane no  clients that haven't paid in last 6 months";
        if(currClient == null)
        	JOptionPane.showMessageDialog(null,"No clients Found");
            }
    
	//-----------------/Close Frame/-----------------
	public void close() {
		WindowEvent closeWindow = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closeWindow);
		//-----------------//-----------------	
	}


}
