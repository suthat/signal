package edu.sit.signal.apis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

/**
 *
 * @author Suthat
 */
public class DbApis extends edu.sit.signal.app.Config {

    private String tableApp = dbname + ".apps";
    private String tableNData = dbname + ".ndata";
    private String tableNotification = dbname + ".notifications";
    private String tableApiOTP = dbname + ".fbotp";
    private String tableApiSignage = dbname + ".nqlsignage";
    
    Connection connector;
    PreparedStatement statement;
    ResultSet result;

    public void connect() {
        try {
            Class.forName(dbdriver);
            connector = DriverManager.getConnection(dbhost, dbusername, dbpassword);
        } catch (Exception e) {
            System.out.println("Exceptopn : DbApis.connect : " + e.getMessage());
        }
    }
    
    
    public boolean createFbOTP(String code, String token, String fbref) {
        String sql = "INSERT INTO " + tableApiOTP + " (code,token,fbref) VALUES(?,?,?);";
        try {
            statement = connector.prepareStatement(sql);
            statement.setString(1, code);
            statement.setString(2, token);
            statement.setString(3, fbref);
            int result = statement.executeUpdate();
            statement.close();
            if (result == 1) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("Exceptopn : DbApis.createFbOTP : " + e.getMessage());
        }
        return false;
    }
    
    public boolean getFbOTP(String code, String token, String fbref) {
        String sql = "SELECT id FROM " + tableApiOTP + " WHERE code = ? AND token = ? AND fbref = ?;";
        try {
            statement = connector.prepareStatement(sql);
            statement.setString(1, code);
            statement.setString(2, token);
            statement.setString(3, fbref);
            result = statement.executeQuery();
            if (result.next()) {
                return true;
            }
            result.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("Exceptopn : DbApis.getFbOTP : " + e.getMessage());
        }
        return false;
    }
    
    public boolean removeFbOTP(String code, String token, String fbref) {
        String sql = "DELETE FROM " + tableApiOTP + " WHERE code = ? AND token = ? AND fbref = ?;";
        try {
            statement = connector.prepareStatement(sql);
            statement.setString(1, code);
            statement.setString(2, token);
            statement.setString(3, fbref);
            int result = statement.executeUpdate();
            statement.close();
            if (result == 1) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("Exceptopn : DbApis.removeFbOTP : " + e.getMessage());
        }
        return false;
    }
    
    public boolean createNQLSignage(String uuid, String signage) {
        String sql = "INSERT INTO " + tableApiSignage + " (uuid,signage) VALUES(?,?);";
        try {
            statement = connector.prepareStatement(sql);
            statement.setString(1, uuid);
            statement.setString(2, signage);
            int result = statement.executeUpdate();
            statement.close();
            if (result == 1) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("Exceptopn : DbApis.createNQLSignage : " + e.getMessage());
        }
        return false;
    }
    
    public String getNQLSignage(String wKey, String wValue) {
        String sql = "SELECT signage FROM " + tableApiSignage + " WHERE "+wKey+" = ?;";
        try {
            statement = connector.prepareStatement(sql);
            statement.setString(1, wValue);
            result = statement.executeQuery();
            if (result.next()) {
                return result.getString("signage");
            }
            result.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("Exceptopn : DbApis.getNQLSignage : " + e.getMessage());
        }
        return null;
    }
    
    public boolean removeNQLSignage(String uuid) {
        String sql = "DELETE FROM " + tableApiSignage + " WHERE uuid = ?;";
        try {
            statement = connector.prepareStatement(sql);
            statement.setString(1, uuid);
            int result = statement.executeUpdate();
            statement.close();
            if (result == 1) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("Exceptopn : DbApis.removeNQLSignage : " + e.getMessage());
        }
        return false;
    }

    public String authorizedProvider(String uuid, String token) {
        String accepted = null;
        String sql = "SELECT id FROM " + tableApp + " WHERE uuid = ? AND secret = ? LIMIT 1;";
        try {
            statement = connector.prepareStatement(sql);
            statement.setString(1, uuid);
            statement.setString(2, token);
            result = statement.executeQuery();
            if (result.next()) {
                accepted = result.getString("id");
            }
            result.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("Exceptopn : DbApis.authorizedProvider : " + e.getMessage());
        }
        return accepted;
    }

    public boolean authorizedAckRequest(String token) {
        boolean accepted = false;
        String sql = "SELECT id FROM " + tableNData + " WHERE token = ? LIMIT 1;";
        try {
            statement = connector.prepareStatement(sql);
            statement.setString(1, token);
            result = statement.executeQuery();
            if (result.next()) {
                accepted = true;
            }
            result.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("Exceptopn : DbApis.authorizedAckRequest : " + e.getMessage());
        }
        return accepted;
    }

    public boolean setNDataState(String token, int stateVal) {
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
    
    public HashMap getNotificationByToken(String token) {
        HashMap notification = new HashMap();
        String sql = "SELECT notification.*, ndata.*  "
                + "FROM " + tableNData + " ndata "
                + "JOIN " + tableNotification + " notification ON notification.uid = ndata.notification "
                + "WHERE ndata.token = ? LIMIT 1;";
        try {
            statement = connector.prepareStatement(sql);
            statement.setString(1, token);
            result = statement.executeQuery();
            if (result.next()) {
                notification.put("notification", result.getString("notification"));
                notification.put("fbactivate", result.getString("fbactivate"));
                notification.put("fbvars", result.getString("fbvars"));
                notification.put("topic", result.getString("topic"));
            }
            result.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("Exceptopn : DbApis.getNotificationByToken : " + e.getMessage());
        }
        return notification;
    }
    
    public String getAppIdByUUID(String uuid){
        String sql = "SELECT id FROM " + tableApp + " WHERE uuid = ? LIMIT 1;";
        try {
            statement = connector.prepareStatement(sql);
            statement.setString(1, uuid);
            result = statement.executeQuery();
            if (result.next()) {
                return result.getString("id");
            }
            result.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("Excepton : DbApis.getAppIdByUUID : " + e.getMessage());
        }
        return null;
    }

    public void disconnect() {
        try {
            connector.close();
        } catch (Exception e) {
            System.out.println("Exceptopn : DbApis.disconnect : " + e.getMessage());
        }
    }
}