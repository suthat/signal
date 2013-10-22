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
public class User extends edu.sit.signal.app.Config{
    
    private String tableName = dbname + ".users";
    
    Connection connector;
    PreparedStatement statement;
    ResultSet result;

    public void connect() {
        try {
            Class.forName(dbdriver);
            connector = DriverManager.getConnection(dbhost, dbusername, dbpassword);
        } catch (Exception e) {
            System.out.println("Exception : User.connect : " + e.getMessage());
        }
    }
    
    public boolean add(HashMap data){
        String sql = "INSERT INTO " + tableName + " (name,email,password,status) " +
                        "VALUES(?,?,?,?);";
        try {
            statement = connector.prepareStatement(sql);
            statement.setString(1, data.get("name").toString());
            statement.setString(2, data.get("email").toString());
            statement.setString(3, data.get("password").toString());
            statement.setInt(4, 1);
            int result = statement.executeUpdate();
            statement.close();
            if (result == 1) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("Exception : User.add : " + e.getMessage());
        }
        return false;
    }
    
    public HashMap authorize(String email, String password){
        HashMap userData = null;
        String sql = "SELECT * FROM " + tableName + " WHERE email = ? AND password= ?;";
        try {
            statement = connector.prepareStatement(sql);
            statement.setString(1, email);
            statement.setString(2, password);
            result = statement.executeQuery();
            if (result.next()) {
                userData = new HashMap();
                userData.put("id", result.getString("id"));
                userData.put("name", result.getString("name"));
                userData.put("email", result.getString("email"));
                userData.put("status", result.getString("status"));
            }
            result.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("Exception : User.authorize : " + e.getMessage());
        }
        return userData;
    }
    
    public void disconnect() {
        try {
            connector.close();
        } catch (Exception e) {
            System.out.println("Exception : User.disconnect : " + e.getMessage());
        }
    }

}
