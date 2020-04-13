

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class RegistrationServer {
	private ServerSocket serverSocket;
	private ExecutorService threadPool;

	/**
	 * Construct a Server with a specified port number to run the game on.
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


	public static void main(String [] args) {
		RegistrationServer registrationServer = new RegistrationServer(3142);
		registrationServer.runServer();
	}
}
