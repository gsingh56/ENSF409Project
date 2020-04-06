package preProject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class RegistrationClient {
	
	private PrintWriter socketOut;
	private Socket registrationSocket;
	private BufferedReader stdIn;
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
			stdIn = new BufferedReader(new InputStreamReader(System.in));
			socketIn = new BufferedReader(new InputStreamReader(
				registrationSocket.getInputStream()));
			socketOut = new PrintWriter(registrationSocket.getOutputStream(), true);
			viewInsert = new ViewInsert("", this);
			guiApp = new GUIApp("", this);
		} catch (IOException e) {
			System.err.println(e.getStackTrace());
		}
	}
	
	public PrintWriter getSocketOut() {
		return socketOut;
	}
	
	public BufferedReader getSocketIn() {
		return socketIn;
	}
	
	public GUIApp getGUIApp() {
		return guiApp;
	}
	
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
				//while(true) {
					input = socketIn.readLine();
					/*if (input.contains("\0")){
						System.out.println(input.replace("\0", ""));
						break;
					}*/
					if(input.equals("update")) {
						update();
					}
					/*if(input.toUpperCase().equals("QUIT")){
						return;
					}*/
					System.out.println(input);
				//}
				/*input = stdIn.readLine();
				socketOut.println(input);
				socketOut.flush();*/
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void update() {
		guiApp.clear();
		try {
			String input = "";
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