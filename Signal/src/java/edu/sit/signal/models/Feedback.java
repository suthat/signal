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
public class Feedback extends edu.sit.signal.app.Config {

    private String tableFData = dbname + ".fdata";
    private String tableFVar = dbname + ".fvar";
    Connection connector;
    PreparedStatement statement;
    ResultSet result;

    public void connect() {
        try {
            Class.forName(dbdriver);
            connector = DriverManager.getConnection(dbhost, dbusername, dbpassword);
        } catch (Exception e) {
            System.out.println("Exceptopn : Feedback.connect : " + e.getMessage());
        }
    }

    public boolean add(String token, String fbRef, String platform, String receiver, String referrer) {
        String sql = "INSERT INTO " + tableFData + " (token,fbref,platform,receiver,referrer) "
                + "VALUES(?,?,?,?,?);";
        //System.out.println(sql);
        try {
            statement = connector.prepareStatement(sql);
            statement.setString(1, token);
            statement.setString(2, fbRef);
            statement.setString(3, platform);
            statement.setString(4, receiver);
            statement.setString(5, referrer);
            int result = statement.executeUpdate();
            statement.close();
            if (result == 1) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("Exception : Feedback.add : " + e.getMessage());
        }
        return false;
    }

    public boolean addVar(String table, String token, String fbRef, String fvname, String fvvalue) {
        String sql = "INSERT INTO " + tableFVar + "" + table + " (token,fbRef,fvname,fvvalue) "
                + "VALUES(\"" + token + "\",\"" + fbRef + "\",\"" + fvname + "\"," + fvvalue + ");";
        //System.out.println(sql);
        try {
            statement = connector.prepareStatement(sql);
            int result = statement.executeUpdate();
            statement.close();
            if (result == 1) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("Exception : Feedback.addVar : " + e.getMessage());
        }
        return false;
    }

    public ArrayList<HashMap> getVars(String token, String whereES, String order, String limit) {
        ArrayList<HashMap> vars = new ArrayList<HashMap>();
        HashMap var = null;
        String sql = "SELECT DISTINCT fdata.*, "
                    + "_string.fvname as strname, _string.fvvalue as strval, "
                    + "_integer.fvname as intname, _integer.fvvalue as intval, "
                    + "_float.fvname as fltname, _float.fvvalue as fltval, "
                    + "_boolean.fvname as bolname, _boolean.fvvalue as bolval, "
                    + "_array.fvname as arrname, _array.fvvalue as arrval "
                        + "FROM " + tableFData + " fdata "
                        + "LEFT JOIN " + tableFVar + "string _string ON _string.fbref = fdata.fbref "
                        + "LEFT JOIN " + tableFVar + "integer _integer ON _integer.fbref = fdata.fbref "
                        + "LEFT JOIN " + tableFVar + "float _float ON _float.fbref = fdata.fbref "
                        + "LEFT JOIN " + tableFVar + "boolean _boolean ON _boolean.fbref = fdata.fbref "
                        + "LEFT JOIN " + tableFVar + "array _array ON _array.fbref = fdata.fbref "
                            + "WHERE fdata.token = ? "+whereES+" ORDER BY fdata."+order+" LIMIT "+limit+";";
        try {
            statement = connector.prepareStatement(sql);
            statement.setString(1, token);
            result = statement.executeQuery();
            while (result.next()) {
                var = new HashMap();
                var.put("token", result.getString("token"));
                var.put("fbref", result.getString("fbref"));
                var.put("platform", result.getString("platform"));
                var.put("deliveredon", result.getString("deliveredon"));
                
                if(result.getString("strname") == null){
                    var.put("strname", "");
                    var.put("strval", "");
                }else{
                    var.put("strname", result.getString("strname"));
                    var.put("strval", result.getString("strval"));
                }

                if(result.getString("intname") == null){
                    var.put("intname", "");
                    var.put("intval", "");
                }else{
                    var.put("intname", result.getString("intname"));
                    var.put("intval", result.getString("intval"));
                }

                if(result.getString("fltname") == null){
                    var.put("fltname", "");
                    var.put("fltval", "");
                }else{
                    var.put("fltname", result.getString("fltname"));
                    var.put("fltval", result.getString("fltval"));
                }

                if(result.getString("bolname") == null){
                    var.put("bolname", "");
                    var.put("bolval", "");
                }else{
                    var.put("bolname", result.getString("bolname"));
                    var.put("bolval", result.getString("bolval"));
                }
                
                if(result.getString("arrname") == null){
                    var.put("arrname", "");
                    var.put("arrval", "");
                }else{
                    var.put("arrname", result.getString("arrname"));
                    var.put("arrval", result.getString("arrval"));
                }
                
                vars.add(var);
            }
            result.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("Exception : Feedback.getVars : " + e.getMessage());
        }
        return vars;
    }

    public void disconnect() {
        try {
            connector.close();
        } catch (Exception e) {
            System.out.println("Exception : Feedback.disconnect : " + e.getMessage());
        }
    }
}
