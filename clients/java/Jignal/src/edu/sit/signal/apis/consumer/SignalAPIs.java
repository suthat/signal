package edu.sit.signal.apis.consumer;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Suthat
 */
public class SignalAPIs {
    
    private String signalAPIsHost = "http://localhost:8084/Signal/";
    
    public String requestOTP(String token, String fbref){ 
        String url = signalAPIsHost + "otp-apis?token=" + token + "&fbref=" + fbref + "&uxt=" + (System.currentTimeMillis() / 1000L);
        try {
            InputStream is = new URL(url).openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = "";
            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }
            jsonText = sb.toString();
            try{
                JSONParser parser = new JSONParser();
                Object obj = parser.parse(jsonText);
                JSONObject jsonObject = (JSONObject) obj;
                long status = (Long) jsonObject.get("status");
                if(status == 200L){
                   return jsonObject.get("code").toString();
                }else{
                    System.out.println("requestOTP error on request >> " + status + "-" + jsonObject.get("message"));
                }
            }catch(Exception e){
                System.out.println("requestOTP error on json parsing >> " + e.getMessage());
            }
            is.close();
        } catch (Exception e) {
            System.out.println("requestOTP error on connection openning >> " + e.getMessage());
        }
        return "";
    }
    
    public String sendFeedbackSignal(String code, String token, String fbref, String receiver){ 
        String url = signalAPIsHost + "ack-apis?receiver=" + receiver + "&code=" + code + "&token=" + token + "&fbref=" + fbref + "&platform=cli";
        try {
            InputStream is = new URL(url).openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = "";
            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }
            jsonText = sb.toString();
            try{
                JSONParser parser = new JSONParser();
                Object obj = parser.parse(jsonText);
                JSONObject jsonObject = (JSONObject) obj;
                long status = (Long) jsonObject.get("status");
                if(status == 200L){
                    return jsonObject.get("message").toString();
                }else{
                    System.out.println("sendFeebackSignal error on request >> " + status + "-" + jsonObject.get("message"));
                }
            }catch(Exception e){
                System.out.println("sendFeebackSignal error on json parsing >> " + e.getMessage());
            }
            is.close();
        } catch (Exception e) {
            System.out.println("sendFeebackSignal error on connection openning >> " + e.getMessage());
        }
        return "";
    }
    
    public String sendFeedbackVar(String code, String token, String fbref, String data){ 
        String url = signalAPIsHost+ "send-apis?code=" + code + "&token=" + token + "&fbref=" + fbref + "&data=" + data;
        try {
            InputStream is = new URL(url).openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = "";
            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }
            jsonText = sb.toString();
            try{
                JSONParser parser = new JSONParser();
                Object obj = parser.parse(jsonText);
                JSONObject jsonObject = (JSONObject) obj;
                long status = (Long) jsonObject.get("status");
                if(status == 200L){
                    return jsonObject.get("message").toString();
                }else{
                    System.out.println("sendFeebackVar error on request >> " + status + "-" + jsonObject.get("message"));
                }
            }catch(Exception e){
                System.out.println("sendFeebackVar error on json parsing >> " + e.getMessage());
            }
            is.close();
        } catch (Exception e) {
            System.out.println("sendFeebackVar error on connection openning >> " + e.getMessage());
        }
        return "";
    }
    
    public String requestSignage(String uuid, String secret){ 
        String url = signalAPIsHost + "signage-apis?uuid=" + uuid + "&secret=" + secret + "&uxt=" + (System.currentTimeMillis() / 1000L);
        try {
            InputStream is = new URL(url).openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = "";
            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }
            jsonText = sb.toString();
            try{
                JSONParser parser = new JSONParser();
                Object obj = parser.parse(jsonText);
                JSONObject jsonObject = (JSONObject) obj;
                long status = (Long) jsonObject.get("status");
                if(status == 200L){
                   return jsonObject.get("signature").toString();
                }else{
                    System.out.println("requestSignage error on request >> " + status + "-" + jsonObject.get("message"));
                }
            }catch(Exception e){
                System.out.println("requestSignage error on json parsing >> " + e.getMessage());
            }
            is.close();
        } catch (Exception e) {
            System.out.println("requestSignage error on connection openning >> " + e.getMessage());
        }
        return "";
    }
    
    public JSONObject sendNQLAndGetNotificationData(String signature, String pointer, String cause, String condition){
        JSONObject jsonObject = null;  
        try{
            String nql = "SELECT ALL FROM " + pointer + " WHERE " + cause + " IS " + condition + " ON TODAY DEVICE ANY ORDER DATE DESC LIMIT 0,50";
            nql = URLEncoder.encode(nql, "UTF-8");
               
            String url = signalAPIsHost + "nql-apis?signature=" + signature + "&nql=" + nql;
            
            InputStream is = new URL(url).openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = "";
            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }
            jsonText = sb.toString();
            System.out.println(jsonText);
            try{
                JSONParser parser = new JSONParser();
                Object obj = parser.parse(jsonText);
                jsonObject = (JSONObject) obj;
            }catch(Exception e){
                System.out.println("error on json parsing >> " + e.getMessage());
            }
        }catch(Exception e){
            System.out.println("errror on url openning >> " + e.getMessage());
        }
        return jsonObject;
    }
}
