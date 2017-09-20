var express = require('express');
var app = express();
var http = require('http').Server(app);
exports.http = http;

var APP_PORT = 8080;
var path = require('path');

http.listen(APP_PORT, function () {
    console.log('app listening on port ' + APP_PORT);
});

app.use(express.static(path.join(__dirname, 'public')));

