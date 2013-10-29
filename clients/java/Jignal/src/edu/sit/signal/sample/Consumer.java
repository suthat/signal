package edu.sit.signal.sample;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import edu.sit.signal.apis.consumer.SignalAPIs;
import org.json.simple.JSONObject;

/**
 *
 * @author Suthat
 */
public class Consumer {

    public static void main(String[] args) {

        // Signal HOST / URL
        final String HOST = "localhost";
        
        // Your Signal Exchange Key
        final String EXCHANGE_KEY = "signal.cli.helloworld";
        
        // Your Receiving Topics 
        final String TOPIC = "#.Frivolism.#";
        
        // Enter Your Provider UUID And Your Secret 
        String uuid = "596b65af-61b3-4b8d-9e58-a84f62c00c89";
        String secret = "eb63ae7e7415a27a8553ae4f7021f314";
        String receiverId = "guest";
        
        // Init Signal APIs Instance
        SignalAPIs signalApis = new SignalAPIs();

        Connection connection = null;
        Channel channel = null;
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(HOST);

            connection = factory.newConnection();
            channel = connection.createChannel();

            channel.exchangeDeclare(EXCHANGE_KEY, "topic");
            String queueName = channel.queueDeclare().getQueue();

            String topicSet[] = {TOPIC};
            for (String bindingKey : topicSet) {
                channel.queueBind(queueName, EXCHANGE_KEY, bindingKey);
            }

            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            QueueingConsumer consumer = new QueueingConsumer(channel);
            channel.basicConsume(queueName, true, consumer);

            while (true) {
                QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                String messageBody = new String(delivery.getBody());
                String routingKey = delivery.getEnvelope().getRoutingKey();
                System.out.println(" [x] Received '" + routingKey + "':'" + messageBody + "'");

                String temp[] = messageBody.split("\\|");
                String token = "";
                String fbref = "";
                String message = "";
                String link = "";
                String custom = "";
                try{
                    token = temp[0];
                    fbref = temp[1];
                    message = temp[2];
                    link = temp[3];
                    custom = temp[4];
                }catch(Exception e){
                    System.out.println("error on exploding message body >> " + e.getMessage());
                }
                
                // Ack
                String code = signalApis.requestOTP(token, fbref);
                String ack = signalApis.sendFeedbackSignal(code, token, fbref, receiverId);
                System.out.println("ACK APIs Message: "+ack);
                
                // Send Feedback
                code = signalApis.requestOTP(token, fbref);
                String var = signalApis.sendFeedbackVar(code, token, fbref, "userid=meanie$.seen=true$.pi=3.141592$.point=78$.scenarios={none,demo}");
                System.out.println("VAR APIs Message: "+var);
                
                // Query Notification And Feedback Data From Signal
                String signature = signalApis.requestSignage(uuid, secret);
                JSONObject respData = signalApis.sendNQLAndGetNotificationData(signature, "NOTIFICATION.FEEDBACK", "NOTIFICATION.TOKEN", token);
          
            }
        } catch (Exception e) {
            System.out.println("errror on consuming >> " + e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception ignore) {
                    ignore.getMessage();
                }
            }
        }
    }
}
