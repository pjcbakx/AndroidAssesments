var app = require('express')();
var http = require('http').Server(app);
var io = require('socket.io')(http)

var port = process.env.PORT || 3003

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
	
	setInterval(function(){
        socket.volatile.emit('heartbeat', {'date': new Date()});
    }, 30000);
	
	if socket.get('MGeText', function(text)) == "portrait" {
	//	socket.broadcast.emit('bgcolor', { 0,255,0,255} );
	} else {
	//	socket.broadcast.emit('bgcolor', { 255,0,0,255} );
	}
	
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
    socket.get('MGeText', function(text){
    socket.broadcast.emit('sendtext', {
            'text':text
		});
    });

});
