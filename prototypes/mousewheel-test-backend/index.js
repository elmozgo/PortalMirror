var express = require('express');
var app = express();
var http = require('http').Server(app);
exports.http = http;

var APP_PORT = 8080;
var path = require('path');

http.listen(APP_PORT, function () {
    console.log('app listening on port ' + APP_PORT);
});

app.use('/static', express.static(path.join(__dirname, 'static')));


app.get('/', function (req, res) {
    res.send('<p>Hello World!</p>');
});
