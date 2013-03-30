url = require('url'),
http = require('http'),
qs = require('querystring');

http.createServer(function(request,response){
  //if method is post we want to get the data and send it back	
  if(request.method == 'POST'){
    console.log("POST Request!");
    var body='';
    request.on('data',function(chunk){    
      body+=chunk; //adding chunks to body
    });
		
    request.on('end',function(){
      var POST = qs.parse(body);
      console.log(POST)
      response.writeHead(200,{'Content-Type':'text/plain'});
      response.write("OK"); // tell the http client that we recive data
      response.end();			
      console.log("Received");
      //console.log("POST: " + qs.stringify(POST) );		
    });
  }
}).listen(8080);

console.log("listening on 8080 port");
