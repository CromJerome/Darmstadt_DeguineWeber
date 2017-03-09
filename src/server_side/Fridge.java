/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server_side;

import java.io.*;
import java.net.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;

import Objects.Sensor;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Fridge {


    static String line;
    public static ArrayList<Sensor> sensorList = new ArrayList<Sensor>();
    public static ArrayList<Sensor> tmpSensorList = new ArrayList<Sensor>();


   // public static ArrayList<String> amountList = new ArrayList<String>();


    static BufferedReader fromClient;
    static DataOutputStream toClient;
    
    public static void main(String[] args) throws SocketException, IOException, XmlRpcException, InterruptedException {

        TCPServer tcpServer = new TCPServer();
        tcpServer.start();
        
        
        UDPServer udpServer = new UDPServer();
                   udpServer.start();

/*<<<<<<< HEAD
        TimeUnit.SECONDS.sleep(15);
        while(true) {
                if(Integer.parseInt(amount.replaceAll("[^\\d.]", "")) < 10) {
                    buyStuff(50);
                }
=======*/

        while(true) {
            Thread.sleep(200);
            for (Sensor s : sensorList){
                if(s.getAmount() < 10) {
                    buyStuff(s,5);
                }
            }
        }
    }

    public static void buyStuff(Sensor s,int quantity) throws MalformedURLException, XmlRpcException {
        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();

        config.setServerURL(new URL("http://localhost:8080/xmlrpc"));
        XmlRpcClient client = new XmlRpcClient();
        client.setConfig(config);

        Object[] params = new Object[]{new Sensor(s.getProduct()+";"+quantity)};
        Integer result = (Integer) client.execute("GroceriesStore.buyStuff", params);

        System.out.println("Result of the xmlRPC = " + result );
        if(result != 0){

            tmpSensorList.add(new Sensor(s.toString())); // = Integer.parseInt(sensorString.replaceAll("[^\\d.]", ""));

            for (Sensor sensor : sensorList){
                if(sensor.getProduct().getName().equals(s.getProduct().getName())) {
                    sensor.addAmount(result);
                    System.out.println("You just buy stuff, you have now : "+ sensor.getAmount());
                }
            }


        }
        else if(result == 0)
        {
            System.out.println("The store is empty");
        }
    }
}
