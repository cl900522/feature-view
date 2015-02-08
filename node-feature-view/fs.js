var fs = require('fs');
fs.readFile('event.js', function(err, data) {
    if (err) {
        console.error(err);
    } else {
        console.log(data);
    }
});
