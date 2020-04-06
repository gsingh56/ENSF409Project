package preProject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class BinaryTreeOperations implements Runnable{
	
	private BinSearchTree binTree;
	private Socket registrationSocket;
	private PrintWriter socketOut;
	private BufferedReader socketIn;
	
	public BinaryTreeOperations(Socket aSocket ) {
		try {
			binTree = new BinSearchTree();
			registrationSocket = aSocket;
			
				socketIn = new BufferedReader(new InputStreamReader(
						registrationSocket.getInputStream()));
			socketOut = new PrintWriter(registrationSocket.getOutputStream(), true);
		}catch (IOException e) {
				System.err.println(e.getStackTrace());
			}
			
	}
	
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
		}catch(IOException e) {
			e.printStackTrace();
		}

		
		String line;
		
		while((line = br.readLine()) != null) {
			
			 ArrayList<String> wordFile = new ArrayList<String>();
			
					//removes punctuation from string	
			 String[] words = line.split(" ");
			 
			 //removes any empty strings from the words and add them to array list wordFile
			 for(String word: words) {
				
				 if(!word.contentEquals(""))
					 {	
						 wordFile.add(word);
						 System.out.println(word);
					 }
				 }
			binTree.insert(wordFile.get(0), wordFile.get(1), wordFile.get(2), wordFile.get(3));

		}
	}
	
	// splits the string and insert the node
	public void insert() {
		String info = "";
		try {
			info = socketIn.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String [] words = info.split(" ");
		binTree.insert(words[0], words[1], words[2], words[3]);
		
	}
	

	public void find() {
		
		String ID = "";
		try {
			ID = socketIn.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String st = "Key not find!";
		Node temp = binTree.find(binTree.root, ID);
		if(temp != null) {
			System.out.println("Not null");
			 st = temp.toString();
		}
		socketOut.println("found");
		socketOut.println(st);
		//socketOut.println("\0");
	}
	
	//prints tree directly to printwriter
	public void printTree() {
		try {
			socketOut.println("update");
			binTree.print_tree(binTree.root, socketOut);
			socketOut.println("\0");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
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
					case 6:
						System.exit(0);
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


	@Override
	public void run() {
		
		menu();
	}
	

}
