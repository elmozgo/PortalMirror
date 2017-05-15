'use strict';

function getJwtToken(portletName) {
    var jwtToken;
    
    jQuery.ajax({
        url: wsTokenSettings.tokenLocation + '?portletName=' + portletName,
        async: false,
        success: function (data) {
            jwtToken = data;
        }
    });
    
    return jwtToken;
}