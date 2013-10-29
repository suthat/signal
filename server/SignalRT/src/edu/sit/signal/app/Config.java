package edu.sit.signal.app;

/**
 *
 * @author Suthat
 */
public class Config {
    
    public String rabbitMQHost = "localhost";
    
    public String dbdriver = "com.mysql.jdbc.Driver";
    public String dbname = "signaldb";
    public String dbhost = "jdbc:mysql://localhost/" + dbname;
    public String dbusername = "root";
    public String dbpassword = "root";
    
    public String ndlPath = "/Users/Suthat/Desktop/";
    public String nmlPath = "/Users/Suthat/Desktop/";
    public String apnsCerKeyPath = "/Users/Suthat/Desktop/";
}
