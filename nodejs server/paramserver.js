var url = require('url');
var qs = require('querystring');
var express = require('express');
var app = express();  //create server

//HTTP
app.post('/track', function(req, res){
	
    //if method is post we want to get the data and send it back	
	  //console.log("POST req!");
	  var body='';

	  req.on('data',function(chunk){    
		body+=chunk; //adding chunks to body
	  });

	  req.on('end',function(){
		 POST = qs.parse(body);
		console.log(POST)
	    res.send("OK"); // tell the http client that we recive data			
	    //console.log("Received");
		//console.log("POST: " + qs.stringify(POST) );		
	  }); 	
});

app.listen(8080);

console.log("listening on 8080 port");

var http = require('http');
server = http.createServer(app);
var io = require('socket.io').listen(server);

app.get('/maps', function (req, res) {
  res.sendfile(__dirname + '/maps.html');
});

io.sockets.on('connection', function (socket) {
	
	socket.on('addme',function(username) {
		socket.username = username;
		socket.emit('chat', '', 'Muchas Gracias');
		socket.broadcast.emit('chat', '', username + ' esta viendo el mapa');
		socket.emit('pos','',POST);
		console.log(username + " esta viendo el mapa");
	});
	
	socket.on('sendchat', function(data) {
			io.sockets.emit('chat', socket.username, data);
	});
	
	socket.on('disconnect', function() {
			io.sockets.emit('chat', '', socket.username + ' se ha ido');
	});
	
});

server.listen(8124);