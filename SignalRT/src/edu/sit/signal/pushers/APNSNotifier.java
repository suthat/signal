package edu.sit.signal.pushers;

import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Suthat
 */
public class APNSNotifier {
    
    public void sendMessageToSingleDevice(String certificatePath, String password, String message, String deviceToken, HashMap customFeilds){
        ApnsService service = APNS.newService()
                                .withCert(certificatePath, password)
                                .withSandboxDestination()
                                .build();
        String payload = APNS.newPayload()
                .alertBody(message)
                .customFields(customFeilds)
                .build();      
        String token = deviceToken;
        service.push(token, payload);
    }  
    
    public void sendMessageToMultipleDevices(String certificatePath, String password, String message, ArrayList<String> devicesList, HashMap customFeilds){
        ApnsService service = APNS.newService()
                                .withCert(certificatePath, password)
                                .withSandboxDestination()
                                .build();
        String payload = APNS.newPayload()
                .alertBody(message)
                .customFields(customFeilds)
                .build();
        for(String deviceToken : devicesList){
            String token = deviceToken;
            service.push(token, payload);
        }
    }  
}
