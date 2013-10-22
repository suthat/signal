package edu.sit.signal.app;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Suthat
 */
public class Helpers {

    public String timeStampToHuman(String ts, boolean includeTime) {
        String[] tmp = ts.split(" ");
        String time = tmp[1];
        if(time.length() > 5){
            time = time.substring(0, 5);
        }
        tmp = tmp[0].split("-");
        int day = Integer.parseInt(tmp[2]);
        String month;
        if (tmp[1].equals("01")) {
            month = "January";
        } else if (tmp[1].equals("02")) {
            month = "February";
        } else if (tmp[1].equals("03")) {
            month = "March";
        } else if (tmp[1].equals("04")) {
            month = "April";
        } else if (tmp[1].equals("05")) {
            month = "May";
        } else if (tmp[1].equals("06")) {
            month = "June";
        } else if (tmp[1].equals("07")) {
            month = "July";
        } else if (tmp[1].equals("08")) {
            month = "August";
        } else if (tmp[1].equals("09")) {
            month = "September";
        } else if (tmp[1].equals("10")) {
            month = "October";
        } else if (tmp[1].equals("11")) {
            month = "November";
        } else {
            month = "December";
        }
        String year = tmp[0];
        if (includeTime) {
            return day + " " + month + " " + year + " on " + time;
        } else {
            return day + " " + month + " " + year;
        }
    }
    
    public String getCurrentTimestamp(String key){
        Date now = new Date( );
        String tmp[] = new Timestamp(now.getTime()).toString().split(" ");
        String date = tmp[0];
        String time = tmp[1];
        tmp = time.split("\\.");
        time = tmp[0];
        time = time.substring(0, time.length()-3);
        
        if(key == null)
            return date+" "+time;
        if(key.equalsIgnoreCase("date"))
            return date;
        if(key.equalsIgnoreCase("time"))
            return time;
        if(key.equalsIgnoreCase("dow")){
            SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE"); 
            return simpleDateformat.format(now).toLowerCase();
        }
        return "";
    }

    public String randomString() {
        SecureRandom random = new SecureRandom();
        return new BigInteger(130, random).toString(32);
    }
    
    public String generateToken(String slug) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(slug.getBytes(), 0, slug.length());
            String token = new BigInteger(1, md5.digest()).toString(16);
            return token;
        }catch(Exception e){
            System.out.println("Exception : Helpers.generateToken : " + e.getMessage());
        }
        return "";
    }
}
