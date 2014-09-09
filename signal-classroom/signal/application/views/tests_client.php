<!DOCTYPE html>
<html>
<head>
  <script src="http://localhost/jquery-1.11.1.min.js"></script>
  <script src="http://cdn.sockjs.org/sockjs-0.3.min.js"></script>
  <script src="http://localhost/stomp.js"></script>
  <script src="http://localhost/signal_apis.js"></script>
</head>
<body lang="en">
	<h2 id="hello">Hello</h2>
	<div id="q"></div>
	<script>
		var SIGNAL_HOST = 'http://localhost:15674/stomp';
        var SIGNAL_EXCHANGE_KEY = 'csc102-B787TQA';
        var TOKEN;

        Stomp.WebSocketClass = SockJS;
        var client = Stomp.client(SIGNAL_HOST);
        var on_connect = function(s) {
        	// To-Do (Status Checking)
            $('#hello').html('READY');
            var arr = [];

            id = client.subscribe('/exchange/' + SIGNAL_EXCHANGE_KEY, function(data) {
            	data = JSON.parse(data.body);

            	// To-Do (Message Receiving)
           		if($.inArray('' + data.SN_MESSAGE_TOKEN, arr) === -1){
                    TOKEN = data.SN_MESSAGE_TOKEN;
           			arr.push('' + data.SN_MESSAGE_TOKEN);
	            	$('#q').append('<h3>' + data.question_id + ' ' + data.question_title +'</h3>');
	            	$('#q').append('<p>' + data.question_body +'</p>');
                    var keys = Object.keys(data.answer_list);
                    console.log(keys);
                    for(i=0; i<keys.length; i++){
                        $('#q').append('<input type="radio" name="answer" value="'+keys[i]+'" onclick="commit(\'' +keys[i]+ '\');"/> ' + keys[i] + '. ' + data.answer_list[keys[i]]);
                    }
                     $('#q').append('<p><input type="button" onclick="submit();" value="Submit"/></p>');
	            	console.log(arr);
	            }
            });
        };
        var on_error =  function() {
            $('#hello').html('LOST CONNECTION');
        };
        client.debug = function(str) {
            console.log(str);
        };
        client.connect('guest', 'hello', on_connect, on_error, '/');

        window.onbeforeunload = function () {
        	return 'If you reload this page, this question will be lost';   
		}

        var answer;
        function commit(data)
        {
            answer = data;
            //alert(answer);   
        }
        function submit()
        {
            send_feedback(TOKEN, 'student_id=54400601&answer=' + answer);
        }
	</script>
</body>
</html>