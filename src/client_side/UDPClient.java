package client_side;

import Objects.Product;
import Objects.Sensor;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
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
        Sensor s2 = new Sensor("orangeJuice;L;3;14");

        ArrayList<Sensor> sensorArray = new ArrayList<Sensor>();
        sensorArray.add(s);
        sensorArray.add(s2);

        // Construct and send Request
        DatagramSocket socket = new DatagramSocket();

        InetAddress address = InetAddress.getByName(host);

        //numberOfProduct = 11;
        String str_sensors = "";

        while(true)
        {
            byte msg[] = new byte[256];

            for (Sensor sensor : sensorArray) {
                str_sensors += sensor.toString()+ "|";
            }
            str_sensors = str_sensors.substring(0,str_sensors.length() -1);

            //str_sensors = Integer.toString(s.getAmount()) + "\n";

            msg = str_sensors.getBytes();

            System.out.println("Sending data : " + str_sensors);
            TimeUnit.SECONDS.sleep(2);

            DatagramPacket packet = new DatagramPacket(msg, msg.length,
                    address, port);
            socket.send(packet);
            packet = new DatagramPacket(msg, msg.length);

            //Reset the array
            msg= new byte[256];
            try{
                socket.setSoTimeout(4000);
                socket.receive(packet);
                String strSensors = new String(packet.getData());

                String[] parts = strSensors.split("|");
                for (Sensor sensor : sensorArray) {
                    for (String srv : parts){
                        Sensor tmp = new Sensor(srv);
                        if(tmp.getProduct().getName() == sensor.getProduct().getName()){
                            
                        }
                    }
                }

                System.out.println("Data obtained from  " + host
                        + " Port " + port + " = " + receiveNumber );

                s.setAmount(Integer.parseInt(receiveNumber.replaceAll("[^\\d.]", "")));

            }catch (SocketTimeoutException e){
                System.out.println("Nothing receive from server " + e);
            }

            for (Sensor sensor : sensorArray) {
                sensor.decreaseAmountByRandom();
            }


        }
        //socket.close();
    }
}

