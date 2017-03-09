package client_side;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class PahoDemo implements MqttCallback{

    MqttClient client;

    public PahoDemo() {

    }
    public void doDemo(String msg) {
        try {
            client = new MqttClient("tcp://127.0.0.1:1883", "Sending");
            client.connect();
            client.setCallback(this);
            client.subscribe("foo");
            MqttMessage message = new MqttMessage();
            message.setPayload(msg
                    .getBytes());
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
        System.out.println("Message arrived with topic "+topic +" : " + message);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        // TODO Auto-generated method stub

    }

}