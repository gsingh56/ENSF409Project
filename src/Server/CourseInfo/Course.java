package Server.CourseInfo;
import java.util.ArrayList;

public class Course {

	private String courseName;
	private int courseNum;
	private ArrayList<Course> preReq;
	private ArrayList<CourseOffering> offeringList;
	private int numEnrolled;

	public Course(String courseName, int courseNum) {
		this.setCourseName(courseName);
		this.setCourseNum(courseNum);
		// Both of the following are only association
		preReq = new ArrayList<Course>();
		offeringList = new ArrayList<CourseOffering>();
	}

	//Add offering to course ofering list
	public void addOffering(CourseOffering offering) {
		if (offering != null && offering.getTheCourse() == null) {
			offering.setTheCourse(this);
			if (!offering.getTheCourse().getCourseName().equals(courseName)
					|| offering.getTheCourse().getCourseNum() != courseNum) {
				System.err.println("Error! This section belongs to another course!");
				return;
			}
			offeringList.add(offering);
		}
	}

	public void addStudent(){
		numEnrolled++;
	}

	public void removeStudent(){
		numEnrolled--;
	}

	public void addPreReq(Course c){
		preReq.add(c);
	}

	public ArrayList<Course> getPreReq(){
		return this.preReq;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public int getCourseNum() {
		return courseNum;
	}

	public void setCourseNum(int courseNum) {
		this.courseNum = courseNum;
	}
	@Override
	public String toString () {
		String st = "";
		st += getCourseName() + " " + getCourseNum ();
		st += "  Students enrolled: " + numEnrolled;
		st += "\1All course sections:\1";
		for (CourseOffering c : offeringList)
			st += c;
		return st;
	}

	public String isRunning(){
		String result;
		if(numEnrolled >= 8){
			result = numEnrolled + "students enrolled. Course is running.\n";
		} else {
			result = "Only " + numEnrolled + " student(s) are currently enrolled.\n";
			result += "Course will start running after " + (8 - numEnrolled) +
					" more student(s) enroll.\n";
		}
		return result;
	}

	public CourseOffering getCourseOfferingAt(int i) {
		// TODO Auto-generated method stub
		if (i < 0 || i >= offeringList.size() )
			return null;
		else
			return offeringList.get(i);
	}

}