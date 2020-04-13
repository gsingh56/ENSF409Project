package Server.DatabaseModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import Server.CourseInfo.*;

public class Database {
	
	private RegistrationApp regApp;
	private String fileName;
	
	public Database(FrontEnd frontEnd) {
		this.regApp = frontEnd.getRegApp();
		fileName = "input.txt";
	}
	
	public void readFile() throws IOException {
		File file = new File(fileName);
		
		BufferedReader br = null;
		
		try {
		 br = new BufferedReader(new FileReader(file));
		}catch(IOException e) {
			e.printStackTrace();
		}

		
		String line = "";
		
		while((line = br.readLine()) != null) {
		
			String [] words = line.split(" ");
			
			if(words[0].equals("course")){
				
				String name = words[1];
				int courseNum = Integer.parseInt(words[2]);
				int secNum = Integer.parseInt(words[3]);
				int cap = Integer.parseInt(words[4]);
				
				regApp.createCourseOffering(regApp.checkCatalogue(name, courseNum),secNum, cap);
		   }
			else if(words[0].equals("students")) {
				
				String name = words[1];
				int ID = Integer.parseInt(words[2]);
				
				regApp.addStudent(name, ID);
			}
			else if(words[0].equals("completed-course")) {
				
				String name = words[1];
				int ID = Integer.parseInt(words[2]);
				String courseName = words[3];
				int courseNum = Integer.parseInt(words[4]);
				
				Student temp = regApp.searchStudent(name);
				Course completed = regApp.checkCatalogue(courseName, courseNum);
				
				temp.addCompletedCourse(completed);
			}
			
			else if(words[0].equals("pre-req")) {
				
				String courseName1 = words[1];
				int courseNum1 = Integer.parseInt(words[2]);
				String courseName2 = words[3];
				int courseNum2 = Integer.parseInt(words[4]);
				
				Course course1 = regApp.checkCatalogue(courseName1, courseNum1);
				Course course2 = regApp.checkCatalogue(courseName2, courseNum2);
				
				course1.addPreReq(course2);
				
			}
			
			
	}
	
	}



}
