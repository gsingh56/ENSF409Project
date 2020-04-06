package preProject;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.*;

/**
 * This class creates a main GUI for the program and handle its functionality.
 * 
 * @author Gurmukh Singh
 * @version 1.0
 * @since 05 April, 2020
 */
public class GUIApp extends JFrame {
	
	/**
	 * text area of main GUI for printing Data
	 */
    JTextArea textArea;
    /**
     * insert button of GUI
     */
    JButton insertButton;
    /**
     * find button of GUI
     */
    JButton findButton;
    /**
     * browse button of GUI
     */
    JButton browseButton;
    /**
     * createTree button of GUI
     */
    JButton createTreeButton;
    /**
     * header of the main GUI
     */
    JLabel label;
    /**
     * object of type client
     */
    RegistrationClient registrationClient;
    
    /**
     * creates object of GUIApp with specified field and client
     * @param name Name of GUI
     * @param rg client of program
     */

    public GUIApp(String name, RegistrationClient rg) {
        
        super(name);
        registrationClient = rg;
        
        //organizes panels in border layout
        this.setLayout(new BorderLayout());

        this.setGUI();
        
        this.setButtons();
        
        this.setTextArea();
        
        this.setLabel();
        
        this.pack();
    }

    /**
     * creates an object of JLabel and adds them to the frame by using a panel.
     */
    private void setLabel(){
    	
        JPanel labelPanel = new JPanel();
        
        //sets the panel in border layout
        labelPanel.setLayout(new BorderLayout());

        label = new JLabel("An Application to Maintain Student Records");
        label.setHorizontalAlignment(JLabel.CENTER);
        labelPanel.add(label, BorderLayout.CENTER);
        
        //adds panel to the NORTH of frame
        this.add(label,BorderLayout.NORTH);  
    }

    /**
     * created an object of JTextArea and adds them to the frame by using a panel
     */
    private void setTextArea() {
        JPanel textAreaPanel = new JPanel();         // Panel to pack everything together
        textArea = new JTextArea(15, 40);  // text area to print the actual information
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);  // scrollbars

        this.getContentPane().add(scrollPane);

        textAreaPanel.add(scrollPane, BorderLayout.CENTER);
        
        //adds panel to the CENTER of the frame.
        this.add(textAreaPanel,BorderLayout.CENTER);
    }
    
    /**
     * appends Strings to the text area
     */
    public void updateText(String st) {
    	textArea.append(st);
    }

    /**
     * clear the text area
     */
    public void clear() {
    	textArea.setText("");
    }
    
    /**
     * Creates the objects of JButton and adds them to the frame by using a panel
     */
    private void setButtons() {
    	
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setVisible(true);

        //create button objects
        insertButton = new JButton("Insert");
        findButton = new JButton("Find");
        browseButton = new JButton("Browse");
        createTreeButton = new JButton("Create Tree from File");
        
        //adds button objects to the buttonsPanel
        buttonsPanel.add(insertButton,BorderLayout.WEST);
        buttonsPanel.add(findButton,BorderLayout.CENTER);
        buttonsPanel.add(browseButton,BorderLayout.EAST);
        buttonsPanel.add(createTreeButton,BorderLayout.SOUTH);


        //adds buttonPanel to the SOUTH of frame
        this.add(buttonsPanel,BorderLayout.SOUTH );
        
        // sets viewInsert frame to visible 
        insertButton.addActionListener((ActionEvent e) ->{
        	
			registrationClient.getViewInsert().setVisible(true);
			setVisible(false);
			
		});
        
        //sends 2 to server and then asks the user for id sends it to the server
        findButton.addActionListener((ActionEvent e) ->{
        	String ID = JOptionPane.showInputDialog("Enter the ID: ");
        	//if user closes the dialog box, then do nothing
        	if(ID != null) {
        		registrationClient.getSocketOut().println(2);
	        	registrationClient.getSocketOut().println(ID);
	        	String result = "No result found";
				try {
					result = registrationClient.getSocketIn().readLine();
				} catch (IOException e1) {
					System.err.println("Error getting ID.");
					e1.printStackTrace();
				}
				JOptionPane.showMessageDialog(this, result);
        	}
		});
        
        //sends 3 to server 
        browseButton.addActionListener((ActionEvent e) ->{
        	registrationClient.getSocketOut().println(3);
		});
        
        //populate binary search tree
        createTreeButton.addActionListener((ActionEvent e) ->{
        	registrationClient.getSocketOut().println(4);
        	String file = JOptionPane.showInputDialog("Enter the name of the input file: ");
        	registrationClient.getSocketOut().println(file);
		});
    }

    /**
     * setup for the GUI 
     */
    public void setGUI(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,1000);
        this.setVisible(false);
        this.setResizable(true);
    }
}