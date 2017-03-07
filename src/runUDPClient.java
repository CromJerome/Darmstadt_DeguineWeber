import java.io.IOException;

/**
 * Created by jerome on 07/03/2017.
 */
public class runUDPClient {
    public static void main(String[] args) throws IOException {
        UDPClient udpClient = new UDPClient("localhost",1313);
        udpClient.startUDPClient();
    }
}
