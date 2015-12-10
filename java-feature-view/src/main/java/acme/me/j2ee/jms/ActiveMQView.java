package acme.me.j2ee.jms;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
public class ActiveMQView {
    private Session session;
    //消息发送到这个Queue
    private Destination queue;
    //消息回复到这个Queue
    private Destination replyQueue;

    @Before
    public void init() {
        try {
            ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("vm://localhost");
            Connection connection = factory.createConnection();
            connection.start();

            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            queue = session.createQueue("testQueue");
            replyQueue = session.createQueue("replyQueue");

            producerTest();
        } catch (Exception e) {
            System.out.println("System filed to init active queue factory.");
        }
    }


    @Test
    public void consumerTest() throws Exception {
        MessageConsumer comsumer = session.createConsumer(queue);
        comsumer.setMessageListener(new MessageListener(){
            public void onMessage(Message m) {
                try {
                    //创建一个新的MessageProducer来发送一个回复消息。
                    MessageProducer producer = session.createProducer(m.getJMSReplyTo());
                    producer.send(session.createTextMessage("Hello " + ((TextMessage) m).getText()));
                } catch (JMSException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    @Test
    public void consumer2Test() throws Exception {
        MessageConsumer comsumer2 = session.createConsumer(replyQueue);
        comsumer2.setMessageListener(new MessageListener(){
            public void onMessage(Message m) {
                try {
                    System.out.println(((TextMessage) m).getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @After
    public void producerTest() throws Exception {
        //创建一个消息，并设置它的JMSReplyTo为replyQueue。
        MessageProducer producer = session.createProducer(queue);

        for(int i = 0; i<100; i++) {
            Message message = session.createTextMessage("This is message of instance " + i);
            message.setJMSReplyTo(replyQueue);
            producer.send(message);
        }
    }
}
