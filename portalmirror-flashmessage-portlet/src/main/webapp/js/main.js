
jQuery.fn.showFlashText = function(text, period, fadeSpeed) {
    
    var that = $(this);
    that.hide();
    that.html(text);
    
    that.bigText({
        horizontalAlign: "center",
        verticalAlign: "center",
        fontSizeFactor: 1
    });
    
    that.fadeIn(fadeSpeed, function() {
            
            setTimeout(function(){
                that.fadeOut(fadeSpeed, function() {
                    //done
                });
            }, period);
    });
    
};

function FlashMessageSocketConnection(messagePlaceholder, portletName, pageId) {
    SocketConnection.call(this, 'flash-message', portletName, pageId);
    this.messagePlaceholder = messagePlaceholder;
}

FlashMessageSocketConnection.prototype = Object.create(SocketConnection.prototype, {
    init: {
        value: function() {
                SocketConnection.prototype.init.call(this, function() {
                    var that = this;
                    
                    this.socket.on('flashMessageEvent', function(message, callback) {
                        callback('flashMessageEvent : [text=' +message.text + '] [period='+ message.period +'] [portletId=' + that.portletName + '] [plid=' + that.pageId +']');
                        $(that.messagePlaceholder).showFlashText(message.text, message.period, 1000);
                    });
                });
        },
        enumerable: true,
        configurable: true, 
        writable: true
    }
});

FlashMessageSocketConnection.prototype.constructor = FlashMessageSocketConnection;
