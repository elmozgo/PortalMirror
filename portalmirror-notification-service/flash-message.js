var PortalMirrorSocket = require('./portalmirror-socket.js').PortalMirrorSocket;

var flashMessageClients = {};
var flashMessage = PortalMirrorSocket('flash-message', flashMessageClients);

var eventConfirmationLogger = function(confirmation) {
    console.log(confirmation);
}

var app = require('./index.js').app;

app.get('/internal/flash-message/sockets', function (req, res) {
    
    var connectedSockets = [];
    Object.keys(flashMessageClients).forEach(function (key, index) {
        connectedSockets.push(flashMessageClients[key]);
    });
    
    res.json(connectedSockets);
});

app.post('/internal/flash-message/sockets/:clientId/message', function (req, res) {
    
    if(req.body.text == undefined || req.body.period == undefined) {
        res.sendStatus(400);
        return;
    }

    var client = flashMessageClients[req.params.clientId];
    if (client !== undefined) {
        var message = {};
        message.text = req.body.text;
        message.period = req.body.period;

        flashMessage.sockets[client.socketId].emit('flashMessageEvent', message, eventConfirmationLogger);
   
        res.sendStatus(201);
       
    } else {
        
        console.log('socket not found: ' + req.params.socketId);
        res.sendStatus(404);
    }
});