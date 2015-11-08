package NET;

import java.awt.Dimension;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class WaitingPage extends JFrame {
	public static ServerSocket serverSocket;
	public static Socket server;
	public WaitingPage(String s) {
		super(s);
	}
	public void createPage() {
		WaitingPage frame = new WaitingPage("Waiting");
		frame.setPreferredSize(new Dimension(800, 800));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JLabel label = new JLabel("YOU HAVE TO WAIT !");
		frame.add(label);
		frame.pack();
		frame.setVisible(true);
		System.out.print("hwllo");
		try {
			serverSocket = new ServerSocket(19998);
			System.out.print("hello1");
			server = serverSocket.accept();
			System.out.print("hello2");
			MainPage goto_Main = new MainPage("");
			goto_Main.CreateGameAndShowGUI();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	public static void main(String[] args) {


	}
}
