var WebSocketClient = require('websocket').client;

const readline = require('readline');
const rl = readline.createInterface({
    input: process.stdin,
    output: process.stdout
});

var client = new WebSocketClient();
var username;

client.on('connectFailed', function(error) {
    console.log('Connect Error: ' + error.toString());
});

client.on('connect', function(connection) {
    connection.sendUTF("-> "+username+" ha entrado en el xat");
    console.log('WebSocket Client Connected');

    connection.on('error', function(error) {
        console.log("Connection Error: " + error.toString());
    });
    connection.on('close', function() {
        console.log('Connection Closed');
    });
    connection.on('message', function(message) {
        if (message.type === 'utf8') {
            process.stdout.clearLine();
            process.stdout.cursorTo(0);
            console.log(message.utf8Data);
            sendMessage();
        }
    });
    
    rl.on("close", function(){
        console.log("\nConexión cerrada");
        connection.sendUTF("-> "+username+" ha salido del xat");
        connection.close();
    });

    function sendMessage() {
        if (connection.connected) {
            rl.question("-> ", answer => {
            connection.sendUTF("-> "+username+": "+answer);
            sendMessage();
            });
        }
    }

    sendMessage();
});


function login(){
    rl.question("Introduzca su nick: ", answer => {
        username = answer;
        var url = "ws://localhost:8000/" + username;
        client.connect(url);
    });
}
login();

