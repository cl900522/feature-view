
import builtins


class BaseMeta(type):
    def __new__(cls, name, bases, body):
        print("cls->%s, name->%s, body->%s" % (cls, name, body))
        return super().__new__(cls, name, bases, body)


class Basic(metaclass=BaseMeta):
    def hasLeg(self):
        return True


'''
old_bc = __build_class__

def my_bc(fun, name, base=None, **kw):
    print("Bc param is->", fun, name, base)

    if base is None:
        return old_bc(fun, name, base, **kw)

    if base is Basic:
        print('Check if bar method is defined')
    else:
        return old_bc(fun, name, base, **kw)

builtins.__build_class__ == my_bc
'''
