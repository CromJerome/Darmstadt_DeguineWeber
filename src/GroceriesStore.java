import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.server.XmlRpcServerConfigImpl;
import org.apache.xmlrpc.webserver.WebServer;

import java.util.ArrayList;

public class GroceriesStore {

    private int port;
    private ArrayList<Product> products;
    private int getProductByName(String name){
        int result = -1;
        for(int i =  0; i < products.size() ; i++){
            if(products.get(i).getName() == name){
                result = i;
            }
        }
        return result;
    }

    private boolean checkProductStock( int productindex, int amount){
        return products.get(productindex).getStock() - amount > 0;
    }

    public GroceriesStore(int port) {
        this.port = port;
        this.products = new ArrayList<>();
    }

    public Integer add(int x, int y) {

        return new Integer(x+y);
    }

    public Integer buy(String productname,int amount) {
        System.out.println("The shop try to sell "+ amount +" of "+ productname);
        //Let the fridge buy from the store
        int productindex = getProductByName(productname);
        if(productindex >= 0){
            if(checkProductStock(products.get(productindex).getStock(),amount))
            products.get(productindex).decreaseStock(amount);
            return new Integer(products.get(productindex).getCost() * amount);
        }
        return new Integer(amount);
    }

    public void startGroceriesStoreServer(){
        generateProducts();
        try {

            WebServer webServer = new WebServer(port);

            XmlRpcServer xmlRpcServer = webServer.getXmlRpcServer();
            PropertyHandlerMapping phm = new PropertyHandlerMapping();

            phm.addHandler( "GroceriesStore", GroceriesStore.class);
            xmlRpcServer.setHandlerMapping(phm);

            XmlRpcServerConfigImpl serverConfig =
                    (XmlRpcServerConfigImpl) xmlRpcServer.getConfig();
            // serverConfig.setEnabledForExtensions(true);
            // serverConfig.setContentLengthOptional(false);

            webServer.start();

            System.out.println("The GroceriesStoreServer has been started..." );

        } catch (Exception exception) {
            System.err.println("JavaServer: " + exception);
        }
    }

    public void generateProducts(){
        products.add(new Product("Milk",1,30));
        products.add(new Product("Chocolate",2,10));
        products.add(new Product("Navid",50,1));
    }
}
