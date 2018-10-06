#!/usr/bin/python3
aMap = {}
aMap["Good"] = 12
print(aMap)
try:
    aMap["Find"] # Get an exception
except KeyError as e:
    print(e)

v = aMap.get("Find", 23) # No Exception
print(v)

v = aMap.get("Good", 22)
print(v)

print(aMap.keys())
print(aMap.items())

aMap = dict.fromkeys(["name", "age", "birthDate"])
aMap.setdefault("birthDate", "1990-01-01")
aMap.setdefault("empty","No value")
print(aMap)

items = [('name', 'Gumby'), ('age', 42)]
d = dict(items);
print(d)

d = dict(name='Gumby', age=42)
print(d)

a = {"a":"a","b":"b","c":"c"}
b = {"a":"a1","d":"d1"}
a.update(b)
print(a)
