<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>WebSocketTst</title>
    <script
            src="https://code.jquery.com/jquery-3.2.1.min.js"
            integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4="
            crossorigin="anonymous"></script>

</head>
<body>

<div>
    <div id="conversationDiv">
        <label>What is your name?</label><input type="text" id="name" />
        <button id="sendName" onclick="sendName();">Send</button>
        <p id="response"></p>
    </div>
</div>

<style>
    .echo>span{
        border:1px solid #000;
    }
</style>
<div class="echo"></div>
<script type="text/javascript">


        var websocket = new WebSocket("ws://localhost:8080/client");
        websocket.onopen = function (evt) {
            console.log("CONNECTED");
        };
        websocket.onmessage = function (evt) {
            console.log(evt.data);
            $(".echo").append("<span>"+evt.data+"</span>");
        };

        function webSocketSend(text) {
            if(typeof websocket != 'undefined') {
                websocket.send(text);
            } else {
                alert("Not connected.");
            }
        }

        function sendName() {
            var name = document.getElementById('name').value;
            webSocketSend('name : ' + name );
        }

</script>
</body>
</html>