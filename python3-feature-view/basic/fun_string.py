#!python
#!/usr/bin/pytyon3
from string import ascii_lowercase as lc
import string
import math

names = ["Json", "Xml", "Webservice", "CXF"]
sen = ""
se = "-"
line = se.join(names)
print(line)
print(line.split(se))
print(line.find(se, 5))
print("Smile".lower().islower())
print(string.capwords("hello i am john"))
print(("\t   HasEmptyStr $").strip("$\t "))


print("Hi, I am {p.__name__}, and i am {p.pi} years old".format(p=math))

print("{pi!s} {pi!r} {pi!a}".format(pi="π"))

width = 60
# <^> 分别表示左，中，右对齐
titleFormat = "{0:<20}{1:^20}{2:>20}"
format = "{0:<20}{1:^20.2f}{2:>20}"
print("="*width)
print(titleFormat.format("BookName", "Price", "Count"))
print("-"*width)
print(format.format("Java 8", 80.842, 5487))
print(format.format("Python2", 31.372512, 258))
print(format.format("Python3", 30.8232, 58485))
print(format.format("Oracle 11g", 22.7432, 25486))
print("="*width)


# lowercase 字符的集合
print(lc)
