package com.xhx.mqtest.subpub;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JMSProducer {
    private static final String USERNAME= ActiveMQConnection.DEFAULT_USER;
    private static final String PASSWORK= ActiveMQConnection.DEFAULT_PASSWORD;
    private static final String BROKERURL="tcp://192.168.94.151:61616";
   // private static final String BROKERURL=ActiveMQConnection.DEFAULT_BROKER_URL;
    private static final int SENTNUM=10;


    public static void main(String[] args) {
        ConnectionFactory connectionFactory;
        Connection connection=null;
        Session session = null;
        Destination destination;
        MessageProducer messageProducer;

        System.out.println(BROKERURL);

        connectionFactory = new ActiveMQConnectionFactory(JMSProducer.USERNAME, JMSProducer.PASSWORK, JMSProducer.BROKERURL);


        try {
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(Boolean.TRUE,Session.AUTO_ACKNOWLEDGE);
            destination = session.createTopic("myFirstTopic");
            messageProducer = session.createProducer(destination);
            TextMessage textMessage = session.createTextMessage("ActimeMQ发送主题");
            System.out.println(textMessage.getText());
            messageProducer.send(textMessage);

            //提交
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(session!=null){
                try {
                    session.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
            if(connection!=null){
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }

        }
    }


}
