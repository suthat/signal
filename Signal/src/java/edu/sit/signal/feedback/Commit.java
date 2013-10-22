package edu.sit.signal.feedback;

import edu.sit.signal.apis.DbApis;
import edu.sit.signal.models.Feedback;
import java.util.HashMap;

/**
 *
 * @author Suthat
 */
public class Commit implements Runnable {
    
    private String token;
    private String fbRef;
    private String[] dataSet;
    
    public Commit(String token, String fbRef, String dataSet[]){
        this.token = token;
        this.fbRef = fbRef;
        this.dataSet = dataSet;
    }

    @Override
    public void run() {

        DbApis dbApis = new DbApis();
        dbApis.connect();
        HashMap notification = dbApis.getNotificationByToken(token);
        dbApis.disconnect();
        
        int fbactivate = Integer.parseInt(notification.get("fbactivate").toString());
        String fbvars[] = {};
        try{
            fbvars = notification.get("fbvars").toString().split(",");
        }catch(Exception e){ 
            e.getMessage();
        }
        
        // Only fbactivated will be commit its feedback
        if(fbactivate == 1){
            
            String dsTmp[];
            HashMap commitVars = new HashMap();
            String fvTmp[], fvType = "", fvName = "";
            
            for(int i=0; i<dataSet.length; i++) {
                dsTmp = dataSet[i].split("=");
                try{
                    commitVars.put(dsTmp[0], dsTmp[1]);
                }catch(Exception e){
                    e.getMessage();
                }
            }
            
            for(int i=0; i<fbvars.length; i++) {
                fvTmp = fbvars[i].split(":");
                fvType = fvTmp[0]; 
                fvName = fvTmp[1];
                
                if(commitVars.containsKey(fvName)){
                    addFeedbackVariable(fvType.toLowerCase(), fvName, commitVars.get(fvName).toString());
                }
            }
            
        }
    }
    
    public boolean addFeedbackVariable(String dataType, String fvName, String fvValue){

        Feedback feedbackModel = new Feedback();
        feedbackModel.connect();
        
        if(dataType.equalsIgnoreCase("boolean")){
            if(fvValue.equalsIgnoreCase("true"))
                fvValue = ""+1;
            else
                fvValue = ""+0;
        }else if(dataType.equalsIgnoreCase("string") || dataType.equalsIgnoreCase("array")) {
            fvValue = "\""+fvValue+"\"";
        }
        
        boolean result = feedbackModel.addVar(dataType, token, fbRef, fvName, fvValue);
        feedbackModel.disconnect();
        
        return result;
    }
}
