package Server.CourseInfo;

import Server.DatabaseModel.*;
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
	private Database database;

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
		database = new Database(this);
	}
	
	public RegistrationApp getRegApp() {
		return regApp;
	}
	
	//For testing
	private void initialize()
	{
		/*regApp.addStudent("John", 1);
		regApp.addStudent("Violet", 2);
		regApp.addStudent("Karl", 3);
		regApp.addStudent("Lara", 4);
		regApp.addStudent("Kane", 5);
		regApp.addStudent("Jude", 6);
		regApp.addStudent("Adam", 7);
		
		Course engg233 = regApp.checkCatalogue("ENGG", 233);
		Student st = new Student ("Sarah", 8);
		st.addCompletedCourse(engg233);
		regApp.addStudent(st);
		
		Course ensf409 = regApp.checkCatalogue("ENSF", 409);
		Course phys259 = regApp.checkCatalogue("PHYS", 259);
		
		regApp.createCourseOffering(engg233, 1, 100);
		regApp.createCourseOffering(ensf409, 2, 50);
		regApp.createCourseOffering(phys259, 3, 70);
		ensf409.addPreReq(engg233);
		
		Student John = regApp.searchStudent("John");
		Student Violet = regApp.searchStudent("Violet");
		Student Karl = regApp.searchStudent("Karl");
		Student Lara = regApp.searchStudent("Lara");
		Student Kane = regApp.searchStudent("Kane");
		Student Jude = regApp.searchStudent("Jude");
		
		regApp.addRegistration(John, engg233);
		regApp.addRegistration(Violet, engg233);
		regApp.addRegistration(Karl, phys259);
		regApp.addRegistration(Lara, engg233);
		regApp.addRegistration(Kane, phys259);
		regApp.addRegistration(Jude, engg233);
		*/
		try {
			database.readFile();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
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
		socketOut.println("6. Create tree from file.");
		socketOut.println("7. Print student records");
		socketOut.println("8. Quit");
	}


	private void searchCatalogue()
	{
		try {
			//socketOut.println("Enter the course name:\0");
			String name = socketIn.readLine();
			//socketOut.println("Enter the course number:\0");
			int num = Integer.parseInt(socketIn.readLine());
			socketOut.println("multipleLines");
			socketOut.println(regApp.searchCatalogue(name, num));
			socketOut.println("\0");
		} catch (IOException e) {
			System.err.println("Communication error");
			System.err.println(e.getStackTrace());
		}
	}

	private void addCourse()
	{
		try {
			//socketOut.println("Enter student name:\0");
			String student = socketIn.readLine();
			
			//socketOut.println("Enter course name:\0");
			String name = socketIn.readLine();
			
			//socketOut.println("Enter course number:\0");
			int num = Integer.parseInt(socketIn.readLine());
			
			Student st = regApp.searchStudent(student);
			
			socketOut.println("multipleLines");
			
			if(st == null){
				socketOut.println("Error student not found.");
				socketOut.println("\0");
				return;
			}
		
			Course course = regApp.checkCatalogue(name, num);
			if(course == null){
				socketOut.println("Error course not found.");
				socketOut.println("\0");
				return;
			}
			socketOut.println(regApp.addRegistration(st, course));
			socketOut.println("\0");
		} catch (IOException e) {
			System.err.println("Communication error");
			System.err.println(e.getStackTrace());
		}
	}

	public void removeCourse()
	{
		try {
			//socketOut.println("Enter student name:\0");
			String student = socketIn.readLine();
			//socketOut.println("Enter course name:\0");
			String name = socketIn.readLine();
			//socketOut.println("Enter course number:\0");
			int num = Integer.parseInt(socketIn.readLine());
			Course course = regApp.checkCatalogue(name, num);
			
			Student st = regApp.searchStudent(student);
			
			socketOut.println("multipleLines");
			
			if(st == null){
				socketOut.println("Student not found!");
				socketOut.println("\0");
				return;
			}
			
			if(course == null){
				socketOut.println("Course not found!");
				socketOut.println("\0");
				return;
			}
			regApp.removeRegistration(st, course);
			socketOut.println("Course removed Successfully!");
			socketOut.println("\0");
		} catch (IOException e) {
			System.err.println("Communication error");
			System.err.println(e.getStackTrace());
		}
	}

	public void viewStudentCourses()
	{
		try {
			//socketOut.println("Enter student name:\0");
			String student = socketIn.readLine();
			Student st = regApp.searchStudent(student);
			
			socketOut.println("multipleLines");
			if(st == null){
				socketOut.println("Error student not found.");
				socketOut.println("\0");
				return;
			}
			
			socketOut.println(st.printRegistrations());
			socketOut.println("\0");
		} catch (IOException e) {
			System.err.println("Communication error");
			System.err.println(e.getStackTrace());
		}
	}

	private void readToTree() {
		try {
			database.readFile();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	public void menu()
	{
		try {
			while(true)
			{
				displayMenu();
				//socketOut.println("Enter your choice:\0");
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
						socketOut.println("multipleLines");
						socketOut.println(regApp.displayCourseCatalogue());
						socketOut.println("\0");
						break;
					case 5:
						viewStudentCourses();
						break;
					case 6:
						readToTree();
						break;
					case 7:
						socketOut.println("multipleLines");
						socketOut.println(regApp);
						socketOut.println("\0");
						break;
					case 8:
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

	public void run() {
		menu();
	}
}