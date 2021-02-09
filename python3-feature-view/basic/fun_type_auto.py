
# int(x [,base ])         将x转换为一个整数
print("int('2',base=20)=", int("22", base=20))
print("int('2',base=10)=", int("22", base=10))

# float(x)  # 将x转换到一个浮点数
print('float("332.5123")=', float("332.5123"))

# complex(real[, imag])  # 创建一个复数
print(complex(1, 1))
print(complex(1, 1)*complex(1, -1))

# str(x)  # 将对象 x 转换为字符串
# repr(x)  # 将对象 x 转换为表达式字符串
print(str("Hello\n"))
print(repr("Hello\n"))

# eval(str)  # 用来计算在字符串中的有效Python表达式,并返回一个对象
eval("print('Eval Value')")

a = range(1, 20)
# tuple(s)  # 将序列 s 转换为一个元组
a = tuple(a)
print(a)

# list(s)  # 将序列 s 转换为一个列表
a = list(a)
print(a)

# chr(x)  # 将一个整数转换为一个字符
print(chr(98))

# unichr(x)  # 将一个整数转换为Unicode字符

# ord(x)  # 将一个字符转换为它的整数值
print(ord("a"))

# hex(x)  # 将一个整数转换为一个十六进制字符串

# oct(x)  # 将一个整数转换为一个八进制字符串
