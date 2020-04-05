package clientSide;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class RegistrationClient {
	private PrintWriter socketOut;
	private Socket registrationSocket;
	private BufferedReader stdIn;
	private BufferedReader socketIn;

	/**
	 * Constructor for class RegistrationClient.
	 * @param serverName String: the name of the server the client should connect to.
	 * @param portNumber int: the port the client should use to connect.
	 */
	public RegistrationClient(String serverName, int portNumber){
		try {
			registrationSocket = new Socket(serverName, portNumber);
			stdIn = new BufferedReader(new InputStreamReader(System.in));
			socketIn = new BufferedReader(new InputStreamReader(
				registrationSocket.getInputStream()));
			socketOut = new PrintWriter(registrationSocket.getOutputStream(), true);
		} catch (IOException e) {
			System.err.println(e.getStackTrace());
		}
	}

	/**
	 * Gets input from the user and sends it to the server.
	 * Then gets output from the server and displays it to the client.
	 */
	public void communicate() {
		try {
			while(true){
				String input = "";
				while(true) {
					input = socketIn.readLine();
					if (input.contains("\0")){
						System.out.println(input.replace("\0", ""));
						break;
					}
					if(input.toUpperCase().equals("QUIT")){
						return;
					}
					System.out.println(input);
				}
				if(input.equals("#7")) {
					input = readFromFile();
				} else {
					input = stdIn.readLine();
				}
				socketOut.println(input);
				socketOut.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String readFromFile() {
		StringBuilder result = new StringBuilder();
		System.out.println("Enter a filename:");
		BufferedReader fileReader = null;
		try {
			String fileName = stdIn.readLine();
			fileReader = new BufferedReader(new FileReader(fileName));
			String line;
			while((line = fileReader.readLine()) != null) {
				result.append(line + " ");
			}
		} catch (IOException e) {
			System.err.println("Error getting fileName");
			System.err.println(e.getStackTrace());
		} finally {
			try {
				if(fileReader != null) {
					fileReader.close();
				}
			} catch (IOException e) {
				System.err.println("Error closing file reader");
				System.err.println(e.getStackTrace());
			}
		}
		return result.toString();
	}
	
	/**
	 * Runs the client at localhost:3142.
	 * @param args String[]: arguments given to the program.
	 */
	public static void main(String [] args){
		RegistrationClient registrationClient = new RegistrationClient("localhost", 3142);
		registrationClient.communicate();
	}
}
