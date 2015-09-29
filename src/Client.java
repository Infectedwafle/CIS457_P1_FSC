import java.io.*;
import java.net.*;
import java.util.Scanner;

import org.apache.commons.io.IOUtils;

class Client{
    public static void main(String args[]) throws Exception{
    	//Prompt user for ip address
    	//Wait for ip address
    	String ip_address, port;
    	
    	Scanner input = new Scanner(System.in);
    	
    	
    	System.out.print("Enter an IP address, loopback address is 127.0.0.1");
    	ip_address = input.next();
    	
    	System.out.print("Enter a port, default port is 9876");
    	port = input.next();
    	
    	
    	//Possibly add error checking for IP address
    	if(checkIP(ip_address) == true && checkPort(port) == true){
    	} else {
    		System.out.print("Not a valid ip address or port.");
    		System.exit(0);
    	}
    	
    	Socket clientSocket = new Socket(ip_address, Integer.parseInt(port));	
		
		//notify user that they are connected to server or show error
		System.out.println("Connected to server...");
		
		WebTransaction(clientSocket);
		
		//repeat or end program.
		clientSocket.close();
    }
    
    public static void WebTransaction(Socket socket) throws IOException{
    	DataInputStream in = new DataInputStream(socket.getInputStream());
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        
        //instructions for the user to communicate to server, ie: enter file name.
  		// notify user if file does not exist
  		System.out.println("Enter a file name: ");
  		String message = inFromUser.readLine();
  		String format = message.substring(message.indexOf("."), message.length());
  		System.out.println(format);
  		//send file to server ***
  		out.println(message + "\n");
  		
	    OutputStream os = new BufferedOutputStream(new FileOutputStream(message));
	    byte[] buffer = new byte[1024];
	    int bytesRead = in.read(buffer);
	    while(bytesRead > 0){
	    	os.write(buffer);
	    	bytesRead = in.read(buffer);
	    }
	    
	    os.close();
	    out.close();
	    in.close();
    }
    /*
    Check to make sure the input is a valid ipv4 address. 
    Valid ip range is 0.0.0.1 to 255.255.255.254
    */
    private static boolean checkIP(String ip){
    	//Do work
    	return true; //or true
    }
    /*
    Check to make sure the input is a valid port. 
    */
    private static boolean checkPort(String port){
    	//Do work
    	return true; //or true
    }
}