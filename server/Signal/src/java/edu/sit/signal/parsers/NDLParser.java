package edu.sit.signal.parsers;

import com.google.gson.Gson;
import edu.sit.signal.apis.DbApis;
import edu.sit.signal.app.Helpers;
import edu.sit.signal.models.Notification;
import edu.sit.signal.ndl.NDLEnum;
import edu.sit.signal.ndl.NDLSchema;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Suthat
 */
public class NDLParser {

    private Notification notificationModel = new Notification();
    private DbApis dbApis = new DbApis();
    private Helpers helpers = new Helpers();
    // Application id
    private String appId = null;
    // NDL file allocated at this path
    private String ndlFileLocated;
    // NDL json string
    private String ndlJSONBody;
    // Unique id
    private String notfId = null;
    // Provider
    private String providerId = null;
    private String providerToken = null;
    // Scope
    private String scopeType = NDLEnum.Scope.TYPE_PUBSUB.getSring();
    private ArrayList<String> scopeDomain = new ArrayList<String>();
    private String scopeTimezone = NDLEnum.Scope.TIMEZONE_IGNORE.getSring();
    private ArrayList<String> scopePlatform = new ArrayList<String>();
    private ArrayList<String> scopeLanguage = new ArrayList<String>();
    // Notification
    private String notifytime = "";
    private String restrict = NDLEnum.Notification.RESTRICT_IGNORE.getSring();
    private String startTimestamp = "";
    private String expireTimestamp = "";
    private String dataTrigger = "";
    private String dataTriggerReference = "";
    private String dataTriggerTime = "";
    private String dataTriggerRepeat = "";
    private ArrayList<String> dataTriggerRepeatCondition = new ArrayList<String>();
    private String dataTopic = "";
    private String dataMessage = "";
    private String dataLink = "";
    private ArrayList<NDLSchema.Data> listData = null;
    private ArrayList<String> dataCustomIos = new ArrayList<String>();
    private ArrayList<String> dataCustomAndroid = new ArrayList<String>();
    private ArrayList<String> dataCustomCli = new ArrayList<String>();
    private ArrayList<String> dataCustomHtml5 = new ArrayList<String>();
    // Consumer
    private ArrayList<String> deviceIos = new ArrayList<String>();
    private ArrayList<String> deviceAndroid = new ArrayList<String>();
    private ArrayList<String> keyTopic = new ArrayList<String>();
    // Feedback
    private String feedbackUse = NDLEnum.Feedback.IGNORE_FEEDBACK.getSring();
    private String feedbackDomain = NDLEnum.Feedback.DOMAIN_IGNORE.getSring();
    private String fbVars = null;
    private ArrayList<Map<String, String>> feedbackVariable = null;

    public NDLParser(String ndlFileLocated, String ndlJSONBody) {
        this.ndlFileLocated = ndlFileLocated; 
        this.ndlJSONBody = ndlJSONBody;
        // Pre-defined timestamp
        notifytime = helpers.getCurrentTimestamp(null);
        startTimestamp = helpers.getCurrentTimestamp(null);
        expireTimestamp = helpers.getCurrentTimestamp(null);
        dataTrigger = helpers.getCurrentTimestamp(null);
    }

    public String parseNDLfromJSONtoDb() {

        NDLSchema ndlSchema;
        boolean complied = true;
        if (ndlFileLocated == null) {
            try {
                ndlSchema = new Gson().fromJson("" + ndlJSONBody, NDLSchema.class);
            } catch (Exception e) {
                complied = false;
                return null;
            }
        } else {
            // JSON NDL's reader
            FileReader reader = null;
            try {
                reader = new FileReader(ndlFileLocated);
                ndlSchema = new Gson().fromJson(reader, NDLSchema.class);
            } catch (FileNotFoundException e) {
                System.out.println("Exception : NDLParser.parseNDLfromJSONtoDb : " + e.getMessage());
                complied = false;
                return null;
            }
        }

        if (complied) {
            // Generate unique id
            notfId = getUniqueId();
            notfId = helpers.generateToken(notfId);

            // Authorize the provider
            providerId = ndlSchema.getSignalndl().getProvider().getId();
            providerToken = ndlSchema.getSignalndl().getProvider().getToken();

            // Provider is authorized
            if (authorizeProvider(providerId, providerToken)) {

                // Get notify time
                notifytime = ndlSchema.getSignalndl().getNotification().getNotifytime();
                // Get Restrict
                restrict = ndlSchema.getSignalndl().getNotification().getRestrict();

                // Make one collect for list of string
                ArrayList<String> collector = ndlSchema.getSignalndl().getScope().getDomain();

                // Scope
                scopeType = ndlSchema.getSignalndl().getScope().getType();
                collector = ndlSchema.getSignalndl().getScope().getDomain();
                if (collector != null) {
                    for (String domain : collector) {
                        scopeDomain.add(domain.trim().toLowerCase());
                    }
                }
                scopeTimezone = ndlSchema.getSignalndl().getScope().getTimezone();
                collector = ndlSchema.getSignalndl().getScope().getPlatform();
                if (collector != null) {
                    for (String domain : collector) {
                        scopePlatform.add(domain.trim().toLowerCase());
                    }
                }
                collector = ndlSchema.getSignalndl().getScope().getLanguage();
                if (collector != null) {
                    for (String domain : collector) {
                        scopeLanguage.add(domain.trim().toLowerCase());
                    }
                }

                // Consumer
                deviceIos = ndlSchema.getSignalndl().getConsumer().getDevice().getIos();
                deviceAndroid = ndlSchema.getSignalndl().getConsumer().getDevice().getAndroid();
                keyTopic = ndlSchema.getSignalndl().getConsumer().getKeytopic();

                // Feedback
                feedbackUse = ndlSchema.getSignalndl().getFeedback().getUse();
                feedbackDomain = ndlSchema.getSignalndl().getFeedback().getDomain();
                feedbackVariable = ndlSchema.getSignalndl().getFeedback().getVariable();

                if (feedbackUse.equalsIgnoreCase(NDLEnum.Feedback.USE_FEEDBACK.getSring())) {
                    feedbackUse = "1";
                }
                fbVars = concatStringFromArrayList(collectEachCustomData(feedbackVariable, 'x'), ",");

                // Force now notification
                if (notifytime.equalsIgnoreCase(NDLEnum.Notification.NOTIFY_TIME_NOW.getSring())) {

                    // Collect notification data
                    dataTopic = ndlSchema.getSignalndl().getNotification().getData().getTopic();
                    dataMessage = ndlSchema.getSignalndl().getNotification().getData().getMessage();
                    dataLink = ndlSchema.getSignalndl().getNotification().getData().getLink();

                    System.out.println(dataTopic + " : " + dataMessage + " : " + dataLink);

                    // Collect custom data
                    dataCustomIos = collectEachCustomData(ndlSchema.getSignalndl().getNotification().getData().getCustomplatform().getIoscustom(), 'j');
                    dataCustomAndroid = collectEachCustomData(ndlSchema.getSignalndl().getNotification().getData().getCustomplatform().getAndroidcustom(), 'j');
                    dataCustomCli = collectEachCustomData(ndlSchema.getSignalndl().getNotification().getData().getCustomplatform().getClicustom(), 'j');
                    dataCustomHtml5 = collectEachCustomData(ndlSchema.getSignalndl().getNotification().getData().getCustomplatform().getHtml5custom(), 'j');
                }

                // Generate schudule notification
                if (notifytime.equalsIgnoreCase(NDLEnum.Notification.NOTIFY_TIME_BASIC_SCHEDULE.getSring()) || notifytime.equalsIgnoreCase(NDLEnum.Notification.NOTIFY_TIME_COMPLEX_SCHEDULE.getSring())) {

                    // Collect notification data
                    startTimestamp = ndlSchema.getSignalndl().getNotification().getInterval().getStarttimestamp();
                    expireTimestamp = ndlSchema.getSignalndl().getNotification().getInterval().getExpiretimestamp();

                    // Collect the timeline, set of notification data
                    listData = ndlSchema.getSignalndl().getNotification().getTimeline();
                    for (NDLSchema.Data data : listData) {

                        dataTrigger = data.getTrigger();
                        dataTriggerReference = data.getTrigger_reference();
                        dataTriggerTime = data.getTrigger_time();
                        dataTriggerRepeat = data.getTrigger_repeat();
                        dataTriggerRepeatCondition = data.getTrigger_repeat_condition();

                        dataTopic = data.getTopic();
                        dataMessage = data.getMessage();
                        dataLink = data.getLink();

                        System.out.println(dataTrigger + " : " + dataTopic + " : " + dataMessage + " : " + dataLink);

                        // Collect custom data
                        // Implement in the loop
                    }
                }

                // !IMPORTANT : Add notification here
                addNotification();
            }
        }

        return notfId;
    }

    public String getUniqueId() {
        String uid = null;
        // Generate the unique notification id
        notificationModel.connect();
        do {
            uid = helpers.randomString();
        } while (notificationModel.isExistedUid(uid));
        notificationModel.disconnect();

        System.out.println("Notification UID : " + uid);
        return uid;
    }

    public boolean authorizeProvider(String providerId, String providerToken) {
        dbApis.connect();
        String result = dbApis.authorizedProvider(providerId, providerToken);
        dbApis.disconnect();
        boolean accepted = false;
        if (result != null) {
            accepted = true;
            appId = result;
        } else {
            System.out.println("Rejected : Unauthorized Provider");
        }
        return accepted;
        // !IMPORTANT ONLY DEV
        //return true; 
    }

    public void addNotification() {
        // !IMPORTANT DEV : Just hard-code the app id
        //appId = "1";

        HashMap notification = new HashMap();
        // Put data 
        notification.put("uid", notfId);
        notification.put("app", appId);
        notification.put("type", scopeType);
        notification.put("domain", concatStringFromArrayList(scopeDomain, ","));
        notification.put("timezone", scopeTimezone);
        notification.put("platform", concatStringFromArrayList(scopePlatform, ","));
        notification.put("language", concatStringFromArrayList(scopeLanguage, ","));
        notification.put("notifytime", notifytime);
        notification.put("restrict", restrict);
        notification.put("starttime", startTimestamp);
        notification.put("expiretime", expireTimestamp);
        notification.put("iosdevice", concatStringFromArrayList(deviceIos, ","));
        notification.put("androiddevice", concatStringFromArrayList(deviceAndroid, ","));
        notification.put("keytopic", concatStringFromArrayList(keyTopic, "."));
        notification.put("fbactivate", feedbackUse);
        notification.put("fbvars", fbVars);
        notification.put("createdby", "ndl");

        notificationModel.connect();

        // Add notification 
        boolean result = notificationModel.addNotification(notification);

        if (result) {
            HashMap ndata = null;
            // Now
            if (notifytime.equalsIgnoreCase(NDLEnum.Notification.NOTIFY_TIME_NOW.getSring())) {
                int slugCounter = 1;
                ndata = new HashMap();
                ndata.put("notification", notfId);
                ndata.put("token", helpers.generateToken(notfId + "$_." + slugCounter));
                ndata.put("trigger", dataTrigger);
                ndata.put("trigger_reference", dataTriggerReference);
                ndata.put("trigger_time", dataTriggerTime);
                ndata.put("trigger_repeat", dataTriggerRepeat);
                ndata.put("topic", dataTopic);
                ndata.put("message", dataMessage);
                ndata.put("link", dataLink);
                ndata.put("customios", concatStringFromArrayList(dataCustomIos, ","));
                ndata.put("customandroid", concatStringFromArrayList(dataCustomAndroid, ","));
                ndata.put("customcli", concatStringFromArrayList(dataCustomCli, ","));
                ndata.put("customhtml5", concatStringFromArrayList(dataCustomHtml5, ","));
                // Add ndata
                result = notificationModel.addNData(ndata);
            }

            // Schedule
            if (notifytime.equalsIgnoreCase(NDLEnum.Notification.NOTIFY_TIME_BASIC_SCHEDULE.getSring()) || notifytime.equalsIgnoreCase(NDLEnum.Notification.NOTIFY_TIME_COMPLEX_SCHEDULE.getSring())) {
                int slugCounter = 1;
                for (NDLSchema.Data data : listData) {
                    ndata = new HashMap();
                    ndata.put("notification", notfId);
                    ndata.put("token", helpers.generateToken(notfId + "$_." + slugCounter));

                    if (data.getTrigger() != null) {
                        ndata.put("trigger", data.getTrigger());
                    } else {
                        ndata.put("trigger", helpers.getCurrentTimestamp(null));
                    }
                    if (data.getTrigger_reference() != null) {
                        ndata.put("trigger_reference", data.getTrigger_reference());
                    } else {
                        ndata.put("trigger_reference", "");
                    }
                    if (data.getTrigger_time() != null) {
                        ndata.put("trigger_time", data.getTrigger_time());
                    } else {
                        ndata.put("trigger_time", "");
                    }
                    if (data.getTrigger_repeat() != null) {
                        ndata.put("trigger_repeat", data.getTrigger_repeat());
                    } else {
                        ndata.put("trigger_repeat", "");
                    }

                    ndata.put("topic", data.getTopic());
                    ndata.put("message", data.getMessage());
                    ndata.put("link", data.getLink());
                    ndata.put("customios", concatStringFromArrayList(collectEachCustomData(data.getCustomplatform().getIoscustom(), 'j'), ","));
                    ndata.put("customandroid", concatStringFromArrayList(collectEachCustomData(data.getCustomplatform().getAndroidcustom(), 'j'), ","));
                    ndata.put("customcli", concatStringFromArrayList(collectEachCustomData(data.getCustomplatform().getClicustom(), 'j'), ","));
                    ndata.put("customhtml5", concatStringFromArrayList(collectEachCustomData(data.getCustomplatform().getHtml5custom(), 'j'), ","));

                    // Add ndata
                    result = notificationModel.addNData(ndata);

                    // Add ncond
                    if (data.getTrigger_repeat_condition() != null) {
                        for (String condition : data.getTrigger_repeat_condition()) {
                            result = notificationModel.addNCond(ndata.get("token").toString(), condition);
                        }
                    }

                    slugCounter++;
                }
            }
        }

        notificationModel.disconnect();
    }

    public ArrayList<String> collectEachCustomData(ArrayList<Map<String, String>> mapper, char filter) {
        // Make one collect for map of string and string
        ArrayList<String> tmp = new ArrayList<String>();
        // Map custom data of each platform
        if (mapper != null) {
            for (Map<String, String> map : mapper) {
                for (Map.Entry<String, String> entity : map.entrySet()) {
                    if (filter == 'j') {
                        tmp.add("\"" + entity.getKey() + "\" : \"" + entity.getValue() + "\"");
                    }
                    if (filter == 'x') {
                        tmp.add(entity.getKey() + ":" + entity.getValue());
                    }
                    if (filter == 'k') {
                        tmp.add(entity.getKey());
                    }
                    if (filter == 'v') {
                        tmp.add(entity.getValue());
                    }
                }
            }
        }
        return tmp;
    }

    public String concatStringFromArrayList(ArrayList<String> arr, String partition) {
        String tmp = "";
        if (arr.size() > 0) {
            for (String entity : arr) {
                tmp = tmp.concat(entity + partition);
            }
            tmp = tmp.substring(0, tmp.length() - 1);
        }
        return tmp;
    }
}
