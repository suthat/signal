<% 
    if(session.getAttribute("id") == null){
        response.sendRedirect("signin");
    }
%>

<%@page import="java.util.ArrayList,java.util.HashMap"%>
<%
    edu.sit.signal.app.Helpers helper = new edu.sit.signal.app.Helpers();
    edu.sit.signal.models.App app = new edu.sit.signal.models.App();
    edu.sit.signal.models.Notification notificationModel = new edu.sit.signal.models.Notification();
    edu.sit.signal.models.Mapper mapperModel = new edu.sit.signal.models.Mapper();
    app.connect();
    notificationModel.connect();
    mapperModel.connect();
    
    HashMap appData = app.findByUser(session.getAttribute("id").toString());
    ArrayList<HashMap> notifications;
    if(appData == null){
        notifications = new ArrayList<HashMap>();
    }else{
        notifications = notificationModel.getNotificationByApp(appData.get("id").toString(), "0,50");
    }
    HashMap mapper = mapperModel.getMapper(null, appData.get("id").toString());
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="description" content="An open-source notification & feedback service to multiple consumers">
        <meta name="author" content="Suthat Ronglong. Computer Science. School of Information Technology, KMUTT">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Dashboard | Signal</title>
        <link rel="stylesheet" type="text/css" href="http://yui.yahooapis.com/pure/0.2.1/pure-min.css">
        <link rel="stylesheet" type="text/css" href="public/css/style.css">
        
        <script language="javascript" type="text/javascript">
            function popitup(url) {
                newwindow = window.open(url,'signal-popup','height=550,width=600');
                if (window.focus) {   
                    newwindow.focus()
                }
                return false;
            }
        </script>
        
    </head>
    <body>
        <div class="container">
            <div class="clear"></div>
            <div class="pure-g-r">
                <div class="pure-u-1">
                    <div class="header center">
                        <h1>Signal</h1>
                        <h3>An open-source notification &amp; feedback service to multiple consumers</h3>
                    </div>
                </div>  
            </div>
            <div class="clear"></div>
            <div class="pure-menu pure-menu-open pure-menu-horizontal center">
                <ul>
                    <li><a href="home"><b>Home</b></a></li>
                    <li><a href="bigpicture">Overview</a></li>
                    <li><a href="learn">Learn</a></li>
                    <li><a href="get">Download</a></li>
                    <% if(session.getAttribute("id") == null){ %>
                        <li><a href="signup"><b>Sign Up</b></a></li>
                        <li><a href="signin"><b>Sign In</b></a></li>
                    <% }else{ %>
                        <li class="pure-menu-selected"><a href="dashboard"><b><%=session.getAttribute("name")%></b></a></li>
                        <li><a href="signout"><b>Sign Out</b></a></li>
                    <% } %>
                </ul>
            </div>
            <div class="clear"></div>
            <div class="pure-g-r center">
                <div class="pure-u-1">
                    <div class="pure-form">
                        <fieldset>
                            <legend>Welcome, <%=session.getAttribute("name")%></legend>
                            <% if(appData == null){ %>
                                <p>
                                    Now, You have no your Signal provider&nbsp;&nbsp;&nbsp;
                                    <a href="create_provider" class="pure-button pure-button-d">Create Signal Provider</a>
                                </p>
                            <% }else{ %>
                                    <p>
                                        <h2 style="margin-bottom:5px;">Signal_. <%=appData.get("name").toString()%></h2>
                                        <%=appData.get("description").toString()%>
                                        <p>Created on <%=helper.timeStampToHuman(appData.get("created").toString(), false)%></p>
                                    </p>
                                    <a class="pure-button pure-button-e" onclick="$('#reference').slideToggle();">View Your Signal Authorized</a>
                                    <div id="reference" style="display:none;">
                                        <h5 style="margin-bottom:5px;">Signal_.UUID = <%=appData.get("uuid").toString()%></h5>
                                        <h5 style="margin-bottom:5px;">Signal_.Secret = <%=appData.get("secret").toString()%></h5>
                                    </div>
                                    <div class="clear"></div>
                                    <p>
                                        <% if(appData.get("ios").toString().equals("use")){ %>
                                        <a href="custom_ios" onclick="return popitup('custom_ios');"><img src="public/img/ios.png" alt="ios"/></a>
                                        <% } %>
                                        <% if(appData.get("android").toString().equals("use")){ %>
                                            <a href="custom_android" onclick="return popitup('custom_android');"><img src="public/img/android.png" alt="android"/></a>
                                        <% } %>
                                        <% if(appData.get("cli").toString().equals("use")){ %>
                                            <a href="custom_cli" onclick="return popitup('custom_cli');"><img src="public/img/cli.png" alt="cli"/></a>
                                        <% } %>
                                        <% if(appData.get("html5").toString().equals("use")){ %>
                                            <a href="custom_html5" onclick="return popitup('custom_html5');"><img src="public/img/html5.png" alt="html5"/></a>
                                        <% } %>
                                        <% if(mapper != null){ %>
                                            <a href="custom_io" onclick="return popitup('custom_io');"><img src="public/img/io.png" alt="io"/></a>
                                        <% } %>
                                    </p>
                                    <div class="clear"></div>
                                    <div class="pure-g-r">
                                        <div class="pure-u-2-5" style="text-align:right;">
                                            <img src="public/img/1377015979.png" alt="languages" style="margin-right:10px;width:100px;height:100px;"/>
                                        </div>
                                        <div class="pure-u-3-5" style="text-align:left;">
                                            <b>Notification Description Language (NDL)</b>
                                            <p></p>
                                            <form action="upload-ndl" method="post" enctype="multipart/form-data" class="pure-form legal">
                                                <label for="userfile">Direct NDL Upload (.JSON file)</label>
                                                <input type="file" name="userfile"/>
                                                <input type="submit" value="Upload" class="pure-button pure-button-c"/>
                                            </form>
                                        </div>
                                    </div>
                            <% } %>
                        </fieldset>
                    </div>
                </div>
                <div class="clear"></div>
                <div class="pure-g-r">
                    <div class="pure-u-1">
                        <% if(appData != null){ %>
                        <h3>Notifications of <%=appData.get("name").toString()%></h3>
                        <table style="text-align:center;width:100%;font-size:0.8em;">
                            <thead>
                                <th>Notification ID._</th>
                                <th>Created._</th>
                                <th>(Exchange Name) Topic._</th>
                                <th>Notify Time._</th>
                                <th>Platform._</th>
                                <th>Feedback._</th>
                                <th>Status._</th>
                            </thead>
                            <tbody>
                                <% if(notifications.size() > 0){ %>
                                    <% for(HashMap notf : notifications){ %>
                                        <tr>
                                            <td>
                                                <a href="lookup_notification?ref=<%=notf.get("notification").toString()%>" onclick="return popitup('lookup_notification?ref=<%=notf.get("notification").toString()%>');"><%=notf.get("notification").toString()%></a>
                                            </td>
                                            <td>
                                                <%=helper.timeStampToHuman(notf.get("createdon").toString(), true)%>
                                            </td>
                                            <td>
                                                <%=notf.get("topic").toString()%>
                                            </td>
                                            <td>
                                                <%=notf.get("notifytime").toString()%>
                                            </td>
                                            <td>
                                                <%=notf.get("platform").toString()%>
                                            </td>
                                            <td>
                                                <% if(notf.get("fbactivate").toString().equalsIgnoreCase("1")){%>
                                                    <a href="lookup_feedback?ref=<%=notf.get("notification").toString()%>" onclick="return popitup('lookup_feedback?ref=<%=notf.get("notification").toString()%>');">Activated</a>
                                                <% } else {%>
                                                    Un-activated
                                                <% } %>
                                            </td>
                                            <td>
                                                <% if(notf.get("status").toString().equalsIgnoreCase("1")){%>
                                                    <span style="color:#04B404;">Compiled</span>
                                                <% } else if(notf.get("status").toString().equalsIgnoreCase("0")) {%>
                                                    Failed
                                                <% } %>
                                            </td>
                                        </tr>
                                    <% } %>
                                <% } %>
                            </tbody>
                        </table>
                        <% } %>
                    </div>
                </div>
            </div>
            <div class="clear"></div>
        </div>
        <div class="legal small pure-g-r">
            <div class="container">
                <div class="pure-g-r">
                    <div class="pure-u-1">
                        <p class="legal-license">All code on this site is licensed under the <a href="http://www.apache.org/licenses/">Apache License</a> unless stated otherwise.</p>
                    </div>
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
    mapperModel.disconnect();
%>