<% 
    if(session.getAttribute("id") == null){
        response.sendRedirect("signin");
    }
%>

<%@page import="java.util.ArrayList,java.util.HashMap,edu.sit.signal.ndl.NDLEnum"%>
<%
    edu.sit.signal.app.Helpers helper = new edu.sit.signal.app.Helpers();
    edu.sit.signal.models.App app = new edu.sit.signal.models.App();
    edu.sit.signal.models.Notification notificationModel = new edu.sit.signal.models.Notification();
    app.connect();
    notificationModel.connect();
    HashMap appData = app.findByUser(session.getAttribute("id").toString());
    HashMap notification = notificationModel.getNotification(request.getParameter("ref"));
    ArrayList<HashMap> ndataSet = notificationModel.getNdataOfNotification(request.getParameter("ref"), "id DESC", "100");
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="description" content="An open-source notification & feedback service to multiple consumers">
        <meta name="author" content="Suthat Ronglong. Computer Science. School of Information Technology, KMUTT">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Notification | Signal</title>
        <link rel="stylesheet" type="text/css" href="http://yui.yahooapis.com/pure/0.2.1/pure-min.css">
        <link rel="stylesheet" type="text/css" href="public/css/style.css">
    </head>
    <body>
        <div class="small pure-g-r">
            <div class="container">
                <div class="pure-u-1">
                    <h2>Signal._ Lookup Notification</h2>
                    <h3>Notification ID: <span style="color:#008ed4;"><%=notification.get("notification").toString()%></span></h3>
                    <b>Notification Type:</b> <%=notification.get("type").toString().toUpperCase()%><br/>
                    <b>Notification Domain:</b> <%=notification.get("domain").toString().toUpperCase()%><br/>
                    <b>Notification Platform:</b> <%=notification.get("platform").toString().toUpperCase()%><br/>
                    <b>Notification Language:</b> <%=notification.get("language").toString().toUpperCase()%><br/>
                    <div class="clear"></div>
                    <b>Notification Time:</b> <%=notification.get("notifytime").toString().toUpperCase()%><br/>
                    <% if(notification.get("notifytime").toString().equalsIgnoreCase(NDLEnum.Notification.NOTIFY_TIME_BASIC_SCHEDULE.getSring()) || notification.get("notifytime").toString().equalsIgnoreCase(NDLEnum.Notification.NOTIFY_TIME_COMPLEX_SCHEDULE.getSring())){%>
                        <b>Notification Start:</b> <%=helper.timeStampToHuman(notification.get("starttime").toString().toUpperCase(), true)%><br/>
                        <b>Notification Expire:</b> <%=helper.timeStampToHuman(notification.get("expiretime").toString().toUpperCase(), true)%><br/>
                    <% } %>
                    <b>Notification Restrict:</b> <%=notification.get("notifyrestrict").toString().toUpperCase()%><br/>
                    
                    <% for(HashMap ndata : ndataSet){ %>
                        <div class="clear"></div>
                        <div class="ndata">
                            <% if(notification.get("notifytime").toString().equalsIgnoreCase(NDLEnum.Notification.NOTIFY_TIME_COMPLEX_SCHEDULE.getSring())) { %>
                                <div class="pure-button-d" style="margin-bottom:5px;padding:3px;">
                                    <% if(! ndata.get("trigger_reference").toString().equals("")){ %>
                                        <b>Trigger Reference:</b> <%=ndata.get("trigger_reference").toString()%><br/>
                                    <% } %>
                                    <% if(! ndata.get("trigger_time").toString().equals("")){ %>
                                        <b>Trigger Time:</b> <%=ndata.get("trigger_time").toString()%><br/>
                                    <% } %>
                                    <% if(! ndata.get("trigger_repeat").toString().equals("")){ %>
                                        <b>Trigger Repeat:</b> <%=ndata.get("trigger_repeat").toString().toUpperCase()%><br/>
                                    <% } %>
                                    
                                    <% ArrayList<String> nconds = notificationModel.getNcond(ndata.get("token").toString()); %>
                                    <% if(nconds.size() > 0){ %>
                                        <b>Repeat Conditions:</b><br/>
                                        <% for(String ncond : nconds){ %>
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=ncond.toLowerCase()%><br/>
                                        <% } %>
                                    <% } %>
                                </div>
                            <% }else{ %>
                                <b>NData Trigger</b> <%=helper.timeStampToHuman(ndata.get("notifytrigger").toString(), true)%><br/>
                            <% } %>
                            <b>NData Topic:</b> <%=ndata.get("topic").toString()%><br/>
                            <b>NData Token:</b> <%=ndata.get("token").toString()%><br/>
                            <b>NData Message:</b> <%=ndata.get("message").toString()%><br/>
                            <b>NData Link:</b> <%=ndata.get("link").toString()%><br/>
                            <b>NData IOS Custom</b>
                            <pre style="margin-left:30px;">{<%=ndata.get("customios").toString()%>}</pre>
                            <b>NData Android Custom</b>
                            <pre style="margin-left:30px;">{<%=ndata.get("customandroid").toString()%>}</pre>
                            <b>NData Cli Custom</b>
                            <pre style="margin-left:30px;">{<%=ndata.get("customcli").toString()%>}</pre>
                            <b>NData HTML5 Custom</b>
                            <pre style="margin-left:30px;">{<%=ndata.get("customhtml5").toString()%>}</pre>
                            <b>NData State:</b> 
                            <% if(ndata.get("state").toString().equals("1")){%>
                                Ready to deliver message
                            <% }else if(ndata.get("state").toString().equals("2")){ %>
                                Notified
                            <% } %>
                        </div>
                    <% } %>
                    
                    <div class="clear"></div>
                    <b>Key Topic:</b><br/>
                    <%
                        String keytopic[] = notification.get("keytopic").toString().split("\\.");
                        for(int i=0;i<keytopic.length;i++){
                    %>
                            <span style="margin-left:30px;"><%=keytopic[i]%></span><br/>
                    <% }%>
                    
                    <div class="clear"></div>
                    <b>IOS Device:</b><br/>
                    <%
                        String iosdevice[] = notification.get("iosdevice").toString().split(",");
                        for(int i=0;i<iosdevice.length;i++){
                    %>
                            <span style="margin-left:30px;"><%=iosdevice[i]%></span><br/>
                    <% }%>
                    
                    <div class="clear"></div>
                    <b>Android Device:</b><br/>
                    <%
                        String androiddevice[] = notification.get("androiddevice").toString().split(",");
                        for(int i=0;i<androiddevice.length;i++){
                    %>
                            <span style="margin-left:30px;"><%=androiddevice[i]%></span><br/>
                    <% }%>
                </div>
            </div>
        </div>
        <div class="clear"></div>
        <div class="legal small pure-g-r">
            <div class="container">
                <div class="pure-g-r">
                    <div class="pure-u-1">
                        <p class="legal-license">&copy; 2013 Signal. School of Information Technology, KMUTT (Thailand)</p>
                    </div>
                </div>
            </div>
        </div>
        <script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
    </body>
</html>

<%
    app.disconnect();
    notificationModel.disconnect();
%>
