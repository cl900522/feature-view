#!python


scope = vars()
x = 55
y="Happy"
print(scope['x'])
scope["x"] += 1
print(x)
print(scope)


def fun1(x):
    print(x+globals()['x'])

fun1(22)


def fun2():
    'by defalut x is local variable'
    x = 22
    x += 10

fun2()
print(x)


def fun3():
    global x
    x += 10

fun3()
print(x)
