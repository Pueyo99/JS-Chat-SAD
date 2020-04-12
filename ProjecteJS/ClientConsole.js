var WebSocketClient = require('websocket').client;

const readline = require('readline');
const rl = readline.createInterface({
    input: process.stdin,
    output: process.stdout
});

var client = new WebSocketClient();

client.on('connectFailed', function(error) {
    console.log('Connect Error: ' + error.toString());
});

client.on('connect', function(connection) {
    console.log('WebSocket Client Connected');
    connection.on('error', function(error) {
        console.log("Connection Error: " + error.toString());
    });
    connection.on('close', function() {
        console.log('echo-protocol Connection Closed');
    });
    connection.on('message', function(message) {
        if (message.type === 'utf8') {
            console.log("\nReceived: '" + message.utf8Data + "'");
            sendMessage();
        }
    });
    
    rl.on("close", function(){
        console.log("\nConexión cerrada");
        connection.close();
    });

    function sendMessage() {
        if (connection.connected) {
            rl.question("Message to send: ", answer => {
            connection.sendUTF(answer);
            });
        }
    }

    sendMessage();
});


function enviar(){
    rl.question("Introduzca su nick: ", answer => {
        var url = "ws://localhost:8000/" + answer;
        console.log(url);
        client.connect(url, 'echo-protocol');
    });
}
enviar();

