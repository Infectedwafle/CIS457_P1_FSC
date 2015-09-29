import java.io.*;
//import org.apache.commons.codec.binary.Base64;  
import java.net.*;
import java.util.Scanner;

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

	    String line = in.readLine();
	    
	    String headers = line;
	    boolean header = true;
	    line = in.readLine();
	    while(header){
	    	if(header == true && line.equals("")){
	        	header = false;
	        }else if(header == true){
	        	headers += line +  "\n";
	        	
		        line = in.readLine();

	        }
	    }
	    
	    System.out.println(headers);

	    StringBuffer result = new StringBuffer();
	    line = in.readLine();
	    while(line != null){
	    	result.append(line + "\n");
	    	line = in.readLine();
	    }


	    byte dataToWrite[] = org.apache.commons.codec.binary.Base64.decodeBase64(result.getBytes());
		FileOutputStream outFile = new FileOutputStream("Transfered File" + format);
		outFile.write(dataToWrite, 0, dataToWrite.length);
		outFile.close();

    }
    /*
    Check to make sure the input is a valid ipv4 address. 
    Valid ip range is 0.0.0.1 to 255.255.255.254
    */
    public static boolean checkIP(String ip){
    	//Do work
	String[] tokens = ip.split(".");
    	if (tokens.length > 4) return false;
    	int token;
	try{
    	for (int i = 0; i < 4; i++){
    		try{token = Integer.parseInt(tokens[i]);}
    		catch(NumberFormatException e){return false;}
    		if (i != 3) {
    			if(token < 0 || token > 255) return false;
    		}
    		else if (token <1 || token > 254) return false;
    	}}catch(ArrayIndexOutOfBoundsException e){e.printStackTrace(System.out);}
    	return true;
    	//return true; //or true
    }

    /*
    Check to make sure the input is a valid port. 
    */
    public static boolean checkPort(String port){
    	//Do work
    	int input;
    	try{input = Integer.parseInt(port);}
    	catch(NumberFormatException e){return false;}
    	if(input < 0 || input > 65535) return false;
    	return true;
	//return true; //or true
    }
}
