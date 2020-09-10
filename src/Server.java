/**File Name: Server.java
 * Author: (Bikramjeet Singh,040955651) (Arpandeep Singh,040950261)
 * Course: JAP, Lab Section: 302,301
 * Assignment: Assignment 2 Part 2
 * Date: 7/08/2020
 * Professor: Daniel Cormier
 * Purpose: GUI for the Client Application
 * Class List: Server Class 
 */
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 * @author Bikramjeet singh, Arpandeep Singh
 *
 */
public class Server {

	/**
	 * 
	 * @param args
	 */
	
	public static void main(String[] args) {
		
		/*initializing to the default port*/
		int port=65535;
		/*if command line argument is supplied at launch*/
		if (args.length > 0) {
            port = Integer.parseInt(args[0]);
            System.out.println("Using port: " + port);
        } else if(args.length==0) {
            System.out.println("Using default port: " + port);
        }

		try (ServerSocket ss = new ServerSocket(port)) {
			while (true){
				Socket incoming = ss.accept();
				System.out.println(
						"Connecting to a client Socket[addr=/"+incoming.getInetAddress()+".port="+incoming.getPort()+",localport="+incoming.getLocalPort()+"]");
				
				Thread runnableThread = new Thread(new ServerSocketRunnable(incoming));
				/*starting the thread*/
				runnableThread.start();
			}
		}catch(BindException e) {
			System.out.println("Unable to create server socket. Port already in use");
		}
		catch(IOException e) {
			System.out.println("An error occured!");
		}
	}

}
