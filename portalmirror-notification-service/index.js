/*jslint node:true */
'use strict';
var express = require('express');
var jwtAuth = require('socketio-jwt-auth');
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
    this.jwt = socket.request.user;
};

var autheniticationHandler = function () {
    return jwtAuth.authenticate({
        secret: 'iM44jqyyDI7gyGPNCJdGyoFTcureF7p4gaEWAODa91PLQHXBWB0tsQwDvg8dJQL',
        algorithm: 'HS256'
    }, function (payload, done) {
        if (payload.exp >= new Date()) {
            return done(null, payload);
        } else {
            console.log('expired token: ' + payload);
            return done(new Error('tokenExpiredError'), false, 'Token is expired');
        }
    });
};

var onConnection = function (socket, clients) {
    var client = new ConnectedClient(socket);
    console.log(client.jwt.sub + ' connected to socket.');
    clients[client.socketId] = client;
};

var onDisconnectEvent = function (socket, clients) {
    var disconectedClient = clients[socket.id];
    delete clients[socket.id];
    console.log(disconectedClient.jwt.sub + ' client disconnected.');
};



var refresherClients = {};

var refresher = io.of('/refresher').use(autheniticationHandler);
refresher.on('connection', function (socket) {
    
    onConnection(socket, refresherClients);
    socket.on('disconnect', onDisconnectEvent(socket, refresherClients));
});

app.post('/internal/refresher/:socketId/refresh', function (req, res) {
    
    var client = refresherClients[req.params.socketId];
    if (client !== undefined) {
        
        refresher.sockets[client.socketId].emit('refreshEvent');
        res.status(200);
    } else {
        
        console.log('socket not found: ' + req.params.socketId);
        res.status(404);
    }
});

