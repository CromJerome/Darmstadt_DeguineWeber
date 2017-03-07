import jdk.nashorn.internal.runtime.ECMAException;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.client.XmlRpcClientException;

import java.io.IOException;
import java.net.URL;

/**
 * Created by jerome on 07/03/2017.
 */
public class XmlRpcClient {
    private org.apache.xmlrpc.client.XmlRpcClient client;
    private int port;
    public XmlRpcClient(int port){
        this.port = port;
    }

    public void startXmlRpcClient(){
        System.out.println("Start XmlRpcClient");
        try{
            XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();

            config.setServerURL(new URL("http://127.0.0.1:"+this.port+"/xmlrpc"));
            client = new org.apache.xmlrpc.client.XmlRpcClient();
            client.setConfig(config);
            }
            catch(IOException e){

            }
    }

    public void buyStuff(String productname, int amount){
        try{
            Object[] params = new Object[]{new String(productname), new Integer(amount)};
            System.out.println("About to get results...(params[0] = " + params[0]
                    + ", params[1] = " + params[1] + ")." );

            Integer result = (Integer) client.execute("GroceriesStore.buy", params);
            System.out.println("Buys = " + result );
            Frigo.amount+=result;
        }catch(XmlRpcException e){

        }
    }
}
