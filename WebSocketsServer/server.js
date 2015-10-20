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
    socket.on('MSendText', function(text){
    socket.broadcast.emit('sendtext', {
            'text':text
		});
    });

});
