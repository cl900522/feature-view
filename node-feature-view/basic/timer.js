process.nextTick(function() {
    console.log("nextTick executed --1");
});

process.nextTick(function() {
    console.log("nextTick executed --2");
});

setImmediate(function () {
    console.log("setImmediate executed --1");
    process.nextTick(function(){
        console.log("nextTick executed --3");
    });
});

setImmediate(function () {
    console.log("setImmediate executed --2");
});

setTimeout(function() {
    console.log("execute setTimeout function.");
}, 0);

var x = 0;
setInterval(function() {
    console.log("execute setInterval function:--"+x);
    x++;
}, 1000);

console.log("normal executed");