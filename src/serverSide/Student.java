package serverSide;

import java.util.ArrayList;

public class Student {
	
	private String studentName;
	private int studentId;
	private ArrayList<CourseOffering> offeringList;
	private ArrayList<Registration> studentRegList;
	private ArrayList<Course> completedCourses;
	
	public Student (String studentName, int studentId) {
		this.setStudentName(studentName);
		this.setStudentId(studentId);
		studentRegList = new ArrayList<Registration>();
		completedCourses = new ArrayList<Course>();
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	@Override
	public String toString () {
		String st = "Student Name: " + getStudentName() + "\n" +
				"Student Id: " + getStudentId() + "\n\n";
		return st;
	}

	public void addRegistration(Registration registration) {
		// TODO Auto-generated method stub
		studentRegList.add(registration);
	}

	public void removeRegistration(Registration reg) {
		studentRegList.remove(reg);
	}

	public void addCompletedCourse(Course c){
		completedCourses.add(c);
	}

	public String printRegistrations()
	{
		StringBuilder st = new StringBuilder();
		for(Registration reg : studentRegList){
			st.append(reg.toString() + "\n");
		}
		return st.toString();
	}

	public boolean checkIfCompleted(Course c)
	{
		for(Course course : completedCourses)
		{
			if(course.getCourseName().equals(c.getCourseName()) &&
				course.getCourseNum() == c.getCourseNum()) {
				return true;
			}
		}
		return false;
	}
}
