var WebSocketServer = require('websocket').server;
var http = require('http');
const url = require('url');

var users = new Map();

var server = http.createServer(function(request, response) {
    response.writeHead(200);
    response.end();
});

server.listen(8000, function() {
    console.log("Server is listening on port 8000");
});

serversocket = new WebSocketServer({
    httpServer: server,
    autoAcceptConnections: false,
});

serversocket.on('request', function(request) {

    connection = request.accept(null, request.origin);
    username = url.parse(request.resourceURL).pathname.split("/")[1];
    users.set(username,connection);
    console.log("Connection accepted from: "+username);

    connection.on('message', function(message) {
        if (message.type === 'utf8') {
            console.log('Received Message: ' + message.utf8Data);

            for(let con of users.values()){
                if(con!=this){
                    con.sendUTF(message.utf8Data);
                }
            }
        }
    });

    connection.on('close', function(reasonCode, description) {
        users.delete(username);
        console.log(username+" disconnected");

    });
});
