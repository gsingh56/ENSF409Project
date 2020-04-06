package preProject;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

public class GUIApp extends JFrame {

    JTextArea textArea;
    JButton insertButton;
    JButton findButton;
    JButton browseButton;
    JButton createTreeButton;
    JLabel label;
    RegistrationClient registrationClient;

    public GUIApp(String name, RegistrationClient rg) {
        
        super(name);
        registrationClient = rg;
        this.setLayout(new BorderLayout());

        this.setGUI();
        this.setButtons();
        this.setTextArea();
        this.setLabel();
        this.pack();
    }

    public class event implements ActionListener {



        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private void setLabel(){
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BorderLayout());

        label = new JLabel("Application to maintain student records");
        labelPanel.add(label, BorderLayout.CENTER);

        this.add(label,BorderLayout.NORTH);
       
    }

    private void setTextArea(){
        JPanel textAreaPanel = new JPanel();         // Panel to pack everything together
        textArea = new JTextArea(15, 30);  // text area to print the actual information
        JScrollPane scrollPane = new JScrollPane(textArea);  // scrollbars

        this.getContentPane().add(scrollPane);

        textAreaPanel.add(scrollPane, BorderLayout.CENTER);

        this.add(textAreaPanel,BorderLayout.CENTER);
    }
    
    public void updateText(String st) {
    	textArea.append(st);
    }

    public void clear() {
    	textArea.setText("");
    }
    
    private void setButtons(){
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setVisible(true);

        insertButton = new JButton("Insert");
        findButton = new JButton("Find");
        browseButton = new JButton("Browse");
        createTreeButton = new JButton("Create Tree from File");
        buttonsPanel.add(insertButton,BorderLayout.WEST);
        buttonsPanel.add(findButton,BorderLayout.CENTER);
        buttonsPanel.add(browseButton,BorderLayout.EAST);
        buttonsPanel.add(createTreeButton,BorderLayout.SOUTH);


        this.add(buttonsPanel,BorderLayout.SOUTH );
        
        //Sends 1 to server to go to insert and sets viewInsert frame to visible 
        
        insertButton.addActionListener((ActionEvent e) ->{
        	registrationClient.getSocketOut().println(1);
			registrationClient.getViewInsert().setVisible(true);
			setVisible(false);
			
		});
        
        //sends 2 to server and then asks the user for id sends it to the server
        findButton.addActionListener((ActionEvent e) ->{
        	registrationClient.getSocketOut().println(2);
        	String ID = JOptionPane.showInputDialog("Enter the ID: ");
        	registrationClient.getSocketOut().println(ID);
        	String result = "No result found";
			try {
				result = registrationClient.getSocketIn().readLine();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			JOptionPane.showMessageDialog(this, result);
		});
        
        //sends 3 to server 
        browseButton.addActionListener((ActionEvent e) ->{
        	registrationClient.getSocketOut().println(3);
			
		});
        
        createTreeButton.addActionListener((ActionEvent e) ->{
        	registrationClient.getSocketOut().println(4);
        	String file = JOptionPane.showInputDialog("Enter the name of the input file: ");
        	registrationClient.getSocketOut().println(file);
			
		});
        

    }

    public void setGUI(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,1000);
        this.setVisible(false);
        this.setResizable(true);
    }

    

}