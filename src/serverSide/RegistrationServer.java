package serverSide;

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
				/*
				Player xPlayer = new Player(serverSocket.accept(), LETTER_X);
				Player oPlayer = new Player(serverSocket.accept(), LETTER_O);

				Referee theRef = new Referee();
				theRef.setxPlayer(xPlayer);
				theRef.setoPlayer(oPlayer);

				Game theGame = new Game();
				theGame.initializeGame(theRef);

				System.out.println("Server has started a game");

				threadPool.execute(theGame);*/
				FrontEnd frontEnd = new FrontEnd(serverSocket.accept());
				System.out.println("Server has established a connection.");
				threadPool.execute(frontEnd);
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
