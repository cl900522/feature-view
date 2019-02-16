#!python

import os
import re
from string import ascii_lowercase as lc
from random import randrange

# 正则表达式
numRe = "[0-9]*"
m = re.match(numRe, "1212123")

if m is not None:
    print(m.group())

numRe = "[0-9]*"
m = re.search(numRe, "1213,26123,6123,323")

if m is not None:
    print(m.group())

# lowercase
print(lc)

# range
print(randrange(50, 100))

for i in range(10):
    print(i)


for row in open("./fun_dict.py"):
    print(row, end="")


s1 = (1, 2, 3, 4, 6)
s2 = (9, 9, 9, 9, 9)
ss = (x+y for x, y in zip(s1, s2))
print(*ss)
