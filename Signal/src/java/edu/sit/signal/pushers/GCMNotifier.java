package edu.sit.signal.pushers;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import java.util.ArrayList;

/**
 *
 * @author Suthat
 */
public class GCMNotifier{
    
    // !IMPORTANT - Dev
    /*public static void main(String args[]){
        new GCMNotifier().sendMessageToSingleDevice("", "", "");
        new GCMNotifier().sendMessageToMultipleDevices("", "", null);
    }*/
    
    public void sendMessageToSingleDevice(String googleAPIsKey, String payLoadMessage, String deviceToken){
        try{
            // !IMPORTANT - Dev
            //googleAPIsKey = "AIzaSyAz_24XXx_HO7kpAIpn5mJjDRVIxTcJBVk";
            //payLoadMessage = "this text will be seen in notification bar!!";
            //deviceToken = "device_token";
            
            Sender sender = new Sender(googleAPIsKey);
            Message message = new Message.Builder()
                                    .collapseKey("1")
                                    .timeToLive(30)
                                    .delayWhileIdle(true)
                                    .addData("message", ""+payLoadMessage)
                                    .build();
            Result result = sender.send(message, deviceToken, 1);
            System.out.println(result.toString());
        } catch(Exception e){
            System.out.println("sendMessageToSingleDevice: " + e.getMessage());
        }
    }
    
    public void sendMessageToMultipleDevices(String googleAPIsKey, String payLoadMessage, ArrayList<String> devicesList){
        try{
            // !IMPORTANT - Dev
            //googleAPIsKey = "AIzaSyAz_24XXx_HO7kpAIpn5mJjDRVIxTcJBVk";
            //payLoadMessage = "this text will be seen in notification bar!!";
            //devicesList = new ArrayList<String>();
            //devicesList.add("device_token_1");
            //devicesList.add("device_token_2");
            
            Sender sender = new Sender(googleAPIsKey);
            Message message = new Message.Builder()
                                    .collapseKey("1")
                                    .timeToLive(600)
                                    .delayWhileIdle(true)
                                    .addData("message", payLoadMessage)
                                    .build();

            MulticastResult result = sender.send(message, devicesList, 1);
            //sender.send(message, devicesList, 1);
            
            System.out.println(result.toString());
        } catch(Exception e){
            System.out.println("sendMessageToMultipleDevices: " + e.getMessage());
        }
    }
}
