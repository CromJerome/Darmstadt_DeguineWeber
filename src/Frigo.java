import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class Frigo extends Thread{
    public static int amount = -1;
    public static int tempAmount = -1;
    public static ArrayList<Integer> amountList = new ArrayList<Integer>();
    private static XmlRpcClient client;
    public static void main(String args[]) {
        try {
            Thread tcpServer = new Thread() {
                public void run() {
                    try {
                        TCPServer tcpServer = new TCPServer(9999);
                        tcpServer.startTCPServer();
                        System.out.println("Amount : "+ amount);
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            };
            Thread udpServer = new Thread() {
                public void run() {
                    try {
                        UDPServer udpServer = new UDPServer(1313);
                        udpServer.startUDPServer();
                        System.out.println("Amount : "+ amount);
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            };
            Thread checkLoop = new Thread(){
                public void run(){
                    System.out.println("Check stock thread started ...");
                    while (true){
                        System.out.print("");
                        if(Frigo.amount !=  -1){
                            System.out.print("");
                            int amount = Frigo.amount;
                            if( amount <= 10) {
                                try {
                                    buyStuff("Milk", 10);
                                } catch (Exception e) {

                                }
                            }
                        }
                    }
                }
            };
            checkLoop.start();
            tcpServer.start();
            udpServer.start();
        } catch (Exception e) {

        }
    }
    public static void buyStuff(String productname, int amount) throws Exception{

        System.out.println("Start XmlRpcClient");
        try{
            XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();

            config.setServerURL(new URL("http://127.0.0.1:"+8000+"/xmlrpc"));
            org.apache.xmlrpc.client.XmlRpcClient client = new org.apache.xmlrpc.client.XmlRpcClient();
            client.setConfig(config);

            Object[] params = new Object[]{new String(productname), new Integer(amount)};
            System.out.println("About to get results...(params[0] = " + params[0]
                    + ", params[1] = " + params[1] + ")." );

            Integer result = (Integer) client.execute("GroceriesStore.buy", params);
            System.out.println("Buys = " + result );
            Frigo.tempAmount = Frigo.amount += result;
        }catch(XmlRpcException e){

        }
    }
}