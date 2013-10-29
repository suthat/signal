package edu.sit.signal.apis;

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
public class DbApis extends edu.sit.signal.app.Config {
    
    private String tableNotification = dbname + ".notifications";
    private String tableNData = dbname + ".ndata";
    private String tableNCond = dbname + ".ncond";
    private String tableApp = dbname + ".apps";
    private String tableGCM = dbname + ".appgcm";
    private String tableAPNs = dbname + ".appapns";
    
    Connection connector;
    PreparedStatement statement;
    ResultSet result;

    public void connect() {
        try {
            Class.forName(dbdriver);
            connector = DriverManager.getConnection(dbhost, dbusername, dbpassword);
        } catch (Exception e) {
            System.out.println("Exception : DbApis.connect : " + e.getMessage());
        }
    }
    
    public ArrayList<HashMap> filterBasicScheduleNotification(String triggerDateTime){
        ArrayList<HashMap> notifications = new ArrayList<HashMap>();
        HashMap notifies = null;
        String sql =  "SELECT notification.*, ndata.*, app.* "
                    + "FROM " + tableNData + " ndata "
                    + "JOIN " + tableNotification + " notification ON notification.uid = ndata.notification "
                    + "JOIN " + tableApp + " app ON app.id = notification.app " 
                    + "WHERE "
                        + "ndata.notifytrigger = ? AND ndata.state = 1;";
        try {
            statement = connector.prepareStatement(sql);
            statement.setString(1, triggerDateTime);
            result = statement.executeQuery();
            while (result.next()) {
                notifies = new HashMap();
                notifies.put("notification", result.getString("uid"));
                notifies.put("app", result.getString("app"));
                notifies.put("type", result.getString("type"));
                notifies.put("domain", result.getString("domain"));
                notifies.put("platform", result.getString("platform"));
                notifies.put("language", result.getString("language"));
                notifies.put("notifyrestrict", result.getString("notifyrestrict"));
                notifies.put("starttime", result.getString("starttime"));
                notifies.put("expiretime", result.getString("expiretime"));
                notifies.put("iosdevice", result.getString("iosdevice"));
                notifies.put("androiddevice", result.getString("androiddevice"));
                notifies.put("keytopic", result.getString("keytopic"));
                notifies.put("fbactivate", result.getString("fbactivate"));
                notifies.put("fbvars", result.getString("fbvars"));
                notifies.put("notifytrigger", result.getString("notifytrigger"));
                notifies.put("token", result.getString("token"));
                notifies.put("topic", result.getString("topic"));
                notifies.put("message", result.getString("message"));
                notifies.put("link", result.getString("link"));
                notifies.put("customios", result.getString("customios"));
                notifies.put("customandroid", result.getString("customandroid"));
                notifies.put("customcli", result.getString("customcli"));
                notifies.put("customhtml5", result.getString("customhtml5"));
                notifies.put("state", result.getString("state"));
                notifies.put("uuid", result.getString("uuid"));
                notifications.add(notifies);
            }
            result.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("Exception : DbApis.filterBasicScheduleNotification : " + e.getMessage());
        }
        return notifications;
    }
    
    public ArrayList<HashMap> filterComplexScheduleNotification(String triggerTime, String triggerRepeat){
        ArrayList<HashMap> notifications = new ArrayList<HashMap>();
        HashMap notifies = null;
        String sql =  "SELECT notification.*, ndata.*, app.* "
                    + "FROM " + tableNData + " ndata "
                    + "JOIN " + tableNotification + " notification ON notification.uid = ndata.notification "
                    + "JOIN " + tableApp + " app ON app.id = notification.app " 
                    + "WHERE "
                        + "ndata.trigger_time = ? AND (ndata.trigger_repeat LIKE \"%"+triggerRepeat+"%\" OR ndata.trigger_repeat = \"everyday\") AND ndata.state = 1;";
        try {
            statement = connector.prepareStatement(sql);
            statement.setString(1, triggerTime);
            result = statement.executeQuery();
            while (result.next()) {
                notifies = new HashMap();
                notifies.put("notification", result.getString("uid"));
                notifies.put("app", result.getString("app"));
                notifies.put("type", result.getString("type"));
                notifies.put("domain", result.getString("domain"));
                notifies.put("platform", result.getString("platform"));
                notifies.put("language", result.getString("language"));
                notifies.put("notifyrestrict", result.getString("notifyrestrict"));
                notifies.put("starttime", result.getString("starttime"));
                notifies.put("expiretime", result.getString("expiretime"));
                notifies.put("iosdevice", result.getString("iosdevice"));
                notifies.put("androiddevice", result.getString("androiddevice"));
                notifies.put("keytopic", result.getString("keytopic"));
                notifies.put("fbactivate", result.getString("fbactivate"));
                notifies.put("fbvars", result.getString("fbvars"));
                notifies.put("notifytrigger", result.getString("notifytrigger"));
                notifies.put("token", result.getString("token"));
                notifies.put("topic", result.getString("topic"));
                notifies.put("message", result.getString("message"));
                notifies.put("link", result.getString("link"));
                notifies.put("customios", result.getString("customios"));
                notifies.put("customandroid", result.getString("customandroid"));
                notifies.put("customcli", result.getString("customcli"));
                notifies.put("customhtml5", result.getString("customhtml5"));
                notifies.put("state", result.getString("state"));
                notifies.put("uuid", result.getString("uuid"));
                notifications.add(notifies);
            }
            result.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("Exception : DbApis.filterComplexScheduleNotification : " + e.getMessage());
        }
        return notifications;
    }
    
    public boolean setNDataState(String token, int stateVal){
        String sql = "UPDATE " + tableNData + " SET state = ? WHERE token = ?;";
        try {
            statement = connector.prepareStatement(sql);
            statement.setInt(1, stateVal);
            statement.setString(2, token);
            int result = statement.executeUpdate();
            statement.close();
            if (result == 1) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("Exceptopn : DbApis.setNDataState : " + e.getMessage());
        }
        return false;
    }
    
    public HashMap getCGM(String uuid){
        HashMap gcmData = null;
        String sql = "SELECT * FROM " + tableGCM + " WHERE uuid = ? LIMIT 1;";
        try {
            statement = connector.prepareStatement(sql);
            statement.setString(1, uuid);
            result = statement.executeQuery();
            if (result.next()) {
                gcmData = new HashMap();
                gcmData.put("googleapiskey", result.getString("googleapiskey"));
            }
            result.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("Excepton : DbApis.getCGM : " + e.getMessage());
        }
        return gcmData;
    }
    
    public HashMap getAPNs(String uuid){
        HashMap apnsData = null;
        String sql = "SELECT * FROM " + tableAPNs + " WHERE uuid = ? LIMIT 1;";
        try {
            statement = connector.prepareStatement(sql);
            statement.setString(1, uuid);
            result = statement.executeQuery();
            if (result.next()) {
                apnsData = new HashMap();
                apnsData.put("certificate", result.getString("certificate"));
                apnsData.put("p12key", result.getString("p12key"));
                apnsData.put("password", result.getString("password"));
            }
            result.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("Excepton : DbApis.getAPNs : " + e.getMessage());
        }
        return apnsData;
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
            System.out.println("Exception : DbApis.getNcond : " + e.getMessage());
        }
        return nconds;
    }
    
    public void disconnect() {
        try {
            connector.close();
        } catch (Exception e) {
            System.out.println("Exception : DbApis.disconnect : " + e.getMessage());
        }
    }
}
