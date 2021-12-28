#!python
#!/usr/bin/python

a, b = divmod(13, 3)
# a = 4
# b = 1
print(a, b)

# 函数接受一个标准输入数据，返回为 string 类型。
y = input("do you agree? [y|n]:")
if y == 'y':
    print("you agreed!")
else:
    print("how dare you disagree!")


class T():
    # 类的静态方法标记
    @staticmethod
    def del2():
        print("del...")


T.del2()


list = ["a", "b", "c", "d"]
for i, v in enumerate(list, 1):
    print("%d:%s" % (i, v))

for i, v in enumerate(list, 0):
    print("%d:%s" % (i, v))


# all()函数用于判断给定的可迭代参数 iterable 中的所有元素是否都为 TRUE，如果是返回 True，否则返回 False。元素除了是 0、空、None、False 外都算 True。
print(all(["a", "b", "c"]))
print(all(["a", "b", ""]))
print(all(["a", 0]))

# any() 函数用于判断给定的可迭代参数 iterable 是否全部为 False，则返回 False，如果有一个为 True，则返回 True。
print(any([0, "", False, None]))
print(any(["a", "b", ""]))
print(any(["a", 0]))


# str() 函数将对象转化为适于人阅读的形式。
dict = {'runoob': 'runoob.com', 'google': 'google.com'}
print(str(dict))

# ord() 函数是 chr() 函数（对于8位的ASCII字符串）或 unichr() 函数（对于Unicode对象）的配对函数，它以一个字符（长度为1的字符串）作为参数，返回对应的 ASCII 数值，或者 Unicode 数值
print(ord('a'))
print(ord('你'))

# bin() 返回一个整数 int 或者长整数 long int 的二进制表示。
print(bin(10))


c = eval("a+b", {"a": 12, "b": 22})
print(c)


a = sum([1, 2, 3, 4, 5])  # 列表
b = sum((1, 2, 3, 4, 5))  # 元组
print(a == b)


# dir() 函数不带参数时，返回当前范围内的变量、方法和定义的类型列表；带参数时，返回参数的属性、方法列表。
print(dir())
print(dir([]))
print(dir(""))


# locals() 函数会以字典类型返回当前位置的全部局部变量。
print(locals())

aTuple = (123, 'runoob', 'google', 'abc', 'abc')
print(len(aTuple))
fList = frozenset(aTuple)
print(len(fList) == 4)

#  id() 函数用于获取对象的内存地址。
print(id(fList))
print(id(aTuple))
