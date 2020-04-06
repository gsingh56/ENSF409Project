package preProject;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ViewInsert extends JFrame{

	RegistrationClient registrationClient;
	JButton insert, mainWindow;
	JTextField ID, faculty, major, year;
	JLabel  mainLabel, IDLabel, facultyLabel, majorLabel, yearLabel;

	public ViewInsert(String s, RegistrationClient rg ) {
		super(s);
		registrationClient = rg;
		setLayout(new BorderLayout());
		addButton();
		addText();
		addMainLabel();
		setGUI();
	
	}
	
	public void addButton() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		insert = new  JButton("INSERT");
		mainWindow = new  JButton("GO TO MAIN WINDOW");
		buttonPanel.add(insert);
		buttonPanel.add(mainWindow);
		add(buttonPanel, BorderLayout.SOUTH);
		
		insert.addActionListener((ActionEvent e) ->{
			String st = ID.getText() +" " + faculty.getText()+ " "+ major.getText() +" "+ year.getText();
			registrationClient.getSocketOut().println(st);
			
		});
		
		mainWindow.addActionListener((ActionEvent e) ->{
			registrationClient.getGUIApp().setVisible(true);
		});
		
	
	}
	
	public void addText() {
		
		JPanel textPanel = new JPanel();
		textPanel.setLayout(new FlowLayout());
		
		ID = new JTextField(10);
		faculty = new JTextField(10);
		major = new JTextField(10);
		year = new JTextField(10);
		
		IDLabel = new JLabel("STUDENT ID");
		facultyLabel = new JLabel("FACULTY");
		majorLabel = new JLabel("MAJOR");
		yearLabel = new JLabel("YEAR");
		
		textPanel.add(IDLabel);
		textPanel.add(ID);
		
		textPanel.add(facultyLabel);
		textPanel.add(faculty);
		
		textPanel.add(majorLabel);
		textPanel.add(major);
		
		textPanel.add(yearLabel);
		textPanel.add(year);
		
		
		add(textPanel, BorderLayout.CENTER);
		
	}
	
	public void addMainLabel(){
		JPanel mainLabelPanel = new JPanel();
		mainLabelPanel .setLayout(new FlowLayout());
		
		mainLabel = new JLabel("INSERT A NEW NODE");
		mainLabelPanel.add(mainLabel);
		add(mainLabelPanel, BorderLayout.NORTH);
		}
	
	public void setGUI(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400,200);
        this.setVisible(false);
        this.setResizable(true);
    }
	
	


	
}
