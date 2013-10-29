package edu.sit.signal.nml;

/**
 *
 * @author Suthat
 */
public class NMLEnum {
    
    public enum Notification{
        
        REQUEST_APPROACH_PUSH("PUSH"),
        REQUEST_APPROACH_PULL("PULL"),
        REQUEST_URL_IGNORE("IGNORE"),
        REQUEST_TIME_INTERVAL_IGNORE("IGNORE");
        
        private String string;
        
        private Notification(String string){
            this.string = string;
        }
        
        public String getSring(){
            return this.string;
        }
    }
}
