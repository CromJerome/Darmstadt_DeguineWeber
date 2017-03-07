/*
 * 22. 10. 10
 */

/**
 *
 * @author Peter Altenberd
 * (Translated into English by Ronald Moore)
 * Computer Science Dept.                   Fachbereich Informatik
 * Darmstadt Univ. of Applied Sciences      Hochschule Darmstadt
 */

import java.io.*;
import java.net.*;

public class TCPClient {

  private String line;
  private Socket socket;
  private BufferedReader fromServer;
  private DataOutputStream toServer;
  private UserInterface user = new UserInterface();
  private int port;
  private String host;


  public TCPClient(String host, int port){
    this.host = host;
    this.port = port;
  }

  public void startTCPClient() throws IOException{
    socket = new Socket(host, port);
    toServer = new DataOutputStream(     // Datastream FROM Server
            socket.getOutputStream());
    fromServer = new BufferedReader(     // Datastream TO Server
            new InputStreamReader(socket.getInputStream()));
    while (sendRequest()) {              // Send requests while connected
      receiveResponse();                 // Process server's answer
    }
    socket.close();
    toServer.close();
    fromServer.close();
  }

  private boolean sendRequest() throws IOException {
    boolean holdTheLine = true;          // Connection exists
    user.output("Enter message for the Server, or end the session with . : ");
    toServer.writeBytes((line = user.input()) + '\n');
    if (line.equals(".")) {              // Does the user want to end the session?
      holdTheLine = false;
    }
    return holdTheLine;
  }

  private void receiveResponse() throws IOException {
    user.output("Server answers: " +
            new String(fromServer.readLine()) + '\n');
  }
}
