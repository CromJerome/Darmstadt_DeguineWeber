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
                Frigo.amount=Integer.parseInt(new String(packet.getData()).replaceAll("[^\\d.]", ""));
                Frigo.amountList.add(Integer.parseInt(new String(packet.getData()).replaceAll("[^\\d.]", "")));
                System.out.println(Frigo.amount);


                // Decode sender, ignore all other content
                InetAddress address = packet.getAddress();
                int port = packet.getPort();

                if(Frigo.tempAmount != -1){
                    data =  String.valueOf(Frigo.tempAmount).getBytes();
                }else{
                    data = String.valueOf(Frigo.amount).getBytes();
                }
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
