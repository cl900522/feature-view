
class Clazz:
    objects = "started"

    # 类方法
    def do():
        print("clazz is doing")

    # 实例的方法
    def cover(self):
        self.objects = "mine"


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
