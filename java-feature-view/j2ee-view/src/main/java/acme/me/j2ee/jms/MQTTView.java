package acme.me.j2ee.jms;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.junit.Test;

public class MQTTView {
    // 定义一个主题
    private static int qos = 2;
    private static String broker = "tcp://192.168.2.208:1883";
    private static String userName = "tuyou";
    private static String passWord = "tuyou";

    @Test
    public void main() throws MqttException {
        runsub("client-id-1", "$share/edge/server/public/a");

        runsub("client-id-2", "$share/edge/server/public/a");

        publish("<This is a short message>", "client-id-0", "$share/edge/server/public/a");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private MqttClient connect(String clientId) throws MqttException {
        MemoryPersistence persistence = new MemoryPersistence();
        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(true);
        connOpts.setUserName(userName);
        connOpts.setPassword(passWord.toCharArray());
        connOpts.setConnectionTimeout(10);
        connOpts.setKeepAliveInterval(5);

        MqttClient mqttClient = new MqttClient(broker, clientId, persistence);
        mqttClient.setCallback(new PushCallback(clientId));
        mqttClient.connect(connOpts);
        return mqttClient;
    }

    private void pub(MqttClient sampleClient, String msg, String topic) throws MqttPersistenceException, MqttException {
        MqttMessage message = new MqttMessage(msg.getBytes());
        message.setQos(qos);
        message.setRetained(false);
        sampleClient.publish(topic, message);
    }

    private void publish(String str, String clientId, String topic) throws MqttException {
        MqttClient mqttClient = connect(clientId);

        if (mqttClient != null) {
            pub(mqttClient, str, topic);
            System.out.println("pub-->" + str);
        }

        if (mqttClient != null) {
            mqttClient.disconnect();
        }
    }

    public void sub(MqttClient mqttClient, String topic) throws MqttException {
        int[] Qos = { qos };
        String[] topics = { topic };
        mqttClient.subscribe(topics, Qos);
    }

    private void runsub(String clientId, String topic) throws MqttException {
        MqttClient mqttClient = connect(clientId);
        if (mqttClient != null) {
            sub(mqttClient, topic);
        }
    }

    static class PushCallback implements MqttCallback {
        private String clientId;

        public PushCallback(String clientId) {
            this.clientId = clientId;
        }

        public void connectionLost(Throwable cause) {
            System.out.println(clientId + "->Connection lost!");
        }

        public void deliveryComplete(IMqttDeliveryToken token) {
            System.out.println(clientId + "->deliveryComplete----" + token.isComplete());
        }

        public void messageArrived(String topic, MqttMessage message) throws Exception {
            String msg = new String(message.getPayload());
            System.out.println(clientId + "->getMessage----" + msg);
        }
    }
}
