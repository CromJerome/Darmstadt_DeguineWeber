package client_side;

import Objects.Product;
import Objects.Sensor;
import org.eclipse.paho.client.mqttv3.*;

import java.util.ArrayList;

/**
 * Created by jerome on 09/03/2017.
 */
public class Supplier extends Thread implements MqttCallback {
    int supplierid;
    ArrayList<Product> products;
    MqttClient client;

    @Override
    public void run(){
        while(true){
            sendPrices();
        }
    }

    public void Supplier(ArrayList<Product> products, int supplierid){
        this.supplierid = supplierid;
        this.products = products;
    }

    public void sendPrices() {
        try {
            client = new MqttClient("tcp://127.0.0.1:1883", "Sending");
            client.connect();
            client.setCallback(this);
            client.subscribe("foo");
            MqttMessage message = new MqttMessage();

            String str_products = "Supplier"+ supplierid + "|";
            for(Product p : this.products){
                str_products += this.products.toString() + "|";
            }
            str_products = str_products.substring(0,str_products.length() -1);
            message.setPayload(str_products.getBytes());
            client.publish("foo", message);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void connectionLost(Throwable cause) {
        // TODO Auto-generated method stub

    }

    @Override
    public void messageArrived(String topic, MqttMessage message)
            throws Exception {
        String str_message = message.toString();
        if(str_message.startsWith("Store|")){
            str_message = str_message.replaceFirst("Store|","");
            String[] storeproducts = str_message.split("|");
            for(Product p : this.products){
                for(String s : storeproducts){
                    Sensor temp = new Sensor(s);
                    if(p.getName() == temp.getProduct().getName()){
                        if(p.getCost() > temp.getProduct().getCost()){
                            p.decreaseCostByRandom(p.getCost() + temp.getProduct().getCost() * 2);
                        }
                    }
                }
            }

        }
        System.out.println("Message arrived" + message);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        // TODO Auto-generated method stub

    }
}
