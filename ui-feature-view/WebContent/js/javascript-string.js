$(function(){
    console.log("############################## substr function Test!!");
    var oriString = "Hello The World";
    console.log("oriString is setted to : " + oriString);
    console.log("oriString.slice(-3) is : " + oriString.slice(-3));
    console.log("oriString.slice(3) is : " + oriString.slice(3));
    console.log("oriString.slice(3, 4) is : " + oriString.slice(3, 4));
    console.log("oriString.substring(-3) is : " + oriString.substring(-3));
    console.log("oriString.substring(3, -4) is : " + oriString.substring(3, -4));
    console.log("oriString.substr(-3) is : " + oriString.substr(-3));
    console.log("oriString.substr(3, -4) is : " + oriString.substr(3, -4));
    console.log("oriString.concat('!') is : " + oriString.concat('!'));

    console.log("############################## case function Test!!");
    var caseString = "Upper Lower";
    console.log("caseString is setted to :" + caseString);
    console.log("caseString.toUpperCase() is :" + caseString.toUpperCase());
    console.log("caseString.toLocaleUpperCase() is :" + caseString.toLocaleUpperCase());
    console.log("caseString.toLowerCase() is :" + caseString.toLowerCase());
    console.log("caseString.toLocaleUpperCase() is :" + caseString.toLocaleUpperCase());

    console.log("############################## charcode function Test!!");
    var charCodeString = "CharCode";
    console.log("charCodeString.charCodeAt(0) is : " + charCodeString.charCodeAt(0));
    console.log("charCodeString.charCodeAt(1) is : " + charCodeString.charCodeAt(1));
    console.log("String.fromCharCode(104, 101, 108) is : " + String.fromCharCode(104, 101, 108, 108, 111));

    console.log("############################## compare function Test!!");
    var s1 = "Nice to Meet you!";
    var s2 = "Nice to Meet You!";
    console.log("s1 is : " + s1);
    console.log("s2 is : " + s2);
    console.log("s1.localeCompare(s2) is : " + s1.localeCompare(s2));

    console.log("############################## indexof function Test!!");
    var str = "Hello world, I miss you!";
    console.log(str +" indexof('o') is:" + str.indexOf("o"));
    console.log(str +" lastIndexOf('o') is:" + str.lastIndexOf("o"));
})