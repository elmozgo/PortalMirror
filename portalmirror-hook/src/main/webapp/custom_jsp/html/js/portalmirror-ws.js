function SocketConnection(namespace, portletName, pageId) {
	this.namespace = namespace;
	this.portletName = portletName;
	this.pageId = pageId;
};

SocketConnection.prototype = {

	socket : null,
	connectTimeout : 1000,
	reconnect : true,
	reconnectionDelay : 300,
	authToken : null,

	configure : function() {
		this.socket = io(wsTokenSettings.nodeWsHost + '/' + this.namespace, {
		    'path' : wsTokenSettings.nodeWsPath, 
			'connect timeout' : this.connectTimeout,
			'reconnect' : this.reconnect,
			'reconnection delay' : this.reconnectionDelay,
			'query' : "pageId=" + this.pageId,
			'transports' : ['websocket'],
			'upgrade': false
		});
	},

	onConnect : function(socket) {
		socket.emit('authenticate', {
			token : this.authToken
		}).on('authenticated', function() {
			console.log('authenticated')
		}).on('unauthorized', function(msg) {
			console.log("unauthorized: " + JSON.stringify(msg.data));
			throw new Error(msg.data.type);
		});
	},

	init : function(afterConnected) {
		var that = this;
		jQuery.ajax({
		    beforeSend: function(request) {
		        request.setRequestHeader("X-CSRF-Token", Liferay.authToken);
		      },
			url : wsTokenSettings.tokenLocation + '?portletName=' + this.portletName ,
			success : function(data) {
				that.authToken = data;
				that.configure();
				that.socket.on('connect', that.onConnect.bind(that, that.socket));
				afterConnected.call(that);
			}
		});
	}
};