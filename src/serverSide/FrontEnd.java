package serverSide;

import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;

public class FrontEnd implements Runnable
{
	private RegistrationApp regApp;
	private Socket socket;
	private PrintWriter socketOut;
	private BufferedReader socketIn;

	public FrontEnd(Socket s){
		socket = s;
		try {
			socketIn = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));
			socketOut = new PrintWriter((socket.getOutputStream()), true);
		} catch (IOException e) {
			System.err.println("Error getting communcation methods");
			System.err.println(e.getStackTrace());
		}
		regApp = new RegistrationApp();
	}

	private void displayMenu()
	{
		socketOut.println("\n\n         COURSE REGISTRATION MENU");
		socketOut.println("------------------------------------------");
		socketOut.println("1. Search course catalogue.");
		socketOut.println("2. Add course to student's courses.");
		socketOut.println("3. Remove course from student's courses.");
		socketOut.println("4. View all courses in the catalogue.");
		socketOut.println("5. View all courses taken by the student.");
		socketOut.println("6. Quit");
	}

	private void enterToContinue()
	{
		try {
			socketOut.println("----- PRESS ENTER TO CONTINUE -----\0");
			socketIn.readLine();
		} catch (IOException e) {
			System.err.println("Communication error");
			System.err.println(e.getStackTrace());
		}
	}

	private void searchCatalogue()
	{
		try {
			socketOut.println("Enter the course name:\0");
			String name = socketIn.readLine();
			socketOut.println("Enter the course number:");
			int num = Integer.parseInt(socketIn.readLine());
			regApp.searchCatalogue(name, num);
		} catch (IOException e) {
			System.err.println("Communication error");
			System.err.println(e.getStackTrace());
		}
	}

	private void addCourse()
	{
		try {
			socketOut.println("Enter student name:");
			String student = socketIn.readLine();
			Student st = regApp.searchStudent(student);
			if(st == null){
				return;
			}
			socketOut.println("Enter course name:");
			String name = socketIn.readLine();
			socketOut.println("Enter course number:");
			int num = Integer.parseInt(socketIn.readLine());
			Course course = regApp.checkCatalogue(name, num);
			if(course == null){
				return;
			}
			regApp.addRegistration(st, course);
		} catch (IOException e) {
			System.err.println("Communication error");
			System.err.println(e.getStackTrace());
		}
	}

	public void removeCourse()
	{
		try {
			socketOut.println("Enter student name:");
			String student = socketIn.readLine();
			Student st = regApp.searchStudent(student);
			if(st == null){
				return;
			}
			socketOut.println("Enter course name:");
			String name = socketIn.readLine();
			socketOut.println("Enter course number:");
			int num = Integer.parseInt(socketIn.readLine());
			Course course = regApp.checkCatalogue(name, num);
			if(course == null){
				return;
			}
			regApp.removeRegistration(st, course);
		} catch (IOException e) {
			System.err.println("Communication error");
			System.err.println(e.getStackTrace());
		}
	}

	public void viewStudentCourses()
	{
		try {
			socketOut.println("Enter student name:");
			String student = socketIn.readLine();
			Student st = regApp.searchStudent(student);
			if(st == null){
				return;
			}
			st.printRegistrations();
		} catch (IOException e) {
			System.err.println("Communication error");
			System.err.println(e.getStackTrace());
		}
	}

	public void menu()
	{
		try {
			while(true)
			{
				displayMenu();
				socketOut.println("Enter your choice:\0");
				String input = socketIn.readLine();
				int choice = Integer.parseInt(input);
				switch(choice)
				{
					case 1:
						searchCatalogue();
						break;
					case 2:
						addCourse();
						break;
					case 3:
						removeCourse();
						break;
					case 4:
						regApp.displayCourseCatalogue();
						break;
					case 5:
						viewStudentCourses();
						break;
					case 6:
						System.exit(0);
						break;
					default:
						System.err.println("Invalid input!!");
						break;
				}
				enterToContinue();
			}
		} catch (IOException e) {
			System.err.println("Communication error");
			System.err.println(e.getStackTrace());
		}
	}

	public void run() {
		menu();
	}
}