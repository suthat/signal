<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="description" content="An open-source notification & feedback service to multiple consumers">
        <meta name="author" content="Suthat Ronglong. Computer Science. School of Information Technology, KMUTT">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Signal</title>
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
                    <li class="pure-menu-selected"><a href="home"><b>Home</b></a></li>
                    <li><a href="bigpicture">Overview</a></li>
                    <li><a href="learn">Learn</a></li>
                    <li><a href="get">Download</a></li>
                    <% if(session.getAttribute("id") == null){ %>
                        <li><a href="signup"><b>Sign Up</b></a></li>
                        <li><a href="signin"><b>Sign In</b></a></li>
                    <% }else{ %>
                        <li><a href="dashboard"><b><%=session.getAttribute("name")%></b></a></li>
                        <li><a href="signout"><b>Sign Out</b></a></li>
                    <% } %>
                </ul>
            </div>
            <div class="clear"></div>
            <div class="pure-g-r center">
                <div class="pure-u-1-4">
                    <img src="public/img/1377015976.png" alt="open-source"/>
                    <h5>100% Open-source</h5>
                    <p class="small">There is no commercial license, it 's full open-source project.</p>
                </div>
                <div class="pure-u-1-4">
                    <img src="public/img/1377016160.png" alt="notification"/>
                    <h5>Notify &amp; Feedback</h5>
                    <p class="small">It sends notification and receive more flexible feedback.</p>
                </div> 
                <div class="pure-u-1-4">
                    <img src="public/img/1377016332.png" alt="consumers"/>
                    <h5>Multiple Consumers</h5>
                    <p class="small">It 's support real-time mobile &amp; tablet OS and web-app notification.</p>
                </div>
                <div class="pure-u-1-4">
                    <img src="public/img/1377015979.png" alt="languages"/>
                    <h5>NDL &amp; NQL</h5>
                    <p class="small">It contains notification description and query languages.</p>
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