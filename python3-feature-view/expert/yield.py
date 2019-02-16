
'''def fun():
    for i in range(0,20):
        x = yield i
        print('good', x)


if __name__ == '__main__':
    a = fun()
    a.__next__()
    x = a.send(5)
    x = a.send(8)
    x = a.send(12)
    x = a.send(3)
    print(x)
'''


def fun():
    for i in range(20):
        x = yield i
        print('good', x)


if __name__ == '__main__':
    a = fun()
    for i in range(30):
        x = a.__next__()
        print(x)
