package server_side;

import java.io.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TCPServer extends Thread {

    private ServerSocket contactSocket;
    private Socket client;
    private static String chicken;
    private static String line;
    private static BufferedReader fromClient;
    private static DataOutputStream toClient;

    public TCPServer() throws IOException {
        this.contactSocket = new ServerSocket(9999);
        System.out.println("Single threaded TCP Server waiting for connections on port" + 9999 );      
    }
    
    @Override
    public void run(){
        while(true)
        {        
            try {    
                Socket client = contactSocket.accept(); // creat communication socket
                System.out.println("Connection with: "+client.getRemoteSocketAddress());
                handleRequests(client);
                client.close();
            } catch (IOException ex) {
                Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
   

    static void handleRequests(Socket s) {
        try {
            fromClient = new BufferedReader(        // Datastream FROM Client
            new InputStreamReader(s.getInputStream()));
            toClient = new DataOutputStream(
            s.getOutputStream());                 // Datastream TO Client
            sendResponse();
            fromClient.close();
            toClient.close();
            s.close();
            System.out.println("Session ended, Server remains active");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    

    static void sendResponse() throws IOException {
        String response ="";
        response = "HTTP/1.1 200 OK \r\n";
        response += "Content-Type: text/html \r\n";
        response += "\r\n";
        response += "<p> Fridge : </p> \r\n";

        response += "<p>";
        for (String val : Fridge.amountList) {
            response += val;
        }
        response += "</p>\r\n";

        toClient.writeBytes(response);  // Send answer
    }
  
}
