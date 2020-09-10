
/**
 * File Name: ClientView.java
 * Author: (Bikramjeet Singh,040955651) (Arpandeep Singh,040950261)
 * Course: JAP, Lab Section: 302,301
 * Assignment: Assignment 2 Part 1
 * Date: 27/07/2020
 * Professor: Daniel Cormier
 * Purpose:   The class ClientView is responsible for building the client GUI.
 * Class List: ClientView
 */

import java.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Bikramjeet singh, Arpandeep Singh
 * @version 1
 * @since 1.8.0_211
 *
 */


public class ClientView extends JPanel implements ActionListener {

    private static final long serialVersionUID = 2478695548748437141L;
    //send button
    private JButton send;
    //host textfield
    private JTextField host;
    // command area
    private JTextField command;
    //display area
    private JTextArea displayArea;
    //connect button
    private JButton connect;
    //combo for port numbers
    private JComboBox<String> combo;
    Socket socket;

    /**
     * Default constructor to initialize the view panel.
     */

    public ClientView() {

        //ports in combo
        String ports[] = new String[]{"", "8088", "65000", "65535"};
        //host label
        JLabel hostLabel = new JLabel("Host: ");
        //port label
        JLabel portLabel = new JLabel("Port: ");

        JPanel hostPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel portPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        //connection panel
        JPanel connectionPanel = new JPanel(new BorderLayout());
        //client request panel
        JPanel clientRequestPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        //display panel
        JPanel displayPanel = new JPanel(new BorderLayout());
        //upper panel
        JPanel upperPanel = new JPanel(new BorderLayout());
        //scroll pane for terminal pane
        JScrollPane scrollPane;

        //used to set properties of host label
        hostLabel.setPreferredSize(new Dimension(40, 25));
        hostLabel.setDisplayedMnemonic('H');
        hostLabel.setLabelFor(host);
        portLabel.setPreferredSize(new Dimension(40, 25));
        portLabel.setDisplayedMnemonic('P');

        //used to set properties of host text field
        host = new JTextField("localhost");
        host.setAlignmentX(LEFT_ALIGNMENT);
        host.setBackground(Color.WHITE);
        host.setEditable(true);
        host.setMargin(new Insets(0, 5, 0, 0));
        host.setPreferredSize(new Dimension(500, 25));


        //combo box for port numbers
        combo = new JComboBox<String>(ports);
        combo.setPreferredSize(new Dimension(100, 25));
        combo.setBackground(Color.WHITE);
        combo.setEditable(true);
        portLabel.setLabelFor(combo);

        //connect button
        connect = new JButton("Connect");
        connect.setMnemonic('C');
        connect.setPreferredSize(combo.getPreferredSize());
        connect.setBackground(Color.RED);
        connect.setActionCommand("Connect");
        connect.addActionListener(this);

        //command text field
        command = new JTextField("Type server request command");

        command.setPreferredSize(new Dimension(470, 25));
        command.setAlignmentX(Component.LEFT_ALIGNMENT);
        command.setBackground(Color.WHITE);
        command.setEditable(true);

        //send button in command
        send = new JButton("Send");
        send.setMnemonic('S');
        send.setEnabled(false);
        send.setPreferredSize(new Dimension(70, command.getPreferredSize().height));
        send.addActionListener(this);
        send.setActionCommand("Send");
        //set display area and set its properties
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setRows(16);
        displayArea.setColumns(40);
        displayArea.setAlignmentX(Component.LEFT_ALIGNMENT);

        //scrollPane for the text area
        scrollPane = new JScrollPane(displayArea);

        // set title for connection panel
        connectionPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.RED, 10),
				"CONNECTION"));
        // set title for client request panel
        clientRequestPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 10)
				, "CLIENT REQUEST"));


        // set title for display panel
        displayPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE, 10),
				"DISPLAY", TitledBorder.CENTER, TitledBorder.CENTER));


        setLayout(new BorderLayout());

        //adding hostLabel and host to hostPanel
        hostPanel.add(hostLabel);
        hostPanel.add(host);


        portPanel.add(portLabel);
        portPanel.add(combo);
        portPanel.add(connect);

        /*adding components to connectionPanel*/
        connectionPanel.add(hostPanel, BorderLayout.NORTH);
        connectionPanel.add(portPanel, BorderLayout.SOUTH);

        //adding command and send button to clientRequestPanel using flow Layout.
        clientRequestPanel.add(command);
        clientRequestPanel.add(send);

        //adding components to the respective panels
        displayPanel.add(scrollPane, BorderLayout.CENTER);
        upperPanel.add(connectionPanel, BorderLayout.NORTH);
        upperPanel.add(clientRequestPanel, BorderLayout.SOUTH);

        //adding panels to the main application panel.
        add(upperPanel, BorderLayout.NORTH);
        add(displayPanel, BorderLayout.CENTER);

    }

    /**
     * @param event - Action performed by the user
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        String action = event.getActionCommand();
        switch (action) {
            case "Connect":
                int port;
                String hostName = host.getText();
                try {
                    port = Integer.parseInt(combo.getSelectedItem().toString());
                } catch (Exception e) {
                    return;
                }
                try {
                    socket = new Socket(InetAddress.getByName(hostName), port);
                    socket.setSoTimeout(10000);
                    /*if socket is connected , setting the properties of send and connect buttons 
                     * and displaying the message on client window*/
                    if (socket.isConnected()) {
                        send.setEnabled(true);
                        connect.setEnabled(false);
                        connect.setBackground(Color.BLUE);
                        displayArea.append("Connected to " + socket.toString() + "\n");
                    } else {
                    	send.setEnabled(false);
                        connect.setEnabled(true);
                        connect.setBackground(Color.RED);
                        displayArea.append("Cannot connect to " + socket + "\n");
                    }
                }  catch (UnknownHostException un) {
                    displayArea.append("CLIENT>ERROR: Unkown host.\n");
                    return;
                } catch (IllegalArgumentException ill ) {
                    displayArea.append(
                        "CLIENT>ERROR: Connection refused: server is not avaliable. Check port or restart server.\n");
                    return;
                } catch (IOException io) {
                    displayArea.append(
                            "CLIENT>ERROR: Connection refused: server is not avaliable. Check port or restart server.\n");
                        return;
                    }
                break;
            case "Send":
                try {
                    InputStreamReader isr = new InputStreamReader(socket.getInputStream());
                    BufferedReader reader = new BufferedReader(isr);
                    PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                    String message = command.getText();
                    String response = "";
                    /*sending the request to server*/
                    writer.println(message);
                    /*receiving the response from server*/
                    response = reader.readLine();
                    System.out.println(response.toString());
                    /*clearing the screen if cld is received as response*/
                    if(response.equals("cld")) {
                    	displayArea.setText("");
                    }else if(response.equals("Available Services:*end*echo*time*date*help*cld")) {
                    	String[] services = response.toString().split("*");
                    	System.out.println(services.length);
                    	for(String service : services) {
                    		displayArea.append(service+"\n");
                    	}displayArea.append("\n");
                    }else displayArea.append("SERVER>"+response+"\n");
                    
                    /*closing the connection */
					if(response.equals("Connection Closed")){
						socket.close();
						isr.close();
						reader.close();
						writer.close();
						displayArea.append("CLIENT> Connection Closed\n");
						send.setEnabled(false);
                        connect.setEnabled(true);
                        connect.setBackground(Color.RED);
						
					}
                } catch(SocketException se) {
                
                	send.setEnabled(false);
                    connect.setEnabled(true);
                    connect.setBackground(Color.RED);
                	 displayArea.append("CLIENT>ERROR: SocketException. Check if server is runnnig\n");
                }catch (IOException e) {
                    displayArea.append("CLIENT>ERROR: An Error Occured\n");
                } 
                break;
        }
        // TODO Auto-generated method stub

    }
}
