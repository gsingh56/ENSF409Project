
package preProject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
/**
 * This class handles the operations on the binary search trees and socket from the server. This class
 * receives the user input from the server and calls the appropriate method from the class BinarySearchTree.
 * @author Gurmukh Singh, Dillon Sahadevan and Eduardo Benetti
 * @version 1.0
 * @since 05 April, 2020
 *
 */

public class BinaryTreeOperations implements Runnable{
	
	/**
	 * binarySearchtree object 
	 */
	private BinSearchTree binTree;
	/**
	 * Socket
	 */
	private Socket registrationSocket;
	/**
	 * PrintWriter object to return the output
	 */
	private PrintWriter socketOut;
	/**
	 * BufferReader object to read input from server
	 */
	private BufferedReader socketIn;
	
	/**
	 * Creates BinaryTreeOperations wit specified fields
	 * @param aSocket Socket 
	 */
	public BinaryTreeOperations(Socket aSocket ) {
		try {
			binTree = new BinSearchTree();
			registrationSocket = aSocket;
			socketIn = new BufferedReader(new InputStreamReader(
					registrationSocket.getInputStream()));
			socketOut = new PrintWriter(registrationSocket.getOutputStream(), true);
		} catch (IOException e) {
				System.err.println(e.getStackTrace());
		}
	}
	
	/**
	 * Reads input from the text file and populate binary search tree.
	 * 
	 * @throws IOException throws exception if file could not be read
	 */
	public void readTheFile() throws IOException{
		String fileName = "";
		try {
			fileName = socketIn.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		File file = new File(fileName);
		
		BufferedReader br = null;
		
		try {
		 br = new BufferedReader(new FileReader(file));
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		String line;
		while((line = br.readLine()) != null) {
			
			ArrayList<String> wordFile = new ArrayList<String>();
			
			//removes punctuation from string	
			String[] words = line.split(" ");
			 
			 //removes any empty strings from the words and add them to array list wordFile
			for(String word: words) {
				if(!word.contentEquals("")) {	
					wordFile.add(word);
				}
			}
			 //inserts node to the binary search tree by calling insert function of BinarySearchTree.
			binTree.insert(wordFile.get(0), wordFile.get(1), wordFile.get(2), wordFile.get(3));
		}
	}
	
	/**
	 * Inserts the Node to the binary search tree.
	 */
	public void insert() {
		String info = "";
		try {
			info = socketIn.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//split the string and inserts Node to the tree.
		String [] words = info.split(" ");
		binTree.insert(words[0], words[1], words[2], words[3]);
		sendToClient(binTree.toString(), "update");
	}
	
	private void sendToClient(String st, String flag) {
		//passes flag to server.
		socketOut.println(flag);
		
		//Outputs data
		socketOut.println(st);
		
		//prints "\0" to indicate the end of the output
		socketOut.println("\0");
	}
	
	/**
	 * Finds the Data object by its ID by calling find method of BinarySearchTree. If found,
	 *  passes the Data to the server.
	 */
	public void find() {
		String ID = "";
		try {
			ID = socketIn.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String st = "Key not found!";
		Node temp = binTree.find(binTree.root, ID);
		//If temp is not null, it passes temp's toString method to server, else passes "key not find!".
		if(temp != null) {
			 st = temp.toString();
		}
		sendToClient(st, "found");
	}
	
	/**
	 * Prints the tree using printTree method of BinarySearchTree.
	 */
	public void printTree() {
		sendToClient(binTree.toString(), "update");
	}
	
	/**
	 * Receives the input from user and run appropriate method.
	 */
	public void menu()
	{
		try {
			while(true)
			{
				String input = socketIn.readLine();
				int choice = Integer.parseInt(input);
				switch(choice)
				{
					case 1:
						insert();
						break;
					case 2:
						find();
						break;
					case 3:
						printTree();
						break;
					case 4:
						readTheFile();
						break;
					default:
						socketOut.println("Invalid input!!");
						break;
				}
			}
		} catch (IOException e) {
			System.err.println("Communication error");
			System.err.println(e.getStackTrace());
		}
	}

	/**
	 * run method of BinaryTreeOperations.
	 * calls the menu method.
	 */
	@Override
	public void run() {
		menu();
	}
}
