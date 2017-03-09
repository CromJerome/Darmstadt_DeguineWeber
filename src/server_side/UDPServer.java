/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server_side;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.io.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UDPServer extends Thread {

    public String response;
    private DatagramPacket packet;
    private DatagramSocket socket;

    public UDPServer() throws SocketException {
        this.socket = new DatagramSocket(1313);
        System.out.println("UDP Time Server started at Port 1313");
    }

    @Override
    public void run() {
       while ( true ) {
            byte data[] = new byte[1024];
            // Wait for request
            this.packet = new DatagramPacket(data, data.length);
           try {
               this.socket.receive(this.packet);
           } catch (IOException ex) {
               Logger.getLogger(UDPServer.class.getName()).log(Level.SEVERE, null, ex);
           }
            data = this.packet.getData();
           try {
               response = new String(data, "UTF-8");
           } catch (UnsupportedEncodingException ex) {
               Logger.getLogger(UDPServer.class.getName()).log(Level.SEVERE, null, ex);
           }
            // Decode sender, ignore all other content
            System.out.print( this.response );
            Fridge.amount = this.response;

            InetAddress address = packet.getAddress();
            int port   = packet.getPort();
            String s = "";
            // Encode answer
            if(Fridge.tmpAmount != -1) {
                s = Integer.toString(Fridge.tmpAmount);
                Fridge.tmpAmount = -1;
                data = s.getBytes();
                // Send ansswer
                packet = new DatagramPacket(data,data.length,address,port);
                try {
                    socket.send(packet);
                } catch (IOException ex) {
                    Logger.getLogger(UDPServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
