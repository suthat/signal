package edu.sit.signal.pushers;

import edu.sit.signal.apis.DbApis;
import edu.sit.signal.app.Helpers;
import edu.sit.signal.ndl.NDLEnum;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Suthat
 */
public class Notifier implements Runnable {

    private HashMap notification = null;
    private AMQPNotifier ampqNotifier = null;
    private APNSNotifier apnsNotifier = null;
    private GCMNotifier gcmNotifier = null;
    private Helpers helpers = null;

    public Notifier(HashMap notification) {
        this.notification = notification;
        ampqNotifier = new AMQPNotifier("localhost");
        apnsNotifier = new APNSNotifier();
        gcmNotifier = new GCMNotifier();
        helpers = new Helpers();
    }

    @Override
    public void run() {

        // Get notify platforms from NDL scope
        String platforms = notification.get("platform").toString();

        // Notify to client application
        if (this.isNotifyTo(platforms, NDLEnum.Scope.PLATFORM_CLI.getSring())) {

            System.out.println("activate cli");

            // !IMPORTANT - PREFIX signal.cli.* as the exchane name
            final String EXCHANGE_NAME = "signal.cli." + notification.get("topic").toString();

            // Collect a key topic maximun 255 charactors  
            String keytopic = notification.get("keytopic").toString();
            if (keytopic.length() > 255) {
                keytopic = keytopic.substring(0, 255);
            }

            // Concat the custom cli data into JSON format 
            String customData = "{";
            if (!notification.get("customcli").toString().equalsIgnoreCase("")) {
                customData = customData.concat(notification.get("customcli").toString());
            }
            customData = customData.concat("}");
            
            // Concat all data as the message 
            String message = notification.get("token").toString() + "|" + generateFbRef(notification.get("token").toString()) + "|" + notification.get("message").toString() + "|" + notification.get("link").toString() + "|" + customData;

            // Push it out
            ampqNotifier.pushToWSByTopic(EXCHANGE_NAME, keytopic, message);

        }

        if (this.isNotifyTo(platforms, NDLEnum.Scope.PLATFORM_HTML5.getSring())) {

            System.out.println("activate html5");

            // !IMPORTANT - PREFIX signal.html5.* as the exchane name
            final String EXCHANGE_NAME = "signal.html5." + notification.get("topic").toString();

            // Concat the custom html5 data into JSON format 
            String customData = "{";
            if (!notification.get("customhtml5").toString().equalsIgnoreCase("")) {
                customData = customData.concat(",").concat(notification.get("customhtml5").toString());
            }
            customData = customData.concat("}");
            
            // Concat all data as the message 
            String message = notification.get("token").toString() + "|" + generateFbRef(notification.get("token").toString()) + "|" + notification.get("message").toString() + "|" + notification.get("link").toString() + "|" + customData;

            // Push it out
            ampqNotifier.pushToWSByFanout(EXCHANGE_NAME, message);
        }

        if (this.isNotifyTo(platforms, NDLEnum.Scope.PLATFORM_IOS.getSring())) {
            System.out.println("activate ios");
            
            DbApis dbApis = new DbApis();
            dbApis.connect();
            HashMap apns = dbApis.getAPNs(notification.get("uuid").toString());
            dbApis.disconnect();
            
            if(apns != null){
                String pathToCer = "/Users/Suthat/Desktop/" + apns.get("certificate").toString();
                String pathToP12 = "/Users/Suthat/Desktop/" + apns.get("p12key").toString();
                String password = apns.get("password").toString();
                String message = notification.get("message").toString();

                try{
                    // To-Do
                    String customios = notification.get("customios").toString();
                    HashMap customdata = new HashMap();

                    // Send it out
                    String device = notification.get("iosdevice").toString();
                    if(device != null){
                        String devices[] = device.split(",");
                        if(devices.length == 1 && !device.equals("")){
                            apnsNotifier.sendMessageToSingleDevice(pathToCer, password, message, devices[0], customdata); 
                        }
                        if(devices.length > 1){
                            ArrayList<String> devicesList = new ArrayList<String>();
                            for(int i=0; i<devices.length; i++){
                                devicesList.add(""+devices[i]);
                            }
                            apnsNotifier.sendMessageToMultipleDevices(pathToCer, password, message, devicesList, customdata);
                        }
                    }
                }catch(Exception e){
                    //System.out.println("Exception: "+e.getMessage());
                }
            }else{
                //System.out.println("Exception: No APNs Authorized");
            }
        }

        if (this.isNotifyTo(platforms, NDLEnum.Scope.PLATFORM_ANDROID.getSring())) {
            System.out.println("activate android");
            
            DbApis dbApis = new DbApis();
            dbApis.connect();
            HashMap gcm = dbApis.getCGM(notification.get("uuid").toString());
            dbApis.disconnect();
            if(gcm != null){
                String googleAPIsKey = gcm.get("googleapiskey").toString();
                String message = notification.get("message").toString();
                
                // To-Do
                String customandroid = notification.get("customandroid").toString();
                HashMap customdata = new HashMap();

                // Send it out
                String device = notification.get("androiddevice").toString();
                if(device != null){
                    String devices[] = device.split(",");
                    if(devices.length == 1 && !device.equals("")){
                        gcmNotifier.sendMessageToSingleDevice(googleAPIsKey, message, devices[0]);
                    }
                    if(devices.length > 1){
                        ArrayList<String> devicesList = new ArrayList<String>();
                        for(int i=0; i<devices.length; i++){
                            devicesList.add(""+devices[i]);
                        }
                        gcmNotifier.sendMessageToMultipleDevices(googleAPIsKey, message, devicesList);
                    }
                }
            } else {
                //System.out.println("No google apis key");
            }
        }
        
        // condition checking
        // ! important under dev
        DbApis dbApis = new DbApis();
        dbApis.connect();
        ArrayList<String> nconds = dbApis.getNcond(notification.get("token").toString());
        dbApis.disconnect();
        for(String ncond : nconds){
            // To-Do
            // 1. condition schemes 
            // 2. condition processes with dbApis and some future calculation  
        }
    }

    public boolean isNotifyTo(String platform, String selectedPlatform) {
        boolean yes = false;
        String tmp[] = platform.split(",");
        for (int i = 0; i < tmp.length; i++) {
            if (tmp[i].equalsIgnoreCase(selectedPlatform)) {
                yes = true;
            }
        }
        return yes;
    }
    
    public String generateFbRef(String token){
        return helpers.generateToken("$_."+token.substring(0,16) + helpers.randomString().substring(0, 16) + (int)(System.currentTimeMillis()/1000L));
    }
}
