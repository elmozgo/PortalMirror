/*jslint node:true */
'use strict';

var argv = require('yarg').argv;
var PropertiesReader = require('properties-reader');


var properties;
if (argv) {
    properties = PropertiesReader('./' + argv.environment + '.properties');
} else {
    properties = PropertiesReader('./dev.properties');
}
var path = require('path');
var express = require('express');
//var jwtAuth = require('socketio-jwt-auth');
var socketioJwt = require('socketio-jwt');
var jwt = require('jsonwebtoken');
var app = express();
var http = require('http').Server(app);
var io = require('socket.io')(http);
app.get('/', function (req, res) {
    res.send('<p>Hello World!</p>');
});

var APP_PORT = 3000;

http.listen(APP_PORT, function () {
    console.log('app listening on port ' + APP_PORT);
});
var ConnectedClient = function (socket) {
    this.socketId = socket.id;
    this.clientId = socket.client.id;
    this.jwt = socket.decoded_token;
    this.pageId = socket.handshake.query.pageId;
};


var onConnection = function (socket, clients) {
    var client = new ConnectedClient(socket);
    console.log(client.jwt.sub + ' connected to socket.');
    clients[client.clientId] = client;
};

var refresherClients = {};

var refresher = io.of('refresher');

refresher.on('connection', socketioJwt.authorize({
    secret: properties.get('ws.secret'),
    timeout: 12000})).on('authenticated', function(socket) {
      
        onConnection(socket, refresherClients);
        socket.on('disconnect', function() {
            var disconectedClient = refresherClients[socket.client.id];
            delete refresherClients[socket.client.id];
            console.log(disconectedClient.jwt.sub + ' client disconnected.');
        });
//         socket.emit('success', {
//             message: 'authenticated',
//             user: socket.request.user
//         });
});

var eventConfirmationLogger = function(confirmation) {
    console.log(confirmation);
}

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

app.get('/testing', function (req, res) {
    
    res.sendFile(path.join(__dirname + '/refresher-portlet-draft.html'));
});
app.get('/portletws.js', function (req, res) {
    
    res.sendFile(path.join(__dirname + '/portletws.js'));
});

