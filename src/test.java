import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;


public class test {
	public static void main(String[] args) {
		try {
			String serverName = "172.20.10.6";
			int port = 19998;
			Socket client = new Socket(serverName, port);
			while(true){
	         	System.out.println("Just connected to "+ client.getRemoteSocketAddress());
	         	OutputStream outToServer = client.getOutputStream();
	        	DataOutputStream out = new DataOutputStream(outToServer);
	        	Scanner sc = new Scanner(System.in);
	        	String input = sc.nextLine();
	       		out.writeUTF(input);
	         	InputStream inFromServer = client.getInputStream();
	         	DataInputStream in = new DataInputStream(inFromServer);
	         	System.out.println("Server says " + in.readUTF());
//	         	client.close();
			}
	    }
	    catch (IOException e) {
	        System.out.println(e);
	    }
	}
}
