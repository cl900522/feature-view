var model01 = require('./model02.js');
var person1 = new model01();
person1.setName('Joy1');

var model02 = require('./model02.js');
var person2 = new model02();
person2.setName('Joy2');

person1.sayHello();
person2.sayHello();