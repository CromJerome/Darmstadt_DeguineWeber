package client_side;

import Objects.Product;
import Objects.Sensor;

import java.io.*;
import java.net.*;
import java.util.concurrent.TimeUnit;

public class UDPClient {
    public static String host = new String("localhost"); //141.100.45.129
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

        Product milk = new Product("Milk", 2, "L");
        Sensor s = new Sensor(milk,11);

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
            try{
                socket.setSoTimeout(2000);
                socket.receive(packet);
                String receiveNumber = new String(packet.getData());

                System.out.println("Data obtained from  " + host
                        + " Port " + port + " = " + receiveNumber );


                s.setAmount(Integer.parseInt(receiveNumber.replaceAll("[^\\d.]", "")));

            }catch (SocketTimeoutException e){
                System.out.println("Nothing receive from server " + e);
            }

            s.decreaseAmountByRandom();

        }
        //socket.close();
    }
}

