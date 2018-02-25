/*jslint node:true */
'use strict';

var argv = require('yargs').argv;
var PropertiesReader = require('properties-reader');
var properties;

if (argv.environment) {
    properties = PropertiesReader('./' + argv.environment + '.properties');
} else {
    properties = PropertiesReader('./dev.properties');
}
exports.properties = properties;


var path = require('path');
var express = require('express');

var jwt = require('jsonwebtoken');
var app = express();
var bodyParser = require('body-parser');
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

var http = require('http').Server(app);
exports.http = http;

var APP_PORT = 3000;

http.listen(APP_PORT, function () {
    console.log('app listening on port ' + APP_PORT);
});

app.get('/', function (req, res) {
    res.send('<p>Hello World!</p>');
});

exports.app = app;

require('./portalmirror-socket.js');
require('./refresher.js');
require('./flash-message.js');