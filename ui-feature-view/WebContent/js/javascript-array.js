$(function(){
    var array = ["red", "green", "blue", "yellow", "brown"];
    console.log("############### Array convert by join ',' & '||'");
    console.log(array.join(","));
    console.log(array.join("||"));

    console.log("############### Array operate");
    array.push("orange");
    console.log("array.push(\"orange\") is : " + array);
    console.log("array.pop() is : " + array.pop());
    console.log("array.shift() is : " + array.shift());
    array.unshift("black");
    console.log("array.unshift(\"black\") is : " + array);
    console.log("array.concat(...) is : " + array.concat("PINK", ["ZOOK", "GOLD"]));

    console.log("############### Array sorting");
    var numbers = [ 11, 9, 3, 55, 5, 23, 74, 43 ];
    console.log("Before sorting :" + numbers);
    numbers.sort(function(value1, value2){
        return value1 - value2;
    });
    console.log("After sorting :" + numbers);
    numbers.reverse();
    console.log("After reversing :"+numbers);

    array = ["red", "green", "blue", "yellow", "brown"];
    console.log("############### Array slice and splice operate");
    console.log("array is : " + array);
    array.slice(2)
    console.log("array.slice(2) is : " + array);
    console.log("array.slice(2, 3) is : " + array.slice(2, 3));
    console.log("array.splice(0, 1) is : "+ array.splice(0, 1));
    array.splice(0, 0, "black", 'pink')
    console.log("array.splice(0, 0, 'black', 'pink') is : " + array);
    array.splice(0, 1, "GOLD")
    console.log("array.splice(0, 1) is : " + array);
})