import java.util.ArrayList;

public class Frigo extends Thread{
    public static String amount = "Please refresh";
    public static ArrayList<String> amountList = new ArrayList<String>();
    private static XmlTpcClient client;
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
            tcpServer.start();
            udpServer.start();
        } catch (Exception e) {

        }
    }
    public static void buyStuff(String productname, int amount) throws Exception{
            client = new XmlTpcClient(8000);
            client.startXmlTpcClient();
        System.out.println("The fridge try to "+ amount + " of "+ productname);
        client.buyStuff(productname,amount);
    }
}