package Client.Controller;

import Client.View.*;

import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JOptionPane;

public class RegistrationClient {
	
	private PrintWriter socketOut;
	private Socket registrationSocket;
	private BufferedReader socketIn;
	private GUIAppClient guiApp;

	/**
	 * Construtor for class RegistrationClient.
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
			guiApp = new GUIAppClient("", this);
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
	public GUIAppClient getGUIApp() {
		return guiApp;
	}
	
	/**
	 * inserts the ViewInsert object
	 * @return ViewInsert
	 */
	

	/**
	 * Gets input from the user and sends it to the server.
	 * Then gets output from the server and displays it to the client.
	 */
	public void communicate() {
		guiApp.setVisible(true);
		try {
			while(true){
				
				
				String input = "";
					
						input = socketIn.readLine();
					
						
						 if(input.equals("multipleLines")) {
							multipleLines();
						}
						
						
						//System.out.println(input);
				
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			socketOut.close();
			socketIn.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private String readInput() {
		String input = "";
		String st = "";
		
		try {
			//prints the contents until it hits "\0"
			while(true) {
				input = socketIn.readLine();
				if(input.equals("\0")) {
					break;
				}
				st+= input;
				
			}
		}catch (IOException e) {
				e.printStackTrace();
			}
			return st;
			
		
	}
	
	/**
	 * Prints the tree contents in the text area of GUIApp
	 */
	private void multipleLines() {
		
			String st = readInput();
			
			
			String [] arr = st.split("\1");
			
			System.out.print(st);
			
			
		
			TextArea ta = new TextArea("","Registration Information");
			for(String string: arr){
			
				ta.updateText(string+"\n");
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