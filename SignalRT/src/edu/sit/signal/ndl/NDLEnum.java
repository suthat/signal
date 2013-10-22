package edu.sit.signal.ndl;

/**
 *
 * @author Suthat
 */
public class NDLEnum {
    
    public enum Scope{
        
        TYPE_PUBSUB("PUBSUB"),
        TYPE_TOPIC("TOPIC"),
        TYPE_IGNORE("IGNORE"),
        DOMAIN_OS("OS"),
        DOAMIN_APP("APP"),
        DOMAIN_WEBSOCKET("WEBSOCKET"),
        TIMEZONE_IGNORE("IGNORE"),
        PLATFORM_IOS("IOS"),
        PLATFORM_ANDROID("ANDROID"),
        PLATFORM_CLI("CLI"),
        PLATFORM_HTML5("HTML5"),
        LANGUAGE_ENGLISH("EN"),
        LANGUAGE_THAI("TH"),
        LANGUAGE_KOREA("KR"),
        LANGUAGE_JAPAN("JP"),
        LANGUAGE_CHINA("CN");
        
        private String string;
        
        private Scope(String string){
            this.string = string;
        }
        
        public String getSring(){
            return this.string;
        }
    }
 
    public enum Notification{
        
        NOTIFY_TIME_NOW("NOW"),
        NOTIFY_TIME_SCHEDULE("SCHEDULE"),
        RESTRICT_HIGH("HIGH"),
        RESTRICT_IGNORE("IGNORE");
        
        private String string;
        
        private Notification(String string){
            this.string = string;
        }
        
        public String getSring(){
            return this.string;
        }
    }
    
    public enum Feedback{
        
        USE_FEEDBACK("TRUE"),
        IGNORE_FEEDBACK("IGNORE"),
        DOMAIN_NQL("NQL"),
        DOMAIN_IGNORE("IGNORE");
        
        private String string;
        
        private Feedback(String string){
            this.string = string;
        }
        
        public String getSring(){
            return this.string;
        }
    }
}
