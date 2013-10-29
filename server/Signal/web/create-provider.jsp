<% 
    if(session.getAttribute("id") == null){
        response.sendRedirect("sign-in.jsp");
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
        <title>Crate Provider | Signal</title>
        <link rel="stylesheet" type="text/css" href="http://yui.yahooapis.com/pure/0.2.1/pure-min.css">
        <link rel="stylesheet" type="text/css" href="public/css/style.css">
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
            <div class="pure-g-r">
                <div class="pure-u-1">
                    <a href="dash.jsp">Back to Dashboard</a>
                    <form action="build" method="post" class="pure-form center">
                        <fieldset>
                            <legend>Create Signal Provider</legend>
                            <p>
                                <label for="title">Provider Name</label>
                                <input type="text" name="name" maxlength="64"/>
                            </p>
                            <p>
                                <label for="description">Description&nbsp;&nbsp;&nbsp;&nbsp;</label>
                                <textarea name="description"></textarea>
                            </p>
                            <p>
                                <label for="platform">Select Platforms</label><br/><br/>
                                <input type="checkbox" name="ios" value="use"/> Apple IOS<br/><br/>
                                <input type="checkbox" name="android" value="use"/> Google Android<br/><br/>
                                <input type="checkbox" name="cli" value="use"/> Desktop Application / CLI<br/><br/>
                                <input type="checkbox" name="html5" value="use"/> HTML5 WebSocket
                            </p>
                            <p>
                                <input type="submit" value="Create" class="pure-button pure-button-d"/>
                            </p>
                        </fieldset>
                    </form>
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
    </body>
</html>