package NET;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class ConnectPage extends JFrame{
	JPanel main_panel;
	static Socket client;
	
	public static void main(String[] args) {
		final ConnectPage begin = new ConnectPage("");
		begin.CreateAndShowGUI();
		
    }
	public void CreateAndShowGUI(){
		ConnectPage frame = new ConnectPage("Card Game");
		frame.setPreferredSize(new Dimension(800,200));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		main_panel = new JPanel(new GridLayout(2,2,2,0));
		JLabel ip = new JLabel("IP:");
		JTextField in_ip = new JTextField(1);
		JLabel port = new JLabel("Port:");
		JTextField in_port = new JTextField(1);
		main_panel.add(ip);
		main_panel.add(in_ip);
		main_panel.add(port);
		main_panel.add(in_port);
		JButton connect = new JButton("Connect");
		connect.addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
            	String serverName = in_ip.getText();
    			int port = Integer.parseInt(in_port.getText());
    			try {
					client = new Socket(serverName, port);
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    			MainClient game = new MainClient("");
            	game.CreateGameAndShowGUI();
                //Execute when button is pressed
//            	try {
//        			String serverName = in_ip.getText();
//        			int port = Integer.parseInt(in_port.getText());
//        			Socket client = new Socket(serverName, port);
//        			OutputStream outToServer = client.getOutputStream();
//    	        	ObjectOutputStream out = new ObjectOutputStream(outToServer);
//    	        	InputStream inFromServer = client.getInputStream();
//    	         	ObjectInputStream in = new ObjectInputStream(inFromServer);
//    	        	Scanner sc = new Scanner(System.in);
//        			while(true){
//        	         	System.out.println("Just connected to "+ client.getRemoteSocketAddress());
//        	        	String input = sc.nextLine();
////        	        	try{
////        	        		Integer.parseInt(input);
////        	        	}
////        	        	catch(Exception e1){
////        	        		break;
////        	        	}
//        	       		out.writeObject(input);
////        	         	InputStream inFromServer = client.getInputStream();
////        	         	ObjectInputStream in = new ObjectInputStream(inFromServer);
//        	         	try {
//							System.out.println("Server says " + in.readObject());
//						} catch (ClassNotFoundException e1) {
//							// TODO Auto-generated catch block
//							e1.printStackTrace();
//						}
////        	         	
//        			}
//        			//client.close();
//        	    }
//        	    catch (IOException e1) {
//        	        System.out.println(e1);
//        	    }
            }
        }); 
		frame.add(main_panel, BorderLayout.NORTH);
		frame.add(connect);
		frame.pack();
		frame.setVisible(true);
	}
	public ConnectPage(String s){
		super(s);
	}

}
