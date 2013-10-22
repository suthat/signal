<!DOCTYPE html>
<html>
    <head>
        <title>Subscriber</title>
        <meta charset="utf-8"/>
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js"></script>
        <script src="http://cdn.sockjs.org/sockjs-0.3.min.js"></script>
        <script src="stomp1.0.js"></script>
        <style type="text/css">
            #notification {
                display: none;
                position: fixed;
                cursor: pointer;
                width: 100%;
                background: #EFEFEF;
                text-align: center;
                border-top: 2px solid #FFF;
                z-index:9999;
            }
        </style>
    </head>
    <body>
        <div id="notification">
          <p id="notification_message"></p>
        </div>
        <p id="error"><p>
        <p id="logs"></p>
        <script src="miniNotification.js"></script>
        <script type="text/javascript">

            /*var ws = new SockJS('http://127.0.0.1:15674/stomp');
            var client = Stomp.over(ws);
            var on_connect = function() {
                id = client.subscribe("/exchange/logs", function(data) {
                });
            };
            var on_error =  function(error) {
            };
            client.debug = function(str) {
            };
            client.connect('guest', 'guest', on_connect, on_error, '/');*/

            // ^
            // Heart-Beat 1000,1000, it creates some lost connection problems on PING, PING every 10sec
            // stomp js version 1.1

            // No Heart-Beat => heart-beat:0,0
            // stomp js version 1.0

            function sendSignal(action, token, fbref, fbdata){
                var targetUrl = "http://localhost:8084/Signal/otp-apis?token=" + token + "&fbref=" + fbref + "&uxt=" + new Date().getTime() + "&callback=?";
                $.ajax({
                    url: targetUrl,
                    type: "GET",
                    dataType: "jsonp",
                    contentType: "application/json;charset=utf-8",
                    crossDomain: true
                }).done(function(data) {
                    var status = data.status;
                    var message = data.message;
                    var code = data.code;
                    if(status == 200){
                        if(action == "signal.ack"){
                            sendFeebackSignal(code, token, fbref);
                        }else if(action == "signal.var"){
                            sendFeebackVar(code, token, fbref, fbdata);
                        }
                    }
                });
            }

            function sendFeebackSignal(code, token, fbref){
                var targetUrl = "http://localhost:8084/Signal/ack-apis?code=" + code + "&token=" + token + "&fbref=" + fbref + "&platform=html5&callback=?";
                $.ajax({
                    url: targetUrl,
                    type: "GET",
                    dataType: "jsonp",
                    contentType: "application/json;charset=utf-8",
                    crossDomain: true
                }).done(function(data) {
                    var status = data.status;
                    var message = data.message;
                    if(status == 200){
                        $("#logs").append("<p>ACK APIs Message: " + message + "</p>");
                    }
                });
            }

            function sendFeebackVar(code, token, fbref, fbdata){
                var targetUrl = "http://localhost:8084/Signal/send-apis?code=" + code + "&token=" + token + "&fbref=" + fbref + "&data=" + fbdata + "&callback=?";
                $.ajax({
                    url: targetUrl,
                    type: "GET",
                    dataType: "jsonp",
                    contentType: "application/json;charset=utf-8",
                    crossDomain: true
                }).done(function(data) {
                    var status = data.status;
                    var message = data.message;
                    if(status == 200){
                        $("#logs").append("<p>VAR APIs Message: " + message + "</p>");
                    }
                });
            }

            function getNFSignal(uuid, secret, pointer, cause, condition){
                var targetUrl = "http://localhost:8084/Signal/signage-apis?uuid=" + uuid + "&secret=" + secret + "&uxt=" + new Date().getTime() + "&callback=?";
                $.ajax({
                    url: targetUrl,
                    type: "GET",
                    dataType: "jsonp",
                    contentType: "application/json;charset=utf-8",
                    crossDomain: true
                }).done(function(data) {
                    var status = data.status;
                    var message = data.message;
                    var signature = data.signature;
                    if(status == 200){
                        sendNQLAndGetNotificationData(signature, pointer, cause, condition);
                    }
                });
            }

            function sendNQLAndGetNotificationData(signature, pointer, cause, condition){
                var nql = "SELECT ALL FROM " + pointer + " WHERE " + cause + " IS " + condition + " ON TODAY DEVICE ANY ORDER DATE DESC LIMIT 0,50";

                nql = encodeURIComponent(nql);
                var targetUrl = "http://localhost:8084/Signal/nql-apis?signature=" + signature + "&nql=" + nql;
                $.ajax({
                    url: targetUrl,
                    type: "GET",
                    dataType: "jsonp",
                    contentType: "application/json;charset=utf-8",
                    crossDomain: true
                }).done(function(data) {
                    $("#logs").append("<p>" + JSON.stringify(data) + "</p>");
                });
            }

            Stomp.WebSocketClass = SockJS;
            var client = Stomp.client('http://127.0.0.1:15674/stomp');
            var on_connect = function(x) {
                $('#error').html('READY');
                id = client.subscribe("/exchange/signal.html5.helloworld", function(data) {
                    var str = data.body;
                    var temp = str.split("|");
                    var token = temp[0];
                    var fbref = temp[1];
                    var message = temp[2];
                    var link = temp[3];
                    var custom = temp[4];

                    $('#notification_message').html(message);
                    $('#notification').miniNotification();

                    sendSignal("signal.ack", token, fbref);
                    sendSignal("signal.var", token, fbref, "userid=meanie$.seen=true$.pi=3.141592$.point=78$.scenarios={none,demo}");
                    getNFSignal("596b65af-61b3-4b8d-9e58-a84f62c00c89", "eb63ae7e7415a27a8553ae4f7021f314", "NOTIFICATION.FEEDBACK", "NOTIFICATION.TOKEN", token);
                });
            };
            var on_error =  function() {
                $('#error').html('LOST CONNECTION');
            };
            client.debug = function(str) {
                $("#logs").append("<p>" + str + "</p>");
            };
            client.connect('guest', 'guest', on_connect, on_error, '/');

            //getNFSignal("596b65af-61b3-4b8d-9e58-a84f62c00c89", "eb63ae7e7415a27a8553ae4f7021f314", "NOTIFICATION", "APP.UUID", "596b65af-61b3-4b8d-9e58-a84f62c00c89");
            //getNFSignal("596b65af-61b3-4b8d-9e58-a84f62c00c89", "eb63ae7e7415a27a8553ae4f7021f314", "NOTIFICATION.DATA", "NOTIFICATION", "b78a81a346c5432ba516db66a221daa")
            //getNFSignal("596b65af-61b3-4b8d-9e58-a84f62c00c89", "eb63ae7e7415a27a8553ae4f7021f314", "NOTIFICATION.FEEDBACK", "NOTIFICATION.TOKEN", "e97b4d0d16bce3542987637a9357e2f2");
        </script>
    </body>
</html>