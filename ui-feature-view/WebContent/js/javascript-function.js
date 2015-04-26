$(function(){
    function createComparisionFunction(propertyName){
        return function(object1, object2){
            var value1 = object1[propertyName];
            var value2 = object2[propertyName];
            if(value1 < value2){
                return -1;
            } else if(value1 > value2){
                return 1;
            } else {
                return 0;
            }
        }
    }
    function getArrayStr(array){
        var str = "";
        for(var i=0; i<array.length; i++){
            str += JSON.stringify(array[i]);
        }
        return str;
    }
    var datas = [{age : 10, name: "Mingxuan"}, {age : 21, name : "Zenghui"}, {age : 8, name : "Jone"}, {age : 44, name : "WeiPing"}]
    console.log("#######################Original Datas is:");
    console.log(getArrayStr(datas));
    console.log("#######################Datas sort by age is:");
    datas.sort(createComparisionFunction("age"));
    console.log(getArrayStr(datas));
    console.log("#######################Datas sort by name is:");
    datas.sort(createComparisionFunction("name"));
    console.log(getArrayStr(datas));

    function sum(num1, num2){
        return num1+num2;
    }
    function applySum(num1, num2){
        return sum.apply(this, arguments);
    }
    function callSum(num1, num2){
        return sum.call(this, num1, num2);
    }
    console.log("######################## Function.call()");
    console.log("sum.call(this, 11, 22) is : " + sum.call(this, 11, 22));
    console.log("######################## Function.apply()");
    console.log("sum.apply(this, [11, 22]) is : " + sum.apply(this, [11, 22]));
    console.log("applySum(11, 22) is : " + applySum(11, 22));
})