public class Frigo extends Thread{
    public static void main(String args[]) {
        try {
            Thread tcpServer = new Thread() {
                public void run() {
                    try {
                        TCPServer tcpServer = new TCPServer(9999);
                        tcpServer.startTCPServer();
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
}