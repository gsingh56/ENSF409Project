package preProject;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * This class creates a secondary GUI for the program and handle its functionality.
 * 
 * @author Gurmukh Singh, Dillon Sahadevan and Eduardo Benetti
 * @version 1.0
 * @since 05 April, 2020
 */

public class ViewInsert extends JFrame{

	/**
	 * Client of the program
	 */
	RegistrationClient registrationClient;
	/**
	 * Buttons for the GUI
	 */
	JButton insert, mainWindow;
	/**
	 * Text fields
	 */
	JTextField ID, faculty, major, year;
	/**
	 * JLabel for the GUI
	 */
	JLabel  mainLabel, IDLabel, facultyLabel, majorLabel, yearLabel;

	/**
	 * Creates ViewInsert with specified fields
	 * @param s name of the frame
	 * @param rg client of the program
	 */
	public ViewInsert(String s, RegistrationClient rg ) {
		super(s);
		registrationClient = rg;
		
		//sets frame in border layout
		setLayout(new BorderLayout());
		
		addButton();
		addText();
		addMainLabel();
		setGUI();
	}
	
	/**
	 * Creates the objects of JButton and adds them to the frame by using a panel
	 */
	public void addButton() {
		
		JPanel buttonPanel = new JPanel();
		
		//sets flow layout for the panel
		buttonPanel.setLayout(new FlowLayout());
		
		insert = new  JButton("INSERT");
		mainWindow = new  JButton("GO TO MAIN WINDOW");
		
		buttonPanel.add(insert);
		buttonPanel.add(mainWindow);
		
		// adds panel to the SOUTH of the frame
		add(buttonPanel, BorderLayout.SOUTH);
		
		//sends "1" to the server and then sends the Data object information to the sever
		insert.addActionListener((ActionEvent e) ->{
			registrationClient.getSocketOut().println(1);
			
			String st = ID.getText() +" " + faculty.getText()+ " "+ major.getText() +" "+ year.getText();
			registrationClient.getSocketOut().println(st);
			
			transitionMainWindow();
			clearTextField();
		});
		
		// transition back to the main window
		mainWindow.addActionListener((ActionEvent e) ->{
			registrationClient.getGUIApp().setVisible(true);
		});
	}
	
	/**
	 * Add text field and the labels to the frame.
	 */
	public void addText() {
		
		JPanel textPanel = new JPanel();
		textPanel.setLayout(new FlowLayout());
		
		//text field for the  GUI
		ID = new JTextField(10);
		faculty = new JTextField(10);
		major = new JTextField(10);
		year = new JTextField(10);
		
		//labels for the GUI
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
		
		//adds panel to the CENTER of the frame
		add(textPanel, BorderLayout.CENTER);	
	}
	
	/**
	 * adds header label for the GUI 
	 */
	public void addMainLabel(){
		
		JPanel mainLabelPanel = new JPanel();
		mainLabelPanel .setLayout(new FlowLayout());
		
		mainLabel = new JLabel("INSERT A NEW NODE");
		mainLabelPanel.add(mainLabel);
		
		add(mainLabelPanel, BorderLayout.NORTH);
	}
	
	/**
	 * setip for the GUI
	 */
	public void setGUI() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400,200);
        this.setVisible(false);
        this.setResizable(true);
    }
	
	/**
	 * clear the text field after the data is inserted to the tree.
	 */
	public void clearTextField() {
		ID.setText("");
		faculty.setText("");
		major.setText("");
		year.setText("");	
	}
	
	/**
	 * hides the ViewInsert and displays the GUIApp
	 */
	public void transitionMainWindow() {
		setVisible(false);
		registrationClient.getGUIApp().setVisible(true);
	}
}
