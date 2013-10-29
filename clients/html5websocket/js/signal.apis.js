var signalAPIsHost = "http://localhost:8084/Signal/";

function sendSignal(action, token, fbref, fbdata){
    var targetUrl = signalAPIsHost + "otp-apis?token=" + token + "&fbref=" + fbref + "&uxt=" + new Date().getTime() + "&callback=?";
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
                sendFeedbackSignal(code, token, fbref, fbdata); // *fbdata => *receiver
            }else if(action == "signal.var"){
                sendFeedbackVar(code, token, fbref, fbdata);
            }
        }
    });
}

function sendFeedbackSignal(code, token, fbref, receiver){
    var targetUrl = signalAPIsHost + "ack-apis?receiver=" + receiver + "&code=" + code + "&token=" + token + "&fbref=" + fbref + "&platform=html5&callback=?";
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

function sendFeedbackVar(code, token, fbref, fbdata){
    var targetUrl = signalAPIsHost + "send-apis?code=" + code + "&token=" + token + "&fbref=" + fbref + "&data=" + fbdata + "&callback=?";
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
    var targetUrl = signalAPIsHost + "signage-apis?uuid=" + uuid + "&secret=" + secret + "&uxt=" + new Date().getTime() + "&callback=?";
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
    var targetUrl = signalAPIsHost + "nql-apis?signature=" + signature + "&nql=" + nql;
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