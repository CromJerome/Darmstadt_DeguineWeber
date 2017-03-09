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

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Fridge {


    static String line;
    public static String amount = "Initialize... Please Wait";
    public static int tmpAmount = -1;
    public static ArrayList<String> amountList = new ArrayList<String>();
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
            if(!amount.equals("Initialize... Please Wait")){
                if(Integer.parseInt(amount.replaceAll("[^\\d.]", "")) < 10) {
                    buyStuff(5);
                }
            }
        }
    }

    public static void buyStuff(int quantity) throws MalformedURLException, XmlRpcException {
        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();

        config.setServerURL(new URL("http://localhost:8080/xmlrpc"));
        XmlRpcClient client = new XmlRpcClient();
        client.setConfig(config);

        Object[] params = new Object[]{new Integer(quantity)};
        Integer result = (Integer) client.execute("GroceriesStore.buyStuff", params);
        System.out.println("Result of the xmlRPC = " + result );
        if(result != 0){
            tmpAmount = Integer.parseInt(amount.replaceAll("[^\\d.]", ""));

            tmpAmount += result;
            amount = Integer.toString(tmpAmount);

            System.out.println("You just buy stuff, you have now:"+amount);
        }
        else if(result == 0)
        {
            System.out.println("The store is empty");
        }
    }
}
