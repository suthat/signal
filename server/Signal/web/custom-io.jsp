<% 
    if(session.getAttribute("id") == null){
        response.sendRedirect("signin");
    }
%>

<%@page import="java.util.HashMap,java.util.ArrayList;"%>
<%
    edu.sit.signal.app.Helpers helpers = new edu.sit.signal.app.Helpers();
    edu.sit.signal.models.App app = new edu.sit.signal.models.App();
    edu.sit.signal.models.Mapper mapperModel = new edu.sit.signal.models.Mapper();
    app.connect();
    mapperModel.connect();
    HashMap appData = app.findByUser(session.getAttribute("id").toString()); 
    HashMap mapper = null;
    HashMap mapperFeedback = null;
    ArrayList<HashMap> mapperStm = null;
    if(appData != null){
        mapper = mapperModel.getMapper(null, appData.get("id").toString());
        mapperFeedback = mapperModel.getMapperFeedback(appData.get("id").toString());
        mapperStm = mapperModel.getMapperStatement(appData.get("id").toString());
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
        <title>Custom IO | Signal</title>
        <link rel="stylesheet" type="text/css" href="http://yui.yahooapis.com/pure/0.2.1/pure-min.css">
        <link rel="stylesheet" type="text/css" href="public/css/style.css">
    </head>
    <body>
        <div class="small pure-g-r">
            <div class="container">
                <div class="pure-u-1">
                    <h2>Signal._ Custom IO</h2>
                </div>
                <div class="pure-u-1">
                    <h3>Notification Mapping Language (NML)</h3>
                    <% if(mapper != null){ %>
                        <p>
                            <strong>Created On:</strong> <%=helpers.timeStampToHuman(mapper.get("createdon").toString(), true)%><br/>
                            <strong>Access Token:</strong> <%=mapper.get("accesstoken").toString()%><br/>
                            <strong>Request Approach:</strong> <%=mapper.get("reqapproach").toString().toUpperCase()%><br/> 
                            <% if(mapper.get("reqapproach").toString().equalsIgnoreCase("push")){ %>
                                <strong>Request URL:</strong> IGNORE<br/>
                                <strong>Request Interval Time (second):</strong> IGNORE<br/>
                            <% }else{ %>
                                <strong>Request URL</strong> <%=mapper.get("requrl").toString()%><br/>
                                <strong>Request Interval Time (sec)</strong> <%=mapper.get("reqtimeinterval").toString()%><br/>
                            <% } %>
                        </p>
                        <p>
                            <strong>Maps:</strong><br/>
                            <% 
                                String maps[] = mapper.get("maps").toString().split(",");
                                for(int i=0;i<maps.length;i++){
                            %>       
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=maps[i]%><br/>
                            <% } %>
                        </p>
                        <p>
                            <strong>Feedback Maps</strong><br/>
                            <strong>Conditions:</strong><br/>
                            <% 
                                String conds[] = mapperFeedback.get("cond").toString().split(",");
                                for(int i=0;i<conds.length;i++){
                            %>       
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=conds[i]%><br/>
                            <% } %>
                        </p>
                        <p>
                            <strong>Feedback Statements</strong><br/>
                            <% for(HashMap stm : mapperStm){ %>
                            <div style="padding:5px;background:#f1f1f1;margin-bottom:5px;">
                                <strong>Reference:</strong> <%=stm.get("reference").toString()%><br/>
                                <strong>Request URL:</strong> <%=stm.get("requrl").toString()%><br/>
                                <strong>Request Timeout (sec):</strong> <%=stm.get("reqtimeout").toString()%><br/>
                                <strong>Request Header:</strong><br/>
                                <% 
                                    String headers[] = stm.get("reqheader").toString().split(",");
                                    for(int i=0;i<headers.length;i++){
                                %>       
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=headers[i]%><br/>
                                <% } %>
                            </div>
                            <% } %>
                        </p>
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
    mapperModel.disconnect();
%>