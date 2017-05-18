var PortalMirrorSocket = require('./portalmirror-socket.js').PortalMirrorSocket;

var refresherClients = {};
var refresher = PortalMirrorSocket('refresher', refresherClients);

var eventConfirmationLogger = function(confirmation) {
    console.log(confirmation);
}

var app = require('./index.js').app;

app.get('/internal/refresher/sockets', function (req, res) {
    
    var connectedSockets = [];
    Object.keys(refresherClients).forEach(function (key, index) {
        connectedSockets.push(refresherClients[key]);
    });
    
    res.json(connectedSockets);
});

app.post('/internal/refresher/sockets/refresh', function (req, res) {
    var pageId = req.query.pageId;
    if(pageId) {
        Object.keys(refresherClients).filter(function(client){
            return refresherClients[client].pageId == pageId;
        }).forEach(function(key) {
            refresher.sockets[refresherClients[key].socketId].emit('refreshEvent', null, eventConfirmationLogger);
        });
        
        res.sendStatus(200);
        
    } else {
        res.sendStatus(400);
    }

});

app.post('/internal/refresher/sockets/:clientId/refresh', function (req, res) {
    
    var client = refresherClients[req.params.clientId];
    if (client !== undefined) {
        
        refresher.sockets[client.socketId].emit('refreshEvent', null, eventConfirmationLogger);
   
        res.sendStatus(200);
       
    } else {
        
        console.log('socket not found: ' + req.params.socketId);
        res.sendStatus(404);
    }
});