import java.io.IOException;

/**
 * Created by jerome on 07/03/2017.
 */
public class runXmlRpcServer {
    public static void main(String[] args) throws IOException {
        GroceriesStore groceriesStore = new GroceriesStore(8000);
        groceriesStore.startGroceriesStoreServer();
    }
}
