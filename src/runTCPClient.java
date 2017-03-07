import java.io.IOException;

/**
 * Created by jerome on 07/03/2017.
 */
public class runTCPClient {
    public static void main(String[] args) throws IOException {
        TCPClient tcpClient = new TCPClient("localhost",9999);
        tcpClient.startTCPClient();
    }
}
