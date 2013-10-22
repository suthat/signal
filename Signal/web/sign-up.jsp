<% 
    if(session.getAttribute("id") != null){
        response.sendRedirect("dash.jsp");
    }
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="description" content="Sign Up to Signal. An open-source notification & feedback service to multiple consumers">
        <meta name="author" content="Suthat Ronglong. Computer Science. School of Information Technology, KMUTT">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Sign Up | Signal</title>
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
                    <li class="pure-menu-selected"><a href="signup"><b>Sign Up</b></a></li>
                    <li><a href="signin"><b>Sign In</b></a></li>
                </ul>
            </div>
            <div class="clear"></div>
            <div class="pure-g-r center">
                <div class="pure-u-1">
                    <form action="register" method="post" class="pure-form">
                        <fieldset>
                            <legend>Sign Up Your Signal</legend>
                            <p>
                                <label for="name">Your App Name&nbsp;&nbsp;&nbsp;</label>
                                <input type="text" name="name" maxlength="64"/>
                            </p>
                            <p>
                                <label for="email">E-mail Address&nbsp;&nbsp;&nbsp;&nbsp;</label>
                                <input type="text" name="email" maxlength="64"/>
                            </p>
                            <p>
                                <label for="password">Your Password&nbsp;&nbsp;&nbsp;&nbsp;</label>
                                <input type="password" name="password" maxlength="16"/>
                            </p>
                            <p>
                                <label for="conf_password">Confirm Password</label>
                                <input type="password" name="conf_password" maxlength="16"/>
                            </p>
                            <p>
                                <input type="submit" value="Sign-Up" class="pure-button pure-button-primary"/>
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