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

// Socket io listeners
io.on('connection', function(socket) {
	
	// Als een device verbinding maakt.
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
			
	// Orientation
	socket.on('potrait', function(text) {
		console.log('Potrait')
		if(numLandscape > 0)
		{
			--numLandscape;
		}
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
   
   // Als een device de verbinding verbreekt.
   socket.on('disconnect', function() {
		--numDevices; 
		if(numDevices == 0)
		{
			numLandscape = 0;
		}
		console.log('Conntected devices: %d', numDevices)
   });
	
});