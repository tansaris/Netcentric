import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
public class TrySocket {
    


	public static void main(String[] args){
		Socket MyClient = null;
		try {
			MyClient = new Socket("Machine name", 9988);
		}
		catch (IOException e) {
			System.out.println(e);
		}
		DataInputStream input = null;
	    try {
	       input = new DataInputStream(MyClient.getInputStream());
	       input.readInt();
	       System.out.print(input);
	    }
	    catch (IOException e) {
	       System.out.println(e);
	    }
	    PrintStream output = null;
	    try {
	       output = new PrintStream(MyClient.getOutputStream());
	       
	    }
	    catch (IOException e) {
	       System.out.println(e);
	    }
	    DataOutputStream output1;
	    try {
	       output1 = new DataOutputStream(MyClient.getOutputStream());
	    }
	    catch (IOException e) {
	       System.out.println(e);
	    }
	    try {
	           output.close();
	           input.close();
	       MyClient.close();
	    } 
	    catch (IOException e) {
	       System.out.println(e);
	    }

	}

}
