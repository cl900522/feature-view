process.stdout.write('Process info is:');
for(var p in process){
    console.log("type: " + (typeof process[p]) +"; value:" + p);
}

process.stdout.write('You can input data in the terminal!');

process.stdin.resume();
process.stdin.on('data', function(data) {
    process.stdout.write('read from console: ' + data.toString());
});

