function RefresherSocketConnection(portletName, pageId) {
    SocketConnection.call(this, 'refresher', portletName, pageId);
}

RefresherSocketConnection.prototype = Object.create(SocketConnection.prototype, {
    init: {
        value: function() {
                SocketConnection.prototype.init.call(this, function() {
                    var that = this;
                    
                    this.socket.on('refreshEvent', function(message, callback) {
                        callback('refreshed : portletId=' + that.portletName + ' plid=' + that.pageId);
                        location.reload(true);
                    });
                });
        },
        enumerable: true,
        configurable: true, 
        writable: true
    }
});

RefresherSocketConnection.prototype.constructor = RefresherSocketConnection;


function RefresherIcon(refreshActionUrl) {

    this.refreshActionUrl = refreshActionUrl;

}

RefresherIcon.prototype = {

    init : function() {
        var that = this;
        
        window.ready('ul.nav.nav-add-controls[aria-label="Layout Controls"]', function(){
            
            var layoutControls = document
                    .querySelector('ul.nav.nav-add-controls[aria-label="Layout Controls"]');
    
            var refreshButton = layoutControls
                    .querySelector('li.site-add-controls').cloneNode(true);
            var refreshButtonAnchor = refreshButton.querySelector('a');
            var refreshButtonIcon = refreshButtonAnchor.querySelector('i');
            var refreshButtonLabel = refreshButtonAnchor.querySelector('span');
    
            refreshButton.setAttribute('id', 'refreshAllSiteInstancesButton');
            refreshButton.removeAttribute('data-panelurl');
            refreshButton.setAttribute('class', '');
    
            refreshButtonAnchor.removeAttribute('id');
            refreshButtonAnchor.setAttribute('title', 'Refresh');
    
            refreshButtonIcon.setAttribute('class', 'icon-refresh');
    
            refreshButtonLabel.innerHTML = 'Refresh';
            
            refreshButton.addEventListener('click', function() {
                
                AUI().use('aui-io-request', function(A){
                    A.io.request(that.refreshActionUrl, {
                        method: 'post',
                        dataType: 'json',
                        data:{},
                        on: {
                            success: function(){
                                console.log('refresh request sent');
                            }                        
                      }
                    });
                });
            });  
            layoutControls.insertBefore(refreshButton, layoutControls.children[1]);
        });
    }

};