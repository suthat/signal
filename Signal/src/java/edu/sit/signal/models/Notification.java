package edu.sit.signal.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Suthat
 */
public class Notification extends edu.sit.signal.app.Config{
    
    private String tableNotification = dbname + ".notifications";
    private String tableNData = dbname + ".ndata";
    private String tableNCond = dbname + ".ncond";
    private String tableApp = dbname + ".apps";
    
    Connection connector;
    PreparedStatement statement;
    ResultSet result;

    public void connect() {
        try {
            Class.forName(dbdriver);
            connector = DriverManager.getConnection(dbhost, dbusername, dbpassword);
        } catch (Exception e) {
            System.out.println("Exception : Notification.connect : " + e.getMessage());
        }
    }
    
    public boolean isExistedUid(String uid){
        boolean isExisted = false;
        String sql = "SELECT id FROM " + tableNotification + " WHERE uid = ? LIMIT 1;";
        try {
            statement = connector.prepareStatement(sql);
            statement.setString(1, uid);
            result = statement.executeQuery();
            if (result.next()) {
                isExisted = true;
            }
            result.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("Exception : Notification.isExistedUid : " + e.getMessage());
        }
        return isExisted;
    }
    
    public boolean addNotification(HashMap data){
        String sql = "INSERT INTO " + tableNotification + " (uid,app,type,domain,timezone,platform,language,notifytime,notifyrestrict,starttime,expiretime,iosdevice,androiddevice,keytopic,createdby,fbactivate,fbvars,status) " +
                        "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
        try {
            statement = connector.prepareStatement(sql);
            statement.setString(1, data.get("uid").toString());
            statement.setInt(2, Integer.parseInt(data.get("app").toString()));
            statement.setString(3, data.get("type").toString());
            statement.setString(4, data.get("domain").toString());
            statement.setString(5, data.get("timezone").toString());
            statement.setString(6, data.get("platform").toString());
            statement.setString(7, data.get("language").toString());
            statement.setString(8, data.get("notifytime").toString());
            statement.setString(9, data.get("restrict").toString());
            statement.setString(10, data.get("starttime").toString());
            statement.setString(11, data.get("expiretime").toString());
            statement.setString(12, data.get("iosdevice").toString());
            statement.setString(13, data.get("androiddevice").toString());
            statement.setString(14, data.get("keytopic").toString());
            statement.setString(15, data.get("createdby").toString());
            statement.setInt(16, Integer.parseInt(data.get("fbactivate").toString()));
            statement.setString(17, data.get("fbvars").toString());
            statement.setInt(18, 1);
            int result = statement.executeUpdate();
            statement.close();
            if (result == 1) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("Exception : Notification.addNotification : " + e.getMessage());
        }
        return false;
    }
    
    public boolean addNData(HashMap data){
        String sql = "INSERT INTO " + tableNData + " (notification,token,notifytrigger,trigger_reference,trigger_time,trigger_repeat,topic,message,link,customios,customandroid,customcli,customhtml5,state) " +
                        "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
        try {
            statement = connector.prepareStatement(sql);
            statement.setString(1, data.get("notification").toString());
            statement.setString(2, data.get("token").toString());
            statement.setString(3, data.get("trigger").toString());
            statement.setString(4, data.get("trigger_reference").toString());
            statement.setString(5, data.get("trigger_time").toString());
            statement.setString(6, data.get("trigger_repeat").toString());
            statement.setString(7, data.get("topic").toString());
            statement.setString(8, data.get("message").toString());
            statement.setString(9, data.get("link").toString());
            statement.setString(10, data.get("customios").toString());
            statement.setString(11, data.get("customandroid").toString());
            statement.setString(12, data.get("customcli").toString());
            statement.setString(13, data.get("customhtml5").toString());
            statement.setInt(14, 1);
            int result = statement.executeUpdate();
            statement.close();
            if (result == 1) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("Exception : Notification.addNData : " + e.getMessage());
        }
        return false;
    }
    
    public boolean addNCond(String token, String condition){
        String sql = "INSERT INTO " + tableNCond + " (token,repeat_cond,state) " +
                        "VALUES(?,?,?);";
        try {
            statement = connector.prepareStatement(sql);
            statement.setString(1, token);
            statement.setString(2, condition);
            statement.setInt(3, 1);
            int result = statement.executeUpdate();
            statement.close();
            if (result == 1) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("Exception : Notification.addNCond : " + e.getMessage());
        }
        return false;
    }
    
    public HashMap getNotification(String noftId){
        HashMap notification = new HashMap();
        String sql =  "SELECT notification.*, ndata.*, app.* "
                    + "FROM " + tableNData + " ndata "
                    + "JOIN " + tableNotification + " notification ON notification.uid = ndata.notification "
                    + "JOIN " + tableApp + " app ON app.id = notification.app " 
                    + "WHERE "
                        + "notification.uid = ? LIMIT 1;";
        try {
            statement = connector.prepareStatement(sql);
            statement.setString(1, noftId);
            result = statement.executeQuery();
            if (result.next()) {
                notification.put("notification", result.getString("uid"));
                notification.put("app", result.getString("app"));
                notification.put("type", result.getString("type"));
                notification.put("domain", result.getString("domain"));
                notification.put("platform", result.getString("platform"));
                notification.put("language", result.getString("language"));
                notification.put("notifytime", result.getString("notifytime"));
                notification.put("notifyrestrict", result.getString("notifyrestrict"));
                notification.put("starttime", result.getString("starttime"));
                notification.put("expiretime", result.getString("expiretime"));
                notification.put("iosdevice", result.getString("iosdevice"));
                notification.put("androiddevice", result.getString("androiddevice"));
                notification.put("keytopic", result.getString("keytopic"));
                notification.put("fbactivate", result.getString("fbactivate"));
                notification.put("fbvars", result.getString("fbvars"));
                notification.put("notifytrigger", result.getString("notifytrigger"));
                notification.put("token", result.getString("token"));
                notification.put("topic", result.getString("topic"));
                notification.put("message", result.getString("message"));
                notification.put("link", result.getString("link"));
                notification.put("customios", result.getString("customios"));
                notification.put("customandroid", result.getString("customandroid"));
                notification.put("customcli", result.getString("customcli"));
                notification.put("customhtml5", result.getString("customhtml5"));
                notification.put("state", result.getString("state"));
                notification.put("uuid", result.getString("uuid"));
            }
            result.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("Exception : Notification.getNotification : " + e.getMessage());
        }
        return notification;
    }
    
    public ArrayList<HashMap> getNotificationByApp(String appId, String limit){
        ArrayList<HashMap> notifications = new ArrayList<HashMap>();
        HashMap notification = null;
        String sql =  "SELECT DISTINCT notification.*,ndata.topic "
                    + "FROM " + tableNotification + " notification "
                    + "JOIN " + tableNData + " ndata ON ndata.notification = notification.uid "
                    + "WHERE "
                        + "notification.app = ? ORDER BY notification.id DESC LIMIT "+limit+";";
        try {
            statement = connector.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(appId));
            result = statement.executeQuery();
            while (result.next()) {
                notification = new HashMap();
                notification.put("notification", result.getString("uid"));
                notification.put("app", result.getString("app"));
                notification.put("type", result.getString("type"));
                notification.put("domain", result.getString("domain"));
                notification.put("platform", result.getString("platform"));
                notification.put("language", result.getString("language"));
                notification.put("notifytime", result.getString("notifytime"));
                notification.put("notifyrestrict", result.getString("notifyrestrict"));
                notification.put("starttime", result.getString("starttime"));
                notification.put("expiretime", result.getString("expiretime"));
                notification.put("iosdevice", result.getString("iosdevice"));
                notification.put("androiddevice", result.getString("androiddevice"));
                notification.put("keytopic", result.getString("keytopic"));
                notification.put("fbactivate", result.getString("fbactivate"));
                notification.put("fbvars", result.getString("fbvars"));
                notification.put("createdon", result.getString("createdon"));
                notification.put("status", result.getString("status"));
                notification.put("topic", result.getString("topic"));
                notifications.add(notification);
            }
            result.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("Exception : Notification.filterNotificationByApp : " + e.getMessage());
        }
        return notifications;
    }
    
    public ArrayList<HashMap> getNdataOfNotification(String notfId, String order, String limit){
        ArrayList<HashMap> ndataSet = new ArrayList<HashMap>();
        HashMap ndata = null;
        String sql =  "SELECT  * "
                    + "FROM " + tableNData + " "
                    + "WHERE "
                        + "notification = ? ORDER BY "+order+" LIMIT "+limit+";";
        try {
            statement = connector.prepareStatement(sql);
            statement.setString(1, notfId);
            result = statement.executeQuery();
            while (result.next()) {
                ndata = new HashMap();
                ndata.put("notifytrigger", result.getString("notifytrigger"));
                ndata.put("trigger_reference", result.getString("trigger_reference"));
                ndata.put("trigger_time", result.getString("trigger_time"));
                ndata.put("trigger_repeat", result.getString("trigger_repeat"));
                ndata.put("token", result.getString("token"));
                ndata.put("topic", result.getString("topic"));
                ndata.put("message", result.getString("message"));
                ndata.put("link", result.getString("link"));
                ndata.put("customios", result.getString("customios"));
                ndata.put("customandroid", result.getString("customandroid"));
                ndata.put("customcli", result.getString("customcli"));
                ndata.put("customhtml5", result.getString("customhtml5"));
                ndata.put("state", result.getString("state"));
                ndataSet.add(ndata);
            }
            result.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("Exception : Notification.getNdataOfNotification : " + e.getMessage());
        }
        return ndataSet;
    }
    
    public ArrayList<String> getNcond(String token){
        ArrayList<String> nconds = new ArrayList<String>();
        String sql =  "SELECT  * "
                    + "FROM " + tableNCond + " "
                    + "WHERE "
                        + "token = ? ;";
        try {
            statement = connector.prepareStatement(sql);
            statement.setString(1, token);
            result = statement.executeQuery();
            while (result.next()) {
                nconds.add(result.getString("repeat_cond"));
            }
            result.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("Exception : Notification.getNcond : " + e.getMessage());
        }
        return nconds;
    }
    
    public void disconnect() {
        try {
            connector.close();
        } catch (Exception e) {
            System.out.println("Exception : Notification.disconnect : " + e.getMessage());
        }
    }

}