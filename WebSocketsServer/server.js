var app = require('express')();
var http = require('http').Server(app);
var io = require('socket.io')(http);


var port = process.env.PORT || 3003
var numDevices = 0;
var numLandscape = 0;

// Start server
http.listen(port, function() {
	console.log('Server op poort %d', port)
});

// Serverpanel
app.get('/', function(req, res) {
	res.sendFile(__dirname + '/website/index.html')
});

// Socket io listeners
io.on('connection', function(socket) {
	
	//Als een device verbinding maakt.
	++numDevices;
	console.log('Conntected devices: %d', numDevices)
	
	socket.emit('begin', {
        	'r':0, 
        	'g':255, 
        	'b':0, 
        	'a':255,
			'or':true
		});
	
	console.log('Sending potrait mode to new device')
	setInterval(function(){
        socket.volatile.emit('heartbeat', {'date': new Date()});
    }, 30000);
			
	//Orientation

	socket.on('potrait', function(text) {
		console.log('Potrait')
		--numLandscape;
		console.log('Aantal landscape: %d',numLandscape)
		if (numLandscape == 0) {
		  io.emit('bgcolor', {
        	'r':0, 
        	'g':255, 
        	'b':0, 
       	    'a':255
         });
		}
	});

	socket.on('landscape', function(text) {
	    console.log('Landscape')
		++numLandscape;
		console.log('Aantal landscape: %d',numLandscape)
          io.emit('bgcolor', {
        	'r':255, 
        	'g':0, 
        	'b':0, 
        	'a':255
		});
	});

	// Past achtergrondkleur aan
	socket.on('MSetBG', function(red, green, blue, alpha){
        socket.broadcast.emit('bgcolor', {
        	'r':red, 
        	'g':green, 
        	'b':blue, 
        	'a':alpha
        });
	});

   // Verstuurd text naar alle apparaten
   socket.on('MGeText', function(text){
     socket.broadcast.emit('sendtext', {
            'text':text
		});
   });
   
   // Als een device de verbinding verbreekt.
   socket.on('disconnect', function() {
		--numDevices; 
		console.log('Conntected devices: %d', numDevices)
   });
	
});