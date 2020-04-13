package preProject;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

/**
 * Server of the program
 * @author Gurmukh Singh, Dillon Sahadevan and Eduardo Benetti
 *
 */
public class RegistrationServer {
	/**
	 * server socket
	 */
	private ServerSocket serverSocket;
	
	/**
	 * thread pool 
	 */
	private ExecutorService threadPool;

	/**
	 * Construct a Server with a specified port number.
	 */
	public RegistrationServer(int port) {
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("Server is running...");
			threadPool = Executors.newCachedThreadPool();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Passes the socket to the BinaryTreeOperations object
	 */
	private void runServer() {
		try {
			while(true){
				
				BinaryTreeOperations binTree = new  BinaryTreeOperations(serverSocket.accept());
				System.out.println("Server has established a connection.");
				threadPool.execute(binTree);
			}
		} catch (Exception e){
			e.printStackTrace();
			threadPool.shutdown();
		}
	}

	/**
	 * main method of server
	 * @param args 
	 */
	public static void main(String [] args) {
		RegistrationServer registrationServer = new RegistrationServer(3142);
		registrationServer.runServer();
	}
}
