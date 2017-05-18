var socketioJwt = require('socketio-jwt');
var properties = require('./index.js').properties;
var io = require('socket.io')(require('./index.js').http);

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

var PortalMirrorSocket = function(namespace, clientRepository){

    var socket = io.of(namespace);

    socket.on('connection', socketioJwt.authorize({
        secret: properties.get('ws.secret'),
        timeout: 12000
    })).on('authenticated', function (socket) {

        onConnection(socket, clientRepository);
        socket.on('disconnect', function () {
            var disconectedClient = clientRepository[socket.client.id];
            delete clientRepository[socket.client.id];
            console.log(disconectedClient.jwt.sub + ' client disconnected.');
        });
    });

    return socket;

};

exports.PortalMirrorSocket = PortalMirrorSocket;