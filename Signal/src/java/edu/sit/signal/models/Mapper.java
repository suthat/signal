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
public class Mapper extends edu.sit.signal.app.Config {

    private String tableName = dbname + ".mapper";
    private String tableMapperFeedback = dbname + ".mapperfeedback";
    private String tableMapperStatement = dbname + ".mapperstatement";
    Connection connector;
    PreparedStatement statement;
    ResultSet result;

    public void connect() {
        try {
            Class.forName(dbdriver);
            connector = DriverManager.getConnection(dbhost, dbusername, dbpassword);
        } catch (Exception e) {
            System.out.println("Exception : Mapper.connect : " + e.getMessage());
        }
    }

    public boolean isExisted(int appId) {
        boolean isExisted = false;
        String sql = "SELECT id FROM " + tableName + " WHERE app = ? LIMIT 1;";
        try {
            statement = connector.prepareStatement(sql);
            statement.setInt(1, appId);
            result = statement.executeQuery();
            if (result.next()) {
                isExisted = true;
            }
            result.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("Exception : Mapper.isExistedApp : " + e.getMessage());
        }
        return isExisted;
    }

    public boolean clearCurrentData(int appId) {
        boolean okay = false;
        String sql = "DELETE FROM " + tableName + " WHERE app = ?;";
        try {
            statement = connector.prepareStatement(sql);
            statement.setInt(1, appId);
            int result = statement.executeUpdate();
            if (result == 1) {
                sql = "DELETE FROM " + tableMapperFeedback + " WHERE app = ?;";
                statement = connector.prepareStatement(sql);
                statement.setInt(1, appId);
                result = statement.executeUpdate();
                if (result == 1) {
                    sql = "DELETE FROM " + tableMapperStatement + " WHERE app = ?;";
                    statement = connector.prepareStatement(sql);
                    statement.setInt(1, appId);
                    result = statement.executeUpdate();
                    if (result == 1) {
                        okay = true;
                    }
                }
            }
            statement.close();
        } catch (Exception e) {
            System.out.println("Exception : Mapper.clearCurrentData : " + e.getMessage());
        }
        return okay;
    }

    public boolean add(HashMap data) {
        String sql = "INSERT INTO " + tableName + " (app,accesstoken,reqapproach,requrl,reqtimeinterval,maps,status) "
                + "VALUES(?,?,?,?,?,?,?);";
        try {
            statement = connector.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(data.get("app").toString()));
            statement.setString(2, data.get("accesstoken").toString());
            statement.setString(3, data.get("reqapproach").toString());
            statement.setString(4, data.get("requrl").toString());
            statement.setString(5, data.get("reqtimeinterval").toString());
            statement.setString(6, data.get("maps").toString());
            statement.setInt(7, 1);
            int result = statement.executeUpdate();
            statement.close();
            if (result == 1) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("Exception : Mapper.add : " + e.getMessage());
        }
        return false;
    }

    public boolean addFeedback(HashMap data) {
        String sql = "INSERT INTO " + tableMapperFeedback + " (app,cond,checker,pointer,stmreference,doesiton) "
                + "VALUES(?,?,?,?,?,?);";
        try {
            statement = connector.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(data.get("app").toString()));
            statement.setString(2, data.get("cond").toString());
            statement.setString(3, data.get("checker").toString());
            statement.setString(4, data.get("pointer").toString());
            statement.setString(5, data.get("stmreference").toString());
            statement.setString(6, data.get("doesiton").toString());
            int result = statement.executeUpdate();
            statement.close();
            if (result == 1) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("Exception : Mapper.addFeedback : " + e.getMessage());
        }
        return false;
    }

    public boolean addStatement(HashMap data) {
        String sql = "INSERT INTO " + tableMapperStatement + " (app,reference,requrl,reqdata,reqtimeout,reqheader) "
                + "VALUES(?,?,?,?,?,?);";
        try {
            statement = connector.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(data.get("app").toString()));
            statement.setString(2, data.get("reference").toString());
            statement.setString(3, data.get("requrl").toString());
            statement.setString(4, data.get("reqdata").toString());
            statement.setInt(5, Integer.parseInt(data.get("reqtimeout").toString()));
            statement.setString(6, data.get("reqheader").toString());
            int result = statement.executeUpdate();
            statement.close();
            if (result == 1) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("Exception : Mapper.addStatement : " + e.getMessage());
        }
        return false;
    }
    
    public HashMap getMapper(String accessToken, String appId) {
        HashMap mapper = null;
        String sql; 
        try {
            if(accessToken == null){
                sql = "SELECT * FROM " + tableName + " WHERE app = ? LIMIT 1;";
                statement = connector.prepareStatement(sql);
                statement.setString(1, appId);
            }else{
                sql = "SELECT * FROM " + tableName + " WHERE accesstoken = ? LIMIT 1;";
                statement = connector.prepareStatement(sql);
                statement.setString(1, accessToken);
            }
            result = statement.executeQuery();
            if (result.next()) {
                mapper = new HashMap();
                mapper.put("accesstoken", result.getString("accesstoken"));
                mapper.put("reqapproach", result.getString("reqapproach"));
                mapper.put("requrl", result.getString("requrl"));
                mapper.put("reqtimeinterval", result.getString("reqtimeinterval"));
                mapper.put("maps", result.getString("maps"));
                mapper.put("createdon", result.getString("createdon"));
            }
            result.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("Exception : Mapper.getMapper : " + e.getMessage());
        }
        return mapper;
    }
    
    public HashMap getMapperFeedback(String appId) {
        HashMap mapper = null;
        String sql = "SELECT * FROM " + tableMapperFeedback + " WHERE app = ? LIMIT 1;";
        try {
            statement = connector.prepareStatement(sql);
            statement.setString(1, appId);
            result = statement.executeQuery();
            if (result.next()) {
                mapper = new HashMap();
                mapper.put("cond", result.getString("cond"));
                mapper.put("checker", result.getString("checker"));
                mapper.put("pointer", result.getString("pointer"));
                mapper.put("stmreference", result.getString("stmreference"));
                mapper.put("doesiton", result.getString("doesiton"));
            }
            result.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("Exception : Mapper.getMapperFeedback : " + e.getMessage());
        }
        return mapper;
    }
    
    public ArrayList<HashMap> getMapperStatement(String appId) {
        ArrayList<HashMap> mappers = new ArrayList<HashMap>();
        HashMap mapper = null;
        String sql = "SELECT * FROM " + tableMapperStatement + " WHERE app = ?;";
        try {
            statement = connector.prepareStatement(sql);
            statement.setString(1, appId);
            result = statement.executeQuery();
            while (result.next()) {
                mapper = new HashMap();
                mapper.put("reference", result.getString("reference"));
                mapper.put("requrl", result.getString("requrl"));
                mapper.put("reqdata", result.getString("reqdata"));
                mapper.put("reqtimeout", result.getString("reqtimeout"));
                mapper.put("reqheader", result.getString("reqheader"));
                mappers.add(mapper);
            }
            result.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("Exception : Mapper.getMapperStatement : " + e.getMessage());
        }
        return mappers;
    }

    public void disconnect() {
        try {
            connector.close();
        } catch (Exception e) {
            System.out.println("Exception : Mapper.disconnect : " + e.getMessage());
        }
    }
}