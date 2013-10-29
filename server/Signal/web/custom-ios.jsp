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
    HashMap apns = null;
    String certificate = "";
    String p12key = "";
    String password = "";
    if(appData != null){
        apns = app.getAPNs(appData.get("uuid").toString());
        if(apns != null){
            certificate = apns.get("certificate").toString();
            p12key = apns.get("p12key").toString();
            password = apns.get("password").toString();
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
        <title>Custom IOS | Signal</title>
        <link rel="stylesheet" type="text/css" href="http://yui.yahooapis.com/pure/0.2.1/pure-min.css">
        <link rel="stylesheet" type="text/css" href="public/css/style.css">
    </head>
    <body>
        <div class="small pure-g-r">
            <div class="container">
                <div class="pure-u-1">
                    <h2>Signal._ Custom IOS</h2>
                </div>
                <div class="clear"></div>
                <div class="pure-u-1">
                    <form action="upload-apns-cer" method="post" enctype="multipart/form-data" class="pure-form center">
                        <fieldset> 
                            <legend>Authorization of Apple Push Notification service (APNs)</legend>
                            <% if(! certificate.equals("")){ %>
                                <p style="color:green;">
                                    <b>Current certificate:</b> <%=certificate%>
                                </p>
                            <% } %>
                            <label for="title"><b>Certificate file(.cer)</b></label>
                            <p><input type="file" name="userfile" style="border:1px solid #f1f1f1;"/></p>
                            <p>
                                <input type="hidden" name="action" value="certificate"/>
                                <input type="hidden" name="uuid" value="<%=appData.get("uuid").toString()%>"/>
                                <input type="submit" value="Upload and Save IOS-App-Certificate" class="pure-button pure-button-d"/>
                            </p>
                        </fieldset>
                    </form>
                    <form action="upload-apns-p12" method="post" enctype="multipart/form-data" class="pure-form center">
                        <fieldset>
                            <label for="title"><b>Key file(.p12)</b></label>
                            <% if(! p12key.equals("")){ %>
                                <p style="color:green;">
                                    <b>Current p12 key:</b> <%=p12key%>
                                </p>
                            <% } %>
                            <p><input type="file" name="userfile" style="border:1px solid #f1f1f1;"/></p>
                            <p>
                                <input type="hidden" name="action" value="p12key"/>
                                <input type="hidden" name="uuid" value="<%=appData.get("uuid").toString()%>"/>
                                <input type="submit" value="Upload and Save IOS-App Key File" class="pure-button pure-button-d"/>
                            </p>
                        </fieldset>
                    </form>
                    <form action="save-app" method="post" class="pure-form center">
                        <fieldset>
                            <label for="title"><b>Private Key Password</b></label>
                            <p><input type="password" name="password" value="<%=password%>"/></p>
                            <p>
                                <input type="hidden" name="action" value="ios-password"/>
                                <input type="hidden" name="uuid" value="<%=appData.get("uuid").toString()%>"/>
                                <input type="submit" value="Save Private Key Password" class="pure-button pure-button-d"/>
                            </p>
                        </fieldset>
                    </form>
                </div>
                <div class="pure-u-1">
                    <h4>How To Get Certificate from Apple Push Notification service (APNs) ?</h4>
                    <p>Local and push notifications are great for keeping users informed with timely and relevant content, whether your app is running in the background or inactive. Once an App ID is created, you need to configure it for push notifications.</p>
                    <p><img src="public/img/apns-generate-key.png" alt=""/></p>
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