function send_feedback(token, data){
    $.ajax({
        url: "http://localhost/signal/apis/feedback/" + token + "/" + data,
        type: "GET"
    }).done(function(data) {
        var status = data.status;
        var message = data.message;
        alert(message);
    });
}