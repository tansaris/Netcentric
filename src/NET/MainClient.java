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
	static JLabel time_label;
	static JLabel turn_label;
	BufferedImage img;
	String input;
	Boolean firsttime = true;
	String playerName;
	static String oppoName;
	static ClientButtons my_buttons[] = new ClientButtons[36];
	static String[] filename = { "1", "2", "3", "4", "5", "6", "7", "8", "9",
			"10", "11", "12", "13", "14", "15", "16", "17", "18" };
	static int[] cards;
	int[] card_temp;
	static ObjectOutputStream out;
	int count;
	static boolean myTurn = false;
	JLabel my_name;
	JLabel oppo_name;
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
			}
			Thread readThread = new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try{
						//final ObjectInputStream in;
						//in = new ObjectInputStream(server.getInputStream());
						while (true) {
							Object x = in.readObject();
							input = x.toString();
							System.out.println("Input: "+input);
							try{
								Answer ans = (Answer)x;
								if(ans.status){
									my_buttons[ans.num1].setVisible(false);
									my_buttons[ans.num2].setVisible(false);
									my_name.setText(ChoosePage.name +": "+ans.cScore);
									System.out.println("Correct Name: "+playerName);
									oppo_name.setText(oppoName+": "+ans.sScore);
								}else{
									changeTurn();
									my_name.setText(ChoosePage.name+": "+ans.cScore);
									oppo_name.setText(oppoName+": "+ans.sScore);
								}
								closeClick();
								continue;
							}catch(Exception e){
								
							}
							if(input.equals("Stop")){
								try{
									MainClient.disableButtons();
								}catch(Exception e){
									continue;
								}
								
							}else if(input.equals("Start")){
								MainClient.enableButtons();
							}
							else{
								// to click buttons
								try{
									int click = Integer.parseInt(input);
									System.out.println("Click rcv: "+ click);
									enableButtons();
									my_buttons[click].doClick();
									System.out.println("Clicked");
								}catch(Exception e){
									oppoName = input;
									System.out.println("Update name: "+oppoName);
								}
								
							}
							
						}
					}catch(Exception e){
						e.printStackTrace();
						System.out.println("Catchhhhh");
					}

				}
			});
			readThread.start();
			//input = in.readObject().toString();
			// to click buttons
			//int click = Integer.parseInt(input);
			//my_buttons[click].doClick();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		MainPage frame = new MainPage("Card Game");
		frame.setPreferredSize(new Dimension(800, 800));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		// INFO
		top_panel = new JPanel(new GridLayout(2, 2, 2, 0));
		my_name = new JLabel(ChoosePage.name + ": 0");
		turn_label = new JLabel("This is " + oppoName +" 's turn");
		while(oppoName ==null);
		oppo_name = new JLabel(oppoName + ": 0");
		time_label = new JLabel("Time left : 10 sec");
		top_panel.add(my_name);
		top_panel.add(oppo_name);
		top_panel.add(turn_label);
		top_panel.add(time_label);
		frame.add(top_panel, BorderLayout.NORTH);
		
		// Creating Buttons
		matching_panel = new JPanel(new GridLayout(6, 6, 4, 4));
		
		while(cards==null);
		
		for (int i = 0; i < 36; i++) {
			my_buttons[i] = new ClientButtons();
			my_buttons[i].setActionCommand(i + "");
			matching_panel.add(my_buttons[i]);
		}
		frame.add(matching_panel);
		frame.pack();
		frame.setVisible(true);
		MainClient.disableButtons();
		try {
			out = new ObjectOutputStream(server.getOutputStream());
			writeToStream(ChoosePage.name);
		}
		catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	public static void writeToStream(Object o){
		try {
//			System.out.println("Sent: "+ o.toString());
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
	public static void changeTurn(){
		System.out.println("Change turn to "+ !myTurn);
		if(myTurn){
			myTurn = false;
			MainClient.turn_label.setText("This is " + oppoName +" 's turn");
		}else{
			myTurn = true;
			MainClient.turn_label.setText("This is " + ChoosePage.name +" 's turn");
		}
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
