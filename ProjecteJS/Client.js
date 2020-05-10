var W3CWebSocket = require('websocket').w3cwebsocket;

function login(){
    username = document.getElementById("nick").value;
    if(username.length!=0){
        localStorage.setItem("username",username);
        window.location.replace("xat.html");
    }else{
        alert("No se ha introducido el Nick")
    }
}

function start(){
    var user = localStorage.getItem("username");
    client = new W3CWebSocket('ws://localhost:8000/' + user);
    conversation = new String();

    document.getElementById("send").addEventListener("click",sendMessage);
    document.getElementById("close").addEventListener("click",close);
    document.getElementById("user").innerHTML = user;


    function sendMessage(){
        if (client.readyState === client.OPEN){
            m = document.getElementById("message").value
            if(m.length!=0){
                document.getElementById("message").value = "";
                client.send("-> "+ user + ": " + m);
                conversation = conversation + "\n-> Yo: " + m;
                document.getElementById("received").innerHTML = conversation;
            }else{
                alert("No hay mensajes a enviar");
            }
        }
    }

    function close(){
        client.send("-> "+ user + " ha salido del xat");
        client.close();
    }

    client.onopen = function(){
        client.send("-> "+ user + " ha entrado en el xat");
        conversation = "-> Has entrado en el xat";
        document.getElementById("received").innerHTML = conversation;
    }

    client.onclose = function(){
        window.location.replace("index.html");
    };

    client.onmessage = function(e){
        if(typeof e.data === 'string'){
            conversation = conversation + "\n" + e.data;
            document.getElementById("received").innerHTML = conversation;
        }
    };

    }

    function listenEnter(input,button){
        var input = document.getElementById(input);
        input.addEventListener("keyup", function(event) {
            if (event.keyCode === 13) {
              event.preventDefault();
              document.getElementById(button).click();
            }
        });
    }

    //Variables pel correcte funcionament de Browserify
    global.login = login;
    global.start = start;
    global.listenEnter = listenEnter;