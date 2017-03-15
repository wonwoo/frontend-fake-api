let newStatus = function(status) {
    if (status === "connected") {
        $.unblockUI();
        sock.send('ping');
    }
    if(status === "disconnected") {
        $.blockUI();
        alert("The server is disconnected");
    }
};

let onMessage = function(msg) {
    setTimeout(function() { if (sock.conn) { sock.send("ping"); } },
        10000);
};

let sock = new SockReconnect("/checked", null, newStatus, onMessage);
if (window.addEventListener) {
    window.addEventListener("load", sock.connect, false);
} else {
    window.attachEvent("onload", sock.connect);
}
