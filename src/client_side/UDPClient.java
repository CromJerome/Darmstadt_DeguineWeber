package client_side;

import java.io.*;
import java.net.*;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class UDPClient {
    public static String host = new String("localhost");
    public static int port = 1313;
    private int sensor;
    public UDPClient(){

    }
    public UDPClient(String host, int port){
        this.host = host;
        this.port = port;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Sensor started (UDP Client)");
        Sensor s = new Sensor(10,"L");

        // Construct and send Request
        DatagramSocket socket = new DatagramSocket();

        InetAddress address = InetAddress.getByName(host);

        //numberOfProduct = 11;
        String str_amount;
        while(true)
        {
            byte msg[] = new byte[256];

            str_amount = Integer.toString(s.getAmount()) + "\n";
            msg = str_amount.getBytes();

            System.out.println("Sending amount : " + str_amount);
            TimeUnit.SECONDS.sleep(2);

            DatagramPacket packet = new DatagramPacket(msg, msg.length,
                    address, port);
            socket.send(packet);
            packet = new DatagramPacket(msg, msg.length);

            //Reset the array
            msg= new byte[256];

            socket.receive(packet);
            System.out.println("Data obtained from  " + host
                    + " Port " + port + " = " );
            String receiveNumber = new String(packet.getData());
            System.out.print(receiveNumber);

            s.setAmount(Integer.parseInt(receiveNumber.replaceAll("[^\\d.]", "")));
            s.decreaseAmountByRandom();
        }
        //socket.close();
    }
}

