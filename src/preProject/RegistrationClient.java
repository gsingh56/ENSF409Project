package preProject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class RegistrationClient {
	
	private PrintWriter socketOut;
	private Socket registrationSocket;
	private BufferedReader socketIn;
	private ViewInsert viewInsert;
	private GUIApp guiApp;

	/**
	 * Constructor for class RegistrationClient.
	 * @param serverName String: the name of the server the client should connect to.
	 * @param portNumber int: the port the client should use to connect.
	 */
	public RegistrationClient(String serverName, int portNumber){
		try {
			registrationSocket = new Socket(serverName, portNumber);
			new BufferedReader(new InputStreamReader(System.in));
			socketIn = new BufferedReader(new InputStreamReader(
				registrationSocket.getInputStream()));
			socketOut = new PrintWriter(registrationSocket.getOutputStream(), true);
			viewInsert = new ViewInsert("", this);
			guiApp = new GUIApp("", this);
		} catch (IOException e) {
			System.err.println(e.getStackTrace());
		}
	}
	
	/**
	 * returns the PrintWriter object
	 * @return Printwriter
	 */
	public PrintWriter getSocketOut() {
		return socketOut;
	}
	
	/**
	 * returns the BufferedReader object
	 * @return BufferedReader
	 */
	public BufferedReader getSocketIn() {
		return socketIn;
	}
	
	/**
	 * returns the GUIApp object
	 * @return GUIApp
	 */
	public GUIApp getGUIApp() {
		return guiApp;
	}
	
	/**
	 * inserts the ViewInsert object
	 * @return ViewInsert
	 */
	public ViewInsert getViewInsert() {
		return viewInsert;
	}

	/**
	 * Gets input from the user and sends it to the server.
	 * Then gets output from the server and displays it to the client.
	 */
	public void communicate() {
		try {
			while(true){
				guiApp.setVisible(true);
				String input = "";
				
					input = socketIn.readLine();
					
					//if output from server has "update", it calls the update method
					if(input.equals("update")) {
						update();
					}
					
					System.out.println(input);
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Prints the tree contents in the text area of GUIApp
	 */
	private void update() {
		guiApp.clear();
		try {
			String input = "";
			
			//prints the contents until it hits "\0"
			while(true) {
				input = socketIn.readLine();
				if(input.contains("\0")) {
					break;
				}
				guiApp.updateText(input + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
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