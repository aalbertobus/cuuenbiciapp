var url = require('url');
var qs = require('querystring');

var express = require('express');
var app = express();  //create server

app.post('/track', function(req, res){
  if(req.url == '/track'){
    //if method is post we want to get the data and send it back	
	  //console.log("POST req!");
	  var body='';

	  req.on('data',function(chunk){    
		body+=chunk; //adding chunks to body
	  });

	  req.on('end',function(){
		var POST = qs.parse(body);
		console.log(POST)
	    res.send("OK"); // tell the http client that we recive data			
	    //console.log("Received");
		//console.log("POST: " + qs.stringify(POST) );		
	  });	
  }
});

app.listen(8080);

console.log("listening on 8080 port");