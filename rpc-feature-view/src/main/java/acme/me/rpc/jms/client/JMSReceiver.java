package acme.me.rpc.jms.client;

import javax.jms.Message;
import javax.jms.TextMessage;

import org.springframework.jms.core.JmsTemplate102;

public class JMSReceiver {
    private JmsTemplate102 jmsTemplate102;

    public JmsTemplate102 getJmsTemplate102() {
        return jmsTemplate102;
    }

    public void setJmsTemplate102(JmsTemplate102 jmsTemplate102) {
        this.jmsTemplate102 = jmsTemplate102;
    }

    public void processMessage() {
        Message msg = jmsTemplate102.receive("JMS_RequestResponseQueue");
        try {
            TextMessage textMessage = (TextMessage) msg;
            if (msg != null) {
                System.out.println(" Message Received -->" + textMessage.getText());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
