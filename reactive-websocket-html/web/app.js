var ws = null;
var url = "ws://localhost:8088/echo";
 
function setConnected(connected)
{
    document.getElementById('connect').disabled = connected;
    document.getElementById('disconnect').disabled = !connected;
    document.getElementById('echo').disabled = !connected;
}
 
function connect()
{
    ws = new WebSocket(url);
    ws.onopen = function() {
        setConnected(true);
        log('Info: Conexion establecida.');
    };
     
    ws.onmessage = function(event) {
        log(event.data);
    };
     
    ws.onclose = function(event) {
        setConnected(false);
        log('Info: Conexion cerrada.');
    };
}
 
function disconnect()
{
    if (ws != null) {
        ws.close();
        ws = null;
    }
    setConnected(false);
}
 
function echo()
{
    if (ws != null)
    {
        var message = document.getElementById('message').value;
        log('Enviando al servidor :: ' + message);
        ws.send(message);
    } else {
        alert('Conexion no detectada... conectate');
    }
}
 
function log(message)
{
    var console = document.getElementById('logging');
    var p = document.createElement('p');
    p.appendChild(document.createTextNode(message));
    console.appendChild(p);
}