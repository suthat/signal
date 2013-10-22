package edu.sit.signal.nql;

/**
 *
 * @author Suthat
 */
public class NQLEnum {
    
    public enum Lang {
        
        LANG_SELECT("SELECT"),
        LANG_FROM("FROM"),
        LANG_WHERE("WHERE"),
        LANG_DEVICE("DEVICE"),
        LANG_ON("ON"),
        LANG_ORDER("ORDER"),
        LANG_LIMIT("LIMIT");
        
        private String string;
        
        private Lang(String string){
            this.string = string;
        }
        
        public String getSring(){
            return this.string;
        }
    }
    
    public enum Select {
        
        SELECT_ALL("ALL");
        
        private String string;
        
        private Select(String string){
            this.string = string;
        }
        
        public String getSring(){
            return this.string;
        }
    }
 
    public enum From{
        
        FROM_NOTIFICATION("NOTIFICATION"),
        FROM_NOTIFICATION_DATA("NOTIFICATION.DATA"),
        FROM_NOTIFICATION_FEEDBACK("NOTIFICATION.FEEDBACK");
        
        private String string;
        
        private From(String string){
            this.string = string;
        }
        
        public String getSring(){
            return this.string;
        }
    }
    
    public enum Where{
        
        WHERE_APP("APP.UUID"),
        WHERE_NOTIFICATION("NOTIFICATION"),
        WHERE_TOKEN("NOTIFICATION.TOKEN"),
        WHERE_EQUALS("IS");
        
        private String string;
        
        private Where(String string){
            this.string = string;
        }
        
        public String getSring(){
            return this.string;
        }
    }
    
    public enum On{
        
        ON_TODAY("TODAY"),
        ON_YESTERDAY("YESTERDAY"),
        ON_BETWEEN("TO");
        
        private String string;
        
        private On(String string){
            this.string = string;
        }
        
        public String getSring(){
            return this.string;
        }
    }
    
    public enum Device{
        
        DEVICE_ANY("ANY");
        
        private String string;
        
        private Device(String string){
            this.string = string;
        }
        
        public String getSring(){
            return this.string;
        }
    }
    
    public enum Order{
        
        ORDER_BY_DATE("DATE"),
        ORDER_SORT_DESC("DESC"),
        ORDER_SORT_ASC("ASC");
        
        private String string;
        
        private Order(String string){
            this.string = string;
        }
        
        public String getSring(){
            return this.string;
        }
    }
}