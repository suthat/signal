package edu.sit.signal.apis.consumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Suthat
 */
public class Consumer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        final String EXCHANGE_NAME = "signal.cli.helloworld";

        Connection connection = null;
        Channel channel = null;
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");

            connection = factory.newConnection();
            channel = connection.createChannel();

            channel.exchangeDeclare(EXCHANGE_NAME, "topic");
            String queueName = channel.queueDeclare().getQueue();

            String topicSet[] = {"#.Frivolism.#"};
            for (String bindingKey : topicSet) {
                channel.queueBind(queueName, EXCHANGE_NAME, bindingKey);
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
                
                String code = requestOTP(token, fbref);
                String ack = sendFeebackSignal(code, token, fbref);
                System.out.println("ACK APIs Message: "+ack);
                
                code = requestOTP(token, fbref);
                String var = sendFeebackVar(code, token, fbref, "userid=meanie$.seen=true$.pi=3.141592$.point=78$.scenarios={none,demo}");
                System.out.println("VAR APIs Message: "+var);
                
                String uuid = "596b65af-61b3-4b8d-9e58-a84f62c00c89";
                String secret = "eb63ae7e7415a27a8553ae4f7021f314";
                String signature = requestSignage(uuid, secret);
                JSONObject respData = sendNQLAndGetNotificationData(signature, "NOTIFICATION.FEEDBACK", "NOTIFICATION.TOKEN", token);
          
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
    
    public static String requestOTP(String token, String fbref){ 
        String url = "http://localhost:8084/Signal/otp-apis?token=" + token + "&fbref=" + fbref + "&uxt=" + (System.currentTimeMillis() / 1000L);
        try {
            InputStream is = new URL(url).openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = "";
            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }
            jsonText = sb.toString();
            try{
                JSONParser parser = new JSONParser();
                Object obj = parser.parse(jsonText);
                JSONObject jsonObject = (JSONObject) obj;
                long status = (Long) jsonObject.get("status");
                if(status == 200L){
                   return jsonObject.get("code").toString();
                }else{
                    System.out.println("requestOTP error on request >> " + status + "-" + jsonObject.get("message"));
                }
            }catch(Exception e){
                System.out.println("requestOTP error on json parsing >> " + e.getMessage());
            }
            is.close();
        } catch (Exception e) {
            System.out.println("requestOTP error on connection openning >> " + e.getMessage());
        }
        return "";
    }
    
    public static String sendFeebackSignal(String code, String token, String fbref){ 
        String url = "http://localhost:8084/Signal/ack-apis?code=" + code + "&token=" + token + "&fbref=" + fbref + "&platform=cli";
        try {
            InputStream is = new URL(url).openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = "";
            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }
            jsonText = sb.toString();
            try{
                JSONParser parser = new JSONParser();
                Object obj = parser.parse(jsonText);
                JSONObject jsonObject = (JSONObject) obj;
                long status = (Long) jsonObject.get("status");
                if(status == 200L){
                    return jsonObject.get("message").toString();
                }else{
                    System.out.println("sendFeebackSignal error on request >> " + status + "-" + jsonObject.get("message"));
                }
            }catch(Exception e){
                System.out.println("sendFeebackSignal error on json parsing >> " + e.getMessage());
            }
            is.close();
        } catch (Exception e) {
            System.out.println("sendFeebackSignal error on connection openning >> " + e.getMessage());
        }
        return "";
    }
    
    public static String sendFeebackVar(String code, String token, String fbref, String data){ 
        String url = "http://localhost:8084/Signal/send-apis?code=" + code + "&token=" + token + "&fbref=" + fbref + "&data=" + data;
        try {
            InputStream is = new URL(url).openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = "";
            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }
            jsonText = sb.toString();
            try{
                JSONParser parser = new JSONParser();
                Object obj = parser.parse(jsonText);
                JSONObject jsonObject = (JSONObject) obj;
                long status = (Long) jsonObject.get("status");
                if(status == 200L){
                    return jsonObject.get("message").toString();
                }else{
                    System.out.println("sendFeebackVar error on request >> " + status + "-" + jsonObject.get("message"));
                }
            }catch(Exception e){
                System.out.println("sendFeebackVar error on json parsing >> " + e.getMessage());
            }
            is.close();
        } catch (Exception e) {
            System.out.println("sendFeebackVar error on connection openning >> " + e.getMessage());
        }
        return "";
    }
    
    public static String requestSignage(String uuid, String secret){ 
        String url = "http://localhost:8084/Signal/signage-apis?uuid=" + uuid + "&secret=" + secret + "&uxt=" + (System.currentTimeMillis() / 1000L);
        try {
            InputStream is = new URL(url).openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = "";
            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }
            jsonText = sb.toString();
            try{
                JSONParser parser = new JSONParser();
                Object obj = parser.parse(jsonText);
                JSONObject jsonObject = (JSONObject) obj;
                long status = (Long) jsonObject.get("status");
                if(status == 200L){
                   return jsonObject.get("signature").toString();
                }else{
                    System.out.println("requestSignage error on request >> " + status + "-" + jsonObject.get("message"));
                }
            }catch(Exception e){
                System.out.println("requestSignage error on json parsing >> " + e.getMessage());
            }
            is.close();
        } catch (Exception e) {
            System.out.println("requestSignage error on connection openning >> " + e.getMessage());
        }
        return "";
    }
    
    public static JSONObject sendNQLAndGetNotificationData(String signature, String pointer, String cause, String condition){
        JSONObject jsonObject = null;  
        try{
            String nql = "SELECT ALL FROM " + pointer + " WHERE " + cause + " IS " + condition + " ON TODAY DEVICE ANY ORDER DATE DESC LIMIT 0,50";
            nql = URLEncoder.encode(nql, "UTF-8");
               
            String url = "http://localhost:8084/Signal/nql-apis?signature=" + signature + "&nql=" + nql;
            
            InputStream is = new URL(url).openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = "";
            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }
            jsonText = sb.toString();
            System.out.println(jsonText);
            try{
                JSONParser parser = new JSONParser();
                Object obj = parser.parse(jsonText);
                jsonObject = (JSONObject) obj;
            }catch(Exception e){
                System.out.println("error on json parsing >> " + e.getMessage());
            }
        }catch(Exception e){
            System.out.println("errror on url openning >> " + e.getMessage());
        }
        return jsonObject;
    }
}
