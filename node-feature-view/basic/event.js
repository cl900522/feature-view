var events = require('events');

var event = new events.EventEmitter();
event.on('some_event', function() {
    console.log('some_event occured.');
});
setTimeout(function() {
    event.emit('some_event');
}, 1000);

var emitter = new events.EventEmitter();
emitter.on('someEvent', function(arg1, arg2) {
    console.log('listener1', arg1, arg2);
});
emitter.on('someEvent', function(arg1, arg2) {
    console.log('listener2', arg1, arg2);
});
emitter.emit('someEvent', 'byvoid', 1991);
