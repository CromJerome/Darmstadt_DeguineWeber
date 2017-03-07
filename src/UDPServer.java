import com.sun.org.apache.xpath.internal.operations.Bool;

import java.net.*;
import java.util.*;

public class UDPServer {
    private int port;

    public UDPServer(int port){
        this.port = port;
    }

    public void startUDPServer(){
        try
        {
            DatagramPacket packet;
            DatagramSocket socket = new DatagramSocket(port);
            System.out.println("UDP Time Server started at Port "+this.port);
            Boolean run = true;
            while ( run )
            {
                byte data[] = new byte[1024];

                // Wait for request
                packet = new DatagramPacket(data, data.length);
                socket.receive(packet);
                Frigo.amount=(new String(packet.getData()));
                Frigo.amountList.add(new String(packet.getData()));
                System.out.println(Frigo.amount);
                if(Frigo.amount !=  "Please refresh"){
                    System.out.println("Check");
                    int amount = Integer.parseInt(Frigo.amount.trim());
                    if( amount <= 0) {
                        try {
                            Frigo.buyStuff("Milk", 10);
                        } catch (Exception e) {

                        }
                    }
                }
                // Decode sender, ignore all other content
                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                data = Frigo.amount.getBytes();
                // Send answer
                packet = new DatagramPacket(data,data.length,address,port);
                socket.send(packet);
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

}
