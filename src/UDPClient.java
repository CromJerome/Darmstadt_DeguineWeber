import java.io.*;
import java.net.*;
import java.util.concurrent.TimeUnit;

public class UDPClient {

//  static String host = new String("192.168.178.20");
    private String host;
    private int port;
    private Sensor sensor;

    public UDPClient(String host, int port){
        this.host = host;
        this.port = port;
    }

    public void startUDPClient() throws IOException{
        sensor = new Sensor(50, "L");
        DatagramSocket socket = new DatagramSocket();
        while(sensor.getAmount() > 0){
            sensor.decreaseAmountByRandom();
            byte msg[] = new byte[1024];

            // Construct and send Request
            InetAddress address = InetAddress.getByName(host);
            String amount = Integer.toString(sensor.getAmount()) +" "+ sensor.getMeasureunit();
            msg = amount.getBytes();
            DatagramPacket packet = new DatagramPacket(msg, msg.length, address, port);
            socket.send(packet);

            // Wait for answer, decode answer, dump answer to System.out
            packet = new DatagramPacket(msg, msg.length);
            socket.receive(packet);
            System.out.println(new String( packet.getData()));
            try{
                TimeUnit.SECONDS.sleep(3);
            }catch (InterruptedException e){

            }
        }
        socket.close();
    }
}