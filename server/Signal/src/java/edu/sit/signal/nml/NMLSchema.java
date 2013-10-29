package edu.sit.signal.nml;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author Suthat
 */
public class NMLSchema {

    @SerializedName("signalnml")
    private NMLSchema.Signalnml signalnml;

    /**
     * @return the signalnml
     */
    public NMLSchema.Signalnml getSignalnml() {
        return signalnml;
    }

    /**
     * @param signalnml the signalnml to set
     */
    public void setSignalnml(NMLSchema.Signalnml signalnml) {
        this.signalnml = signalnml;
    }
        
    public static class Signalnml {
        
        @SerializedName("auth")
        private NMLSchema.Auth auth;
        @SerializedName("notification")
        private NMLSchema.Notification notification;
        @SerializedName("feedback")
        private NMLSchema.Feedback feedback;

        /**
         * @return the auth
         */
        public NMLSchema.Auth getAuth() {
            return auth;
        }

        /**
         * @param auth the auth to set
         */
        public void setAuth(NMLSchema.Auth auth) {
            this.auth = auth;
        }

        /**
         * @return the notification
         */
        public NMLSchema.Notification getNotification() {
            return notification;
        }

        /**
         * @param notification the notification to set
         */
        public void setNotification(NMLSchema.Notification notification) {
            this.notification = notification;
        }

        /**
         * @return the feedback
         */
        public NMLSchema.Feedback getFeedback() {
            return feedback;
        }

        /**
         * @param feedback the feedback to set
         */
        public void setFeedback(NMLSchema.Feedback feedback) {
            this.feedback = feedback;
        }
        
    }
    
    public static class Auth {
        
        @SerializedName("id")
        private String id;
        @SerializedName("token")
        private String token;

        /**
         * @return the id
         */
        public String getId() {
            return id;
        }

        /**
         * @param id the id to set
         */
        public void setId(String id) {
            this.id = id;
        }

        /**
         * @return the token
         */
        public String getToken() {
            return token;
        }

        /**
         * @param token the token to set
         */
        public void setToken(String token) {
            this.token = token;
        }
        
    }
    
    public static class Notification {
     
        @SerializedName("requestapproach")
        private String requestapproach;
        @SerializedName("requesturl")
        private String requesturl;
        @SerializedName("requesttimeinterval")
        private String requesttimeinterval;
        @SerializedName("mapper")
        private String[] mapper;

        /**
         * @return the requestapproach
         */
        public String getRequestapproach() {
            return requestapproach;
        }

        /**
         * @param requestapproach the requestapproach to set
         */
        public void setRequestapproach(String requestapproach) {
            this.requestapproach = requestapproach;
        }

        /**
         * @return the requesturl
         */
        public String getRequesturl() {
            return requesturl;
        }

        /**
         * @param requesturl the requesturl to set
         */
        public void setRequesturl(String requesturl) {
            this.requesturl = requesturl;
        }

        /**
         * @return the requesttimeinterval
         */
        public String getRequesttimeinterval() {
            return requesttimeinterval;
        }

        /**
         * @param requesttimeinterval the requesttimeinterval to set
         */
        public void setRequesttimeinterval(String requesttimeinterval) {
            this.requesttimeinterval = requesttimeinterval;
        }

        /**
         * @return the mapper
         */
        public String[] getMapper() {
            return mapper;
        }

        /**
         * @param mapper the mapper to set
         */
        public void setMapper(String[] mapper) {
            this.mapper = mapper;
        }
        
    }
    
    public static class Feedback {
        
        @SerializedName("condition")
        private String[] condition;
        @SerializedName("statement")
        private ArrayList<NMLSchema.Statement> statement;

        /**
         * @return the condition
         */
        public String[] getCondition() {
            return condition;
        }

        /**
         * @param condition the condition to set
         */
        public void setCondition(String[] condition) {
            this.condition = condition;
        }

        /**
         * @return the statement
         */
        public ArrayList<Statement> getStatement() {
            return statement;
        }

        /**
         * @param statement the statement to set
         */
        public void setStatement(ArrayList<Statement> statement) {
            this.statement = statement;
        }
        
    }
    
    public static class Statement {
        
        @SerializedName("reference")
        private String reference;
        @SerializedName("requesturl")
        private String requesturl;
        @SerializedName("requesttimeout")
        private String requesttimeout;
        @SerializedName("requestdata")
        private ArrayList<Map<String,String>> requestdata;
        @SerializedName("requestheader")
        private ArrayList<Map<String,String>> requestheader;

        /**
         * @return the reference
         */
        public String getReference() {
            return reference;
        }

        /**
         * @param reference the reference to set
         */
        public void setReference(String reference) {
            this.reference = reference;
        }

        /**
         * @return the requesturl
         */
        public String getRequesturl() {
            return requesturl;
        }

        /**
         * @param requesturl the requesturl to set
         */
        public void setRequesturl(String requesturl) {
            this.requesturl = requesturl;
        }

        /**
         * @return the requesttimeout
         */
        public String getRequesttimeout() {
            return requesttimeout;
        }

        /**
         * @param requesttimeout the requesttimeout to set
         */
        public void setRequesttimeout(String requesttimeout) {
            this.requesttimeout = requesttimeout;
        }

        /**
         * @return the requestdata
         */
        public ArrayList<Map<String,String>> getRequestdata() {
            return requestdata;
        }

        /**
         * @param requestdata the requestdata to set
         */
        public void setRequestdata(ArrayList<Map<String,String>> requestdata) {
            this.requestdata = requestdata;
        }

        /**
         * @return the requestheader
         */
        public ArrayList<Map<String,String>> getRequestheader() {
            return requestheader;
        }

        /**
         * @param requestheader the requestheader to set
         */
        public void setRequestheader(ArrayList<Map<String,String>> requestheader) {
            this.requestheader = requestheader;
        }
        
    }
}
