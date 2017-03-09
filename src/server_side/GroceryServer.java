package server_side;

import Objects.Product;
import Objects.Sensor;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.server.XmlRpcServerConfigImpl;
import org.apache.xmlrpc.webserver.WebServer;
import org.eclipse.paho.client.mqttv3.MqttClient;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class GroceryServer {
    MqttClient client;

    public static ArrayList<Sensor> sensors ;
    public static int stock = 3000;
    public static int cost = 5;
    private static final int port = 8080;

    public String buyStuff(String sensor) {
        Sensor temp = new Sensor(sensor);
        for(Sensor s : sensors){
            if (s.getProduct().getName() == temp.getProduct().getName()){
                if(s.getAmount() == 0) {
                    return new String(temp.toString());
                }
                else if(temp.getAmount() > s.getAmount()) {
                    temp.setAmount(s.getAmount());
                    s.setAmount(0);

                }else{
                    temp.setAmount(s.getAmount());
                    s.setAmount(s.getAmount() - temp.getAmount());
                }
            }
        }
        return new String(temp.toString());
    }

    public static void main (String [] args) {
            Thread xmlRpc = new Thread() {
                public void run() {
                    try{
                        WebServer webServer = new WebServer(port);

                        XmlRpcServer xmlRpcServer = webServer.getXmlRpcServer();
                        PropertyHandlerMapping phm = new PropertyHandlerMapping();

                        phm.addHandler("GroceriesStore", GroceryServer.class);
                        xmlRpcServer.setHandlerMapping(phm);

                        XmlRpcServerConfigImpl serverConfig =
                                (XmlRpcServerConfigImpl) xmlRpcServer.getConfig();
                        // serverConfig.setEnabledForExtensions(true);
                        // serverConfig.setContentLengthOptional(false);
                        webServer.start();
                        System.out.println("The GroceriesStore Server has been started...");

                    } catch(Exception exception){
                        System.err.println("JavaServer: " + exception);
                    }
                }

            };
            Thread mqttClient =  new Thread(){
                public void run(){
                    while (true){
                        try{
                            TimeUnit.SECONDS.sleep(10);
                        }catch (InterruptedException e){

                        }
                        String resp ="Store|";
                        for(Sensor s : sensors){
                            resp+=s.toString() + "|";
                        }

                    }
                }
            };
            xmlRpc.start();
    }
}
