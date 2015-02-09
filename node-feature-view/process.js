process.stdout.write('Process info is:');
console.log(process);

process.stdout.write('You can input data in the terminal!');

process.stdin.resume();
process.stdin.on('data', function(data) {
    process.stdout.write('read from console: ' + data.toString());
});

