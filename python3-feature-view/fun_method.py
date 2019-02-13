#!python


def speak(name, title="Mr"):
    'speak method doc'
    print("Hello, %s %s" % (title, name))


print("Doc for fun: %s" % speak.__doc__)
print(callable(speak))
speak(name="John")
speak(name="Lily", title="Miss")

param = {"name": "Mike"}
speak(**param)
param = {"name": "Kate", "title": "Mrs"}
speak(**param)

param = ('Wukong',)
speak(*param)
param = ("Hua", "Miss")
speak(*param)


def speak1(*param):
    print(param)

def speak2(**param):
    print(param)

speak1("p1","p2", "p3")
speak2(p1="p1", p2="p2", p3="p3")


def multi(a):
    '闭包'
    def cal(b):
        return a*b
    return cal

triple = multi(3)
print(triple(2))

multi5 = multi(5)
print(multi5(2))
    
