package edu.sit.signal.parsers;

import edu.sit.signal.apis.DbApis;
import edu.sit.signal.app.Helpers;
import edu.sit.signal.models.Mapper;
import edu.sit.signal.nml.NMLEnum;
import edu.sit.signal.nml.NMLSchema;
import edu.sit.signal.nml.NMLSchema.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Suthat
 */
public class NMLParser {
    
    private Mapper mapperModel = new Mapper();
    private DbApis dbApis = new DbApis();
    private Helpers helpers = new Helpers();
    
    private int appId;
    private NMLSchema nmlSchema = null;
    
    public NMLParser(NMLSchema nmlSchema) {
        this.nmlSchema = nmlSchema;
        this.appId = 0;
    }
    
    public String parseNDLfromJSONtoDb() {
        
        // get app id
        dbApis.connect();
        appId = Integer.parseInt(dbApis.authorizedProvider(nmlSchema.getSignalnml().getAuth().getId(), nmlSchema.getSignalnml().getAuth().getToken()));
        dbApis.disconnect();
        
        mapperModel.connect();
        
        if(mapperModel.isExisted(appId)){
            mapperModel.clearCurrentData(appId);
        }
        
        HashMap mapper = new HashMap();
        mapper.put("app", ""+appId);
        mapper.put("accesstoken", helpers.generateToken(helpers.randomString()+"$"+helpers.getCurrentTimestamp("time")));
        
        // mapper
        mapper.put("reqapproach", nmlSchema.getSignalnml().getNotification().getRequestapproach());
        if(nmlSchema.getSignalnml().getNotification().getRequesturl().equalsIgnoreCase(NMLEnum.Notification.REQUEST_URL_IGNORE.getSring()))
            mapper.put("requrl", ""+nmlSchema.getSignalnml().getNotification().getRequesturl());
        else    
            mapper.put("requrl", ""+nmlSchema.getSignalnml().getNotification().getRequesturl());
        
        if(nmlSchema.getSignalnml().getNotification().getRequesttimeinterval().equalsIgnoreCase(NMLEnum.Notification.REQUEST_TIME_INTERVAL_IGNORE.getSring()))
            mapper.put("reqtimeinterval", "0");
        else    
            mapper.put("reqtimeinterval", ""+nmlSchema.getSignalnml().getNotification().getRequesttimeinterval());
        
        String tmp[] = nmlSchema.getSignalnml().getNotification().getMapper();
        String maps = "";
        for(int i=0; i<tmp.length; i++){
            maps = maps.concat(tmp[i]).concat(",");
        }
        maps = maps.substring(0, maps.length() - 1);
        mapper.put("maps", maps);
        
        // feedback
        HashMap feebback = new HashMap();
        feebback.put("app", ""+appId);
        
        String conds = "";
        tmp = nmlSchema.getSignalnml().getFeedback().getCondition();
         for(int i=0; i<tmp.length; i++){
            conds = conds.concat(tmp[i]).concat(",");
        }
        conds = conds.substring(0, conds.length() - 1);
        feebback.put("cond", conds);
        
        feebback.put("checker", "");
        feebback.put("pointer", "");
        feebback.put("stmreference", "");
        feebback.put("doesiton", "");
        
        mapperModel.add(mapper);
        mapperModel.addFeedback(feebback);
        
        // statement
        HashMap statement = null;
        ArrayList<Statement> statements = nmlSchema.getSignalnml().getFeedback().getStatement();
        for(Statement stm : statements){
            statement = new HashMap();
            statement.put("app", ""+appId);
            statement.put("reference", stm.getReference());
            statement.put("requrl", ""+stm.getRequesturl());
            statement.put("reqtimeout", ""+Integer.parseInt(stm.getRequesttimeout()));
            
            ArrayList<String> temp = collectEachCustomData(stm.getRequestdata(), 'x');
            String reqdata = "";
            for(String rqdata : temp){
                reqdata = reqdata.concat(rqdata).concat(",");
            }
            reqdata = reqdata.substring(0, reqdata.length() - 1);
            statement.put("reqdata", reqdata);
            
            temp = collectEachCustomData(stm.getRequestheader(), 'x');
            String reqheader = "";
            for(String rqheader : temp){
                reqheader = reqheader.concat(rqheader).concat(",");
            }
            reqheader = reqheader.substring(0, reqheader.length() - 1);
            statement.put("reqheader", reqheader);
            
            mapperModel.addStatement(statement);
        }  
        
        mapperModel.disconnect();
        
        return "";
    }
    
    public ArrayList<String> collectEachCustomData(ArrayList<Map<String, String>> mapper, char filter) {
        // Make one collect for map of string and string
        ArrayList<String> tmp = new ArrayList<String>();
        // Map custom data of each platform
        if (mapper != null) {
            for (Map<String, String> map : mapper) {
                for (Map.Entry<String, String> entity : map.entrySet()) {
                    if (filter == 'j') {
                        tmp.add("\"" + entity.getKey() + "\" : \"" + entity.getValue() + "\"");
                    }
                    if (filter == 'x') {
                        tmp.add(entity.getKey() + ":" + entity.getValue());
                    }
                    if (filter == 'k') {
                        tmp.add(entity.getKey());
                    }
                    if (filter == 'v') {
                        tmp.add(entity.getValue());
                    }
                }
            }
        }
        return tmp;
    }
}
