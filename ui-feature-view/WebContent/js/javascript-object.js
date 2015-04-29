$(function(){
    console.log("##################### Object test!");
    function Person(name){
        this.name = name;
    };
    Person.prototype.sayName = function(){
        console.log("prototype's sayName is: " + this.name);
    }
    Person.prototype.age = 12;

    var engineer = new Person("Chenmx");
    engineer.sayName();
    console.log("Person's constructor is:")
    console.log(engineer.constructor);
    engineer.sayName = function(){
        console.log("object's sayName is: " + this.name);
    }
    console.log("Is engineer's prototype Person ? " + Person.prototype.isPrototypeOf(engineer));
    engineer.sayName();
    console.log("Do engineer has property name ? " + engineer.hasOwnProperty("name"));
    console.log("Do engineer has property age ? " + engineer.hasOwnProperty("age")); //false
    console.log("Do name in engineer ? " + ("name" in engineer));
    console.log("Do age in engineer ? " + ("age" in engineer));
    delete engineer.name;
    console.log("##DELTE NAME");
    console.log("Do engineer has property name ? " + engineer.hasOwnProperty("name"));
    console.log("Do engineer has property age ? " + engineer.hasOwnProperty("age"));
    console.log("Do name in engineer ? " + ("name" in engineer));
    console.log("Do age in engineer ? " + ("age" in engineer));

    var person = new Person();
    for(var prop in person){
        console.log("Person has property ["+prop+"] of value " + person[prop]);
    }

    function Fruit(){};
    Fruit.prototype = {
        name : "apple",
        color : "green",
        eatable :false,
        eat : function(){
            console.log("##### fruit is good");
        }
    };
    try{
        apple.eat();
    }catch(error){
        console.log("!!!apple eat does not exist");
    }
    var apple = new Fruit();
    Fruit.prototype.eat = function(){
        console.log("##### fruit is good");
    };
    apple.eat();

    Fruit.prototype.name = "fruit";
    Fruit.prototype.color = "green"
    Fruit.prototype.metainfo = { area: "china"};
    var orange = new Fruit();
    apple.name = "apple";
    orange.name = "orange";
    apple.metainfo.area = "shanxi";
    orange.metainfo.area = "shenzhen";
    console.log("apple's name is: *" + apple.name);
    console.log("orange's name is: *" + orange.name);
    console.log("apple's metainfo is: *" + JSON.stringify(apple.metainfo));
    console.log("orange's metainfo is: *" + JSON.stringify(orange.metainfo));
})