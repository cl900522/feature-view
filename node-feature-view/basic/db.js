var mysql= require('mysql');
console.log(mysql);
var Client = mysql.Client;

var client = new Client();
client.host = '192.168.10.101';
client.port = 3306;
client.user = 'dev';
client.password = 'dev';
client.database='askmobile';

var events = require("events");
var proxy = new events.EventEmitter();
var status = "ready";

var select = function (callback){
    proxy.once("selected", callback);
    if(status === "ready") {
        status = "pending";
        client.select("select * from dual", function(results){
            proxy.emit("selected", results);
            status = "ready";
        });
    }
} 