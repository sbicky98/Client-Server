/**File Name: ServerSocketRunnable.java
 * Author: (Bikramjeet Singh,040955651) (Arpandeep Singh,040950261)
 * Course: JAP, Lab Section: 302,301
 * Assignment: Assignment 2 Part 2
 * Date: 7/08/2020
 * Professor: Daniel Cormier
 * Purpose: GUI for the Client Application
 * Class List: Server Socket class  
 */
import java.io.*;
import java.net.*;
import java.time.*;
import java.util.Scanner;
/**
 * 
 * @author Bikramjeet singh, Arpandeep Singh
 *
 */
public class ServerSocketRunnable implements Runnable {

    /*incomming socket instance*/
	Socket socket = null;
	/*command extracted from the client request*/
	private String command = "";
	/*optional string extracted from client request*/
	private String optional = "";
	/*client request string containing command and optional string*/
	private String inputString = "";

	/**
	 * 
	 * @param socket
	 */
	public ServerSocketRunnable(Socket socket) {
		this.socket = socket;
	}

	/**
	 * Method to get month Name from the month number
	 * @param monthIndex
	 * @return name of the month
	 */
	String getMonthName(int monthIndex) {
		String months[] = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
				"October", "November", "December" };
		return months[monthIndex - 1];
	}

	@Override
	/**
	 *Method to write response to the client request
	 */
	public void run() {
		
		/*input stream*/
		InputStreamReader inStream =null;
		BufferedReader input = null;
		PrintWriter out = null;
		try {

			if (socket == null)
				return;
			try {
				 inStream = new InputStreamReader(socket.getInputStream());
				 input = new BufferedReader(inStream);
				 out = new PrintWriter(socket.getOutputStream(), true);
				
			    
				boolean done = false;
				/*Looping until end command is received from the client*/
				while (!done) {
					     /*reading the client request from the input stream*/
					    inputString = input.readLine().toString();
					    
					if (inputString.startsWith("-")) {
						inputString = inputString.substring(1);
						String[] commandArray = inputString.split("-", 2);
						command = commandArray[0];
						if (commandArray.length == 2) optional = commandArray[1];
                       
						/*responses according to the command received*/
						switch (command) {
						case "echo":
							out.println("ECHO: " + optional);
							break;
						case "time":
							LocalDateTime time = LocalDateTime.now();

							out.println(String.format("TIME: %2s:%2s:%2s %s", time.getHour()<10?"0"+time.getHour():time.getHour(), time.getMinute()<10?"0"+time.getMinute():time.getMinute(),
									time.getSecond()<10?"0"+time.getSecond():time.getSecond(), time.getHour() > 11 ? "PM" : "AM"));
							break;
						case "date":
							LocalDateTime date = LocalDateTime.now();
							out.println("DATE: " + date.getDayOfMonth() + " " + this.getMonthName(date.getMonthValue())
									+ " " + date.getYear());
							break;
						case "help":
							out.println("Available Services:*end*echo*time*date*help*cld");
							break;
						case "cld":
							out.println("cld");
							break;
						case "end":
							out.println("Connection Closed");
							/*breaking the loop when end command is received*/
							done = true;
							System.out.println("Server Socket: Closing client connection...");
							break;
						default:
							out.println("ERROR: Unrecognized command");
							break;

						}
						/*making the thread sleep after every iteration*/
						Thread.sleep(100);
						
					}else out.println("ERROR: Unrecognized command");

				}
				/*handling when client window closes*/
			}catch(SocketException se) {
				System.out.println("Server Socket: Connection to the client lost");
			}
			/*handling any other exception*/
			catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println("An Error Occured!");
			} finally {
				/*closing the sockets and streams in the end*/
				socket.close();
				inStream.close();
				input.close();
				out.close();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
		
			System.out.println("An Error Ocurred");
		}

	}

}
