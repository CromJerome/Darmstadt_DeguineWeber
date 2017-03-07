// 22. 10. 10

/**
 *
 * @author Peter Altenberd
 * (Translated into English by Ronald Moore)
 * Computer Science Dept.                   Fachbereich Informatik
 * Darmstadt Univ. of Applied Sciences      Hochschule Darmstadt
 */

import java.io.*;
import java.net.*;

public class TCPServer {

  private String line;
  private BufferedReader fromClient;
  private DataOutputStream toClient;
  private ServerSocket contactSocket;
  private int port;

  public TCPServer(int port) throws Exception{
    this.port = port;
    contactSocket = new ServerSocket(port);
    System.out.println("Single threaded TCP Server waiting for connections on port"
            + port );
  }

  public void startTCPServer() throws IOException{
    while (true) {                            // Handle connection request
      Socket client = contactSocket.accept(); // creat communication socket
      System.out.println("Connection with: "+client.getRemoteSocketAddress());
      handleRequests(client);
    }
  }

  private void handleRequests(Socket s) {
    try {
      fromClient = new BufferedReader(        // Datastream FROM Client
        new InputStreamReader(s.getInputStream()));
      toClient = new DataOutputStream(
        s.getOutputStream());                 // Datastream TO Client
             // As long as connection exists
      sendResponse();
      fromClient.close();
      toClient.close();
      s.close();
      System.out.println("Session ended, Server remains active");
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  private void sendResponse() throws IOException {
    String response ="";
    response = "HTTP/1.1 200 OK \r\n";
    response += "Content-Type: text/html \r\n";
    response += "\r\n";
    response += "<p> Hello world </p> \r\n";
    toClient.writeBytes(response);  // Send answer
  }
}
