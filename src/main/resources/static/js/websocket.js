let newStatus = function(status) {
    if (status === "connected") {
        $.unblockUI();
        sock.send('ping');
        // sock.conn.
    }
    if(status === "disconnected") {
        $.blockUI({"message": "server disconnected. wait!"});
        alert("The server is disconnected");
    }
};

let onMessage = function(msg) {
    setTimeout(function() {
        if (sock.conn) {
            sock.send("ping");
        }
    }, 10000);
};

let sock = new SockReconnect("/checked", null, newStatus, onMessage);
if (window.addEventListener) {
    window.addEventListener("load", sock.connect, false);
} else {
    window.attachEvent("onload", sock.connect);
}
//
// let ws;
// function connect() {
//     // var target = document.getElementById('target').value;
//     ws = new SockJS("/checked");
//     ws.onopen = function () {
//         console.log('Info: WebSocket connection opened.');
//     };
//     ws.onmessage = function (event) {
//         console.log('Received: ' + event.data);
//     };
//     ws.onclose = function () {
//         console.log('Info: WebSocket connection closed.');
//     };
//     ws.onheartbeat = function(){
//         console.log('heartbeat');
//     }
// }
// connect();