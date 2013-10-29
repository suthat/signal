package edu.sit.signal.pushers;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 *
 * @author Suthat
 */
public class AMQPNotifier {

    private String AMQPHost;
    private ConnectionFactory factory = new ConnectionFactory();

    public AMQPNotifier(String AMQPHost) {
        this.AMQPHost = AMQPHost;
        factory.setHost(AMQPHost);
    }

    public boolean pushToWSByFanout(String exchange_name, String message) {

        try {

            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.exchangeDeclare(exchange_name, "fanout");
            channel.basicPublish(exchange_name, "", null, message.getBytes());

            channel.close();
            connection.close();

        } catch (Exception e) {
            System.out.println("Exception : AMQPNotifier.pushToWSByFanout : " + e.getMessage());
            return false;
        }

        return true;
    }

    public boolean pushToWSByTopic(String exchange_name, String keytopic, String message) {

        try {

            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.exchangeDeclare(exchange_name, "topic");
            channel.basicPublish(exchange_name, keytopic, null, message.getBytes());

            channel.close();
            connection.close();

        } catch (Exception e) {
            System.out.println("Exception : AMQPNotifier.pushToWSByTopic : " + e.getMessage());
            return false;
        }

        return true;
    }
}
