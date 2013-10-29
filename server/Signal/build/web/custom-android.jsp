<% 
    if(session.getAttribute("id") == null){
        response.sendRedirect("signin");
    }
%>

<%@page import="java.util.HashMap"%>
<%
    edu.sit.signal.models.App app = new edu.sit.signal.models.App();
    app.connect();
    HashMap appData = app.findByUser(session.getAttribute("id").toString()); 
    HashMap gcm = null;
    String googleAPIsKey = "";
    String payload = "";
    if(appData != null){
        gcm = app.getCGM(appData.get("uuid").toString());
        if(gcm != null){
            googleAPIsKey = gcm.get("googleapiskey").toString();
        }
    }
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="description" content="An open-source notification & feedback service to multiple consumers">
        <meta name="author" content="Suthat Ronglong. Computer Science. School of Information Technology, KMUTT">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Custom Android | Signal</title>
        <link rel="stylesheet" href="http://yui.yahooapis.com/pure/0.3.0/pure-min.css">
        <link rel="stylesheet" type="text/css" href="public/css/style.css">
    </head>
    <body>
        <div class="small pure-g-r">
            <div class="container">
                <div class="pure-u-1">
                    <h2>Signal._ Custom Android</h2>
                </div>
                <div class="clear"></div>
                <div class="pure-u-1">
                    <form action="save-app" method="post" class="pure-form center">
                        <fieldset>
                            <% if(gcm == null){ %>
                                <p class="code">
                                    You have to enter an GCM APIs for sending notification to Android devices via Google messaging service.
                                </p>
                            <% } %>
                            <legend>Google APIs (Simple API Access)</legend>
                            <label for="title">Key for browser apps (with referers)</label>
                            <p><input type="text" name="googleapiskey" maxlength="64" class="pure-input-2-3" value="<%=googleAPIsKey%>"/></p>
                            <p>
                                <input type="hidden" name="action" value="android"/>
                                <input type="hidden" name="uuid" value="<%=appData.get("uuid").toString()%>"/>
                                <input type="submit" value="Save and Update an Android Signal" class="pure-button pure-button-d"/>
                            </p>
                        </fieldset>
                    </form>
                </div>
                <div class="pure-u-1">
                    <h4>How To Get APIs Key for Google Cloud Messaging (GCM) ?</h4>
                    <p>A condensed version of the steps found at <a href="http://developer.android.com/guide/google/gcm/gs.html">GCM Getting Started</a> will go here. The key point is to turn on GCM messaging for your project and to capture the project id and the generated API keys.</p>
                    <p><img src="public/img/gcm-create-api.png" alt=""/></p>
                    <p><img src="public/img/gcm_turn_on_messaging.png" alt=""/></p>
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
%>