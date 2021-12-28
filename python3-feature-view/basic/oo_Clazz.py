
class Clazz:
    objects = "started"

    def __init__(self):
        self._x = 0
        self._y = 0
        self._voltage = 9999999

    # 类方法
    def do():
        print("clazz is doing")

    # 实例的方法
    def cover(self):
        self.objects = "mine"

    def getx(self):
        return self._x

    def setx(self, value):
        self._x = value

    def delx(self):
        print("del _x")
        del self._x

    x = property(getx, setx, delx, "I'm the 'x' property.")

    @property
    def y(self):
        """I'm the 'x' property."""
        return self._y

    @y.setter
    def y(self, value):
        self._y = value

    @y.deleter
    def y(self):
        print("del _y")
        del self._y

    # @property 函数用作装饰器可以很方便的创建只读属性：
    @property
    def voltage(self):
        """Get the current voltage."""
        return self._voltage


Clazz.do()

c = Clazz()
print("c.objects ->", c.objects)
print("Clazz.objects ->", Clazz.objects)

c.cover()
Clazz.objects = "clazz objects"

print("c.objects ->", c.objects)
print("Clazz.objects ->", Clazz.objects)

# error happen 因为do方法不是对象，而是类的方法
try:
    c.do()
except Exception as e:
    print("c.do() 调用不合法")

print(type(c))
print(isinstance(c, Clazz))


c.x = 1
c.y = 2
print(c.x)
print(c.y)
print(c.voltage)

del c.x
del c.y


setattr(c, "x", 99)
setattr(c, "y", 100)
print(getattr(c, "x"))
print(getattr(c, "y"))
