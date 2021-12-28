#!python
#!/usr/bin/python

from sys import stdout


d = list(("a", "b", "c"))
d.append("c")
print(len(d) == 4)

set1 = set(d)
print(len(set1) == 3)

b = frozenset(d)
print(type(b))
print(len(b) == 3)

t = tuple(d)
print(len(t) == 4)

# slice() 函数实现切片对象，主要用在切片操作函数里的参数传递。
myslice = slice(1, 114, 2)
l = range(0, 20, 2)
print(list(l))
print(list(l[myslice]))
