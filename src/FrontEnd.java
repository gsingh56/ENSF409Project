import java.util.Scanner;

public class FrontEnd
{
	private RegistrationApp regApp;

	public FrontEnd(){
		regApp = new RegistrationApp();
	}

	//Simulation purpose only.
	public void Initialize()
	{
		regApp.addStudent("John", 1);
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
	}

	private void displayMenu()
	{
		System.out.println("\n\n         COURSE REGISTRATION MENU");
		System.out.println("------------------------------------------");
		System.out.println("1. Search course catalogue.");
		System.out.println("2. Add course to student's courses.");
		System.out.println("3. Remove course from student's courses.");
		System.out.println("4. View all courses in the catalogue.");
		System.out.println("5. View all courses taken by the student.");
		System.out.println("6. Quit");
	}

	private void enterToContinue()
	{
		Scanner scan = new Scanner(System.in);
		System.out.println("----- PRESS ENTER TO CONTINUE -----");
		scan.nextLine();
	}

	private void searchCatalogue()
	{
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter the course name:");
		String name = scan.nextLine();
		System.out.println("Enter the course number:");
		int num = Integer.parseInt(scan.nextLine());
		regApp.searchCatalogue(name, num);
	}

	private void addCourse()
	{
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter student name:");
		String student = scan.nextLine();
		Student st = regApp.searchStudent(student);
		if(st == null){
			return;
		}
		System.out.println("Enter course name:");
		String name = scan.nextLine();
		System.out.println("Enter course number:");
		int num = Integer.parseInt(scan.nextLine());
		Course course = regApp.checkCatalogue(name, num);
		if(course == null){
			return;
		}
		regApp.addRegistration(st, course);
	}

	public void removeCourse()
	{
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter student name:");
		String student = scan.nextLine();
		Student st = regApp.searchStudent(student);
		if(st == null){
			return;
		}
		System.out.println("Enter course name:");
		String name = scan.nextLine();
		System.out.println("Enter course number:");
		int num = Integer.parseInt(scan.nextLine());
		Course course = regApp.checkCatalogue(name, num);
		if(course == null){
			return;
		}
		regApp.removeRegistration(st, course);
	}

	public void viewStudentCourses()
	{
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter student name:");
		String student = scan.nextLine();
		Student st = regApp.searchStudent(student);
		if(st == null){
			return;
		}
		st.printRegistrations();
	}

	public void menu()
	{
		Scanner scan = new Scanner(System.in);
		while(true)
		{
			displayMenu();
			System.out.println("Enter your choice:");
			String input = scan.nextLine();
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
	}

	public static void main(String [] args)
	{
		FrontEnd frontEnd = new FrontEnd();
		frontEnd.Initialize();
		frontEnd.menu();
	}
}