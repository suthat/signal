package edu.sit.signal.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

/**
 *
 * @author Suthat
 */
public class App extends edu.sit.signal.app.Config{
    
    private String tableName = dbname + ".apps";
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
            System.out.println("Exception : App.connect : " + e.getMessage());
        }
    }
    
    public boolean add(HashMap data){
        String sql = "INSERT INTO " + tableName + " (user,uuid,secret,name,description,status,ios,android,cli,html5) " +
                        "VALUES(?,?,?,?,?,?,?,?,?,?);";
        try {
            statement = connector.prepareStatement(sql);
            statement.setString(1, data.get("user").toString());
            statement.setString(2, data.get("uuid").toString());
            statement.setString(3, data.get("secret").toString());
            statement.setString(4, data.get("name").toString());
            statement.setString(5, data.get("description").toString());
            statement.setInt(6, 1);
            statement.setString(7, data.get("ios").toString());
            statement.setString(8, data.get("android").toString());
            statement.setString(9, data.get("cli").toString());
            statement.setString(10, data.get("html5").toString());
            int result = statement.executeUpdate();
            statement.close();
            if (result == 1) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("Exception : App.add : " + e.getMessage());
        }
        return false;
    }
    
    public HashMap findByUser(String user){
        HashMap appData = null;
        String sql = "SELECT * FROM " + tableName + " WHERE user = ? LIMIT 1;";
        try {
            statement = connector.prepareStatement(sql);
            statement.setString(1, user);
            result = statement.executeQuery();
            if (result.next()) {
                appData = new HashMap();
                appData.put("id", result.getString("id"));
                appData.put("uuid", result.getString("uuid"));
                appData.put("secret", result.getString("secret"));
                appData.put("name", result.getString("name"));
                appData.put("description", result.getString("description"));
                appData.put("created", result.getString("created"));
                appData.put("created", result.getString("created"));
                appData.put("status", result.getString("status"));
                appData.put("ios", result.getString("ios"));
                appData.put("android", result.getString("android"));
                appData.put("cli", result.getString("cli"));
                appData.put("html5", result.getString("html5"));
            }
            result.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("Exception : App.findByUser : " + e.getMessage());
        }
        return appData;
    }
    
    public boolean addGCM(String command, String uuid, String googleAPIsKey){
        String sql = "";
        if(command.equals("insert")){
            sql = "INSERT INTO " + tableGCM + " (uuid,googleapiskey) VALUES(?,?);";
        }else if(command.equals("update")){
            sql = "UPDATE " + tableGCM + " SET googleapiskey = ? WHERE uuid = ?;";
        }
        try {
            statement = connector.prepareStatement(sql);
            if(command.equals("insert")){
                statement.setString(1, uuid);
                statement.setString(2, googleAPIsKey);
            }else if(command.equals("update")){
                statement.setString(1, googleAPIsKey);
                statement.setString(2, uuid);
            }
            int result = statement.executeUpdate();
            statement.close();
            if (result == 1) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("Exception : App.addGCM : " + e.getMessage());
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
            System.out.println("Exception : App.getCGM : " + e.getMessage());
        }
        return gcmData;
    }
    
    public boolean addAPNs(String command, String uuid, String entity, String value){
        String sql = "";
        if(command.equals("insert")){
            sql = "INSERT INTO " + tableAPNs + " (uuid,certificate,p12key,password) VALUES(?,?,?,?);";
        }else if(command.equals("update")){
            sql = "UPDATE " + tableAPNs + " SET "+entity+" = ? WHERE uuid = ?;";
        }
        try {
            statement = connector.prepareStatement(sql);
            if(command.equals("insert")){
                statement.setString(1, uuid);
                statement.setString(2, "");
                statement.setString(3, "");
                statement.setString(4, "");
            }else if(command.equals("update")){
                statement.setString(1, value);
                statement.setString(2, uuid);
            }
            int result = statement.executeUpdate();
            statement.close();
            if (result == 1) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("Exception : App.addAPNs : " + e.getMessage());
        }
        return false;
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
            System.out.println("Exception : App.getAPNs : " + e.getMessage());
        }
        return apnsData;
    }
    
    public void disconnect() {
        try {
            connector.close();
        } catch (Exception e) {
            System.out.println("Exception : App.disconnect : " + e.getMessage());
        }
    }

}