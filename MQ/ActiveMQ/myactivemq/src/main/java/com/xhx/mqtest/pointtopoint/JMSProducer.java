package com.xhx.mqtest.pointtopoint;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JMSProducer {
    private static final String USERNAME = "admin"; //ActiveMQConnection.DEFAULT_USER;
    private static final String PASSWORK = "admin"; // ActiveMQConnection.DEFAULT_PASSWORD;
    private static final String BROKERURL = "tcp://11.11.156.206:61616";
    // private static final String BROKERURL=ActiveMQConnection.DEFAULT_BROKER_URL;
    private static final int SENTNUM = 10;


    public static void main(String[] args) {
        ConnectionFactory connectionFactory;
        Connection connection = null;
        Session session = null;
        Destination destination;
        MessageProducer messageProducer;

        System.out.println(BROKERURL);

        connectionFactory = new ActiveMQConnectionFactory(JMSProducer.USERNAME, JMSProducer.PASSWORK, JMSProducer.BROKERURL);


        try {
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue("LOCAL_Q_PARTYDUES_LASTPARTYFEEDATE");
            messageProducer = session.createProducer(destination);
            sendMessage(session, messageProducer);

            //提交
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }

        }
    }


    public static void sendMessage(Session session, MessageProducer messageProducer) throws Exception {
      /*  for(int i = 0; i< JMSProducer.SENTNUM;i++){
            TextMessage textMessage = session.createTextMessage("ActiveMQ发送消息+" + i);
            System.out.println(textMessage.getText());
            messageProducer.send(textMessage);
        }*/

        //{"id":181351016514479027, "modifyType":"LAST_PARTY_FEE_DATE", "lastFeeDate":"2018-04-01"}
        for (int i = 0; i < 10; i++) {
            TextMessage textMessage = session.createTextMessage("{\"id\":4717316812998671, \"modifyType\":\"LAST_PARTY_FEE_DATE\", \"lastFeeDate\":\"2018-02-01\"}");
            System.out.println(textMessage.getText());
            messageProducer.send(textMessage);
        }
    }


}
