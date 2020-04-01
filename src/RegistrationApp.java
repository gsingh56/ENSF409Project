import java.util.ArrayList;

public class RegistrationApp {
	
	private CourseCatalogue cat;
	private ArrayList<Student> students;
	private ArrayList<Registration> registrations;

	public RegistrationApp(){
		cat = new CourseCatalogue();
		students = new ArrayList<Student>();
		registrations = new ArrayList<Registration>();
	}

	public void displayCourseCatalogue(){
		System.out.println(cat);
	}

	public void searchCatalogue(String name, int num){
		Course c = cat.searchCat(name, num);
		System.out.println((c == null ? "" : c));
	}

	public Course checkCatalogue(String name, int num){
		return cat.searchCat(name, num);
	}

	public Student searchStudent(String name)
	{
		for(Student st : students){
			if(st.getStudentName().equals(name)){
				return st;
			}
		}
		displayStudentNotFound();
		return null;
	}

	private void displayStudentNotFound(){
		System.err.println("Requested student not found.");
	}

	private boolean checkPrerequisites(Student st, Course c)
	{
		ArrayList<Course> preReq = c.getPreReq();
		for(int i = 0; i < preReq.size(); i++)
		{
			if(!st.checkIfCompleted(preReq.get(i))){
				System.err.println("This student has not completed all prerequisites");
				return false;
			}
		}
		return true;
	}

	public void removeRegistration(Student st, Course c)
	{
		for(Registration reg : registrations)
		{
			if(reg.getTheStudent().getStudentId() == st.getStudentId() &&
				reg.getTheOffering().getTheCourse().getCourseName().equals(c.getCourseName()) &&
				reg.getTheOffering().getTheCourse().getCourseNum() == c.getCourseNum()){
				reg.removeRegistration();
				c.removeStudent();
			}
		}
	}

	public void addRegistration(Student st, Course c)
	{
		if(!checkPrerequisites(st, c)){
			return;
		}
		Registration reg = new Registration();
		CourseOffering co = c.getCourseOfferingAt(0);
		reg.completeRegistration(st, co);
		System.out.println(reg);
		registrations.add(reg);
		c.addStudent();
		c.isRunning();
	}

	public void addStudent(String name, int id){
		Student st = new Student(name, id);
		students.add(st);
	}

	public void addStudent(Student st){
		students.add(st);
	}

	public void createCourseOffering(Course c, int num, int cap){
		cat.createCourseOffering(c, num, cap);
	}
}
