var fs = require('fs');
fs.readFile('timer.js', function(err, data) {
    if (err) {
        console.error(err);
    } else {
        console.log(data);
    }
});

for(var p in fs){
    console.log("type: " + (typeof fs[p]) +"; value:" + p);
}
