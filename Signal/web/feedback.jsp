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
    edu.sit.signal.models.Feedback feedbackModel = new edu.sit.signal.models.Feedback();
    app.connect();
    notificationModel.connect();
    feedbackModel.connect();
    HashMap appData = app.findByUser(session.getAttribute("id").toString());
    HashMap notification = notificationModel.getNotification(request.getParameter("ref"));
    ArrayList<HashMap> ndataSet = notificationModel.getNdataOfNotification(request.getParameter("ref"), "id DESC", "50");
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="description" content="An open-source notification & feedback service to multiple consumers">
        <meta name="author" content="Suthat Ronglong. Computer Science. School of Information Technology, KMUTT">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Feedback | Signal</title>
        <link rel="stylesheet" type="text/css" href="http://yui.yahooapis.com/pure/0.2.1/pure-min.css">
        <link rel="stylesheet" type="text/css" href="public/css/style.css">
    </head>
    <body>
        <div class="small pure-g-r">
            <div class="container">
                <div class="pure-u-1">
                    <h2>Signal._ Lookup Feedback</h2>
                    <h3>Notification ID: <span style="color:#008ed4;"><%=notification.get("notification").toString()%></span></h3>
                    <b>Use Feedback:</b> 
                    <% if(notification.get("fbactivate").toString().equals("1")){%>
                        YES<br/>
                        <b>Feedback Variable:</b><br/>
                        <%
                            String fbvars[] = notification.get("fbvars").toString().split(",");
                            for(int i=0;i<fbvars.length;i++){
                        %>
                                <span style="margin-left:30px;"><%=fbvars[i].toUpperCase()%></span><br/>
                        <% }%>
                    <% }else{ %>
                        NO<br/>
                    <% }%>
                    <div class="clear"></div>
                    <h3>Notification Query Language (NQL)</h3>
                    <pre>Query Notification Data</pre>
                    <div class="code">
                        <code>SELECT token.topic.state<br/> 
                            FROM NOTIFICATION.DATA <br/> 
                            WHERE NOTIFICATION IS <%=notification.get("notification").toString()%><br/>
                            ORDER DATE DESC<br/>
                            LIMIT 50
                        </code>
                    </div>
                    <div class="clear"></div>  
                    <% for(HashMap ndata : ndataSet){ %>
                        <code>
                            {<br/>
                            "token":"<%=ndata.get("token").toString()%>"<br/>
                            "topic":"<%=ndata.get("topic").toString()%>"<br/>
                            "state":
                            <% if(ndata.get("state").toString().equals("1")){%>
                                "Ready to deliver message"
                            <% }else if(ndata.get("state").toString().equals("2")){ %>
                                "Notified"
                            <% } %><br/>
                            },<br/>
                        </code>
                    <% } %>
                    <div class="clear"></div>
                    <pre>Query Notification Feedback</pre>
                    <% for(HashMap ndata : ndataSet){ %>
                        <div class="code">
                            <code>SELECT ALL<br/> 
                                FROM NOTIFICATION.FEEDBACK<br/> 
                                WHERE TOKEN IS <%=ndata.get("token").toString()%><br/>
                                DEVICE ANY<br/>
                                ORDER DATE DESC<br/>
                                LIMIT 50
                            </code>
                        </div>
                        <div class="clear"></div>
                        <%
                            ArrayList<HashMap> fbs = feedbackModel.getVars(ndata.get("token").toString(), "", "id DESC", "50");
                        %>
                        
                        <% if(fbs.size() == 0) { %>
                            <code>EMPTY</code>
                        <% } %>
                        
                        <% for(HashMap fb : fbs){ %>
                            <p class="feedback">
                                <code>
                                    <b>Reference: <%=fb.get("fbref").toString()%></b><br/>
                                    <b>Reference: <%=fb.get("platform").toString()%></b><br/>
                                    <b>Delivered: <%=helper.timeStampToHuman(fb.get("deliveredon").toString(), true)%></b><br/>
                                    
                                    <% if(! fb.get("strname").toString().equals("")) { %>
                                        <%=fb.get("strname").toString()%> = "<%=fb.get("strval").toString()%>"<br/>
                                    <% } %>
                                    <% if(! fb.get("intname").toString().equals("")) { %>
                                        <%=fb.get("intname").toString()%> = <%=fb.get("intval").toString()%><br/>
                                    <% } %>
                                    <% if(! fb.get("fltname").toString().equals("")) { %>
                                        <%=fb.get("fltname").toString()%> = <%=fb.get("fltval").toString()%><br/>
                                    <% } %>
                                    <% if(! fb.get("bolname").toString().equals("")) { %>
                                        <%=fb.get("bolname").toString()%> = 
                                        <% if(fb.get("bolval").toString().equals("1")) {%>
                                            true
                                        <% } else { %>
                                            false
                                        <% } %>
                                        <br/>
                                    <% } %>
                                    <% if(! fb.get("arrname").toString().equals("")) { %>
                                        <%=fb.get("arrname").toString()%> = <%=fb.get("arrval").toString()%><br/>
                                    <% } %>
                                </code>
                            </p>
                        <% } %>
                        <div class="clear"></div>
                    <% } %>
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
    feedbackModel.disconnect();
%>