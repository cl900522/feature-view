this.name = '';

exports.sayHello = function (){
	console.log('Hello,'+this.name+"!");
}

exports.setName = function (newName){
	this.name = newName;
}