package NET;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class MainClient extends JFrame {
	JPanel top_panel;
	JPanel matching_panel;
	BufferedImage img;
	String input;
	Boolean firsttime = true;
	String playerName;
	static ClientButtons my_buttons[] = new ClientButtons[36];
	static String[] filename = { "1", "2", "3", "4", "5", "6", "7", "8", "9",
			"10", "11", "12", "13", "14", "15", "16", "17", "18" };
	static int[] cards;
	int[] card_temp;
	static ObjectOutputStream out;
	int count;
	public void CreateGameAndShowGUI(){
		final Socket server = ConnectPage.client;
		final ObjectInputStream in;
		for (int i = 0; i < 18; i++) {
			filename[i] += ".jpg";
			
		}
		try {
			in = new ObjectInputStream(server.getInputStream());
			while(cards == null){
				cards = (int[])in.readObject();
				System.out.println("Create cards");
				System.out.println(cards);
			}
			//input = in.readObject().toString();
			// to click buttons
			//int click = Integer.parseInt(input);
			//my_buttons[click].doClick();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		
//		Thread readThread = new Thread(new Runnable() {
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				try{
//					final ObjectInputStream in;
//					in = new ObjectInputStream(server.getInputStream());
//					while (true) {
//						if(cards == null){
//							cards = (int[])in.readObject();
//							System.out.println("Create cards");
//							System.out.println(cards);
//						}
//						input = in.readObject().toString();
//						// to click buttons
//						int click = Integer.parseInt(input);
//						my_buttons[click].doClick();
//					}
//				}catch(Exception e){
//					System.out.println("Catchhhhh");
//				}
//
//			}
//		});
//		readThread.start();
		System.out.println("ready to create page");
		MainPage frame = new MainPage("Card Game");
		frame.setPreferredSize(new Dimension(800, 800));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		// INFO
		top_panel = new JPanel(new GridLayout(2, 2, 2, 0));
		JLabel my_name = new JLabel(" Alice : 0 ");
		JLabel turn_label = new JLabel(" This is " + " Bob's " + "turn");
		JLabel oppo_name = new JLabel(" Bob : 1 ");
		JLabel time_label = new JLabel("Time left : 3 sec");
		top_panel.add(my_name);
		top_panel.add(oppo_name);
		top_panel.add(turn_label);
		top_panel.add(time_label);
		frame.add(top_panel, BorderLayout.NORTH);
		
		// Creating Buttons
		matching_panel = new JPanel(new GridLayout(6, 6, 4, 4));
		// String[] filename =
		// {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18"};
		System.out.println(cards+"before while");
		
		while(cards==null);//System.out.println("looping");
		
		System.out.println("assigning cards");
		for (int i = 0; i < 36; i++) {
			System.out.println("creating buttons");
			my_buttons[i] = new ClientButtons();
			my_buttons[i].setActionCommand(i + "");
			matching_panel.add(my_buttons[i]);
		}
		System.out.println("finished creating buttons");
		frame.add(matching_panel);
		frame.pack();
		frame.setVisible(true);
		
		try {
			System.out.println("helloooofrom the other pageeeee");
			
			out = new ObjectOutputStream(server.getOutputStream());
//			Thread writeThread = new Thread(new Runnable() {
//				@Override
//				public void run() {
//					// TODO Auto-generated method stub
//					try{
//						
//					while (true) {
//						out.writeObject("Thank you for connecting to "+ server.getLocalSocketAddress() + "\nGoodbye!");
//					}
//					}catch(Exception e){
//						System.out.println("Catchhhhh");
//					}
//
//				}
//			});
			
//			writeThread.start();

		}

		// server.close();
		catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	public static void writeToStream(Object o){
		try {
			out.writeObject(o);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public MainClient(String s) {
		super(s);
	}
	public static void main(String[] args) {
		final MainClient begin = new MainClient("");
		begin.CreateGameAndShowGUI();
	}
	public static void closeClick() {
		for (int i = 0; i < 36; i++) {
			my_buttons[i].setIcon(null);
		}
	}

	public static void disableButtons() {
		for (int i = 0; i < 36; i++) {
			my_buttons[i].setEnabled(false);
		}
	}

	public static void enableButtons() {
		for (int i = 0; i < 36; i++) {
			my_buttons[i].setEnabled(true);
		}
	}

	static String temp = "";

	public static void previous(String a) {
		temp = a;
	}

	public static String getPrevious() {
		return temp;
	}

	public static void compareNum() {
		String nameCurrent = ClientButtons.storeNum;
		String namePrevious = getPrevious();
		// compare if they are equal
		if (cards[Integer.parseInt(nameCurrent)] == cards[Integer
				.parseInt(namePrevious)] && (nameCurrent != namePrevious)) {
			System.out.print("Hello from the other side");
			Timer t = new Timer(2000, new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					my_buttons[Integer.parseInt(nameCurrent)].setVisible(false);
					my_buttons[Integer.parseInt(namePrevious)].setVisible(false);
				}
				
			});
			t.start();
		} else {
			// not equal
		}

	}

}
