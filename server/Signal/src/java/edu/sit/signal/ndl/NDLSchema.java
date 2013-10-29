package edu.sit.signal.ndl;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author Suthat
 */
public class NDLSchema {

    @SerializedName("signalndl")
    private NDLSchema.Signalndl signalndl;

    /**
     * @return the signalndl
     */
    public NDLSchema.Signalndl getSignalndl() {
        return signalndl;
    }

    /**
     * @param signalndl the signalndl to set
     */
    public void setSignalndl(NDLSchema.Signalndl signalndl) {
        this.signalndl = signalndl;
    }

    public static class Signalndl {

        @SerializedName("provider")
        private NDLSchema.Provider provider;
        @SerializedName("scope")
        private NDLSchema.Scope scope;
        @SerializedName("notification")
        private NDLSchema.Notification notification;
        @SerializedName("consumer")
        private NDLSchema.Consumer consumer;
        @SerializedName("feedback")
        private NDLSchema.Feedback feedback;

        /**
         * @return the provider
         */
        public NDLSchema.Provider getProvider() {
            return provider;
        }

        /**
         * @param provider the provider to set
         */
        public void setProvider(NDLSchema.Provider provider) {
            this.provider = provider;
        }

        /**
         * @return the scope
         */
        public NDLSchema.Scope getScope() {
            return scope;
        }

        /**
         * @param scope the scope to set
         */
        public void setScope(NDLSchema.Scope scope) {
            this.scope = scope;
        }

        /**
         * @return the notification
         */
        public NDLSchema.Notification getNotification() {
            return notification;
        }

        /**
         * @param notification the notification to set
         */
        public void setNotification(NDLSchema.Notification notification) {
            this.notification = notification;
        }

        /**
         * @return the consumer
         */
        public NDLSchema.Consumer getConsumer() {
            return consumer;
        }

        /**
         * @param consumer the consumer to set
         */
        public void setConsumer(NDLSchema.Consumer consumer) {
            this.consumer = consumer;
        }

        /**
         * @return the feedback
         */
        public NDLSchema.Feedback getFeedback() {
            return feedback;
        }

        /**
         * @param feedback the feedback to set
         */
        public void setFeedback(NDLSchema.Feedback feedback) {
            this.feedback = feedback;
        }
    }

    public static class Provider {

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

    public static class Scope {

        @SerializedName("type")
        private String type;
        @SerializedName("domain")
        private ArrayList<String> domain;
        @SerializedName("timezone")
        private String timezone;
        @SerializedName("platform")
        private ArrayList<String> platform;
        @SerializedName("language")
        private ArrayList<String> language;

        /**
         * @return the type
         */
        public String getType() {
            return type;
        }

        /**
         * @param type the type to set
         */
        public void setType(String type) {
            this.type = type;
        }

        /**
         * @return the domain
         */
        public ArrayList<String> getDomain() {
            return domain;
        }

        /**
         * @param domain the domain to set
         */
        public void setDomain(ArrayList<String> domain) {
            this.domain = domain;
        }

        /**
         * @return the timezone
         */
        public String getTimezone() {
            return timezone;
        }

        /**
         * @param timezone the timezone to set
         */
        public void setTimezone(String timezone) {
            this.timezone = timezone;
        }

        /**
         * @return the platform
         */
        public ArrayList<String> getPlatform() {
            return platform;
        }

        /**
         * @param platform the platform to set
         */
        public void setPlatform(ArrayList<String> platform) {
            this.platform = platform;
        }

        /**
         * @return the language
         */
        public ArrayList<String> getLanguage() {
            return language;
        }

        /**
         * @param language the language to set
         */
        public void setLanguage(ArrayList<String> language) {
            this.language = language;
        }
    }

    public static class Notification {

        @SerializedName("notifytime")
        private String notifytime;
        @SerializedName("restrict")
        private String restrict;
        @SerializedName("data")
        private NDLSchema.Data data;
        @SerializedName("interval")
        private NDLSchema.Interval interval;
        @SerializedName("timeline")
        private ArrayList<NDLSchema.Data> timeline;

        /**
         * @return the notifytime
         */
        public String getNotifytime() {
            return notifytime;
        }

        /**
         * @param notifytime the notifytime to set
         */
        public void setNotifytime(String notifytime) {
            this.notifytime = notifytime;
        }

        /**
         * @return the restrict
         */
        public String getRestrict() {
            return restrict;
        }

        /**
         * @param restrict the restrict to set
         */
        public void setRestrict(String restrict) {
            this.restrict = restrict;
        }

        /**
         * @return the data
         */
        public NDLSchema.Data getData() {
            return data;
        }

        /**
         * @param data the data to set
         */
        public void setData(NDLSchema.Data data) {
            this.data = data;
        }

        /**
         * @return the interval
         */
        public NDLSchema.Interval getInterval() {
            return interval;
        }

        /**
         * @param interval the interval to set
         */
        public void setInterval(NDLSchema.Interval interval) {
            this.interval = interval;
        }

        /**
         * @return the timeline
         */
        public ArrayList<NDLSchema.Data> getTimeline() {
            return timeline;
        }

        /**
         * @param timeline the timeline to set
         */
        public void setTimeline(ArrayList<NDLSchema.Data> timeline) {
            this.timeline = timeline;
        }
    }

    public static class Consumer {

        @SerializedName("device")
        private NDLSchema.Device device;
        @SerializedName("keytopic")
        private ArrayList<String> keytopic;

        /**
         * @return the device
         */
        public NDLSchema.Device getDevice() {
            return device;
        }

        /**
         * @param device the device to set
         */
        public void setDevice(NDLSchema.Device device) {
            this.device = device;
        }

        /**
         * @return the keytopic
         */
        public ArrayList<String> getKeytopic() {
            return keytopic;
        }

        /**
         * @param keytopic the keytopic to set
         */
        public void setKeytopic(ArrayList<String> keytopic) {
            this.keytopic = keytopic;
        }
    }

    public static class Feedback {

        @SerializedName("use")
        private String use;
        @SerializedName("domain")
        private String domain;
        @SerializedName("variable")
        private ArrayList<Map<String,String>> variable;

        /**
         * @return the use
         */
        public String getUse() {
            return use;
        }

        /**
         * @param use the use to set
         */
        public void setUse(String use) {
            this.use = use;
        }

        /**
         * @return the domain
         */
        public String getDomain() {
            return domain;
        }

        /**
         * @param domain the domain to set
         */
        public void setDomain(String domain) {
            this.domain = domain;
        }

        /**
         * @return the variable
         */
        public ArrayList<Map<String,String>> getVariable() {
            return variable;
        }

        /**
         * @param variable the variable to set
         */
        public void setVariable(ArrayList<Map<String,String>> variable) {
            this.variable = variable;
        }
    }

    public static class Data {

        @SerializedName("trigger")
        private String trigger;
        @SerializedName("trigger_reference")
        private String trigger_reference;
        @SerializedName("trigger_time")
        private String trigger_time;
        @SerializedName("trigger_repeat")
        private String trigger_repeat;
        @SerializedName("trigger_repeat_condition")
        private ArrayList<String> trigger_repeat_condition;
        
        @SerializedName("topic")
        private String topic;
        @SerializedName("message")
        private String message;
        @SerializedName("link")
        private String link;
        @SerializedName("customplatform")
        private NDLSchema.Customplatform customplatform;

        /**
         * @return the trigger
         */
        public String getTrigger() {
            return trigger;
        }

        /**
         * @param trigger the trigger to set
         */
        public void setTrigger(String trigger) {
            this.trigger = trigger;
        }

        /**
         * @return the trigger_reference
         */
        public String getTrigger_reference() {
            return trigger_reference;
        }

        /**
         * @param trigger_reference the trigger_reference to set
         */
        public void setTrigger_reference(String trigger_reference) {
            this.trigger_reference = trigger_reference;
        }

        /**
         * @return the trigger_time
         */
        public String getTrigger_time() {
            return trigger_time;
        }

        /**
         * @param trigger_time the trigger_time to set
         */
        public void setTrigger_time(String trigger_time) {
            this.trigger_time = trigger_time;
        }

        /**
         * @return the trigger_repeat
         */
        public String getTrigger_repeat() {
            return trigger_repeat;
        }

        /**
         * @param trigger_repeat the trigger_repeat to set
         */
        public void setTrigger_repeat(String trigger_repeat) {
            this.trigger_repeat = trigger_repeat;
        }

        /**
         * @return the trigger_repeat_condition
         */
        public ArrayList<String> getTrigger_repeat_condition() {
            return trigger_repeat_condition;
        }

        /**
         * @param trigger_repeat_condition the trigger_repeat_condition to set
         */
        public void setTrigger_repeat_condition(ArrayList<String> trigger_repeat_condition) {
            this.trigger_repeat_condition = trigger_repeat_condition;
        }

        /**
         * @return the topic
         */
        public String getTopic() {
            return topic;
        }

        /**
         * @param topic the topic to set
         */
        public void setTopic(String topic) {
            this.topic = topic;
        }

        /**
         * @return the message
         */
        public String getMessage() {
            return message;
        }

        /**
         * @param message the message to set
         */
        public void setMessage(String message) {
            this.message = message;
        }

        /**
         * @return the link
         */
        public String getLink() {
            return link;
        }

        /**
         * @param link the link to set
         */
        public void setLink(String link) {
            this.link = link;
        }

        /**
         * @return the customplatform
         */
        public NDLSchema.Customplatform getCustomplatform() {
            return customplatform;
        }

        /**
         * @param customplatform the customplatform to set
         */
        public void setCustomplatform(NDLSchema.Customplatform customplatform) {
            this.customplatform = customplatform;
        }
  
    }

    public static class Interval {

        @SerializedName("starttimestamp")
        private String starttimestamp;
        @SerializedName("expiretimestamp")
        private String expiretimestamp;

        /**
         * @return the starttimestamp
         */
        public String getStarttimestamp() {
            return starttimestamp;
        }

        /**
         * @param starttimestamp the starttimestamp to set
         */
        public void setStarttimestamp(String starttimestamp) {
            this.starttimestamp = starttimestamp;
        }

        /**
         * @return the expiretimestamp
         */
        public String getExpiretimestamp() {
            return expiretimestamp;
        }

        /**
         * @param expiretimestamp the expiretimestamp to set
         */
        public void setExpiretimestamp(String expiretimestamp) {
            this.expiretimestamp = expiretimestamp;
        }
    }

    public static class Customplatform {
        
        @SerializedName("ioscustom")
        private ArrayList<Map<String,String>> ioscustom;
        @SerializedName("androidcustom")
        private ArrayList<Map<String,String>> androidcustom;
        @SerializedName("clicustom")
        private ArrayList<Map<String,String>> clicustom;
        @SerializedName("html5custom")
        private ArrayList<Map<String,String>> html5custom;

        /**
         * @return the ioscustom
         */
        public ArrayList<Map<String,String>> getIoscustom() {
            return ioscustom;
        }

        /**
         * @param ioscustom the ioscustom to set
         */
        public void setIoscustom(ArrayList<Map<String,String>> ioscustom) {
            this.ioscustom = ioscustom;
        }

        /**
         * @return the androidcustom
         */
        public ArrayList<Map<String,String>> getAndroidcustom() {
            return androidcustom;
        }

        /**
         * @param androidcustom the androidcustom to set
         */
        public void setAndroidcustom(ArrayList<Map<String,String>> androidcustom) {
            this.androidcustom = androidcustom;
        }

        /**
         * @return the clicustom
         */
        public ArrayList<Map<String,String>> getClicustom() {
            return clicustom;
        }

        /**
         * @param clicustom the clicustom to set
         */
        public void setClicustom(ArrayList<Map<String,String>> clicustom) {
            this.clicustom = clicustom;
        }

        /**
         * @return the html5custom
         */
        public ArrayList<Map<String,String>> getHtml5custom() {
            return html5custom;
        }

        /**
         * @param html5custom the html5custom to set
         */
        public void setHtml5custom(ArrayList<Map<String,String>> html5custom) {
            this.html5custom = html5custom;
        }
    }

    public static class Device {

        @SerializedName("ios")
        private ArrayList<String> ios;
        @SerializedName("android")
        private ArrayList<String> android;

        /**
         * @return the ios
         */
        public ArrayList<String> getIos() {
            return ios;
        }

        /**
         * @param ios the ios to set
         */
        public void setIos(ArrayList<String> ios) {
            this.ios = ios;
        }

        /**
         * @return the android
         */
        public ArrayList<String> getAndroid() {
            return android;
        }

        /**
         * @param android the android to set
         */
        public void setAndroid(ArrayList<String> android) {
            this.android = android;
        }
    }
}