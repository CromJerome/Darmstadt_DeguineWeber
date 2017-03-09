package server_side;

import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.server.XmlRpcServerConfigImpl;
import org.apache.xmlrpc.webserver.WebServer;

public class GroceryServer {
    public static int stock = 3000;
    public static int cost = 5;
    private static final int port = 8080;

    public Integer buyStuff(int quantity) {
        int res;
        if(stock == 0) {
           res = 0;
        }
        else if(quantity > stock)        {
            res = stock;
            stock = 0;

        }else{
            res = quantity;
            stock = stock - quantity;
        }
        return new Integer(res);

    }

    public static void main (String [] args) {
        try {

            WebServer webServer = new WebServer(port);

            XmlRpcServer xmlRpcServer = webServer.getXmlRpcServer();
            PropertyHandlerMapping phm = new PropertyHandlerMapping();

            phm.addHandler( "GroceriesStore", GroceryServer.class);
            xmlRpcServer.setHandlerMapping(phm);

            XmlRpcServerConfigImpl serverConfig =
                    (XmlRpcServerConfigImpl) xmlRpcServer.getConfig();
            // serverConfig.setEnabledForExtensions(true);
            // serverConfig.setContentLengthOptional(false);

            webServer.start();

            System.out.println("The GroceriesStore Server has been started..." );

        } catch (Exception exception) {
            System.err.println("JavaServer: " + exception);
        }
    }

}
