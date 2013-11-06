package edu.sit.signal.parsers;

import edu.sit.signal.apis.DbApis;
import edu.sit.signal.app.Helpers;
import edu.sit.signal.models.Feedback;
import edu.sit.signal.models.Notification;
import edu.sit.signal.nql.NQLEnum;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author Suthat
 */
public class NQLParser {

    LinkedList<String> nqlNode = null;
    Notification notificationModel = null;
    Feedback feedbackModel = null;
    Helpers helpers = null;
    String select = "";
    String from = "";
    String where = "";
    String on = "";
    String device = "";
    String order = "";
    String limit = "";

    public NQLParser() {
        nqlNode = new LinkedList<String>();
        notificationModel = new Notification();
        feedbackModel = new Feedback();
        helpers = new Helpers();
    }

    // !IMPORTANT - Dev
    /*public static void main(String args[]) {
        new NQLParser().parseNQLfromDbtoJSON("");
    }*/

    public String parseNQLfromDbtoJSON(String nql){
        //nql = "SELECT ALL FROM NOTIFICATION WHERE APP.UUID IS 596b65af-61b3-4b8d-9e58-a84f62c00c89 ON TODAY ORDER DATE DESC LIMIT 0,50";
        //nql = "SELECT ALL FROM NOTIFICATION.DATA WHERE NOTIFICATION IS 4779a8a383f61ba4faef2674b2690ae7 ON TODAY ORDER DATE DESC LIMIT 0,50";
        //nql = "SELECT ALL FROM NOTIFICATION.FEEDBACK WHERE NOTIFICATION.TOKEN IS 20e1ef28c7ffc51ceb77df784236bb3c ON TODAY DEVICE ANY ORDER DATE DESC LIMIT 0,50";
        
        String nqlSet[] = nql.split(" ");
        for (int i = 0; i < nqlSet.length; i++) {
            nqlNode.add(nqlSet[i]);
        }
        
        try {
            if (nqlNode.getFirst().equalsIgnoreCase(NQLEnum.Lang.LANG_SELECT.getSring())) {
                nqlNode.removeFirst();
                select = nqlNode.getFirst();
                nqlNode.removeFirst();
            }
            if(nqlNode.getFirst().equalsIgnoreCase(NQLEnum.Lang.LANG_FROM.getSring())){
                nqlNode.removeFirst();
                from = nqlNode.getFirst();
                nqlNode.removeFirst();
            }
            if(nqlNode.getFirst().equalsIgnoreCase(NQLEnum.Lang.LANG_WHERE.getSring())){
                nqlNode.removeFirst();
                where = nqlNode.getFirst();
                nqlNode.removeFirst();
                if(nqlNode.getFirst().equalsIgnoreCase(NQLEnum.Where.WHERE_EQUALS.getSring())){
                    nqlNode.removeFirst();
                    where = where.concat(">");
                    where = where.concat(nqlNode.getFirst());
                    nqlNode.removeFirst();
                }
            }
            if(! nqlNode.isEmpty()){
                if(nqlNode.getFirst().equalsIgnoreCase(NQLEnum.Lang.LANG_ON.getSring())){
                    nqlNode.removeFirst();
                    on = nqlNode.getFirst();
                    nqlNode.removeFirst();
                    if(nqlNode.getFirst().equalsIgnoreCase(NQLEnum.On.ON_BETWEEN.getSring())){
                        nqlNode.removeFirst();
                        on = on.concat(">");
                        on = on.concat(nqlNode.getFirst());
                        nqlNode.removeFirst();
                    }
                }
            }
            if(! nqlNode.isEmpty()){
                if(nqlNode.getFirst().equalsIgnoreCase(NQLEnum.Lang.LANG_DEVICE.getSring())){
                    nqlNode.removeFirst();
                    device = nqlNode.getFirst();
                    nqlNode.removeFirst();
                }
            }
            if(! nqlNode.isEmpty()){
                if(nqlNode.getFirst().equalsIgnoreCase(NQLEnum.Lang.LANG_ORDER.getSring())){
                    nqlNode.removeFirst();
                    order = nqlNode.getFirst();
                    nqlNode.removeFirst();
                    order = order.concat(">");
                    order = order.concat(nqlNode.getFirst());
                    nqlNode.removeFirst();
                }
            }
            if(! nqlNode.isEmpty()){
                if(nqlNode.getFirst().equalsIgnoreCase(NQLEnum.Lang.LANG_LIMIT.getSring())){
                    nqlNode.removeFirst();
                    limit = nqlNode.getFirst();
                    nqlNode.removeFirst();
                }
            }
            
            // !IMPORTANT - Dev
            /*System.out.println("select: " + select);
            System.out.println("from: " + from);
            System.out.println("where: " + where);
            System.out.println("on: " + on);
            System.out.println("device: " + device);
            System.out.println("order: " + order);
            System.out.println("limit: " + limit);*/
            
        } catch(Exception e) {
            return "Exception: Invalid Syntax";
        }
        
        if (from.equalsIgnoreCase(NQLEnum.From.FROM_NOTIFICATION.getSring())) {
            try{
                String selectList[] = select.split(",");
                String tmp[] = where.split(">");
                String uuid = tmp[1];
                String orderBy = "id DESC";
                if(! order.equals("")){
                    tmp = order.split(">");
                    if(! tmp[0].equals(NQLEnum.Order.ORDER_BY_DATE.getSring()))
                        orderBy = tmp[0]+" "+tmp[1];
                }
                if(limit.equals(""))
                    limit = "100";
                
                DbApis dbApis = new DbApis();
                dbApis.connect();
                String appId = dbApis.getAppIdByUUID(uuid);
                dbApis.disconnect();
                
                notificationModel.connect();
                ArrayList<HashMap> notifications = notificationModel.getNotificationByApp(appId, limit);
                notificationModel.disconnect();
                
                String temp = "";
                String resultJSON = "{";
                if(notifications.size() > 0)
                    resultJSON = resultJSON.concat("\"notification\":[");
                
                for(HashMap notification : notifications){
                    temp += "{";
                    if(Arrays.asList(selectList).contains("notification") || select.equalsIgnoreCase(NQLEnum.Select.SELECT_ALL.getSring()))
                        temp += "\"notification\":\""+notification.get("notification").toString()+"\",";
                    if(Arrays.asList(selectList).contains("type") || select.equalsIgnoreCase(NQLEnum.Select.SELECT_ALL.getSring()))
                        temp += "\"type\":\""+notification.get("type").toString()+"\",";
                    if(Arrays.asList(selectList).contains("domain") || select.equalsIgnoreCase(NQLEnum.Select.SELECT_ALL.getSring()))
                        temp += "\"domain\":\""+notification.get("domain").toString()+"\",";
                    if(Arrays.asList(selectList).contains("platform") || select.equalsIgnoreCase(NQLEnum.Select.SELECT_ALL.getSring()))
                        temp += "\"platform\":\""+notification.get("platform").toString()+"\",";
                    if(Arrays.asList(selectList).contains("notifytime") || select.equalsIgnoreCase(NQLEnum.Select.SELECT_ALL.getSring()))
                        temp += "\"notifytime\":\""+notification.get("notifytime").toString()+"\",";
                    if(Arrays.asList(selectList).contains("notifyrestrict") || select.equalsIgnoreCase(NQLEnum.Select.SELECT_ALL.getSring()))
                        temp += "\"notifyrestrict\":\""+notification.get("notifyrestrict").toString()+"\",";
                    if(Arrays.asList(selectList).contains("fbactivate") || select.equalsIgnoreCase(NQLEnum.Select.SELECT_ALL.getSring()))
                        temp += "\"fbactivate\":\""+notification.get("fbactivate").toString()+"\",";
                    if(Arrays.asList(selectList).contains("fbvars") || select.equalsIgnoreCase(NQLEnum.Select.SELECT_ALL.getSring()))
                        temp += "\"fbvars\":\""+notification.get("fbvars").toString()+"\",";
                    temp = temp.substring(0, temp.length()-1);
                    temp += "},";
                    resultJSON = resultJSON.concat(temp);
                    temp = "";
                }
                if(notifications.size() > 0)
                    resultJSON = resultJSON.substring(0, resultJSON.length()-1);
                
                if(notifications.size() > 0)
                    resultJSON = resultJSON.concat("]");
                
                resultJSON = resultJSON.concat("}");
                
                //System.out.println(resultJSON);
                return resultJSON;
                
            }catch(Exception e){
                return "Exception : Invalid Parameters";
            }
        }
        
        if (from.equalsIgnoreCase(NQLEnum.From.FROM_NOTIFICATION_DATA.getSring())) {
            try{
                String selectList[] = select.split(",");
                String tmp[] = where.split(">");
                String noftId = tmp[1];
                String orderBy = "id DESC";
                if(! order.equals("")){
                    tmp = order.split(">");
                    if(! tmp[0].equals(NQLEnum.Order.ORDER_BY_DATE.getSring()))
                        orderBy = tmp[0]+" "+tmp[1];
                }
                if(limit.equals(""))
                    limit = "100";
                
                notificationModel.connect();
                ArrayList<HashMap> ndataSet = notificationModel.getNdataOfNotification(noftId, orderBy, limit);
                notificationModel.disconnect();
                
                String temp = "";
                String resultJSON = "{";
                if(ndataSet.size() > 0)
                    resultJSON = resultJSON.concat("\"notification-data\":[");
                
                for(HashMap ndata : ndataSet){
                    temp += "{";
                    if(Arrays.asList(selectList).contains("token") || select.equalsIgnoreCase(NQLEnum.Select.SELECT_ALL.getSring()))
                        temp += "\"token\":\""+ndata.get("token").toString()+"\",";
                    if(Arrays.asList(selectList).contains("topic") || select.equalsIgnoreCase(NQLEnum.Select.SELECT_ALL.getSring()))
                        temp += "\"topic\":\""+ndata.get("topic").toString()+"\",";
                    if(Arrays.asList(selectList).contains("message") || select.equalsIgnoreCase(NQLEnum.Select.SELECT_ALL.getSring()))
                        temp += "\"message\":\""+ndata.get("message").toString()+"\",";
                    if(Arrays.asList(selectList).contains("link") || select.equalsIgnoreCase(NQLEnum.Select.SELECT_ALL.getSring()))
                        temp += "\"link\":\""+ndata.get("link").toString()+"\",";
                    if(Arrays.asList(selectList).contains("state") || select.equalsIgnoreCase(NQLEnum.Select.SELECT_ALL.getSring())){
                        if(ndata.get("state").toString().equals("1"))
                            temp += "\"state\":\"Ready to deliver message\",";
                        if(ndata.get("state").toString().equals("2"))
                            temp += "\"state\":\"Notified\",";
                    }
                    temp = temp.substring(0, temp.length()-1);
                    temp += "},";
                    resultJSON = resultJSON.concat(temp);
                    temp = "";
                }
                if(ndataSet.size() > 0)
                    resultJSON = resultJSON.substring(0, resultJSON.length()-1);
                
                if(ndataSet.size() > 0)
                    resultJSON = resultJSON.concat("]");
                
                resultJSON = resultJSON.concat("}");
                
                //System.out.println(resultJSON);
                return resultJSON;
                
            }catch(Exception e){
                return "Exception : Invalid Parameters";
            }
        }
        
        if (from.equalsIgnoreCase(NQLEnum.From.FROM_NOTIFICATION_FEEDBACK.getSring())) {
            try{
                String selectList[] = select.split(",");

                String tmp[] = where.split(">");
                String ndataToken = tmp[1];
                String whereES = "";
                if(! device.equalsIgnoreCase(NQLEnum.Device.DEVICE_ANY.getSring())){
                    whereES = whereES.concat(" AND (fdata.platfrom = \""+device+"\")");
                }
                String orderBy = "id DESC";
                if(! order.equals("")){
                    tmp = order.split(">");
                    if(! tmp[0].equals(NQLEnum.Order.ORDER_BY_DATE.getSring()))
                        orderBy = tmp[0]+" "+tmp[1];
                }
                if(limit.equals(""))
                    limit = "100";
                
                feedbackModel.connect();
                ArrayList<HashMap> fbVars = feedbackModel.getVars(ndataToken, whereES, orderBy, limit);
                feedbackModel.disconnect();
                
                String temp = "";
                String resultJSON = "{";
                if(fbVars.size() > 0)
                    resultJSON = resultJSON.concat("\"notification-feedback\":[");
                
                for(HashMap fbvar : fbVars){
                    temp += "{";
                    temp += "\"feedback-reference\":\""+fbvar.get("fbref").toString()+"\",";
                    temp += "\"feedback-platform\":\""+fbvar.get("platform").toString()+"\",";
                    temp += "\"delivered-on\":\""+helpers.timeStampToHuman(fbvar.get("deliveredon").toString(), true)+"\",";
                    if(! fbvar.get("strname").toString().equals(""))
                        if(Arrays.asList(selectList).contains(fbvar.get("strname").toString()) || select.equalsIgnoreCase(NQLEnum.Select.SELECT_ALL.getSring()))
                            temp += "\""+fbvar.get("strname").toString()+"\":\""+fbvar.get("strval").toString()+"\",";
                    if(! fbvar.get("intname").toString().equals(""))
                        if(Arrays.asList(selectList).contains(fbvar.get("intname").toString()) || select.equalsIgnoreCase(NQLEnum.Select.SELECT_ALL.getSring()))
                            temp += "\""+fbvar.get("intname").toString()+"\":"+fbvar.get("intval").toString()+",";                
                    if(! fbvar.get("fltname").toString().equals(""))
                        if(Arrays.asList(selectList).contains(fbvar.get("fltname").toString()) || select.equalsIgnoreCase(NQLEnum.Select.SELECT_ALL.getSring()))
                            temp += "\""+fbvar.get("fltname").toString()+"\":"+fbvar.get("fltval").toString()+","; 
                    if(! fbvar.get("bolname").toString().equals(""))
                        if(Arrays.asList(selectList).contains(fbvar.get("bolname").toString()) || select.equalsIgnoreCase(NQLEnum.Select.SELECT_ALL.getSring()))
                            if(fbvar.get("bolval").toString().equals("1"))
                                temp += "\""+fbvar.get("bolname").toString()+"\":\"true\","; 
                            else
                                temp += "\""+fbvar.get("bolname").toString()+"\":\"false\","; 
                    if(! fbvar.get("arrname").toString().equals(""))
                        if(Arrays.asList(selectList).contains(fbvar.get("arrname").toString()) || select.equalsIgnoreCase(NQLEnum.Select.SELECT_ALL.getSring()))
                            temp += "\""+fbvar.get("arrname").toString()+"\":[\""+fbvar.get("arrval").toString()+"\"],";
                    temp = temp.substring(0, temp.length()-1);
                    temp += "},";
                    resultJSON = resultJSON.concat(temp);
                    temp = "";
                }
                if(fbVars.size() > 0)
                    resultJSON = resultJSON.substring(0, resultJSON.length()-1);
                
                if(fbVars.size() > 0)
                    resultJSON = resultJSON.concat("]");
                
                resultJSON = resultJSON.concat("}");
                
                //System.out.println(resultJSON);
                return resultJSON;
                
            }catch(Exception e){
                return "Exception : Invalid Parameters";
            }
        }
        
        return "";
    }
}
