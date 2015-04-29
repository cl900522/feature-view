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

    function Level1(){
        this.function1 = function(){
            console.log("function1 execute : 1");
        }
    }
    function Level2(){
        this.function2 = function(){
            console.log("function2 execute : 2");
        }
    }
    Level2.prototype = new Level1();

    function Level3(){
        this.function3 = function(){
            console.log("function3 execute : 3");
        }
    }
    Level3.prototype = new Level2();

    var object = new Level3();
    object.function1();
    object.function2();
    object.function3();
    console.log("is object instanceof Level1? " + (object instanceof Level1));
    console.log("is object instanceof Level2? " + (object instanceof Level2));
    console.log("is object instanceof Level3? " + (object instanceof Level3));

    console.log("#######Borrow Constructor function");
    function SuperType(){
        this.colors =  ["red", "green" ,"blue"];
    }
    function SubType(){
        /*********************/
        SuperType.call(this);
    }

    var instance1 = new SubType();
    var instance2 = new SubType();
    instance1.colors.push("black");
    instance2.colors.push("yellow");
    console.log("instance1 colors are:" + instance1.colors);
    console.log("instance2 colors are:" + instance2.colors);
    console.log("#########there colors are not sharing########");


})