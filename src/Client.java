
/**File Name: Client.java
 * Author: (Bikramjeet Singh,040955651) (Arpandeep Singh,040950261)
 * Course: JAP, Lab Section: 302,301
 * Assignment: Assignment 2 Part 2
 * Date: 27/07/2020
 * Professor: Daniel Cormier
 * Purpose: GUI for the Client Application
 * Class List: main class  
 */
import java.*;
import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JFrame;

/**
 * 
 * @author Bikramjeet singh,Arpandeep Singh
 * @version 1
 * @see ClientGUI
 * @since 1.8.0_211
 *
 */
public class Client {
	/**
	 * The main method to launch the frame.
	 * @param args - command line arguments
	 */
	
	public static void main(String[] args) {
		ClientView view = new ClientView();
		EventQueue.invokeLater(new Runnable() {
			/**
			 * The run method to run the frame. 
			 */
			public void run() {
				JFrame frame = new JFrame();
				frame.setTitle("Arpandeep and Bikramjeet's Client");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setMinimumSize(new Dimension(600, 550));
                frame.add(view);
                frame.setVisible(true);
			}
		});
	}

}
